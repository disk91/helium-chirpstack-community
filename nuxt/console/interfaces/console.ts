declare module "vue/types/console" {
    import { DeviceItf } from 'vue/types/proxy';

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
        status : string,
        selected : boolean,
        devProfile : string, // selected device type
        application : string, // selected application
    }
}