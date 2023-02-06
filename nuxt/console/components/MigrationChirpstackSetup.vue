<template>
    <div>       

        <b-card>
            <b-row><b-col cols="12" class="mb-2" style="font-weight: 600;">
                {{ $t('mig_data_select')}}
            </b-col></b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_labels') }}</b-col>
                <b-col cols="2" class="text-primary">{{ targetLabel.name }}</b-col>

                <b-col cols="2">{{ $t('mig_loaded_eu868') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countEU868(targetLabel.id) }}</b-col>
                <b-col cols="2">{{ $t('mig_loaded_au915') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countAU915(targetLabel.id) }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_devices') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countDevices(targetLabel.id) }}</b-col>

                <b-col cols="2">{{ $t('mig_loaded_us915') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countUS915(targetLabel.id) }}</b-col>
                <b-col cols="2">{{ $t('mig_loaded_as923') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countAS923(targetLabel.id) }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_active') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countActive(targetLabel.id) }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_live') }}</b-col>
                <b-col cols="2" class="text-success">{{ consoleObject.countInOui(targetLabel.id) }}</b-col>
                <b-col cols="2">{{ $t('mig_loaded_impact') }}</b-col>
                <b-col v-if="consoleObject.countIncompatible(targetLabel.id) > 0" cols="2" class="text-danger">{{ consoleObject.countIncompatible(targetLabel.id) }}</b-col>
                <b-col v-if="consoleObject.countIncompatible(targetLabel.id) == 0" cols="2" class="text-success">{{ consoleObject.countIncompatible(targetLabel.id) }}</b-col>
                <b-col cols="2">{{ $t('mig_loaded_uregion') }}</b-col>
                <b-col v-if="consoleObject.countUnknownRegion(targetLabel.id) > 0" cols="2" class="text-warning">{{ consoleObject.countUnknownRegion(targetLabel.id) }}</b-col>
                <b-col v-if="consoleObject.countUnknownRegion(targetLabel.id) == 0" cols="2" class="text-success">{{ consoleObject.countUnknownRegion(targetLabel.id) }}</b-col>
            </b-row>
        </b-card>

        <b-row class="mx-1 my-3">
            <b-col cols="12"
                   class="bg-light p-3"
                   style="border-radius: 0.5rem;"
            >
                <b-row>
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_tenant_select')"></div>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col cols="4" class="mb-3">
                        <b-form-select v-model.number="targetTenant" 
                                :options="sourceOption"
                                size="sm"
                                class="mt-2"
                                :disabled="selectTenantDisabled"
                        ></b-form-select>
                        <b-form-text style="font-size:0.6rem;color:#DC3545 !important;">{{ $t(errorMessage) }}</b-form-text>
                    </b-col>
                    <b-col cols="2">
                        <b-button block
                            variant="primary"
                            size="sm"
                            @click="selectTenant()"
                            style="text-align: right;font-size:0.8rem;"
                            class="mt-2"
                            :disabled="selectTenantDisabled"
                        >
                            {{ $t('mig_select_tenant') }}
                            <b-icon icon="arrow-right-circle" variant="white"></b-icon>
                        </b-button>
                    </b-col>
                    <b-col cols="6" class="mt-2">
                        <b-icon v-if="(apiState==0)" icon="question-circle" variant="secondary"></b-icon>
                        
                        <b-icon v-if="(apiState==1)" icon="check-circle" variant="success"></b-icon>
                        <span v-if="(apiState==1)" class="text-success">{{ $t(apiMessage) }}</span>

                        <b-icon v-if="(apiState==2)" icon="x-circle" variant="danger"></b-icon>
                        <span v-if="(apiState==2)" class="text-danger">{{ $t(apiMessage) }}</span>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_tenant_api')"></div>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>

        <b-card v-if="loadedChirpstack">
            <b-row><b-col cols="12" class="mb-2" style="font-weight: 600;">
                {{ $t('mig_loaded_chirpdata')}}
            </b-col></b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_devprofile') }}</b-col>
                <b-col cols="2" class="text-primary">{{ chirpstackObject.countDeviceProfile("",false) }}</b-col>

                <b-col cols="1">{{ $t('mig_loaded_eu868') }}</b-col>
                <b-col cols="2" class="text-primary">
                    <span class="text-dark">OTAA</span> {{ chirpstackObject.countDeviceProfile("EU868", true) }} /
                    <span class="text-dark">ABP</span>  {{ chirpstackObject.countDeviceProfile("EU868", false) }} 
                </b-col>
                <b-col cols="1">{{ $t('mig_loaded_au915') }}</b-col>
                <b-col cols="2" class="text-primary">
                    <span class="text-dark">OTAA</span>  {{ chirpstackObject.countDeviceProfile("AU915", true) }} /
                    <span class="text-dark">ABP</span> {{ chirpstackObject.countDeviceProfile("AU915", false) }} 
                </b-col>
            </b-row>
            <b-row>
                <b-col cols="2"></b-col>
                <b-col cols="2" class="text-primary"></b-col>

                <b-col cols="1">{{ $t('mig_loaded_us915') }}</b-col>
                <b-col cols="2" class="text-primary">
                    <span class="text-dark">OTAA</span>  {{ chirpstackObject.countDeviceProfile("US915", true) }} /
                    <span class="text-dark">ABP</span> {{ chirpstackObject.countDeviceProfile("US915", false) }} 
                </b-col>
                <b-col cols="1">{{ $t('mig_loaded_as923') }}</b-col>
                <b-col cols="2" class="text-primary">
                    <span class="text-dark">OTAA</span>  {{ chirpstackObject.countDeviceProfile("AS923", true) }} /
                    <span class="text-dark">ABP</span> {{ chirpstackObject.countDeviceProfile("AS923", false) }} 
                </b-col>
            </b-row>

        </b-card>

        <b-row  v-if="loadedChirpstack" class="mx-1 my-3">
            <b-col cols="12"
                   class="bg-light p-3"
                   style="border-radius: 0.5rem;"    
            >
                <b-row>
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_devprofexplained_1')"></div>
                    </b-col>
                </b-row>

                <b-row v-if="consoleObject.countInOui() != 0" class="mt-2 mb-1">
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_devprofexplained_2')"></div>
                    </b-col>
                </b-row>
                <b-row v-if="consoleObject.countInOui() != 0">
                    <b-col cols="2">
                        <b-button block
                            :variant="getVariant('EU868',false)"
                            size="sm"
                            @click="createDevProfile('EU868', false)"
                            style="text-align: center;font-size:0.8rem;"
                            class=""
                            :disabled="existDevProfile('EU868',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.8rem;"></b-icon>
                            ABP - EU868
                        </b-button>
                    </b-col>
                    <b-col cols="2">
                        <b-button block
                            :variant="getVariant('US915',false)"
                            size="sm"
                            @click="createDevProfile('US915', false)"
                            style="text-align: center;font-size:0.8rem;"
                            class=""
                            :disabled="existDevProfile('US915',false)"
                        >
                        <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.8rem;"></b-icon>
                            ABP - US915
                        </b-button>
                    </b-col>
                    <b-col cols="2">
                        <b-button block
                            :variant="getVariant('AU915',false)"
                            size="sm"
                            @click="createDevProfile('AU915', false)"
                            style="text-align: center;font-size:0.8rem;"
                            class=""
                            :disabled="existDevProfile('AU915',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.8rem;"></b-icon>
                            ABP - AU915
                        </b-button>
                    </b-col>
                    <b-col cols="2">
                        <b-button block
                            :variant="getVariant('AS923',false)"
                            size="sm"
                            @click="createDevProfile('AS923', false)"
                            style="text-align: center;font-size:0.8rem;"
                            class=""
                            :disabled="existDevProfile('AS923',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.8rem;"></b-icon>
                            ABP - AS923
                        </b-button>
                    </b-col>
                </b-row>

                <b-row class="mt-2 mb-1">
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_devprofexplained_3')"></div>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col cols="2">
                        <b-button block
                            :variant="getVariant('EU868',true)"
                            size="sm"
                            @click="createDevProfile('EU868', true)""
                            style="text-align: center;font-size:0.8rem;"
                            class=""
                            :disabled="existDevProfile('EU868',true)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.8rem;"></b-icon>
                            OTAA - EU868
                        </b-button>
                    </b-col>
                    <b-col cols="2">
                        <b-button block
                            :variant="getVariant('US915',true)"
                            size="sm"
                            @click="createDevProfile('US915', true)""
                            style="text-align: center;font-size:0.8rem;"
                            class=""
                            :disabled="existDevProfile('US915',true)"
                        >
                        <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.8rem;"></b-icon>
                            OTAA - US915
                        </b-button>
                    </b-col>
                    <b-col cols="2">
                        <b-button block
                            :variant="getVariant('AU915',true)"
                            size="sm"
                            @click="createDevProfile('AU915', true)"
                            style="text-align: center;font-size:0.8rem;"
                            class=""
                            :disabled="existDevProfile('AU915',true)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.8rem;"></b-icon>
                            OTAA - AU915
                        </b-button>
                    </b-col>
                    <b-col cols="2">
                        <b-button block
                            :variant="getVariant('AS923',true)"
                            size="sm"
                            @click="createDevProfile('AS923', true)"
                            style="text-align: center;font-size:0.8rem;"
                            class=""
                            :disabled="existDevProfile('AS923',true)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.8rem;"></b-icon>
                            OTAA - AS923
                        </b-button>
                    </b-col>
                </b-row>


            </b-col>
        </b-row>

        <b-card v-if="loadedChirpstack">
            <b-row><b-col cols="12" class="mb-2" style="font-weight: 600;">
                {{ $t('mig_loaded_chirpdata')}}
            </b-col></b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_applications') }}</b-col>
                <b-col cols="2" class="text-primary">{{ chirpstackObject.countApplications() }}</b-col>
            </b-row>
        </b-card>

        <b-row  v-if="loadedChirpstack" class="mx-1 my-3">
            <b-col cols="12"
                   class="bg-light p-3"
                   style="border-radius: 0.5rem;"    
            >
                <b-row>
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_appexplained_1')"></div>
                    </b-col>
                </b-row>

                <b-row>

                    <b-col cols="3">
                        <b-button block
                            variant="primary"
                            size="sm"
                            @click="addApplication()"
                            style="text-align: right;font-size:0.8rem;"
                            class="mt-2"
                            :disabled="createApplicationDisabled"
                        >
                            <b-icon icon="plus-circle" variant="white"></b-icon>
                            {{ $t('mig_add_application') }}
                        </b-button>
                    </b-col>

                    <b-col cols="2" class="mt-2">
                        <div v-html="$t('mig_setup_defaultapp')"></div>
                    </b-col>

                    <b-col cols="3" class="mb-3">
                        <b-form-select v-model="targetApplication" 
                                :options="applicationOption"
                                size="sm"
                                class="mt-2"
                                :disabled="selectApplicationDisabled"
                        ></b-form-select>
                        <b-form-text style="font-size:0.6rem;color:#DC3545 !important;">{{ $t(appErrorMessage) }}</b-form-text>
                    </b-col>

                    <b-col cols="3">
                        <b-button block
                            variant="primary"
                            size="sm"
                            @click="selectApplication()"
                            style="text-align: right;font-size:0.8rem;"
                            class="mt-2"
                            :disabled="selectApplicationDisabled"
                        >
                            {{ $t('mig_select_application') }}
                            <b-icon icon="arrow-right-circle" variant="white"></b-icon>
                        </b-button>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>


        <b-modal id="LoadChirpsatckModal" 
                 centered 
                 content-class="shadow"
                 v-model="loadChirpstack"
                 header-border-variant="white"
                 header-text-variant="dark"
                 footer-border-variant="white"
                 class="text-center"
                 hide-footer
                 hide-header
        >
            <b-card-text class="text-dark" style="text-align:center;font-size:1.5rem;" >
                {{ $t('mig_loading_chirpstack') }}
                <b-icon icon="three-dots" animation="cylon" font-scale="2" style="position:relative;top:10px;left:5px;"></b-icon>
            </b-card-text>
        </b-modal>

        <b-modal id="NewApplicationName" 
                 centered 
                 content-class="shadow"
                 v-model="newAppNameModal"
                 header-border-variant="white"
                 header-text-variant="dark"
                 footer-border-variant="white"
                 class="text-center"
                 button-size="sm"
                 hide-header
                 @ok="createNewApplication"
        >
            <b-card-text class="text-dark" style="text-align:center;font-size:1.2rem;" >
                {{ $t('mig_new_app_setname') }}
            </b-card-text>
            <b-form-input v-model="newAppName"
                          type="text" 
                          :placeholder="$t('mig_new_app_expl')"
                          class="mb-2"
                          size="sm"
            ></b-form-input>

        </b-modal>


    </div>
