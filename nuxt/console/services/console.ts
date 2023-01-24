import { ProxyConfig, ProxyGetReqItf, LabelItf, DeviceItf } from 'vue/types/proxy';

export class HeliumConsoleService {

    axios : any;
    proxyConfig : ProxyConfig;
    apiKey : string;        // legacy console api key 
    apiUrl : string;
    isBusy : boolean;

    accountLabels : LabelItf[];
    accountDevices : DeviceItf[];


    labelsGet : string = "v1/labels";
    devicesGet : string = "v1/devices";

    constructor (axios:any, proxyConfig:ProxyConfig) {
        this.proxyConfig = proxyConfig;
        this.axios = axios; 
        
        // to create a new axios instance w/o bearer addition
        //.create();
        // delete this.axios.defaults.headers.common['Authorization'];

        this.apiKey = "";
        this.apiUrl = "";
        this.isBusy = false;
        this.accountLabels = [];
        this.accountDevices = [];
    }

    setApiKey(ak:string) {
        this.apiKey = ak;
    }

    setApiUrl(url:string) {
        this.apiUrl = url;
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

    countDevices() : number {
        return this.accountDevices.length;
    }

    async getDevices() : Promise<string> {
        this.isBusy = true;
        let body : ProxyGetReqItf = {
            endpoint: this.apiUrl+this.devicesGet,
            key:this.apiKey,
        };
        return new Promise<string>((resolve) => { 
            this.axios.post(this.proxyConfig.getterUrl,body,this.getHeader())
            .then((response : any) =>{
                if (response.status == 200 ) {
                  this.isBusy = false;
                  (response.data as DeviceItf[]).forEach ( (label)=> {
                       this.accountDevices.push(label); 
                  });
                  resolve("");
                } else resolve(response.data.message);
            }).catch((err : any) =>{
                if ( err != undefined ) resolve(err.response.data.message);
            })
        });
    }



  }