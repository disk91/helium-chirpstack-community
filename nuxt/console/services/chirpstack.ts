import { DeviceProfileList, DeviceProfile, DeviceProfileCreate, ApplicationList, Application, ApplicationCreate } from 'vue/types/chirpstack';

interface _DeviceProfile {
    profile: DeviceProfile,
    isUsable : boolean,
}

export class ChirpstackService {

    axios : any;
    apiKey : string;       
    tenantId : string;
    oui : number;
    funcDecoder : string;  // data transformation function

    deviceProfileGet : string = "/rest-api/api/device-profiles";
    deviceProfilePost : string = "/rest-api/api/device-profiles";
    applicationsGet : string = "/rest-api/api/applications";
    applicationsPost : string = "/rest-api/api/applications";

    constructor (axios:any) {
        this.apiKey = "";
        this.tenantId = "";
        this.oui = -1;
        this.axios = axios.create();
        this.funcDecoder = "";
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

    setFunction(f:string) {
        this.funcDecoder = f;
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
     * Returns true when the device profile already exists on the backend
     * This is based on the device profile name
     * @param zone 
     * @param otaa 
     */
    existsDevProfile(zone:string, otaa:boolean, label: string = "") : boolean {
        for ( var i = 0 ; i < this.deviceProfile.length ; i++ ) {
            console.log(this.deviceProfile[i].profile.name);
            if ( this.deviceProfile[i].profile.name == "Migration "+((otaa)?"OTAA":"ABP")+" "+label && this.deviceProfile[i].profile.region == zone ) return true;
        }
        return false;     
    }

    /**
     * Create a device profile for migration, for a given zone with a given profile OTAA or ABP
     * @param zone - zone to be created
     * @param otaa - type of profile
     */
    async addDevProfile(zone:string, otaa:boolean, label: string = "") : Promise<string>  {
        let script = "// Decode uplink function.\n//\n// Input is an object with the following fields:\n// - bytes = Byte array containing the uplink payload, e.g. [255, 230, 255, 0]\n// - fPort = Uplink fPort.\n// - variables = Object containing the configured device variables.\n//\n// Output must be an object with the following fields:\n// - data = Object representing the decoded payload.\nfunction decodeUplink(input) {\n  return {\n    data: {\n      temp: 22.5\n    }\n  };\n}\n\n// Encode downlink function.\n//\n// Input is an object with the following fields:\n// - data = Object representing the payload that must be encoded.\n// - variables = Object containing the configured device variables.\n//\n// Output must be an object with the following fields:\n// - bytes = Byte array containing the downlink payload.\nfunction encodeDownlink(input) {\n  return {\n    bytes: [225, 230, 255, 0]\n  };\n}\n";
        let decoder = "NONE";
        if ( this.funcDecoder == "cayenne" ) {
            decoder = "CAYENNE_LPP";
        } else if (this.funcDecoder == "none") {
            // default values
        } else {
            decoder = "JS";
            script = this.funcDecoder;
        }

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
                description : "Migration Profile "+zone+" for label "+label,
                deviceStatusReqInterval : 1,
                flushQueueOnActivate : false,
                macVersion : "LORAWAN_1_1_0",
                regParamsRevision : "B",
                name : "Migration "+((otaa)?"OTAA":"ABP")+" "+label,
                payloadCodecRuntime : decoder,
                payloadCodecScript : script,
                region : zone,
                supportsClassB : false,
                supportsClassC : false,
                supportsOtaa : otaa,
                tenantId : this.tenantId,
                uplinkInterval : 3600,
                tags : {
                    "label" : label,
                },
            }
        }
        return new Promise<string>((resolve) => { 
            this.axios.post(this.deviceProfilePost,body,this.getHeader())
            .then((response : any) =>{
                if (response.status == 200 ) {
                    resolve("");
                }
            }).catch((err : any) =>{
                resolve("Error : "+err.response.data.message);
            })
        });
    }

    /**
     * Migrate a legacy console function to a chirpstack function
     * try to make our best ;)
     * @param fsource 
     */
    migrateFunction(fsource: string) : string {
        var fdest : string;
        const reg = /function[ ]+Decoder([^)]+)[^{]*{/
        fdest = fsource.replace(reg,
         "// Decode uplink function.\n"
        +"//\n"
        +"// Input is an object with the following fields:\n"
        +"// - bytes = Byte array containing the uplink payload, e.g. [255, 230, 255, 0]\n"
        +"// - fPort = Uplink fPort.\n"
        +"// - variables = Object containing the configured device variables.\n"
        +"//\n"
        +"// Output must be an object with the following fields:\n"
        +"// - data = Object representing the decoded payload.\n"
        +"function decodeUplink(intput) { \n"
        +"   var bytes = input.bytes;\n"
        +"   var port = input.fPort;\n"
        +"   var uplink_info = {};\n"
        +"   // Initial code imported, if you were using \n"
        +"   // uplink_info, you really need to review your function \n"
        );
        
        const reg3 = /return[^;{]*({[^}]*})/g
        fdest = fdest.replace(reg3,"return { \n     data: \n$1 \n  };\n");

        const reg2 = /return([^;{}]*)(;|\n)/g
        fdest = fdest.replace(reg2,"return { \n     data:$1 \n  };\n");

        fdest = fdest.concat(
            "\n"+
            "// Encode downlink function.\n" +
            "//\n" +
            "// Input is an object with the following fields:\n" +
            "// - data = Object representing the payload that must be encoded.\n" +
            "// - variables = Object containing the configured device variables.\n" +
            "//\n" +
            "// Output must be an object with the following fields:\n" +
            "// - bytes = Byte array containing the downlink payload.\n" +
            "function encodeDownlink(input) {\n" +
            "   return {\n" +
            "      bytes: [0]\n" +
            "   };\n" +
            "}\n"
        );

        return fdest;
    }

    // --------------------------------------

    applications : Application[] = [];
    loadAplications() {
        this.applications = [];
        this.axios.get(this.applicationsGet+'?tenantId='+this.tenantId+'&limit=100',this.getHeader())
        .then((response : any) =>{
            if (response.status == 200 ) {
                let r : ApplicationList = response.data.result;
                this.applications = r.result;
            }
        }).catch((err : any) =>{
            this.applications = [] as Application[];
        })
    }

    addApplication(_name : string) : Promise<string> {

        let body : ApplicationCreate = {
                application : {
                    name : _name,
                    description : "Application created by migration tools",
                    tenantId : this.tenantId
                }
            }

        return new Promise<string>((resolve) => { 
            this.axios.post(this.applicationsPost,body,this.getHeader())
            .then((response : any) =>{
                if (response.status == 200 ) {
                    resolve("");
                }
            }).catch((err : any) =>{
                resolve("Error : "+err.response.data.message);
            })
        });

    }

    getApplications() : Application[] {
        return this.applications;
    }

    countApplications() : number {
        return this.applications.length;
    }



  }