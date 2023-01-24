declare module "vue/types/proxy" {
    interface ProxyGetReqItf {
        endpoint : string,
        key : string,
    }
    interface ProxyConfig {
        bearer : string,
        getterUrl : string,
    }
    interface LabelItf {
        adr_allowed : boolean,
        cf_list_enabled : boolean,
        config_profile_id : string,
        id : string,
        name : string,
        rx_delay : number
    }
    interface DeviceItf {
        active : boolean,
        adr_allowed : boolean,
        dev_eui : string,
        app_eui : string,
        app_key : string,
        cf_list_enabled : boolean,
        config_profile_id : string,
        dc_usage : number,
        id : string,
        in_xor_filter : boolean,
        labels : LabelItf[],
        last_connected : string,
        name : string,
        organization_id : string,
        oui : number,
        rx_delay : number,
        total_packets : number,
    }
}