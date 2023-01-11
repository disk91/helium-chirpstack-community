declare module "vue/types/transaction" {
    interface TransactionListRespItf {
        transactionUUID : string,
        type : number,
        createAt : BigInt,
        dcs : BigInt,
        tenantName : string,
        cost : number,
        status : string
    }
    interface TransactionStripeReqItf {
        dcs : bigint,
        tenantUUID : string,
        cost : number,
    }
    interface TransactionStripeRespItf {
        stripePublicKey : string,
        stripeTxSecret : string,
        transactionUUID : string,
    }
}