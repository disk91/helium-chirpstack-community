declare module "vue/types/tenantSearch" {
    interface TenantSearchResponse {
        tenantUUID : string,
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
}