<template>
    <div>
        <b-row v-if="init">
            <b-col cols="12">
                <b-row class="py-1" v-if="ctx.identity != null">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                        {{ $t('ctx_hotspotName') }}
                    </b-col>
                    <b-col cols="7"
                        style="text-align:left;margin-right:2px;font-size:0.8rem;"
                        class="text-info bg-light"
                    >
                        <a :href=getExplorerLink() target="_blank">
                            {{ ctx.identity.animalName }}
                        </a>
                    </b-col>
                </b-row>
                <b-row class="py-1" v-if="ctx.identity == null && ctx.details != null">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                        {{ $t('ctx_hotspotID') }}
                    </b-col>
                    <b-col cols="7"
                        style="text-align:left;margin-right:2px;font-size:0.8rem;"
                        class="text-info bg-light"
                    >
                        <a :href=getExplorerLink() target="_blank">
                            {{ ctx.details.gatewayId }}
                        </a>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>
        <b-row v-if="init && ctx.isDetailed && ctx.details != null">
            <b-col cols="12">
                <b-row class="py-1">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_lastSeen') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ getLastSeenString(ctx.details.lastSeen) }}
                    </b-col>
                </b-row>
                <b-row class="py-1" v-if="ctx.details.region != null">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_region') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ ctx.details.region }}
                    </b-col>
                </b-row>
                <b-row class="py-1">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_avgRssi') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ (ctx.details.sumOfRssi / ctx.details.count).toFixed(0)  }} dBm
                    </b-col>
                </b-row>
                <b-row class="py-1">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_avgSnr') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ (ctx.details.sumOfSnr / ctx.details.count).toFixed(2)  }} dBm
                    </b-col>
                </b-row>
            </b-col>
        </b-row>
        <b-row class="py-1" v-if="init && loading">
            <b-col cols="12">
                <b-button variant="secondary" disabled>
                <b-spinner small type="grow"></b-spinner>
                    {{ $t('ctx_loading_in_progress') }}
                </b-button>
            </b-col>
        </b-row>
        <b-row class="py-1" v-if="init && !loading && hsDetails != null">
            <b-col cols="12">
                <b-row class="py-1">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_brand') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ hsDetails.brand }}
                    </b-col>
                </b-row>
                <b-row class="py-1">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_etl_update') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ getLastSeenString(hsDetails.lastEtlUpdate) }}
                    </b-col>
                </b-row>
                <b-row class="py-1">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_beacon_update') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ getLastSeenString(hsDetails.lastBeaconMs) }}
                    </b-col>
                </b-row>
                <b-row class="py-1">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_witness_update') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ getLastSeenString(hsDetails.lastWitnessMs) }}
                    </b-col>
                </b-row>
                <b-row class="py-1">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_reward_update') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ getLastSeenString(hsDetails.lastRewardMs) }}
                    </b-col>
                </b-row>
                <b-row class="py-1">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_reward_total') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ hsDetails.sumOfIoTRewards }} IoTs
                    </b-col>
                </b-row>
                <b-row class="py-1">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_beacon_tot') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ hsDetails.beaconned }} Hs
                    </b-col>
                </b-row>
                <b-row class="py-1">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_beacon_dist') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ (hsDetails.maxTxDistance / 1000).toFixed(1) }} Km
                    </b-col>
                </b-row>
                <b-row class="py-1">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_witness_tot') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ hsDetails.witnessed }} Hs
                    </b-col>
                </b-row>
                <b-row class="py-1">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_witness_dist') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ (hsDetails.maxRxDistance / 1000).toFixed(1) }} Km
                    </b-col>
                    <b-col v-if="((hsDetails.maxTxDistance*0.8) > hsDetails.maxRxDistance)" cols="4" class="text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;"></b-col>
                    <b-col v-if="((hsDetails.maxTxDistance*0.8) > hsDetails.maxRxDistance)" cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.6rem;color: rgb(150,50,50);"
                            class="bg-light"
                    >
                        {{ $t('ctx_hs_rx_warning')}}
                    </b-col>
                </b-row>
                <b-row class="py-1">
                    <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_rx_budget') }}
                    </b-col>
                    <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                    >
                        {{ hsDetails.maxRxBudgetLinkDB }} dBm
                    </b-col>
                    <b-col v-if="( hsDetails.maxRxBudgetLinkDB < 120 )" cols="4" class="text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;"></b-col>
                    <b-col v-if="( hsDetails.maxRxBudgetLinkDB < 120 )" cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.6rem;color: rgb(150,50,50);"
                            class="bg-light"
                    >
                        {{ $t('ctx_hs_bl_warning')}}
                    </b-col>
                </b-row>
                <b-row v-if="hsDetails.trafficHistory.length > 0" class="py-1">
                    <b-col cols="12" class="bg-secondary text-white ml-1 mr-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_traffic_chart') }}
                    </b-col>
                </b-row>
                <b-row v-if="hsDetails.trafficHistory.length > 0" class="py-1">
                    <b-col>
                        <trafficChart type="bar" height="200" :options="lnsSeenTrafficOption" :series="lnsSeenTrafficData"></trafficChart>
                    </b-col>
                </b-row>
                <b-row v-if="hsDetails.witnessesHistory.length > 0" class="py-1">
                    <b-col cols="12" class="bg-secondary text-white ml-1 mr-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_witness_chart') }}
                    </b-col>
                </b-row>
                <b-row v-if="hsDetails.witnessesHistory.length > 0" class="py-1">
                    <b-col>
                        <witnessChart type="bar" height="200" :options="hsSeenWitnessOption" :series="hsSeenWitnessData"></witnessChart>
                    </b-col>
                </b-row>
                <b-row v-if="hsDetails.rewardHistories.length > 0" class="py-1">
                    <b-col cols="12" class="bg-secondary text-white ml-1 mr-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_hs_reward_chart') }}
                    </b-col>
                </b-row>
                <b-row v-if="hsDetails.rewardHistories.length > 0" class="py-1">
                    <b-col>
                        <rewardChart type="bar" height="200" :options="hsSeenRewardOption" :series="hsSeenRewardData"></rewardChart>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>
    </div>
