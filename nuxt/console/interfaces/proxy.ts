declare module "vue/types/proxy" {
    interface ProxyGetReqItf {
        endpoint : string,
        key : string,
    }
    interface ProxyDeactivateDeviceReqItf {
        endpoint : string,
        key : string,
        deviceId : string,
        deactivate : boolean
    }
    interface ProxyConfig {
        bearer : string,
        getterUrl : string,
        deactivaterUrl : string,
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
        app_s_key : string,
        cf_list_enabled : boolean,
        config_profile_id : string,
        dc_usage : number,
        devaddr : string,
        id : string,
        in_xor_filter : boolean,
        labels : LabelItf[],
        last_connected : string,
        name : string,
        nwk_s_key: string,
        organization_id : string,
        oui : number,
        region : string,
        rx_delay : number,
        total_packets : number,
    }
    interface FunctionItf {
        active : boolean,
        body : string,
        format : string,  // browan_object_locator | cayenne | custom
        id : string,
        name : string,
        type : string,
    }

    interface FlowItf {
        device_id : string,
        function_id: string,
        id : string,
        integration_id : string,
        label_id: string,
        organization_id : string,
    }

    interface IntegrationItf {
        credentials : {
            endpoint : string,
            headers : {},
            inbound_token : string,
            method : string,
            url_params : {},
            downlink : {
                topic: string,
            }
            uplink : {
                topic : string,
            }
        },
        name : string,
        type : string,
        id : string,
    }

}