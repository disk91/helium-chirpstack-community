<template>
    <div>       
        <b-row>
            <b-col cols="11"
                   class="bg-light p-4 m-3"
                   style="border-radius: 0.5rem;margin-top:5px;"
            >
            <div v-html="$t('mig_setup_apiexplained_1')"></div> <br/>
            <br/>
            <div v-html="$t('mig_setup_apiexplained_2')"></div> <br/>
            </b-col>
        </b-row>

        <b-row>
            <b-col cols="8"
            >
                <img src="/static/front/create_api_key.png" style="width: 100%;"/>
            </b-col>
            <b-col cols="3"
            >
                <img src="/static/front/get_api_key.png" style="width: 100%;"/>
            </b-col>
        </b-row>

        <b-row>
            <b-col cols="11"
                   class="bg-light p-4 m-3"
                   style="border-radius: 0.5rem;margin-top:5px;"
            >
             <div v-html="$t('mig_setup_apiexplained_3')"></div> 
            </b-col>
        </b-row>

        <b-row>
            <b-col cols="11"
                   class="bg-light p-4 m-3"
                   style="border-radius: 0.5rem;margin-top:5px;"
            >
            <b-row>
                <b-col cols="5">
                    {{ $t('mig_setup_setkey') }} <br/>
                    <b-form-input v-model="apiKey"
                            type="text" 
                            :placeholder="$t('mig_setup_setkeyinput')"
                            class="mb-2"
                            size="sm"
                    ></b-form-input>
                </b-col>
                <b-col cols="4">
                    {{ $t('mig_setup_setapi') }} <br/>
                    <b-form-select v-model.number="apiService" 
                        :options="apiServiceOptions"
                        @change="onUpdateApiService"
                        size="sm"
                        class="mb-3"
                    ></b-form-select>
                    <b-form-input v-model="apiServiceOtherUrl"
                            v-if="showOtherApiService"
                            type="text" 
                            :placeholder="$t('mig_setup_setotherapi')"
                            class="mb-2"
                            size="sm"
                    ></b-form-input>
                </b-col>
                <b-col cols="2"  style="font-size:0.8rem;margin-top:5px;">
                    <br/>  
                    <b-button block
                        variant="primary"
                        size="sm"
                        @click="validateConsoleAccess()"
                        style="text-align: right;font-size:0.8rem;"
                        :disabled="!isValidApi()"
                    >
                        {{ $t('mig_setup_testapi') }}
                        <b-icon icon="arrow-right-circle" variant="white"></b-icon>
                    </b-button>
                </b-col>
                <b-col cols="1" style="font-size:1rem;margin-top:2px;">
                    <br/>
                    <b-icon v-if="cnxState===0" icon="question-circle" variant="secondary"></b-icon>
                    <b-icon v-if="(cnxState===1)" icon="check-circle" variant="success"></b-icon>
                    <b-icon v-if="(cnxState===2)" icon="x-circle" variant="danger"></b-icon>
                </b-col>
            </b-row>
            <b-row>
                <b-col cols="5" style="font-size:0.7rem; color:rgb(139, 114, 31)">{{ $t('mig_setup_auth_data_tx') }}</b-col>
                <b-col cols="4"></b-col>
                <b-col cols="3">
                    <b-card-text class="text-danger" style="text-align:left;font-size:0.8rem;margin-top:-10px;" >
                        {{ $t(errorMessage)}} 
                    </b-card-text>
                </b-col>
            </b-row>
            </b-col>
        </b-row>

        <b-modal id="LoadDevicesModal" 
                 centered 
                 content-class="shadow"
                 v-model="loadDevices"
                 header-border-variant="white"
                 header-text-variant="dark"
                 footer-border-variant="white"
                 class="text-center"
                 hide-footer
                 hide-header
        >
            <b-card-text class="text-dark" style="text-align:center;font-size:1.5rem;" >
                {{ $t('mig_loading_devices') }}
                <b-icon icon="three-dots" animation="cylon" font-scale="2" style="position:relative;top:10px;left:5px;"></b-icon>
            </b-card-text>
        </b-modal>

    </div>
