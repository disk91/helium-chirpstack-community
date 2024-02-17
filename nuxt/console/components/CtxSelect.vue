<template>
  <div>
    <b-row v-if="init">
        <b-col cols="12">
            hello
        </b-col>
    </b-row>
  </div>
</template>

<script lang="ts">
    import Vue from 'vue';
    import { DeviceSearchGetItf } from 'vue/types/context';

    interface context {
        init: boolean,
        loading: boolean,
        devices: DeviceSearchGetItf[],
    }

    export default Vue.extend({
        name: 'CtxSelect',
        components: {
        },
        data() : context {
            return {
                init: false,
                loading: false,
                devices: [],
            }
        },
        methods: {
            searchHotspot(searchTerm: string, tenantId: string) {
                let config = {
                    headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                    }
                };
                this.loading = true;
                this.$axios.get<DeviceSearchGetItf[]>(this.$config.ctxDeviceSearchGet+'/'+btoa(searchTerm)+'/'+tenantId+'/',config)
                    .then((response) =>{
                        if (response.status == 200 ) {
                            this.devices = response.data;   
                            this.loading = false;        
                            console.log(this.devices);                       
                        } else {
                            this.devices = [];
                            this.loading = false;                               
                        }
                    }).catch((err) =>{
                        this.devices = [];
                        this.loading = false;                               
                    });
            }
        },
        mounted() {
            // reset
            this.init = true;
            let tenantId = this.$store.state.currentTenant;

            tenantId= '52f14cd4-c6f1-4fbd-8f87-4025e1d49242';
            if ( tenantId != undefined && tenantId != null && tenantId.length > 5 ) {
                this.searchHotspot(' ',tenantId)
            } 
        },
        beforeDestroy() {
            this.init = false;
        },
    })
</script>