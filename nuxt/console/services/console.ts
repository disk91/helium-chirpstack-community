import { ProxyConfig, ProxyGetReqItf, LabelItf, DeviceItf, FunctionItf, FlowItf } from 'vue/types/proxy';


interface Device {
    rawDevice : DeviceItf,
    isActive : boolean,             // the activation flag is on
    isInactive : boolean,           // !active || last_connected = 0 || dc_usage = 0 || in_xor_filter = false
    isNeverSeen : boolean,          // !active || last_connected = 0 || dc_usage = 0 || in_xor_filter = false
    isEU868 : boolean,
    isUS915 : boolean,
    isAS923_1 : boolean,
    isAS923_1B : boolean,
    isAS923_2 : boolean,
    isAS923_3 : boolean,
    isAS923_4 : boolean,
    isCN470 : boolean,
    isAU915_1 : boolean,
    isAU915_6 : boolean,
    region : string,
    isRegion : boolean, // false = unknonwn
    isMultipleLabel : boolean,
}



export class HeliumConsoleService {

    
    axios : any;
    proxyConfig : ProxyConfig;
    apiKey : string;        // legacy console api key 
    apiUrl : string;
    isBusy : boolean;
    oui : number;

    accountLabels : LabelItf[];
    accountDevices : Device[];
    accountFunctions : FunctionItf[];
    accountFlows : FlowItf[];

    labelsGet : string = "v1/labels";
    devicesGet : string = "v1/devices";
    functionGet : string = "v1/functions"
    flowGet : string = "v1/flows"

    constructor (axios:any, proxyConfig:ProxyConfig) {
        this.proxyConfig = proxyConfig;
        this.axios = axios; 
        
        this.apiKey = "";
        this.apiUrl = "";
        this.isBusy = false;
        this.accountLabels = [];
        this.accountDevices = [];
        this.accountFunctions = [];
        this.accountFlows = [];
        this.oui = -1;
    }

    setApiKey(ak:string) {
        this.apiKey = ak;
    }

    setApiUrl(url:string) {
        this.apiUrl = url;
    }

    setOui(o:number) {
        this.oui = o;
    }

    getHeader() : any {
        return {
            headers: {
                'Content-Type' : 'application/json',
                'Authorization': 'Bearer '+this.proxyConfig.bearer, 
            }
        }
    }

    // --------------------------------------


    async testConnection() : Promise<string> {
        this.isBusy = true;
        let body : ProxyGetReqItf = {
            endpoint: this.apiUrl+this.labelsGet,
            key:this.apiKey,
        };
        return new Promise<string>((resolve) => { 
            this.axios.post(this.proxyConfig.getterUrl,body,this.getHeader())
            .then((response : any) =>{
                if (response.status == 200 ) {
                  this.isBusy = false;
                  (response.data as LabelItf[]).forEach ( (label)=> {
                       this.accountLabels.push(label); 
                  });
                  resolve("");
                } else resolve(response.data.message);
            }).catch((err : any) =>{
                if ( err != undefined ) resolve(err.response.data.message);
            })
        });
    }

    countLabels() : number {
        return this.accountLabels.length;
    }

    countDevices(label:string = "") : number {
        let r = 0;
        this.accountDevices.forEach( (device) => {
            if ( label == "no_label" && device.rawDevice.labels.length  == 0 ) r++;
            if ( label == "" || device.rawDevice.labels.length > 0 && device.rawDevice.labels[0].id == label ) r++;
        })
        return r;
    }

    countActive(label:string = "") : number {
        let r = 0;
        this.accountDevices.forEach( (device) => {
            if ( label == "no_label" && device.rawDevice.labels.length  == 0 )
                if ( device.isActive ) r++;
            if ( label == "" || device.rawDevice.labels.length > 0 && device.rawDevice.labels[0].id == label )
               if ( device.isActive ) r++;
        })
        return r;
    }

    countIncompatible(label:string = "") : number {
        let r = 0;
        this.accountDevices.forEach( (device) => {
            if ( label == "no_label" && device.rawDevice.labels.length  == 0 )
                if ( device.isMultipleLabel ) r++;
            if ( label == "" || device.rawDevice.labels.length > 0 && device.rawDevice.labels[0].id == label )
               if ( device.isMultipleLabel ) r++;
        })
        return r;
    }

    countEU868(label:string = "") : number {
        let r = 0;
        this.accountDevices.forEach( (device) => {
            if ( label == "no_label" && device.rawDevice.labels.length  == 0 )
                if ( device.isEU868 ) r++;
            if ( label == "" || device.rawDevice.labels.length > 0 && device.rawDevice.labels[0].id == label )
               if ( device.isEU868 ) r++;
        })
        return r;
    }

    countUS915(label:string = "") : number {
        let r = 0;
        this.accountDevices.forEach( (device) => {
            if ( label == "no_label" && device.rawDevice.labels.length  == 0 )
                if ( device.isUS915 ) r++;
            if ( label == "" || device.rawDevice.labels.length > 0 && device.rawDevice.labels[0].id == label )
               if ( device.isUS915 ) r++;
        })
        return r;
    }

    countAU915(label:string = "") : number {
        let r = 0;
        this.accountDevices.forEach( (device) => {
            if ( label == "no_label" && device.rawDevice.labels.length  == 0 )
                if ( device.isAU915_1 ) r++;
            if ( label == "" || device.rawDevice.labels.length > 0 && device.rawDevice.labels[0].id == label )
               if ( device.isAU915_1 ) r++;
        })
        return r;
    }