</template>

<script lang="ts">
    import Vue from 'vue';
    import { HotspotContext, HotspotGetItf } from 'vue/types/context'; 
    import VueApexCharts from 'vue-apexcharts';

    interface context {
        ctx: HotspotContext,
        hsDetails: HotspotGetItf | null,
        init: boolean,
        loading: boolean,
        lnsSeenTrafficOption: any,
        lnsSeenTrafficData: any,
        hsSeenWitnessOption: any,
        hsSeenWitnessData: any,
        hsSeenRewardOption: any,
        hsSeenRewardData: any,
    }

    export default Vue.extend({
        name: 'CtxHotspot',
        components: {
            trafficChart: VueApexCharts,
            witnessChart: VueApexCharts,
            rewardChart: VueApexCharts,
        },
        data() : context {
            return {
                ctx: {
                    id: '',
                    identity: null,
                    details: null,
                    isDetailed: false,
                },
                hsDetails: null,
                init: false,
                loading: false,
                // LNS seen traffic
                lnsSeenTrafficOption : {
                    chart: { type: 'bar', height: 100, stacked: false },
                    plotOptions: { bar: { horizontal: false, }, },
                    dataLabels: { enabled:false },
                    fill: { opacity: 1 },
                    legend: { position: 'bottom', horizontalAlign: 'left', offsetX: 40 },
                    xaxis: { categories: [], labels : { rotate: -45, rotateAlways: false, style: {fontSize:'9px'}}, }
                },
                lnsSeenTrafficData: [{
                    name: 'traffic',
                    data: []
                }],
                // Witness history
                hsSeenWitnessOption : {
                    chart: { type: 'bar', height: 100, stacked: false },
                    plotOptions: { bar: { horizontal: false, }, },
                    dataLabels: { enabled:false },
                    fill: { opacity: 1 },
                    legend: { position: 'bottom', horizontalAlign: 'left', offsetX: 40 },
                    xaxis: { categories: [], labels : { rotate: -45, rotateAlways: false, style: {fontSize:'9px'} }, }
                },
                hsSeenWitnessData: [{
                    name: 'witnesses',
                    data: []
                },{
                    name: 'selected',
                    data: []
                }],
                // Reward history
                hsSeenRewardOption : {
                    chart: { type: 'bar', height: 100, stacked: true },
                    plotOptions: { bar: { horizontal: false, }, },
                    dataLabels: { enabled:false },
                    fill: { opacity: 1 },
                    legend: { position: 'bottom', horizontalAlign: 'left', offsetX: 40 },
                    xaxis: { categories: [], labels : { rotate: -45, rotateAlways: false, style: {fontSize:'9px'} }, }
                },
                hsSeenRewardData: [{
                    name: 'witness',
                    data: []
                },{
                    name: 'beacon',
                    data: []
                },{
                    name: 'data',
                    data: []
                }],
            }
        },
        methods: {
            updateHotspot(msg:HotspotContext) {
                this.init = true;
                this.ctx = msg;
            },
            getExplorerLink() : string {
                if ( this.ctx.identity != null ) {
                    return "https://explorer.moken.io/hotspots/"+this.ctx.identity.hotspotId+"?lat="+this.ctx.identity.position.lat+"@lng="+this.ctx.identity.position.lng+"&zoom=12&layer=hotspots";
                }
                return "";
            },
            getLastSeenString(ts: number) : string {
                let now = Date.now();
                let delta = now - ts;
                if ( delta < 1000 ) return 'now';
                if ( delta < 60000 ) return ""+Math.floor(delta / 1000)+"s ago";
                if ( delta < 3600000 ) return ""+Math.floor(delta / 60000)+"m ago";
                if ( delta < 48*3600000 ) return ""+Math.floor(delta / 3600000)+"h ago";
                if ( delta < 10*24*3600000 ) return ""+Math.floor(delta / (24*3600000) )+" days ago";
                let date = new Date(ts);
                return ""+date.getFullYear()+"-"+(date.getMonth()+1).toString().padStart(2,'0')+"-"+date.getDate().toString().padStart(2,'0')+" "+date.getHours().toString().padStart(2,'0')+":"+date.getMinutes().toString().padStart(2,'0');
            },
            getDateText(ts: number) : string {
                let date = new Date(ts);
                return ""+(date.getMonth()+1).toString().padStart(2,'0')+"-"+date.getDate().toString().padStart(2,'0')+" "+date.getHours().toString().padStart(2,'0')+":"+date.getMinutes().toString().padStart(2,'0');
            },
            loadHotspot(hotspotId: string) {
                let config = {
                    headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                    }
                };
                this.loading = true;
                this.$axios.get<HotspotGetItf>(this.$config.ctxHotspotGet+'/'+hotspotId+'/',config)
                    .then((response) =>{
                        if (response.status == 200 ) {
                            this.hsDetails = response.data;   
                            // refresh traffic charts
                            this.lnsSeenTrafficData[0].data = [];
                            this.lnsSeenTrafficOption.xaxis.categories = [];
                            for ( let i = 0 ; i < this.hsDetails.trafficHistory.length ; i++ ) {
                                this.lnsSeenTrafficOption.xaxis.categories.push(this.getDateText(this.hsDetails.trafficHistory[i].hourRef));
                                this.lnsSeenTrafficData[0].data.push(this.hsDetails.trafficHistory[i].packetsSeen);
                            }
                            // Data does not contains the last hours since last data seen
                            let now = Date.now();
                            let last = this.hsDetails.trafficHistory[this.hsDetails.trafficHistory.length-1].hourRef;
                            while ( last < (now - 3600000) ) {
                                last += 3600000;
                                this.lnsSeenTrafficOption.xaxis.categories.push(this.getDateText(last));
                                this.lnsSeenTrafficData[0].data.push(0);
                            }

                            // refresh witness charts
                            this.hsSeenWitnessData[0].data = [];
                            this.hsSeenWitnessData[1].data = [];
                            this.hsSeenWitnessOption.xaxis.categories = [];
                            for ( let i = 0 ; i < this.hsDetails.witnessesHistory.length ; i++ ) {
                                this.hsSeenWitnessOption.xaxis.categories.push(this.getDateText(this.hsDetails.witnessesHistory[i].timeRef));
                                this.hsSeenWitnessData[0].data.push(this.hsDetails.witnessesHistory[i].countWitnesses);
                                this.hsSeenWitnessData[1].data.push(this.hsDetails.witnessesHistory[i].seletedWitness);
                            }
                            // refresh reward charts
                            this.hsSeenRewardData[0].data = [];
                            this.hsSeenRewardData[1].data = [];
                            this.hsSeenRewardData[2].data = [];
                            this.hsSeenRewardOption.xaxis.categories = [];
                            for ( let i = 0 ; i < this.hsDetails.rewardHistories.length ; i++ ) {
                                this.hsSeenRewardOption.xaxis.categories.push(this.getDateText(this.hsDetails.witnessesHistory[i].timeRef));
                                this.hsSeenRewardData[0].data.push((this.hsDetails.rewardHistories[i].witnessReward/1000000).toFixed(0));
                                this.hsSeenRewardData[1].data.push((this.hsDetails.rewardHistories[i].beaconReward/1000000).toFixed(0));
                                this.hsSeenRewardData[2].data.push((this.hsDetails.rewardHistories[i].dataReward/1000000).toFixed(0));
                            }
                            this.loading = false;                               
                        } else {
                            this.hsDetails = null;
                            this.loading = false;                               
                        }
                    }).catch((err) =>{
                        this.hsDetails = null;
                        this.loading = false;                               
                    });
            }
        },
        mounted() {
            // hotspot around
            this.$root.$off("message-context-hotspot-update");
            this.$root.$on("message-context-hotspot-update", (hotspot:HotspotContext) => {
                if ( hotspot == undefined ) {
                    this.ctx = {
                        id: '',
                        identity: null,
                        details: null,
                        isDetailed: false,
                    };
                    this.hsDetails = null;
                } else {
                    this.updateHotspot(hotspot);
                    if ( hotspot.isDetailed ) { 
                        this.loadHotspot(hotspot.id);
                    } else {
                        this.hsDetails = null;
                        this.lnsSeenTrafficData[0].data = [];
                        this.lnsSeenTrafficOption.xaxis.categories = [];
                        this.hsSeenWitnessData[0].data = [];
                        this.hsSeenWitnessData[1].data = [];
                        this.hsSeenWitnessOption.xaxis.categories = [];
                        this.hsSeenRewardData[0].data = [];
                        this.hsSeenRewardData[1].data = [];
                        this.hsSeenRewardData[2].data = [];
                        this.hsSeenRewardOption.xaxis.categories = [];
                    } 
                }
            });
            // reset
            this.init = false;
        },
        beforeDestroy() {
            this.init = false;
        },
    })
</script>