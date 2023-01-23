export class HeliumConsoleService {

    $axios : any;
    apiKey : string;        // legacy console api key 

    constructor (axios:any) {
        this.$axios = axios
        this.apiKey = "";
    }

    setApiKey(ak:string) {
        this.apiKey = ak;
    }

    getHello() : string {
        return "Hello";
    }
    
  }