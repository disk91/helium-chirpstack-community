/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2024.
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

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Simple cache solution with a limit of size and limit of time with a clean on access
 * Not optimized for big cache, mostly made to make sure the cache does not grow under scaling
 * performance will be reduced when scaling.
 * potential improvement - when max size reached, remove the last accessed more than the oldest
 */
public class TinyCache<K,V> {

    private record ValueWithExpiry<K, V>(K key, V value, long expiryTime) {

        boolean isExpired() {
                return Now.NowUtcMs() > expiryTime;
            }
        }

    private final ConcurrentHashMap<K,ValueWithExpiry<K,V>> cache;
    private final int maxElements;
    private final long lifeTimeMs;
    private final long cleanRateMs;
    private long nextClean;

    public TinyCache(
        int _maxElements,
        long _lifeTimeMs,
        long _cleanRateMs
    ) {
        this.cache = new ConcurrentHashMap<>();
        this.lifeTimeMs = _lifeTimeMs;
        this.maxElements = _maxElements;
        this.cleanRateMs = _cleanRateMs;
        this.nextClean = Now.NowUtcMs()+this.cleanRateMs;
    }

    public int put(K key,V value) {
        if ( this.cache.size() >= this.maxElements || this.nextClean < Now.NowUtcMs() ) this.cleanCache();

        ValueWithExpiry<K,V> v = new ValueWithExpiry<>(key,value,Now.NowUtcMs()+this.lifeTimeMs);
        this.cache.put(key,v);
        return this.cache.size();
    }

    public V get(K key) {
        if ( this.nextClean < Now.NowUtcMs() ) this.cleanCache();

        ValueWithExpiry<K,V> v = this.cache.get(key);
        if ( v != null ) return v.value;
        return null;
    }

    synchronized private void cleanCache() {
        if ( this.cache.isEmpty() ) return;
        long now = Now.NowUtcMs();

        ArrayList<K> toRemove = new ArrayList<>();
        K older = null;
        long olderTm = now+this.lifeTimeMs;
        for ( ValueWithExpiry<K,V> v : this.cache.values() ) {
            if ( v.isExpired() ) toRemove.add(v.key);
            if ( olderTm > v.expiryTime ) {
                olderTm = v.expiryTime;
                older = v.key;
            }
        }
        for ( K key : toRemove ) cache.remove(key);
        if ( older != null && cache.size() > this.maxElements ) cache.remove(older);
        if ( this.nextClean <= now ) this.nextClean = now+this.cleanRateMs;
    }

}
