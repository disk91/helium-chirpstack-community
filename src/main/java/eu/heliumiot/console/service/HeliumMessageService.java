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

import eu.heliumiot.console.api.interfaces.MessageItf;
import eu.heliumiot.console.api.interfaces.MessagePendingRespItf;
import eu.heliumiot.console.jpa.db.HeliumMessages;
import eu.heliumiot.console.jpa.repository.HeliumMessageRepository;
import fr.ingeniousthings.tools.ITParseException;
import fr.ingeniousthings.tools.ITRightException;
import fr.ingeniousthings.tools.Now;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HeliumMessageService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final int HELIUM_MESSAGE_TYPE_MODAL = 1;       // modal for full screen display
    public static final int HELIUM_MESSAGE_TYPE_LTOATS = 2;      // Large Toaster display

    public static final int HELIUM_MESSAGE_FRONT_PAGE = 3;       // message printed on front page before login

    public static final int HELIUM_MESSAGE_CATEGORY_INFO = 0;       // type of display
    public static final int HELIUM_MESSAGE_CATEGORY_WARNING = 1;
    public static final int HELIUM_MESSAGE_CATEGORY_DANGER = 2;

    @Autowired
    protected HeliumMessageRepository heliumMessageRepository;

    @Autowired
    protected UserCacheService userCacheService;

    public List<MessagePendingRespItf> getHeliumMessageFrontPage() {
        ArrayList<MessagePendingRespItf> r = new ArrayList<>();
        HeliumMessages hm = heliumMessageRepository.findFirstHeliumMessagesByUntilGreaterThanAndTypeOrderByIndexDesc(
                Now.NowUtcMs(),
                HELIUM_MESSAGE_FRONT_PAGE
        );

        if ( hm != null ) {
            MessagePendingRespItf i = new MessagePendingRespItf();
            i.setType(hm.getType());
            i.setCategory(hm.getCategory());
            i.setContent(hm.getContent());
            r.add(i);
        }

        return r;
    }


    public List<MessagePendingRespItf> getHeliumMessageForUser(String user)
    throws ITRightException {

        UserCacheService.UserCacheElement u = userCacheService.getUserById(user);
        if ( u == null ) throw new ITRightException();

        ArrayList<MessagePendingRespItf> r = new ArrayList<>();
        HeliumMessages hm = heliumMessageRepository.findFirstHeliumMessagesByIndexGreaterThanAndUserMessOrderByIndexDesc(
                u.heliumUser.getLastMessSeen(),
                true
        );

        if ( hm == null ) {
            // try to see if we have an until without onlyOne to be displayed again
            hm = heliumMessageRepository.findFirstHeliumMessagesByUntilGreaterThanAndOnlyoneAndUserMessOrderByUntilAsc(
                    Now.NowUtcMs(),
                    false,
                    true
            );
        }

        if ( hm != null && hm.getUntil() > 0 && hm.getUntil() > Now.NowUtcMs() ) {
            MessagePendingRespItf i = new MessagePendingRespItf();
            i.setType(hm.getType());
            i.setCategory(hm.getCategory());
            i.setContent(hm.getContent());
            r.add(i);
        }

        return r;
    }

    public void ackHeliumMessageForUser(String user)
    throws ITRightException {

        UserCacheService.UserCacheElement u = userCacheService.getUserById(user);
        if ( u == null ) throw new ITRightException();

        // update User Last Seen
        u.heliumUser.setLastMessSeen(Now.NowUtcMs());
        userCacheService.updateHeliumUser(u);

    }

    // =========================================================
    // admin crud
    // =========================================================

    /**
     * Create a new message instance
     * @param m
     * @return
     * @throws ITParseException
     */
    public MessageItf createMessage(MessageItf m)
    throws ITParseException {
        if ( m.getCategory() < HELIUM_MESSAGE_CATEGORY_INFO || m.getCategory() > HELIUM_MESSAGE_CATEGORY_DANGER )
            throw new ITParseException("err_category");

        if ( m.getType() < HELIUM_MESSAGE_TYPE_MODAL || m.getType() > HELIUM_MESSAGE_FRONT_PAGE )
            throw new ITParseException("err_type");

        if ( m.getContent().length() == 0 ) throw new ITParseException("err_content");
        if ( m.getUntil() < Now.NowUtcMs() && m.getUntil() > 0 ) throw new ITParseException("err_until");

        HeliumMessages hm = new HeliumMessages();
        hm.setIndex(Now.NowUtcMs());
        hm.setCategory(m.getCategory());
        hm.setContent(m.getContent());
        hm.setType(m.getType());
        hm.setUntil(m.getUntil());
        hm.setOnlyone(m.isOnlyone());
        if ( m.getType() == HELIUM_MESSAGE_TYPE_MODAL || m.getType() == HELIUM_MESSAGE_TYPE_LTOATS ) {
            hm.setUserMess(true);
        }
        if ( m.getType() == HELIUM_MESSAGE_FRONT_PAGE ) {
            hm.setUserMess(false);
        }
        hm = this.heliumMessageRepository.save(hm);

        m.setId(hm.getId().toString());
        m.setIndex(hm.getIndex());
        return m;
    }

    /**
     * Update one of the message instance
     * @param m
     * @return
     * @throws ITParseException
     */
    public MessageItf updateMessage(MessageItf m)
            throws ITParseException {

        HeliumMessages hm = this.heliumMessageRepository.findOneHeliumMessageById(UUID.fromString(m.getId()));
        if ( hm == null ) throw new ITParseException("err_id");

        if ( hm.getIndex() != m.getIndex() ) throw new ITParseException("err_index");

        if ( m.getCategory() < HELIUM_MESSAGE_CATEGORY_INFO || m.getCategory() > HELIUM_MESSAGE_CATEGORY_DANGER )
            throw new ITParseException("err_category");

        if ( m.getType() < HELIUM_MESSAGE_TYPE_MODAL || m.getType() > HELIUM_MESSAGE_FRONT_PAGE )
            throw new ITParseException("err_type");

        if ( m.getContent().length() == 0 ) throw new ITParseException("err_content");
        if ( m.getUntil() < Now.NowUtcMs() && m.getUntil() > 0 ) throw new ITParseException("err_until");

        hm.setCategory(m.getCategory());
        hm.setContent(m.getContent());
        hm.setType(m.getType());
        hm.setUntil(m.getUntil());
        hm.setOnlyone(m.isOnlyone());
        if ( m.getType() == HELIUM_MESSAGE_TYPE_MODAL || m.getType() == HELIUM_MESSAGE_TYPE_LTOATS ) {
            hm.setUserMess(true);
        }
        if ( m.getType() == HELIUM_MESSAGE_FRONT_PAGE ) {
            hm.setUserMess(false);
        }
        hm = this.heliumMessageRepository.save(hm);

        return m;
    }

    /**
     * Delete Message instance
     * @param messageId
     * @throws ITParseException
     */
    public void deleteMessage(String messageId)
            throws ITParseException {

        HeliumMessages hm = this.heliumMessageRepository.findOneHeliumMessageById(UUID.fromString(messageId));
        if ( hm == null ) throw new ITParseException("err_id");

        this.heliumMessageRepository.delete(hm);
    }


    /**
     * Return the list of messages
     * @param limit
     * @return
     */
    public List<MessageItf> getLastMessages(int limit) {
        ArrayList<MessageItf> r = new ArrayList<>();
        if ( limit == 0 ) limit = 5;
        if ( limit > 100 ) limit = 100;

        List<HeliumMessages> hms = this.heliumMessageRepository.findLastHeliumMessages(limit);
        if ( hms != null ) {
            for ( HeliumMessages hm : hms ) {
                MessageItf m = new MessageItf();
                m.setId(hm.getId().toString());
                m.setIndex(hm.getIndex());
                m.setCategory(hm.getCategory());
                m.setContent(hm.getContent());
                m.setType(hm.getType());
                m.setUntil(hm.getUntil());
                m.setOnlyone(hm.isOnlyone());
                r.add(m);
            }
        }
        return r;
    }


}
