<template>
  <div v-if="init">
    <b-row>
        <b-col cols="10">
            <b-form-group class="mb-1 mr-1">
                <b-form-input 
                        v-model="searchKey"
                        type="text" size="sm"
                        @update="updateSearch()"
                ></b-form-input>
                <b-form-text style="font-size:0.5rem;">{{ $t('ctx_search_dev') }}</b-form-text>
            </b-form-group>
        </b-col>
        <b-col cols="2">
            <b-spinner v-if="loading" class="float-right" label="Floated Right"></b-spinner>
        </b-col>
    </b-row>
    <b-row>
        <b-col cols="8">
            <b-form-select v-model="selected" :options="options" size="sm" class="mt-1" :select-size="4"></b-form-select>
        </b-col>
        <b-col cols="4">
            <b-button 
              size="sm"
              class="mt-1"
              variant="outline-secondary"
              block
              @click="onSelectDevice"
            >
              {{ $t('ctx_device_select') }}
            </b-button>
        </b-col>
    </b-row>
  </div>
</template>

<script lang="ts">
    import Vue from 'vue';
    import { DeviceSearchGetItf, DataContext } from 'vue/types/context';

    interface SelectOption {
        value: DeviceSearchGetItf,
        text: string,
    }

    interface context {
        init: boolean,
        loading: boolean,
        devices: DeviceSearchGetItf[],
        selected: DeviceSearchGetItf,
        options: SelectOption[],
        searchKey: string,
        context: DataContext | undefined,
        tmout: any | null,
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
                selected: {} as DeviceSearchGetItf,
                options: [],
                searchKey: '',
                context: undefined,
                tmout: null,
            }
        },
        methods: {
            onSelectDevice() {
                this.$root.$emit("message-context-select-change", this.selected);
            },
            updateSearch() {
                // limit rate
                if ( !this.loading ) {
                    if ( this.tmout != null ) clearTimeout(this.tmout);
                    this.tmout = setTimeout(
                        () => {
                                if ( this.context == undefined ) return; 
                                if ( this.searchKey.length == 0 ) {
                                    this.searchHotspot(' ',this.context.tenantID);
                                } else {
                                    this.searchHotspot(this.searchKey,this.context.tenantID);
                                }
                                this.tmout = null;
                        }, 250);
                }
            },
            searchHotspot(searchTerm: string, tenantId: string) {
                this.loading = true;
                let config = {
                    headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                    }
                };
                this.options = [];
                this.$axios.get<DeviceSearchGetItf[]>(this.$config.ctxDeviceSearchGet+'/'+btoa(searchTerm)+'/'+tenantId+'/',config)
                    .then((response) =>{
                        if (response.status == 200 ) {
                            response.data.forEach( (r) => {
                                let opt : SelectOption = {
                                    value: r,
                                    text: r.deviceEui.toLowerCase()
                                };
                                this.options.push(opt);
                                if ( this.context!=null && this.context.device != undefined && r.deviceEui.toLowerCase() === this.context.device.devEui.toLowerCase() ) {
                                    this.selected = r;
                                }
                            });
                            this.loading = false;        
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
            this.loading = true;
            this.$root.$off("message-context-select-update");
            this.$root.$on("message-context-select-update", (context:DataContext) => {
                this.context = context;
                this.searchHotspot(' ',this.context.tenantID);
            });
        },
        beforeDestroy() {
            this.$root.$off("message-context-select-update");
            this.init = false;
        },
    })
</script>