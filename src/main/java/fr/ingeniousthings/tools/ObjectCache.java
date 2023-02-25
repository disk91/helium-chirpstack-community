/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2020.
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 *    and associated documentation files (the "Software"), to deal in the Software without restriction,
 *    including without limitation the rights to use, copy, modify, merge, publish, distribute,
 *    sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all copies or
 *    substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *    OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package fr.ingeniousthings.tools;

import io.micrometer.core.instrument.MeterRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

public abstract class ObjectCache<K, T> {

    protected class CachedObject<K, T> {
        protected T obj;
        protected K key;
        protected long lastAccessTime; // ms
        protected int score;
        protected boolean updated;
        protected long expirationTime;

        //  ---


        public T getObj() {
            return obj;
        }

        public void setObj(T obj) {
            this.obj = obj;
        }

        public long getLastAccessTime() {
            return lastAccessTime;
        }

        public void setLastAccessTime(long lastAccessTime) {
            this.lastAccessTime = lastAccessTime;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public boolean isUpdated() {
            return updated;
        }

        public void setUpdated(boolean updated) {
            this.updated = updated;
        }

        public K getKey() {
            return key;
        }

        public void setKey(K key) {
            this.key = key;
        }

        public long getExpirationTime() {
            return expirationTime;
        }

        public void setExpirationTime(long expirationTime) {
            this.expirationTime = expirationTime;
        }

        public boolean isExpired() {
            return (Now.NowUtcMs() > this.expirationTime);
        }
    }
    protected HashMap<K, CachedObject<K,T>> cache;
    protected int maxCacheSize;
    protected int cacheSize;
    protected long cacheMissStat;

    // Total time in the cache put & get functions + GC in NS
    protected long totalCacheTime;
    protected long totalCacheTry;

    // Last Garbage collection call duration
    protected long lastGCDurationMs;

    // Last time the garbage collection has been executed
    protected long lastGCMs;

    protected String name;


    // Set the max live of an element, will be regularly checked
    protected long expirationMs;
    /**
     * Init the cache system
     * @param maxSize
     */
    public ObjectCache (String name, int maxSize) {
        this(name,maxSize,-1);
    }

    public ObjectCache (String name, int maxSize, long expirationMs ) {
        this.cache = new HashMap<K,CachedObject<K,T>>(256,0.8f);
        this.maxCacheSize = maxSize;
        this.cacheMissStat = 0;
        this.totalCacheTime = 0;
        this.totalCacheTry = 0;
        this.cacheSize = 0;
        this.lastGCDurationMs = 0;
        this.lastGCMs = 0;
        this.name = name;
    }


    /**
     * This class defines what to do when an object is modified and removed from cache
     * Non modified objects are not concerned.
     * @param obj
     * @param key
     */
    public abstract void onCacheRemoval(K key,T obj);

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public T get(K key) {
        long start = Now.NanoTime();
        CachedObject<K,T> c = this.cache.get(key);
        if ( c != null ) {
            long now = Now.NowUtcMs();
            long cachedPeriod = now - c.getLastAccessTime();
            if ( c.getScore() < 1000 ) {
                if (cachedPeriod < 100) c.setScore(c.getScore() + 100);
                else if (cachedPeriod < 1_000) c.setScore(c.getScore() + 10);
                else if (cachedPeriod < 10_000) c.setScore(c.getScore() + 1);
            }
            if ( c.getScore() > -1000 ) {
                if (cachedPeriod > Now.ONE_HOUR) c.setScore(c.getScore() - 100);
                else if (cachedPeriod > 300_000) c.setScore(c.getScore() - 10);
                else if (cachedPeriod > 30_000) c.setScore(c.getScore() - 1);
            }
            c.setLastAccessTime(now);
        } else {
            this.cacheMissStat++;
        }
        this.totalCacheTry++;
        this.totalCacheTime+=(Now.NanoTime()-start);
        log.debug("get duration "+(Now.NanoTime()-start)+"ns");
        return (c!=null)?c.getObj():null;
    }

    /**
     * Add an element into the cache if we still have some place in cache,
     * call cache cleaner if not anyMore place
     * @param obj
     * @param key
     * @return
     */
    public boolean put(T obj, K key) {
        long start = Now.NanoTime();
        boolean ret = false;
        long now = Now.NowUtcMs();
        CachedObject<K,T> c = this.cache.get(key);
        if ( c != null ) {
            // no change, we just want to notice that an update inside the object has been made
            if ( c == obj ) {
                c.setUpdated(true);
                c.setLastAccessTime(now);
                ret = true;
            }
            // replace object if not updated in cache, otherwise we have a problem
            if ( c != obj && !c.isUpdated() ) {
                c.setObj(obj);
                c.setLastAccessTime(now);
                ret = true;
            }
        } else {
            // new entry
            if ( this.cacheSize >= this.maxCacheSize ) {
                this.cleanCache();
            }
            c = new CachedObject<K,T>();
            c.setObj(obj);
            c.setLastAccessTime(now);
            c.setScore(0);
            c.setUpdated(false);
            c.setKey(key);
            if (expirationMs>0) {
                c.setExpirationTime(now+expirationMs);
            } else c.setExpirationTime(-1);
            this.cache.put(key,c);
            this.cacheSize++;
            ret = true;
        }
        this.totalCacheTry++;
        this.totalCacheTime+=(Now.NanoTime()-start);
        log.debug("put duration "+(Now.NanoTime()-start)+"ns");
        return ret;
    }

