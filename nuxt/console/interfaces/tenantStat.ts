declare module "vue/types/tenantStat" {
    interface TenantBasicStat {
        tenantUUID : string,
        tenantName : string,
        dcBalanceStop : bigint,
        freeTenantDc : bigint,
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
        maxOwnedTenants : bigint,
        maxDevices : bigint,
        day : bigint,
        duration : bigint,

        registrationDc : bigint,
        uplink : bigint,
        joinReq : bigint,
        uplinkDc : bigint,
        duplicate : bigint,
        duplicateDc : bigint,
        downlink : bigint,
        downlinkDc : bigint,
        inactivityDc : bigint,
        activityDc : bigint,
        maxCopy: number,
        dcPerJoinRequest: number,
        maxJoinRequestDup: number,
        joinDc : number,
        dcPerJoinAccept : number,
        joinAcceptDc : number,
    }
    interface TenantUpdateMaxCopyReqItf {
        tenantId : string,
        newMaxCopy : number,
    }
    interface TenantSetupStatsSerie {
        name : string,
        data : number[],
    }
    interface TenantSetupStatsRespItf {
        series : TenantSetupStatsSerie[],
        dateLabel : string[],
    }
}
