declare module "vue/types/tenantSearch" {
    interface TenantSearchResponse {
        tenantUUID : string,
        routeId: string,
        ownerEmail : string,
        tenantName : string,
        dcBalance : bigint
    }
    interface TenantDcBalanceReqItf {
        tenantUUID : string,
        dcs : bigint
    }
    interface TenantDcBalancesReqItf {
        tenantName : string,
        tenantUUID : string,
        dcBalance : bigint,
        minBalance : bigint
    }
    interface TenantDcTransferReqItf {
        tenantSrcUUID : string,
        tenantDestUUID : string,
        dcs : BigInt
    } 
    interface TenantDcTransferRespItf {
        transcationUUID : string,
        dcs : BigInt
    } 
    interface TenantSetupRespItf {
        tenantUUID : string,
        dcBalanceStop : number,
        dcPrice : number,
        dcMin : number
    }
    interface TenantApiKeyRespItf {
        tenantUUID : string,
        tenantApiKey : string
    }
}