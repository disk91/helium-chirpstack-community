<template>
    <div>
        <b-row v-if="init && ctx != null">
            <b-col cols="12">
                <div>
                <b-table 
                sticky-header
                :items="frames" 
                :fields="fields" 
                :busy="!init"
                sort-by="rxTimeMs"
                :sort-desc=true
                responsive
                small
                striped
                hover
                headVariant="dark"
                style="font-size:8px;"
                @row-clicked="onRowHovered"
                >
                    <template #cell(rxTimeMs)="row">
                        {{ getLastSeenString(row.value) }} 
                    </template>
                    <template #cell(frameType)="row">
                        {{ getFrameType(row.value) }} 
                    </template>
                    <template #cell(hotspots)="row">
                        {{ row.value.length }} 
                    </template>
                    <template #cell(rssi)="row">
                        {{ getRssiRange(row) }} 
                    </template>
                    <template #cell(snr)="row">
                        {{ getBestSnr(row) }} 
                    </template>
                </b-table>
                </div>
            </b-col>
        </b-row>
    </div>
</template>

<script lang="ts">
    import Vue from 'vue';
    import { DataContext, FrameEntry } from 'vue/types/context'; 

    interface context {
        ctx: DataContext | null,
        frames: FrameEntry[],
        init: boolean,
        loading: boolean,
        fields: {
            key: string,
            label: any,
            sortable?: boolean,
            stickyColumn?: boolean,
            isRowHeader?: boolean,
        }[],
    }

    export default Vue.extend({
        name: 'CtxFrames',
        components: {
        },
        data() : context {
            return {
                ctx: null,
                frames: [],
                init: false,
                loading: false,
                fields : [
                    // NB sortable is killing the sticky header
                    {key: 'rxTimeMs', label: this.$t('ctx_frame_rxtime')},
                    {key: 'fCnt',  label: this.$t('ctx_frame_fcnt')},
                    {key: 'dr', label: this.$t('ctx_frame_dr')},
                    {key: 'dataSize', label: this.$t('ctx_frame_sz')},
                    {key: 'frameType', label: this.$t('ctx_frame_type')},
                    {key: 'hotspots', label: this.$t('ctx_frame_hscount')},
                    {key: 'rssi', label: this.$t('ctx_frame_rssi')},
                    {key: 'snr', label: this.$t('ctx_frame_snr')},
                ],
            }
        },
        methods: {
            onRowHovered(row:FrameEntry) {
                this.$root.$emit("message-context-frame-hightlight", row);
            },
            updateFrames(msg:DataContext) {
                this.ctx = msg;
                this.init = true;
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
                return ""+date.getFullYear()+"-"+(date.getMonth()+1).toString().padStart(2,'0')+"-"+date.getDate().toString().padStart(2,'0')+" "+date.getHours().toString().padStart(2,'0')+":"+date.getMinutes().toString().padStart(2,'0')+":"+date.getSeconds().toString().padStart(2,'0');
            },
            getDateText(ts: number) : string {
                let date = new Date(ts);
                return ""+date.getFullYear()+"-"+(date.getMonth()+1).toString().padStart(2,'0')+"-"+date.getDate().toString().padStart(2,'0')+" "+date.getHours().toString().padStart(2,'0')+":"+date.getMinutes().toString().padStart(2,'0')+":"+date.getSeconds().toString().padStart(2,'0');
            },
            getFrameType(type:number) : string {
                if ( type == 0 ) return "Uplink";
                if ( type == 1 ) return "Join Request";
                return "Unknown";
            },
            getRssiRange(row:any) : string {
                let frame : FrameEntry = row.item;
                if ( frame.hotspots.length == 0 ) return 'NA';
                let minRssi = 1000, maxRssi = -1000;
                frame.hotspots.forEach( (h) => {
                    if ( h.rssi > maxRssi ) maxRssi = h.rssi;
                    if ( h.rssi < minRssi ) minRssi = h.rssi;
                });
                return "[ "+minRssi+"dBm / "+maxRssi+"dBm"+" ]";
            },
            getBestSnr(row:any) : string {
                let frame : FrameEntry = row.item;
                if ( frame.hotspots.length == 0 ) return 'NA';
                let bestSnr = -1000;
                frame.hotspots.forEach( (h) => {
                    if ( h.snr > bestSnr ) bestSnr = h.snr;
                });
                return bestSnr.toFixed(2)+"dBm";
            }
        },
        mounted() {
            // hotspot around
            this.$root.$off("message-context-frames-update");
            this.$root.$on("message-context-frames-update", (hotspot:DataContext) => {
                if ( hotspot.device != undefined ) {
                    this.frames = hotspot.device.recentFrames;
                    this.updateFrames(hotspot);
                } else {
                    this.frames = [];
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