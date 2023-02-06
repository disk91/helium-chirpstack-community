declare module "vue/types/chirpstack" {

    interface DeviceProfile {
        createdAt: string,
        updatedAt : string,
        id : string,
        macVersion : string,
        name : string,
        regParamsRevision : string,
        region : string,
        supportsClassB : boolean,
        supportsClassC : boolean,
        supportsOtaa : boolean,
    }

    interface DeviceProfileList {
        result : DeviceProfile[],
        totalCount : number,
    }

    interface DeviceProfileCreateSub {
        abpRx1Delay : number,
        abpRx1DrOffset : number,
        abpRx2Dr : number,
        abpRx2Freq : number,
        adrAlgorithmId : string,
        classBPingSlotDr : number,
        classBPingSlotFreq : number,
        classBPingSlotPeriod : number,
        classBTimeout : number,
        classCTimeout : number,
        description : string,
        deviceStatusReqInterval : number,
        flushQueueOnActivate : boolean,
        macVersion : string,
        name : string,
        payloadCodecRuntime : string,
        payloadCodecScript : string,
        regParamsRevision : string,
        region : string,
        supportsClassB : boolean,
        supportsClassC : boolean,
        supportsOtaa : boolean,
        tenantId : string,
        uplinkInterval : number,
        tags : any,
    }

    interface DeviceProfileCreate {
        deviceProfile : DeviceProfileCreateSub,
    }

    interface ApplicationList {
        totalCount : number,
        result : Application[],
    }

    interface Application {
        id : string,
        createdAt : string,
        updatedAt : string,
        name : string,
        description : string,
    }

    interface ApplicationCreate {
        application : {
            description : string,
            name : string,
            tenantId : string,
        }
    }

}