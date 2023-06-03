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

    interface DeviceCreate {
        device : {
            applicationId: string,
            description: string,
            devEui: string,
            deviceProfileId: string,
            isDisabled: boolean,
            name: string,
            skipFcntCheck: boolean,
            variables: {
                migrated : string,
                app_eui : string,
                organization_id : string,
                labels : string,
                source_oui : string,
            },
        }
    }

    interface DeviceKey {
        deviceKeys: {
            appKey: string,
            nwkKey: string
        }
    }

    interface DeviceActivation {
        deviceActivation: {
              aFCntDown: number,
              appSKey: string,
              devAddr: string,
              fCntUp: number,
              fNwkSIntKey: string,
              nFCntDown: number,
              nwkSEncKey: string,
              sNwkSIntKey: string
        }
    }

    interface IntegrationHttp {
        integration : {
            applicationId : string,
            encoding : string,
            eventEndpointUrl : string,
            headers : any
        }
    }

    interface IntegrationHttpCreate {
        integration : {
            encoding : string,
            eventEndpointUrl : string,
            headers : any
        }
    }

}