</template>
<style>
div#NewApplicationName___BV_modal_content_ {
    -webkit-border-radius: 1rem !important;
    -moz-border-radius: 1rem !important;
    border-radius: 1rem !important; 
}
</style>

<script lang="ts">
import Vue from 'vue'
import { HeliumConsoleService } from '~/services/console';
import { TenantDcBalancesReqItf, TenantApiKeyRespItf } from 'vue/types/tenantSearch';
import { ChirpstackService } from '~/services/chirpstack';
import { LabelItf } from 'vue/types/proxy';
import { Application } from 'vue/types/chirpstack';

export default Vue.extend({
    name: "MigrationChirpstackSetup",
    props : {
        consoleObject : HeliumConsoleService,
        chirpstackObject : ChirpstackService,
    },
    components: {
    },
    async fetch() {

        // Get the possible tenants for the target
        let config = {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
            }
        };
        this.isBusy = true;
        this.$axios.get<TenantDcBalancesReqItf[]>(this.$config.tenantsDcGet,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.ownedTenants = response.data;
                  this.sourceOption=[];
                  if ( this.ownedTenants.length == 0 ) {
                    this.errorMessage = 'mig_err_empty_tenants';
                  }
                  for ( let i = 0 ; i < this.ownedTenants.length ; i++ ) {
                    this.sourceOption.push( {
                        value: i,
                        text: this.ownedTenants[i].tenantName,
                    })
                  }
                  this.isBusy = false;
                }
            }).catch((err) =>{
               this.errorMessage = 'error_load_dc_tenants';
               this.ownedTenants = [];
            })

    },
    data() {
        return {
            isBusy : false as boolean,
            errorMessage : "" as string,
            ownedTenants : [] as TenantDcBalancesReqItf[],
            sourceOption : [] as any,
            targetTenant : 0 as number,
            apiState : 0 as number,
            apiMessage : '',
            loadChirpstack : false as boolean,
            loadedChirpstack : false as boolean,
            targetLabel : {} as LabelItf,
            selectTenantDisabled : false as boolean,
            applicationOption : [] as any,
            targetApplication : 0 as number,
            appErrorMessage : "" as string,
            selectApplicationDisabled : false as boolean,
            createApplicationDisabled : false as boolean,
            newAppNameModal : false as boolean,
            newAppName : "" as string,
        };
    },
    methods : {
        reset() {
            this.errorMessage = "";
            this.appErrorMessage = "";
            this.apiMessage = "";
            this.apiState = 0;
            this.loadChirpstack = false;
            this.loadedChirpstack = false;
            this.selectTenantDisabled = false;
            this.targetLabel = {} as LabelItf;
            this.targetApplication = 0 as number;
            this.selectApplicationDisabled = false;
            this.createApplicationDisabled = false;
            this.newAppNameModal = false;
            this.newAppName = "";
        },
        selectTenant() {

            let config = {
                headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                'X-Chripstack-Bearer' : this.$store.state.chirpstackBearer,
                }
            };
            this.isBusy = true;

            let tenantUUID = this.ownedTenants[this.targetTenant].tenantUUID;

            this.apiMessage='';
            this.apiState=0;
            this.$axios.get<TenantApiKeyRespItf>(this.$config.tenantKeyCreate+'/'+tenantUUID+'/',config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.apiState=1;
                  this.apiMessage='mig_chip_api_ok';
                  this.chirpstackObject.setApiKey(response.data.tenantApiKey);
                  this.chirpstackObject.setTenantId(tenantUUID);

                  // Now we can get the data from chirpstack
                  this.loadChirpstack = true;
                  this.chirpstackObject.loadDeviceProfile()
                  .then ( (mess) => {
                    this.chirpstackObject.loadApplications()
                    .then ( (mess) => {
                        if ( mess == "" ) {
                            this.applicationOption = [];
                            for ( var i = 0 ; i < this.chirpstackObject.getApplications().length ; i++ ) {
                                this.applicationOption.push( {
                                    value: i,
                                    text: this.chirpstackObject.getApplications()[i].name,
                                });
                            };
                            if ( this.applicationOption.length > 0 ) {
                                this.targetApplication = 0;
                                this.selectApplicationDisabled = false;
                            } else {
                            this.selectApplicationDisabled = true;
                            }
                        } else {
                            this.appErrorMessage = mess;
                            this.selectApplicationDisabled = true;
                        }
                        this.isBusy = false;
                        this.loadChirpstack = false;
                        this.loadedChirpstack = true;
                        this.selectTenantDisabled = true;
                    });
                  });
                }
            }).catch((err) =>{
               this.apiState=2;
               this.apiMessage='mig_err_'+err.response.data.message;
               this.chirpstackObject.setApiKey("");
               this.loadedChirpstack = false;
            })
        },
        getVariant(zone:string,otaa:boolean) : string {
            let r : string = "primary";
            if ( !otaa && this.consoleObject.countInOui() == 0 ) return "secondary";
            if ( this.chirpstackObject.countDeviceProfile(zone,otaa) > 0 ) r = "secondary";
            else {
                if ( zone == 'EU868' && ( this.consoleObject.countEU868() == 0 && this.consoleObject.countUnknownRegion() == 0 ) ) r = "secondary"; 
                if ( zone == 'US915' && ( this.consoleObject.countUS915() == 0 && this.consoleObject.countUnknownRegion() == 0 ) ) r = "secondary"; 
                if ( zone == 'AS923' && ( this.consoleObject.countAS923() == 0 && this.consoleObject.countUnknownRegion() == 0 ) ) r = "secondary"; 
                if ( zone == 'AU915' && ( this.consoleObject.countAU915() == 0 && this.consoleObject.countUnknownRegion() == 0 ) ) r = "secondary"; 
            }
            return r;
        },
        createDevProfile(zone:string, otaa:boolean) {

            this.chirpstackObject.addDevProfile(zone,otaa,this.targetLabel.name)
            .then((response:string) => {
                if ( response.length == 0 ) {
                    this.chirpstackObject.loadDeviceProfile();
                } else {
                    console.log(response);
                }
            });

        },
        existDevProfile(zone:string, otaa:boolean) : boolean {
            return this.chirpstackObject.existsDevProfile(zone,otaa,this.targetLabel.name);
        },
        selectApplication() {
            // store selected app for next step
            this.chirpstackObject.setDefaultApplication(this.chirpstackObject.getApplications()[this.targetApplication]);
            this.selectApplicationDisabled = true;
            this.createApplicationDisabled = true;
        },
        addApplication() {
            // need to modal for the name and create it
            this.newAppNameModal = true;
        },
        createNewApplication() {
            if ( this.newAppName != "" ) {
                this.chirpstackObject.addApplication(this.newAppName)
                .then ( (mess) => {
                    if (mess == "" ) {
                        this.reloadApplication();
                        this.newAppName = "";
                    }
                });
            }
        },
        reloadApplication() {
            this.chirpstackObject.loadApplications()
            .then ( (mess) => {
                if ( mess == "" ) {
                    this.applicationOption = [];
                    for ( var i = 0 ; i < this.chirpstackObject.getApplications().length ; i++ ) {
                        this.applicationOption.push( {
                            value: i,
                            text: this.chirpstackObject.getApplications()[i].name,
                        });
                    };
                    if ( this.applicationOption.length > 0 ) {
                        this.targetApplication = 0;
                        this.selectApplicationDisabled = false;
                    } else {
                        this.selectApplicationDisabled = true;
                    }
                } else {
                    this.appErrorMessage = mess;
                    this.selectApplicationDisabled = true;
                }
            });
        },
    },
    mounted() {
        this.$root.$on("message-migration-validate-label", (msg:any) => {
            let l = this.consoleObject.getLabelById(this.consoleObject.getCurrentLabel());
            this.targetLabel = l;
        });
    },
    created () {
    },
    beforeDestroy () {
    },
})
</script>