    /**
     * Remove a element from the cache
     * @param key
     * @param callAction - true when you want to call the flush action on removal if modified
     */
    public void remove(K key, boolean callAction) {
        CachedObject<K,T> c = this.cache.get(key);
        if  ( c != null ) {
            if ( c.isUpdated() && callAction ) {
                this.onCacheRemoval(key,c.getObj());
            }
            this.cache.remove(key);
            this.cacheSize--;
        }
    }

    /**
     * Clear unused element in cache to make space for new elements
     * Target is to clean 10% of cache as a minimum
     */
    protected void cleanCache() {
        long start = Now.NanoTime();
        long now = Now.NowUtcMs();
        int toRemove = (this.maxCacheSize * 10) / 100;
        this.lastGCMs = now;

        int [] countValues = new int[1900];
        ArrayList<K> keysToBeRemoved = new ArrayList<K>();
        ArrayList<K> keysToBeUpdated = new ArrayList<K>();
        for ( int i = 0 ; i < 1900 ; i++ ) countValues[i] = 0;

        for (CachedObject<K,T> c : this.cache.values() ) {
            if ( c.getScore() >= -900 ){
                if ( c.getScore() >= 1000 ) {
                    countValues[1899]++;
                } else {
                    countValues[c.getScore() + 900]++;
                }
            } else {
                keysToBeRemoved.add(c.getKey());
                if ( c.isUpdated() ) keysToBeUpdated.add(c.getKey());
                toRemove--;
            }
        }
        if ( toRemove > 0 ) {
            int minScore = -900;
            while (toRemove > 0 && minScore < 1000) {
                toRemove -= countValues[minScore+900];
                minScore++;
            }
            for (CachedObject<K,T> c : this.cache.values() ) {
                if (c.getScore() <= minScore) {
                    keysToBeRemoved.add(c.getKey());
                    if ( c.isUpdated() ) keysToBeUpdated.add(c.getKey());
                }
            }
        }

        // Update action on object before removal
        for ( K key : keysToBeUpdated ) {
            CachedObject<K,T> o = this.cache.get(key);
            if ( o != null ) onCacheRemoval(key,o.getObj());
        }

        // clear entries
        for ( K key : keysToBeRemoved ) {
            this.cache.remove(key);
            this.cacheSize--;
        }

        // Update stats
        this.lastGCDurationMs = (Now.NanoTime() - start)/1000;
    }

    // returns %age of cache usage
    public int cacheUsage() {
        return ((100*this.cacheSize) / this.maxCacheSize);
    }

    // Before clearing the cache, we want to sync the modifications
    // or just to make it on regular basis, expired object are also removed
    public void flush() {
        ArrayList<K> toRemove = new ArrayList<K>();
        for (CachedObject<K,T> c : this.cache.values() ) {
            boolean expired = (c.expirationTime >  0 && c.expirationTime < Now.NowUtcMs() );
            if ( c.isUpdated() || expired ) {
                onCacheRemoval(c.getKey(),c.getObj());
                c.setUpdated(false);
            }
            if ( expired ) toRemove.add(c.getKey());
        }
        for ( K key : toRemove ) {
            this.cache.remove(key);
            this.cacheSize--;
        }
    }

    // Some logs
    public void log() {
        log.info("---------- Cache log (" + this.name + ") -------------");
        log.info("-- Size    " + Math.floor(100.0 * this.cacheSize / this.maxCacheSize) + "% "+ this.cacheSize + " / " + this.maxCacheSize);
        log.info("-- Miss    " + ((totalCacheTry>0)?Math.floor(100.0 * this.cacheMissStat / this.totalCacheTry):"NA") + "% " + this.cacheMissStat + " / " + this.totalCacheTry);
        log.info("-- Avg Tm  " + ((totalCacheTry>0)?Math.floor(this.totalCacheTime / (double)this.totalCacheTry):"NA") + "ns average");
        if (this.lastGCMs > 0) {
            log.info("-- GC      " + (Now.NowUtcMs() - this.lastGCMs) / (60_000) + "m ago, duration " + this.lastGCDurationMs + "ms");
        } else {
            log.info("-- GC      NEVER" );
        }
        log.info("--------------------------------------------------------------");
    }

    // Prometheus providers
    public Supplier<Number> getCacheMissStat() {
        return ()->cacheMissStat;
    }

    public Supplier<Number>  getTotalCacheTime() {
        return ()->totalCacheTime;
    }

    public Supplier<Number>  getTotalCacheTry() {
        return ()->totalCacheTry;
    }


}
