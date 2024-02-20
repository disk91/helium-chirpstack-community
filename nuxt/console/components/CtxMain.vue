<template>
    <div>
    <b-row v-if="isBusy">
        <b-col cols="12">
            <b-button variant="primary" disabled>
                <b-spinner small type="grow"></b-spinner>
                {{ $t('ctx_loading_in_progress') }}
            </b-button>
        </b-col>
    </b-row>
    <b-row>
        <b-col cols="3">
            <b-row v-if="!isBusy">
                <b-col cols="12" class="py-1" style="font-size:0.8rem;">
                    <b-card
                        :header="(dctx.device)?dctx.device.tenantName:'unknown'"
                        class="ml-0 TenantInfo"
                    >
                    <CtxSelect/>
                    </b-card>
                </b-col>
            </b-row>
            <b-row v-if="!isBusy">
                <b-col cols="12" class="py-1" style="font-size:0.7rem;">
                    <b-card
                        :header="(dctx.device)?dctx.device.devName:'unknown'"
                        class="ml-0 TenantInfo"
                    >
                    <b-row class="py-1">
                        <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_deviceEui') }}
                        </b-col>
                        <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                        >
                            {{ (dctx.device)?dctx.device.devEui:'unknonwn' }}
                        </b-col>
                     </b-row>
                     <b-row class="py-1">
                        <b-col cols="4" class="bg-secondary text-white ml-1" style="margin-right:2px;border-radius: 0.2rem;text-align:left;">
                            {{ $t('ctx_lastSeen') }}
                        </b-col>
                        <b-col cols="7"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                        >
                            {{ getDevLastSeen() }}
                        </b-col>
                     </b-row>
                    </b-card>
                </b-col>
            </b-row>
            <b-row>
                <b-col cols="12" class="py-1" style="font-size:0.7rem;">
                    <b-card
                        :header="$t('ctx_hotspots_title')"
                        class="ml-0 TenantInfo"
                    >
                    <CtxHotspot/>
                    </b-card>
                </b-col>
            </b-row>
        </b-col>
        <b-col cols="9">
            <b-row>
                <b-col cols="12" class="py-1" style="font-size:0.8rem;">
                    <b-card
                        :header="(dctx.device)?dctx.device.devName:'unknown'"
                        class="ml-0 TenantInfo"
                    >
                        <LeafletMap/>
                    </b-card>
                </b-col>
            </b-row>
            <b-row>
                <b-col cols="12" class="py-1" style="font-size:0.7rem;">
                    <b-card
                        :header="$t('ctx_frames_title')"
                        class="ml-0 TenantInfo"
                    >
                        <CtxFrames/>
                    </b-card>
                </b-col>
            </b-row>
        </b-col>
    </b-row>
    </div>
</template>
<style>
 .TenantInfo .card-header  {
    font-size: 0.8rem;
    font-weight: 600;
    font-variant: small-caps;
}
</style>

