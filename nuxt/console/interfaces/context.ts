declare module "vue/types/context" {

    interface MapInit {
        latitude : number,
        longitude : number,
        zoom: number,
    }

    interface FrameHotspot {
        hotspotId : string,
        rssi : number,
        snr : number
    }

    interface FrameEntry {
        rxTimeMs : number,
        fCnt : number,
        dr : number,
        dataSize : number,
        frameType : number,
        hotspots : FrameHotspot[]
    }

    interface HotspotEntry {
        lastSeen : number,
        count : number,
        gatewayId : string,
        lat : number,
        lng : number,
        sumOfRssi : number,
        sumOfSnr : number,
        region : string
    }

    interface GetDeviceFramesItf {
        devEui : string,
        devName: string,
        tenantID : string,
        tenantName : string,
        lastSeen : number,
        frameSeen : number,
        estimatedLon : number,
        estimatedLat : number,
        mobile : boolean,
        recentFrames : FrameEntry[],
        hotspotAround : HotspotEntry[],
    }

    interface Owner {
        hntOwner : string,
        solOwner : string,
        timeMs : number
    }

    interface LatLng {
        lastDatePosition: number,
        lat: number,
        lng: number,
        alt: number,
        gain: number
    }

    interface HotspotIdent {
        hotspotId: string,
        animalName: string,
        owner: Owner,
        position: LatLng,
    }

    interface DataContext {
        // the device data
        device: GetDeviceFramesItf | undefined,
        // bounds
        latS: number,
        latN: number,
        lonW: number,
        lonE: number,
        // hotspots Around
        hotspots: HotspotIdent[],
        // context
        tenantID: string,
    }

    interface HotspotContext {
        id: string,
        isDetailed: boolean,
        identity: HotspotIdent | null,
        details: HotspotEntry | null
    }


    interface HotspotHourlyUsage {
        hourRef: number,
        packetsSeen: number,
    }

    interface WitnessHistory {
        timeRef: number,
        countWitnesses: number,
        seletedWitness: number,
    }

    interface RewardHistory {
        timeRef: number,
        witnessReward: number,
        beaconReward: number,
        dataReward: number
    }

    interface HotspotGetItf {
        hotspotId: string,
        name: string,
        lastSeen: number,
        seen: number,
        trafficHistory: HotspotHourlyUsage[],
        lastEtlUpdate: number,
        lastBeaconMs: number,
        lastWitnessMs: number,
        lastRewardMs: number,
        sumOfIoTRewards: number,
        beaconned: number,
        maxTxDistance: number,
        witnessed: number,
        maxRxDistance: number,
        maxRxBudgetLinkDB: number,
        brand: string,
        witnessesHistory: WitnessHistory[],
        rewardHistories: RewardHistory[],
    }

    interface DeviceSearchGetItf {
        deviceEui : string,
        applicationUUID : string,
        tenantUUID : string,
    }
}