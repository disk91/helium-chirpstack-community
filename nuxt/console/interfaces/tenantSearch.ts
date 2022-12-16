declare module "vue/types/tenantSearch" {
    interface TenantSearchResponse {
        tenantUUID : string,
        ownerEmail : string,
        tenantName : string,
        dcBalance : bigint
    }
}