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
package eu.heliumiot.console.service;

import eu.heliumiot.console.api.interfaces.MessagePendingRespItf;
import eu.heliumiot.console.jpa.db.HeliumMessages;
import eu.heliumiot.console.jpa.repository.HeliumMessageRepository;
import fr.ingeniousthings.tools.Now;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HeliumMessageService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final int HELIUM_MESSAGE_TYPE_MODAL = 1;       // modal for full screen display
    public static final int HELIUM_MESSAGE_TYPE_LTOATS = 2;      // Large Toaster display

    public static final int HELIUM_MESSAGE_CATEGORY_INFO = 0;       // type of display
    public static final int HELIUM_MESSAGE_CATEGORY_WARNING = 1;
    public static final int HELIUM_MESSAGE_CATEGORY_DANGER = 2;

    @Autowired
    protected HeliumMessageRepository heliumMessageRepository;

    @Autowired
    protected UserCacheService userCacheService;

    public List<MessagePendingRespItf> getHeliumMessageForUser(String user) {

        UserCacheService.UserCacheElement u = userCacheService.getUserById(user);
        ArrayList<MessagePendingRespItf> r = new ArrayList<>();
        HeliumMessages hm = heliumMessageRepository.findFirstHeliumMessagesByIndexGreaterThanOrderByIndexDesc(
            u.heliumUser.getLastMessSeen()
        );

        if ( hm != null && hm.getUntil() > 0 && hm.getUntil() > Now.NowUtcMs() ) {
            MessagePendingRespItf i = new MessagePendingRespItf();
            i.setType(hm.getType());
            i.setCategory(hm.getCategory());
            i.setContent(hm.getContent());
            r.add(i);
        }

        return r;
    }

    public void ackHeliumMessageForUser(String user) {

        UserCacheService.UserCacheElement u = userCacheService.getUserById(user);
        // update User Last Seen
        u.heliumUser.setLastMessSeen(Now.NowUtcMs());
        userCacheService.updateHeliumUser(u);

    }

}