<script lang="ts">
  import Vue from 'vue'
  import { GetDeviceFramesItf, HotspotIdent, DataContext, HotspotContext, DeviceSearchGetItf } from 'vue/types/context'

  interface context {
    errorMessage: string,
    isBusy: boolean,
    dctx: DataContext,
    test:string;
  };
  export default Vue.extend({
      name: "MainContext",
      components: { 
      },
      data() : context {
        return {
            errorMessage: '',
            isBusy: false,
            dctx: {
                device: undefined
            } as DataContext,
            test: "",
        }
      },
      async fetch() {
        let tenantId = this.$store.state.currentTenant;
        let deviceId = this.$store.state.currentDevice;
        if ( tenantId != undefined && tenantId != null && tenantId.length > 5 ) {
            if ( deviceId != undefined && deviceId != null && deviceId.length > 5 ) {
                this.loadData(tenantId,deviceId);
            } else {
                this.dctx.tenantID = tenantId;
                this.dctx.device = undefined;
                this.updateSubContent();
            }
        }
      },
      methods: {
        loadData(_tenantId: string, _deviceEui: string) {
            let config = {
                headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                }
            };
            this.errorMessage = '';
            this.isBusy = true;
            this.dctx.tenantID = _tenantId;
            this.$axios.get<GetDeviceFramesItf>(this.$config.ctxDevFramesGet+'/'+_deviceEui+'/',config)
                .then((response) =>{
                    if (response.status == 200 ) {
                    this.dctx.device = response.data;                

                    // calculate map borders
                    this.dctx.latS=1000, this.dctx.lonW=1000, this.dctx.latN=-1000, this.dctx.lonE=-1000;
                    this.dctx.device.hotspotAround.forEach( h => {
                        if ( h.lat < this.dctx.latS ) this.dctx.latS = h.lat;
                        if ( h.lng < this.dctx.lonW ) this.dctx.lonW = h.lng;
                        if ( h.lat > this.dctx.latN ) this.dctx.latN = h.lat;
                        if ( h.lng > this.dctx.lonE ) this.dctx.lonE = h.lng;
                    });
                    let margin : number = 0.1;
                    this.$axios.get<HotspotIdent[]>(this.$config.ctxHotspotAroundGet+'/'+(this.dctx.latN+margin)+'/'+(this.dctx.latS-margin)+'/'+(this.dctx.lonW-margin)+'/'+(this.dctx.lonE+margin)+'/',config)
                        .then((response) =>{
                            if (response.status == 200 ) {
                                this.dctx.hotspots = response.data;
                                this.isBusy = false;
                                this.updateSubContent();
                            } else {
                                // 204 case
                            }
                        }).catch((err) => {
                            this.dctx.hotspots = [];
                            this.isBusy = false;
                            this.updateSubContent();
                            this.errorMessage = 'ctx_error_load_hs_around';
                        })
                    } else {
                        // 204 response case
                        this.dctx.hotspots = [];
                        this.dctx.device = undefined;
                        this.isBusy = false;
                        this.updateSubContent();
                        this.errorMessage = 'ctx_error_load_hs_around';
                    }
                }).catch((err) =>{
                    this.dctx = {} as DataContext;
                    this.errorMessage = 'ctx_error_load_device';
                });
        },
        updateSubContent() {
            setTimeout( () => {
                this.$root.$emit("message-context-map-update", this.dctx);
                this.$root.$emit("message-context-select-update", this.dctx);
                this.$root.$emit("message-context-frames-update", this.dctx);
                this.$root.$emit("message-context-hotspot-update", undefined);
            },250);
        },
        getDevLastSeen() : string {
            if ( this.dctx.device == undefined ) return 'unknown';
            let now = Date.now();
            let delta = now - this.dctx.device.lastSeen;
            if ( delta < 1000 ) return 'now';
            if ( delta < 60000 ) return ""+Math.floor(delta / 1000)+"s ago";
            if ( delta < 3600000 ) return ""+Math.floor(delta / 60000)+"m ago";
            if ( delta < 48*3600000 ) return ""+Math.floor(delta / 3600000)+"h ago";
            if ( delta < 10*24*3600000 ) return ""+Math.floor(delta / (24*3600000) )+" days ago";
            let date = new Date(this.dctx.device.lastSeen);
            return ""+date.getFullYear()+"-"+(date.getMonth()+1).toString().padStart(2,'0')+"-"+date.getDate().toString().padStart(2,'0')+" "+date.getHours().toString().padStart(2,'0')+":"+date.getMinutes().toString().padStart(2,'0');
        },
        controlHotspotDetails(hotspotId:string, detailed:boolean) {
           // search Hotspot in list
           let hs : HotspotContext = {
               id: hotspotId,
               isDetailed : detailed,
               identity : null,
               details : null
           };
           for ( let i = 0 ; i < this.dctx.hotspots.length ; i++ ) {
                if( this.dctx.hotspots[i].hotspotId === hotspotId ) {
                    hs.identity = this.dctx.hotspots[i];
                    break;
                }
           }
           if ( detailed && this.dctx.device != undefined ) {
            for ( let i = 0 ; i < this.dctx.device.hotspotAround.length ; i++ ) {
                if( this.dctx.device.hotspotAround[i].gatewayId === hotspotId ) {
                    hs.details = this.dctx.device.hotspotAround[i];
                    break;
                }
            }
           }
           if ( !hs.isDetailed || hs.details != null ) {
                this.$root.$emit("message-context-hotspot-update", hs);
           }
        }
      },
      mounted() {
        // hotspot around
        this.$root.$off("message-context-ahotspot-update");
        this.$root.$on("message-context-ahotspot-update", (hotspotId:string) => {
            this.controlHotspotDetails(hotspotId,false);
        });
        // hotspot used
        this.$root.$off("message-context-uhotspot-update");
        this.$root.$on("message-context-uhotspot-update", (hotspotId:string) => {
            this.controlHotspotDetails(hotspotId,true);
        });
        // change device
        this.$root.$off("message-context-select-change");
        this.$root.$on("message-context-select-change", (newDevice:DeviceSearchGetItf) => {
            this.loadData(newDevice.tenantUUID,newDevice.deviceEui.toLowerCase());
        })
      }
   })
  
</script>
 