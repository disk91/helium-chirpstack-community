import { DeviceProfileList, DeviceProfile, DeviceProfileCreate } from 'vue/types/chirpstack';

interface _DeviceProfile {
    profile: DeviceProfile,
    isUsable : boolean,
}

export class ChirpstackService {

    axios : any;
    apiKey : string;       
    tenantId : string;
    oui : number;

    deviceProfileGet : string = "/rest-api/api/device-profiles";
    devicesGet : string = "v1/devices";

    constructor (axios:any) {
        this.apiKey = "";
        this.tenantId = "";
        this.oui = -1;
        this.axios = axios.create();
        delete this.axios.defaults.headers.common['Authorization'];
    }

    setApiKey(ak:string) {
        this.apiKey = ak;
    }

    setTenantId(t:string) {
        this.tenantId = t;
    }
    setOui(o:number) {
        this.oui = o;
    }

    getHeader() : any {
        return {
            headers: {
                'Content-Type' : 'application/json',
                'authorization': 'Bearer '+this.apiKey, 
            }
        }
    }

    // --------------------------------------

    deviceProfile : _DeviceProfile[] = [];
    loadDeviceProfile() {
        this.deviceProfile = [];
        this.axios.get(this.deviceProfileGet+'?tenantId='+this.tenantId+'&limit=100',this.getHeader())
        .then((response : any) =>{
            if (response.status == 200 ) {
                response.data.result.forEach((devicep : DeviceProfile) => {
                    let d : _DeviceProfile = {} as _DeviceProfile;
                    d.profile = devicep;
                    d.isUsable = true;
                    this.deviceProfile.push(d);
                });  
                console.log(this.deviceProfile);
            }
        }).catch((err : any) =>{
            this.deviceProfile = [] as _DeviceProfile[];
        })
    }

    /**
     * Count the profiles, based on parameters : zone and otaa or abp
     * @param zone - select a zone, if empty, count all the profile that can be used for migration
     * @param otaa - indicate if we cound abp or otaa
     * @returns 
     */
    countDeviceProfile(zone:string, otaa:boolean) : number {
        let r : number = 0;
        this.deviceProfile.forEach( (devp : _DeviceProfile) => {
            if ( zone != "" ) {
                if ( devp.isUsable && devp.profile.region == zone && devp.profile.supportsOtaa == otaa ) r++;
            } else {
                if ( devp.isUsable ) r++;
            }
        });
        return r;
    }

    /**
     * Create a device profile for migration, for a given zone with a given profile OTAA or ABP
     * @param zone - zone to be created
     * @param otaa - type of profile
     */
    addDevProfile(zone:string, otaa:boolean, label: string = "") {
        let body : DeviceProfileCreate = {
            deviceProfile : {
                abpRx1Delay : 0,
                abpRx1DrOffset : 0,
                abpRx2Dr : 0,
                abpRx2Freq : 0,
                adrAlgorithmId : "default",
                classBPingSlotDr : 0,
                classBPingSlotFreq : 0,
                classBPingSlotPeriod : 0,
                classBTimeout : 0,
                classCTimeout : 0,
                description : "Migrated "+zone+" for label "+label,
                deviceStatusReqInterval : 1,
                flushQueueOnActivate : false,
                macVersion : "LORAWAN_1_1_0",
                regParamsRevision : "B",
                name : ((otaa)?"OTAA":"ABP")+"Migration "+label,
                payloadCodecRuntime : "NONE",
                payloadCodecScript : "",
                region : zone,
                supportsClassB : false,
                supportsClassC : false,
                supportsOtaa : otaa,
                tenantId : this.tenantId,
                uplinkInterval : 3600,
            }
        }

    }


  }