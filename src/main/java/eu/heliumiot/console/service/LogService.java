/*
 * Copyright (c) - Paul Pinault (aka disk91) - 2024.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 *    FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *    OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR
 *    IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package eu.heliumiot.console.service;

import eu.heliumiot.console.service.interfaces.LogEntry;
import fr.ingeniousthings.tools.DateConverters;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    Object privLogService = null;
    
    /**
     * LogService is a full version component only providing an API doing nothing in the community version
     * It provides inMemory Logs for debug & info level And Persistent logs for warning & error level
     * these logs can be displayed on the admin console. Public version just wrap logs to the console.
     */
    @PostConstruct
    private void initLogService() {
        log.info("Init initLogService");
        try {
            Class<?> clazz = Class.forName("eu.heliumiot.console.service.PrivLogService");
            privLogService = clazz.getDeclaredConstructor().newInstance();
        } catch (ClassNotFoundException e) {
            privLogService = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void log(LogEntry message) {
        if ( privLogService == null ) {
            switch(message.level()) {
                case DEBUG -> log.debug("[DEBUG] ({}) {} - {}", DateConverters.msToStringDate(message.timeMs()),message.module(),message.message());
                case INFO -> log.info("[INFO] ({}) {} - {}", DateConverters.msToStringDate(message.timeMs()),message.module(),message.message());
                case WARN -> log.warn("[WARN] ({}) {} - {}", DateConverters.msToStringDate(message.timeMs()),message.module(),message.message());
                case ERROR -> log.error("[ERROR] ({}) {} - {}", DateConverters.msToStringDate(message.timeMs()),message.module(),message.message());
            }
        }
        else {
            try {
                privLogService.getClass().getMethod("processLog", LogEntry.class).invoke(privLogService, message);
            } catch (Exception ignored) {}
        }
    }

}