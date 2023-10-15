declare module "vue/types/tenantSetup" {
    interface TenantTemplate {
        tenantUUID : string,
        dcBalanceStop : bigint,
        freeTenantDc : bigint,
        dcPerJoinRequest : bigint,
        dcPerJoinAccept : bigint,
        maxJoinRequestDup : bigint,
        dcPer24BMessage : bigint,
        dcPer24BDownlink : bigint,
        dcPer24BDuplicate : bigint,
        dcPerDeviceInserted : bigint,
        inactivityBillingPeriodMs : bigint,
        dcPerInactivityPeriod : bigint,
        activityBillingPeriodMs : bigint,
        dcPerActivityPeriod : bigint,
        maxDcPerDevice: bigint,
        limitDcRatePeriodMs: bigint,
        limitDcRatePerDevice : bigint,
        dcPrice : number,
        dcMin : bigint,
        maxCopy : bigint,
        maxOwnedTenants : bigint,
        maxDevices : bigint,
        signupAllowed : boolean,
        id : string,
    }
    interface TenantSetupTemplateCouponReqItf {
        prefix : string,
        tenantUUID : string,
        maxUse : number,
        start : number,
        stop : number,
        toCreate : number,
        couponFor : string,
    }
    interface TenantSetupTemplateCouponRespItf {
        tenantUUID : string,
        couponID : string,
    }
    interface CouponListRespItf {
        tenantUUID : string,
        couponID : string,
        maxUse : number,
        inUse : number,
        start : number,
        stop : number,
        couponFor : string,
    }
}