</template>
<style>
div#LoadDevicesModal___BV_modal_content_ {
    -webkit-border-radius: 1rem !important;
    -moz-border-radius: 1rem !important;
    border-radius: 1rem !important; 
}
</style>

<script lang="ts">
import Vue from 'vue'
import { HeliumConsoleService } from '~/services/console';


export default Vue.extend({
    name: "MigrationHeliumSetup",
    props : {
        consoleObject : HeliumConsoleService,
    },
    components: {
    },
    data() {
        return {
            apiKey : "" as string,
            apiService : "https://console.helium-iot.eu/api/" as string,
            apiServiceOptions : [
              {
                value : "https://console.helium-iot.eu/api/",
                text : "Helium IoT.eu"
              },
              {
                value : "https://console.helium.com/api/",
                text : "Foundation Console"
              },
              {
                value : "https://console-vip.helium.com/api/",
                text : "Vip Console"
              },
              { value : "https://helium-console-dev.herokuapp.com/api/",
                text : "Helium Testnet Console"
              },
              {
                value : "",
                text : "Other"
              },
            ],
            showOtherApiService : false as boolean,
            apiServiceOtherUrl : "" as string,
            cnxState : 0 as number,
            errorMessage : "" as string,
            loadDevices : false,
        };
    },
    methods : {
        reset() {
            this.errorMessage = "";
            this.cnxState = 0;
            this.loadDevices = false;
        },
        onUpdateApiService() {
            if ( this.apiService == "" ) {
                this.showOtherApiService = true;
            } else {
                this.showOtherApiService = false;
            }
        },
        isValidApi() : boolean {
            if  ( this.apiKey.length > 0 ) {
                if ( this.apiService != "" ) return true;
                if ( this.apiServiceOtherUrl.length > 0 ) {
                    // must end by /api/
                    if ( this.apiServiceOtherUrl.endsWith("/api/") ) return true;
                }
            }
            return false;
        },
        validateConsoleAccess() {
            if ( this.apiKey.length > 0 ) {
                this.consoleObject.setApiKey(this.apiKey);
                if ( this.apiService != "" ) this.consoleObject.setApiUrl(this.apiService);
                else if ( this.apiServiceOtherUrl.length > 0 ) {
                     this.consoleObject.setApiUrl(this.apiServiceOtherUrl);
                }
                else return;
                this.testConnection();
            } 
        },
        testConnection() {
            let self = this;
            this.consoleObject.testConnection()
                .then ((result:string) => {
                    if ( result == "" ) {
                        self.errorMessage = '';
                        self.cnxState = 1;
                        self.loadDevices = true;
                        this.consoleObject.getDevices()
                        .then ((result:string) => {
                            if ( result == "" ) {
                                this.consoleObject.getFunctions()
                                .then ((result:string) => {
                                    if ( result == "" ) {
                                        this.consoleObject.getFlows()
                                        .then ((result:string) => {
                                            self.loadDevices = false;
                                            if ( result == "" ) {
                                                this.consoleObject.getIntegration()
                                                .then ((result:string) => {
                                                    if ( result == "" ) {
                                                        self.loadDevices = false;
                                                        self.$root.$emit("message-migration-validate-api", "");
                                                        self.$root.$emit("message-migration-next-tab", "");
                                                    } else {
                                                        self.errorMessage = 'mig_err_'+result;
                                                        self.cnxState = 2;
                                                    }
                                                });
                                            } else {
                                                self.errorMessage = 'mig_err_'+result;
                                                self.cnxState = 2;
                                            }
                                        })
                                    } else {
                                        self.errorMessage = 'mig_err_'+result;
                                        self.cnxState = 2;
                                    }
                                })
                            } else {
                                self.errorMessage = 'mig_err_'+result;
                                self.cnxState = 2;
                            }
                        })
                    } else {
                        self.errorMessage = 'mig_err_'+result;
                        self.cnxState = 2;
                    }               
                })
        },
    },
    mounted() {
        this.$root.$on("message-migration-cancel", (msg:any) => {
            this.reset();
        });
    },
    created () {
    },
    beforeDestroy () {
    },
})
</script>