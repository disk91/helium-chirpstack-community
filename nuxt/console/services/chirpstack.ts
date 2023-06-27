import { DeviceProfileList, DeviceKey, DeviceCreate, DeviceActivation, DeviceProfile, DeviceProfileCreate, ApplicationList, Application, ApplicationCreate, IntegrationHttp, IntegrationHttpCreate } from 'vue/types/chirpstack';
import { Device } from 'vue/types/console';

interface _DeviceProfile {
    profile: DeviceProfile,
    isUsable : boolean,
}

interface _Integration {
    type : string,
    verb : string,
    endpoint : string,
    ulrparams : {},
    headers : {},
    topic_up : string,
    topic_down : string,
    id : string,
    name : string,
}

export class ChirpstackService {

    axios : any;
    apiKey : string;       
    tenantId : string;
    oui : number;
    funcDecoder : string;  // data transformation function
    integration : _Integration; 

    deviceProfileGet : string = "/rest-api/api/device-profiles";
    deviceProfilePost : string = "/rest-api/api/device-profiles";
    applicationsGet : string = "/rest-api/api/applications";
    applicationsPost : string = "/rest-api/api/applications";
    devicesPost : string = "/rest-api/api/devices";
    devicesDelete : string = "/rest-api/api/devices";
    devicesActivation : string = "/activate";
    devicesKey : string = "/keys";
    integrationBGet : string = "/rest-api/api/applications/";
    integrationEGet : string = "/integrations/http";
    integrationBPost : string = "/rest-api/api/applications/";
    integrationEPost : string = "/integrations/http";

