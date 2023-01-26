
export class ChirpstackService {

    axios : any;
    apiKey : string;        // legacy console api key 

    labelsGet : string = "v1/labels";
    devicesGet : string = "v1/devices";

    constructor (axios:any) {
        this.apiKey = "";
        this.axios = axios;         
    }

    setApiKey(ak:string) {
        this.apiKey = ak;
    }

    getHeader() : any {
        return {
            headers: {
                'Content-Type' : 'application/json',
                //'Authorization': 'Bearer '+this.proxyConfig.bearer, 
            }
        }
    }

    // --------------------------------------


  }