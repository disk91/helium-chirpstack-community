declare module "vue/types/transaction" {
    interface TransactionListRespItf {
        transactionUUID : string,
        type : number,
        createAt : BigInt,
        dcs : BigInt,
        tenantName : string,
        cost : number
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