    constructor (axios:any) {
        this.apiKey = "";
        this.tenantId = "";
        this.oui = -1;
        this.axios = axios.create();
        this.funcDecoder = "";
        this.applications = [];
        this.defaultApplication = undefined as any;
        delete this.axios.defaults.headers.common['Authorization'];
        this.integration = null as any;
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
    loadDeviceProfile() : Promise<string>  {
        this.deviceProfile = [];
        return new Promise<string>((resolve) => { 
            this.axios.get(this.deviceProfileGet+'?tenantId='+this.tenantId+'&limit=100',this.getHeader())
            .then((response : any) =>{
                if (response.status == 200 ) {
                    response.data.result.forEach((devicep : DeviceProfile) => {
                        let d : _DeviceProfile = {} as _DeviceProfile;
                        d.profile = devicep;
                        d.isUsable = true;
                        this.deviceProfile.push(d);
                    });  
                    resolve("");
                }
            }).catch((err : any) =>{
                this.deviceProfile = [] as _DeviceProfile[];
                resolve(err.response.data.message);
            })
        });
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
     * Return false when no device profiles have been found on the tenant
     */
    haveDeviceProfile() : boolean {
        return (this.deviceProfile.length > 0);
    }


    /**
     * Returns true when the device profile already exists on the backend
     * This is based on the device profile name
     * @param zone 
     * @param otaa 
     */
    existsDevProfile(zone:string, otaa:boolean, label: string = "") : boolean {
        for ( var i = 0 ; i < this.deviceProfile.length ; i++ ) {
            //console.log(this.deviceProfile[i].profile.name);
            if ( this.deviceProfile[i].profile.name == "("+zone+") Migration "+((otaa)?"OTAA":"ABP")+" "+label && this.deviceProfile[i].profile.region == zone ) return true;
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
                macVersion : "LORAWAN_1_0_4",
                regParamsRevision : "B",
                name : "("+zone+") Migration "+((otaa)?"OTAA":"ABP")+" "+label,
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
        +"function decodeUplink(input) { \n"
        +"   var bytes = input.bytes;\n"
        +"   var port = input.fPort;\n"
        +"   var uplink_info = {};\n"
        +"   // Initial code imported, if you were using \n"
        +"   // uplink_info, you really need to review your function \n"
        +"   // If you need to preserve your app_eui, add as part of the data the following entry \n"
        +"   // appeui: input.variables.app_eui "
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

    getDevProfiles(zone:string, otaa:boolean, label:string) : _DeviceProfile[] {
        let r = [] as _DeviceProfile [];
        for ( var i = 0 ; i < this.deviceProfile.length ; i++ ) {
            if ( this.deviceProfile[i].profile.name == "("+zone+") Migration "+((otaa)?"OTAA":"ABP")+" "+label ) {
                if ( zone == "Unknown" ) {
                    r.push(this.deviceProfile[i]);
                } else if ( this.deviceProfile[i].profile.region == zone ) r.push(this.deviceProfile[i]);
            }
        }
        // Add other profiles in the same zone
        for ( var i = 0 ; i < this.deviceProfile.length ; i++ ) {
            if ( this.deviceProfile[i].profile.name != "("+zone+") Migration "+((otaa)?"OTAA":"ABP")+" "+label ) {
                if ( zone == "Unknown" ) {
                    r.push(this.deviceProfile[i]);
                } else if ( this.deviceProfile[i].profile.region == zone ) r.push(this.deviceProfile[i]);
            }
        }
        return r;     
    }

    getBestProfiles(zone:string, otaa:boolean, label:string) : _DeviceProfile {
        if ( zone == "Unknown" ) return this.deviceProfile[0];
        // search profile created during the previous step for this label
        for ( var i = 0 ; i < this.deviceProfile.length ; i++ ) {
            if ( this.deviceProfile[i].profile.name == "("+zone+") Migration "+((otaa)?"OTAA":"ABP")+" "+label ) {
                if ( this.deviceProfile[i].profile.region == zone ) return this.deviceProfile[i];
            }
        }
        // if not found, find a profile with the correct zone
        for ( var i = 0 ; i < this.deviceProfile.length ; i++ ) {
            if ( this.deviceProfile[i].profile.region == zone ) return this.deviceProfile[i];
        }

        // if not found return undefined
        return undefined as any;     
    }

    getProfileById(id: string) : _DeviceProfile {
        for ( var i = 0 ; i < this.deviceProfile.length ; i++ ) {
            if ( this.deviceProfile[i].profile.id == id ) {
                return this.deviceProfile[i];
            }
        }
        return this.deviceProfile[0];     
    }

    // --------------------------------------

    applications : Application[];
    defaultApplication : Application;
    loadApplications() : Promise<string> {
        this.applications = [];
        return new Promise<string>( (resolve) => {
            this.axios.get(this.applicationsGet+'?tenantId='+this.tenantId+'&limit=100',this.getHeader())
            .then((response : any) =>{
                if (response.status == 200 ) {
                    let r : ApplicationList = response.data;
                    this.applications = r.result;
                    resolve("");
                }
            }).catch((err : any) =>{
                this.applications = [] as Application[];
                resolve(err.response.data.message);
            })
        });
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
        if ( this.applications == undefined ) return 0;
        return this.applications.length;
    }

    setDefaultApplication( a : Application ) {
        this.defaultApplication = a;
    }

    getDefaultApplication() : Application {
        return this.defaultApplication;
    }

    // --- Integration

    setIntegration(i:_Integration) {
        this.integration = i;
    }

    getIntegration() : _Integration {
        return this.integration;
    }


    getApplicationIntegration(applicationId : string) : Promise<IntegrationHttp> {
        return new Promise<IntegrationHttp>((resolve) => { 
            this.axios.get(this.integrationBGet+applicationId+this.integrationEGet,this.getHeader())
            .then((response : any) =>{
                if (response.status == 200 ) {
                    resolve(response.data);
                }
            }).catch((err : any) =>{
                resolve(null as any);
            })
        });
    }

    createApplicationIntegration() : Promise<string> {

        return new Promise<string>((resolve) => {
            this.getApplicationIntegration(this.defaultApplication.id)
            .then( (integ) => {
                if ( integ == null ) {
                    // create it
                    let body : IntegrationHttpCreate = {
                        integration : {
                            encoding : "JSON",
                            eventEndpointUrl : "http://fwdlb:8082/capture/",
                            headers : {
                                "hid" : this.integration.id,
                                "hendpoint" : this.integration.endpoint,
                                "htype" : this.integration.type,
                                "hverb" : this.integration.verb,
                                "hurlparam" : JSON.stringify(this.integration.ulrparams),
                                "hheaders" : JSON.stringify(this.integration.headers),
                                "huptopic" : (this.integration.topic_up!="")?this.integration.topic_up:"none",
                                "hdntopic" : (this.integration.topic_down!="")?this.integration.topic_down:"none",
                            }
                        }
                    };

                    this.axios.post(this.integrationBPost+this.defaultApplication.id+this.integrationEPost,body,this.getHeader())
                    .then((response : any) =>{
                        if (response.status == 200 ) {
                            resolve("");
                        }
                    }).catch((err : any) =>{
                        resolve("Error : "+err.response.data.message);
                    })
                } else if ( integ.integration.headers.hid != undefined && integ.integration.headers.hid == this.integration.id ) {
                    // same already existing
                    resolve("");
                } else {
                    // another integration is already existing
                    resolve("Error : An integration already exists");
                }
            })
        });

    }

    // ------------------------------------

    createdevice(dev : Device) : Promise<string> {

        let r = "" as string;
        if ( dev.rawDevice.labels != undefined ) {
            for ( var i = 0 ; i < dev.rawDevice.labels.length ; i++ ) {
                r += dev.rawDevice.labels[i].name+ " ";
            }
        }

        let body : DeviceCreate = {
                device : {
                    applicationId: dev.application,
                    description: "migrated device",
                    devEui: dev.rawDevice.dev_eui,
                    deviceProfileId: dev.devProfile,
                    isDisabled: false,
                    name: dev.rawDevice.name,
                    skipFcntCheck: true,
                    variables: {
                        migrated : "true",
                        organization_id : dev.rawDevice.organization_id,
                        labels : r,
                        app_eui : dev.rawDevice.app_eui,
                        source_oui : ""+dev.rawDevice.oui,
                    },
                }
            }

        let bodyKeys : DeviceKey = {
            deviceKeys: {
                appKey : "00000000000000000000000000000000",
                nwkKey : dev.rawDevice.app_key,
            }
        }

        return new Promise<string>((resolve) => { 
            // create device
            this.axios.post(this.devicesPost,body,this.getHeader())
            .then((response : any) =>{
                if (response.status == 200 ) {
                    // add keys
                    this.axios.post(this.devicesPost+'/'+dev.rawDevice.dev_eui.toLowerCase()+this.devicesKey, bodyKeys, this.getHeader())
                    .then((response : any) => {
                        if ( response.status == 200 ) {
                            resolve("");
                        } else {
                            resolve("err_failed_add_keys");
                        }
                    }).catch((err : any) => {
                        console.log("Error : "+err.response.data.message);
                        // need to delete device
                        this.axios.delete(this.devicesPost+'/'+dev.rawDevice.dev_eui.toLowerCase(), this.getHeader())
                        .then((response : any) => {
                            if ( response.status == 200 ) {
                                console.log("Created device has been deleted");
                            }
                        }).catch((err : any) =>{
                            console.log("Failed to remove created device");
                        })
                        resolve("err_failed_add_keys");
                    })
                }
            }).catch((err : any) =>{
                resolve("err_failed_create_device");
                console.log("Error : "+err.response.data.message);
            })
        });
    }

    activatedevice (dev : Device) : Promise<string> {

        // this device has never been activated and we should bypass it
        if ( dev.rawDevice.app_s_key == null || dev.rawDevice.app_s_key.length < 5 ) {
            return new Promise<string>((resolve) => {
                resolve("");  
            })
        }

        // normal case where the device has already been activated prevously
        let body : DeviceActivation = {
            deviceActivation : {
                aFCntDown : 0,
                nFCntDown: 0,
                fCntUp : 0,
                appSKey : dev.rawDevice.app_s_key,
                devAddr : dev.rawDevice.devaddr,
                fNwkSIntKey : dev.rawDevice.nwk_s_key,
                nwkSEncKey: dev.rawDevice.nwk_s_key,
                sNwkSIntKey : dev.rawDevice.nwk_s_key,
            }
        }
        return new Promise<string>((resolve) => { 
            // create device
            //console.log(dev);
            this.axios.post(this.devicesPost+'/'+dev.rawDevice.dev_eui.toLowerCase()+this.devicesActivation,body,this.getHeader())
            .then((response : any) =>{
                if (response.status == 200 ) {
                    resolve("");
                } else {
                    console.log("Failed to activate device with code "+response.status);
                    resolve('err_failed_activation');
                }
            }).catch((err : any) =>{
                console.log("Failed to activate device");
                resolve('err_failed_activation');
            })
        })
    }

/*
"deviceActivation": {
    "devEui": "239f4b02b699cd01",
    "devAddr": "4800053e",
    "appSKey": "04c66da22c010d9b726fc477110b7893",
    "nwkSEncKey": "26ab9a866cd86fd2b22f0923072b4201",
    "sNwkSIntKey": "26ab9a866cd86fd2b22f0923072b4201",
    "fNwkSIntKey": "26ab9a866cd86fd2b22f0923072b4201",
    "fCntUp": 23,
    "nFCntDown": 2,
    "aFCntDown": 0
  }

*/
    

    deletedevice (dev : Device) : Promise<string> {
        return new Promise<string>((resolve) => { 
            this.axios.delete(this.devicesPost+'/'+dev.rawDevice.dev_eui.toLowerCase(), this.getHeader())
            .then((response : any) => {
                if ( response.status == 200 ) {
                    console.log("Created device has been deleted");
                    resolve("");
                }
            }).catch((err : any) =>{
                console.log("Failed to remove created device");
            })
            resolve("err_failed_add_keys");
        })
    }

}