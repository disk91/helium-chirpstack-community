declare module "vue/types/inactiv" {

    interface AdvDeviceInacGetItf {
        tenantUUID: string;
        tenantName: string;
        inactivCount: number;
        currentPage: number;
        totalPage: number;
        perPage: number;
        inactives: AdvDeviceInacSubItf[];
      }

    interface AdvDeviceInacSubItf {
        devEui: string;
        devName: string;
        applicationName: string;
        applicationId: string;
        creationDate: number;
        lastSeenDate: number;
        devAddr: string;
        disabled: boolean;
        routeEui: number;
        routeSkfs: number;
        skfsCollision: number;
        neverSeen: boolean;
        onlyJoinReq: number;
        coverageRisk: number;
      }
       


    interface InactivContext {
        // context
        tenantID: string;
        data: AdvDeviceInacGetItf | undefined;
    }


}