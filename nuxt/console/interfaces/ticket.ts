declare module "vue/types/ticket" {
    interface TicketListRespItf {
        ticketUUID : string,
        topic : string,
        details : string,
        status : bigint,
        createdAt : bigint,
    }
    interface TicketCreationReqItf {
        topic : string,
        details : string,
    }
    interface TicketDetailResponseItf {
        content : string,
        createdAt : bigint,
        adminReponse : boolean,
    }
    interface TicketDetailRespItf {
        ticketUUID : string,
        owner : string,
        topic : string,
        details : string,
        status : number,
        createdAt : bigint,
        closedAt : bigint,
        responses : TicketDetailResponseItf[]
    }
    interface TicketResponseReqItf {
        ticketUUID : string,
        content : string,
        closing : boolean,
    }
    interface TicketCountRespItf {
        pending: number,
    }
}