    countAS923(label:string = "") : number {
        let r = 0;
        this.accountDevices.forEach( (device) => {
            if ( label == "no_label" && device.rawDevice.labels.length  == 0 )
                if ( device.isAS923_1 ) r++;
            if ( label == "" || device.rawDevice.labels.length > 0 && device.rawDevice.labels[0].id == label )
               if ( device.isAS923_1 ) r++;
        })
        return r;
    }

    countUnknownRegion(label:string = "") : number {
        let r = 0;
        this.accountDevices.forEach( (device) => {
            if ( label == "no_label" && device.rawDevice.labels.length  == 0 )
                if ( !device.isRegion ) r++;
            if ( label == "" || device.rawDevice.labels.length > 0 && device.rawDevice.labels[0].id == label )
               if ( !device.isRegion ) r++;
        })
        return r;
    }

    countInOui(label:string = "") : number {
        let r = 0;
        this.accountDevices.forEach( (device) => {
            if ( label == "no_label" && device.rawDevice.labels.length  == 0 )
                if ( device.rawDevice.oui == this.oui ) r++;
            if ( label == "" || device.rawDevice.labels.length > 0 && device.rawDevice.labels[0].id == label )
               if ( device.rawDevice.oui == this.oui ) r++;
        })
        return r;
    }

    getDownloadedLabels() : LabelItf[] {
        return this.accountLabels;
    }

    getDownloadedFunction() : FunctionItf[] {
        return this.accountFunctions;
    }

    /**
     * Return true when a lable is used with other labels for some devices
     * @param labelId 
     * @returns 
     */
    isLabelSingleUsed(labelId:string) : boolean {
        for ( var device of this.accountDevices ) {
            if ( device.rawDevice.labels.length > 1 ) {
                for ( var label of device.rawDevice.labels ) {
                    if ( label.id == labelId ) return false;
                }
            } 
        }
        return true;
    }

    async getDevices() : Promise<string> {
        this.isBusy = true;
        let body : ProxyGetReqItf = {
            endpoint: this.apiUrl+this.devicesGet,
            key:this.apiKey,
        };
        this.accountDevices = [];
        return new Promise<string>((resolve) => { 
            this.axios.post(this.proxyConfig.getterUrl,body,this.getHeader())
            .then((response : any) =>{
                if (response.status == 200 ) {
                  this.isBusy = false;
                  (response.data as DeviceItf[]).forEach ( (deviceItf)=> {
                       let lastConnected = 0;
                       if ( deviceItf.last_connected != null ) {
                           lastConnected = (new Date(deviceItf.last_connected)).getMilliseconds();
                       } 
                       let device = {
                            rawDevice : deviceItf,
                            isActive : deviceItf.active,
                            isInactive : (!deviceItf.active || lastConnected < 10000 || !deviceItf.in_xor_filter || deviceItf.dc_usage == 0 ), 
                            isNeverSeen : ( deviceItf.dc_usage == 0 || lastConnected < 10000 ) && (deviceItf.active && deviceItf.in_xor_filter),
                            isEU868 : false,
                            isUS915 : false,
                            isAS923_1 : false,
                            isAS923_1B : false,
                            isAS923_2 : false,
                            isAS923_3 : false,
                            isAS923_4 : false,
                            isCN470 : false,
                            isAU915_1 : false,
                            isAU915_6 : false,
                            region : "Unknown",
                            isRegion : false, // false = unknonwn
                            isMultipleLabel : (deviceItf.labels.length > 1),
                       } as Device;
                       this.accountDevices.push(device); 
                  });
                  resolve("");
                } else resolve(response.data.message);
            }).catch((err : any) =>{
                if ( err != undefined ) resolve(err.response.data.message);
            })
        });
    }


    async getFunctions() : Promise<string> {
        this.isBusy = true;
        let body : ProxyGetReqItf = {
            endpoint: this.apiUrl+this.functionGet,
            key:this.apiKey,
        };
        this.accountFunctions = [];
        return new Promise<string>((resolve) => { 
            this.axios.post(this.proxyConfig.getterUrl,body,this.getHeader())
            .then((response : any) =>{
                if (response.status == 200 ) {
                  this.isBusy = false;
                  (response.data as FunctionItf[]).forEach ( (functionItf)=> {
                       this.accountFunctions.push(functionItf); 
                  });
                  resolve("");
                } else resolve(response.data.message);
            }).catch((err : any) =>{
                if ( err != undefined ) resolve(err.response.data.message);
            })
        });
    }

    getOneFunction(funcId:string) : FunctionItf {
        for ( let i = 0 ; i < this.accountFunctions.length ; i ++ ) {
            if ( this.accountFunctions[i].id == funcId ) return this.accountFunctions[i]; 
        }
        return undefined as any;
    }

    async getFlows() : Promise<string> {
        this.isBusy = true;
        let body : ProxyGetReqItf = {
            endpoint: this.apiUrl+this.flowGet,
            key:this.apiKey,
        };
        this.accountFlows = [];
        return new Promise<string>((resolve) => { 
            this.axios.post(this.proxyConfig.getterUrl,body,this.getHeader())
            .then((response : any) =>{
                this.accountFlows = [];
                if (response.status == 200 ) {
                  this.isBusy = false;
                  (response.data as FlowItf[]).forEach ( (flowItf)=> {
                       this.accountFlows.push(flowItf); 
                  });
                  resolve("");
                } else resolve(response.data.message);
            }).catch((err : any) =>{
                if ( err != undefined ) resolve(err.response.data.message);
            })
        });
    }

    isFunctionUsedInALabel(funcId:string, labelId:string) : boolean {
        let found = false;
        this.accountFlows.forEach((flow:FlowItf) => {
            if ( flow.function_id != null && flow.label_id != null && flow.function_id == funcId && flow.label_id == labelId ) {
                found = true;
            } 
        });
        return found;
    }


  }