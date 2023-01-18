declare module "vue/types/ticket" {
    interface TicketListRespItf {
        ticketUUID : string,
        topic : string,
        details : string,
        status : bigint,
        createdAt : bigint,
    }
}