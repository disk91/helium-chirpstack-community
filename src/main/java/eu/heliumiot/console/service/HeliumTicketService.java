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

import eu.heliumiot.console.ConsoleConfig;
import eu.heliumiot.console.api.interfaces.*;
import eu.heliumiot.console.jpa.db.HeliumTicket;
import eu.heliumiot.console.jpa.db.HeliumTicketResponse;
import eu.heliumiot.console.jpa.repository.HeliumTicketRepository;
import eu.heliumiot.console.jpa.repository.HeliumTicketResponseRepository;
import eu.heliumiot.console.tools.ExecuteEmail;
import fr.ingeniousthings.tools.ITParseException;
import fr.ingeniousthings.tools.ITRightException;
import fr.ingeniousthings.tools.Now;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class HeliumTicketService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public static final int HELIUM_TICKET_STATUS_OPEN  = 1;      // ticket is open
    public static final int HELIUM_TICKET_STATUS_RESPP = 2;      // response pending ( from user )
    public static final int HELIUM_TICKET_STATUS_CLOSE = 3;      // ticket is closed

    @Autowired
    protected HeliumTicketRepository heliumTicketRepository;

    @Autowired
    protected HeliumTicketResponseRepository heliumTicketResponseRepository;

    @Autowired
    protected UserCacheService userCacheService;

    @Autowired
    protected ExecuteEmail executeEmail;

    @Autowired
    protected ConsoleConfig consoleConfig;

    // create a new ticket
    public void createTicket(String userUUID, TicketCreationReqItf tc)
    throws ITParseException, ITRightException {

        // anti spam
        List<HeliumTicket> ts = heliumTicketRepository.findHeliumTicketByUserUUIDAndStatusOrderByCreatedAtDesc(
                UUID.fromString(userUUID), HELIUM_TICKET_STATUS_OPEN
        );
        if ( ts != null && ts.size() > 10 ) throw new ITRightException("ticket_too_many");

        ts = heliumTicketRepository.findHeliumTicketByUserUUIDAndStatusOrderByCreatedAtDesc(
                UUID.fromString(userUUID), HELIUM_TICKET_STATUS_RESPP
        );
        if ( ts != null && ts.size() > 10 ) throw new ITRightException("ticket_too_many");

        // create ticket
        HeliumTicket t = new HeliumTicket();
        t.setClosedAt(new Timestamp(0));
        t.setCreatedAt(new Timestamp(Now.NowUtcMs()));
        t.setUserUUID(UUID.fromString(userUUID));
        t.setStatus(HELIUM_TICKET_STATUS_OPEN);
        t.setTopic(tc.getTopic());
        t.setDetail(tc.getDetails());

        heliumTicketRepository.save(t);

        // fire an email
        executeEmail.execute(
                consoleConfig.getHeliumMailFrom(),
                "A new service request has been created with topic :"+tc.getTopic(),
                "["+consoleConfig.getHeliumConsoleName()+"] New Service Request",
                consoleConfig.getHeliumMailFrom()
        );
    }

    // List user ticket
    public List<TicketListRespItf> getUserTickets(String userUUID)
    throws ITRightException {

        // Admin have a different path to get all pending
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userUUID);
        if (user == null) throw new ITRightException();
        if ( user.user.isAdmin() ) return getAdminTickets();

        ArrayList<TicketListRespItf> rs = new ArrayList<>();
        List<HeliumTicket> ts = heliumTicketRepository.findHeliumTicketByUserUUIDOrderByCreatedAtDesc(
                UUID.fromString(userUUID)
        );
        if ( ts != null ) {
            for ( HeliumTicket t : ts ) {
                TicketListRespItf r = new TicketListRespItf();
                // do not report a ticket closed 100days ago
                if ( t.getStatus() != HELIUM_TICKET_STATUS_CLOSE || t.getClosedAt().getTime() > (Now.NowUtcMs()-100*Now.ONE_FULL_DAY) ) {
                    r.setTicketUUID(t.getId().toString());
                    r.setStatus(t.getStatus());
                    r.setCreatedAt(t.getCreatedAt().getTime());
                    r.setTopic(t.getTopic());
                    r.setDetails(t.getDetail());
                    rs.add(r);
                }
            }
        }
        return rs;
    }


    public TicketCountRespItf countUserTickets(String userUUID)
        throws ITRightException {

        TicketCountRespItf r = new TicketCountRespItf();
        r.setPending(0);

        // Admin have a different path to get all pending
        UserCacheService.UserCacheElement user = userCacheService.getUserById(userUUID);
        if (user == null) throw new ITRightException();
        if ( user.user.isAdmin() ) {
            r.setPending(getAdminTickets().size());
        } else {
            List<HeliumTicket> ts = heliumTicketRepository.findHeliumTicketByUserUUIDAndStatusOrderByCreatedAtDesc(
                UUID.fromString(userUUID),
                HELIUM_TICKET_STATUS_RESPP
            );
            if (ts != null) {
                r.setPending(ts.size());
            }
        }
        return r;
    }


    // List admin ticket
    public List<TicketListRespItf> getAdminTickets() {
        List<HeliumTicket> ts = heliumTicketRepository.findHeliumTicketByStatusOrderByCreatedAtDesc(
                HELIUM_TICKET_STATUS_OPEN
        );
        ArrayList<TicketListRespItf> rs = new ArrayList<>();
        if ( ts != null ) {
            for ( HeliumTicket t : ts ) {
                TicketListRespItf r = new TicketListRespItf();
                r.setTicketUUID(t.getId().toString());
                r.setStatus(t.getStatus());
                r.setCreatedAt(t.getCreatedAt().getTime());
                r.setTopic(t.getTopic());
                r.setDetails(t.getDetail());
                rs.add(r);
            }
        }
        return rs;
    }

    // add a response and eventually close the discussion
    public void addResponse(String userUUID, TicketResponseReqItf tr)
    throws ITParseException, ITRightException {

        UserCacheService.UserCacheElement user = userCacheService.getUserById(userUUID);
        if (user == null) throw new ITRightException();

        HeliumTicket t = heliumTicketRepository.findOneHeliumTicketById(UUID.fromString(tr.getTicketUUID()));
        if ( t == null ) throw new ITRightException("not_found");

        if ( tr.getContent() == null ) tr.setContent("");
        if ( tr.getContent().length() == 0 && tr.isClosing() == false ) throw  new ITParseException("empty");

        if ( user.user.isAdmin() || t.getUserUUID().toString().compareToIgnoreCase(userUUID) == 0) {
            HeliumTicketResponse r = new HeliumTicketResponse();
            r.setTicketUUID(UUID.fromString(tr.getTicketUUID()));
            r.setCreatedAt(new Timestamp(Now.NowUtcMs()));
            r.setAdmin(user.user.isAdmin());
            r.setContent(tr.getContent());
            // update ticket status
            if ( tr.isClosing() ) {
                t.setStatus(HELIUM_TICKET_STATUS_CLOSE);
                t.setClosedAt(new Timestamp(Now.NowUtcMs()));
            } else {
                if (user.user.isAdmin()) {
                    t.setStatus(HELIUM_TICKET_STATUS_RESPP);
                } else {
                    t.setStatus(HELIUM_TICKET_STATUS_OPEN);
                }
            }
            if ( r.getContent().length() > 0 ) {
                heliumTicketResponseRepository.save(r);
            }
            heliumTicketRepository.save(t);

        } else throw new ITRightException("not_authorized");

    }


    public TicketDetailRespItf getTicketDetails(String userUUID, String ticketUUID)
    throws ITRightException {

        UserCacheService.UserCacheElement user = userCacheService.getUserById(userUUID);
        if (user == null) throw new ITRightException();

        HeliumTicket t = heliumTicketRepository.findOneHeliumTicketById(UUID.fromString(ticketUUID));
        if (t == null) throw new ITRightException("not_found");

        if (user.user.isAdmin() || t.getUserUUID().toString().compareToIgnoreCase(userUUID) == 0) {

            TicketDetailRespItf r = new TicketDetailRespItf();
            r.setTicketUUID(t.getId().toString());
            r.setCreatedAt(t.getCreatedAt().getTime());
            r.setClosedAt(t.getClosedAt().getTime());
            r.setStatus(t.getStatus());
            r.setTopic(t.getTopic());
            r.setDetails(t.getDetail());
            if (t.getUserUUID() != user.user.getId()) {
                UserCacheService.UserCacheElement owner = userCacheService.getUserById(t.getUserUUID().toString());
                r.setOwner(owner.user.getEmail());
            } else {
                r.setOwner(user.user.getEmail());
            }

            ArrayList<TicketDetailResponseItf> rrs = new ArrayList<>();
            List<HeliumTicketResponse> trs = heliumTicketResponseRepository.findHeliumTicketByTicketUUIDOrderByCreatedAtAsc(t.getId());
            for (HeliumTicketResponse tr : trs) {
                TicketDetailResponseItf rr =  new TicketDetailResponseItf();
                rr.setAdminReponse(tr.isAdmin());
                rr.setContent(tr.getContent());
                rr.setCreatedAt(tr.getCreatedAt().getTime());
                rrs.add(rr);
            }
            r.setResponses(rrs);
            return r;

        } else throw new ITRightException("not authorized");
    }

}
