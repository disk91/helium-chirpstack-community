declare module "vue/types/transaction" {
    interface TransactionListRespItf {
        transactionUUID : string,
        type : number,
        createAt : BigInt,
        dcs : BigInt,
        tenantName : string,
        cost : number
    }
}