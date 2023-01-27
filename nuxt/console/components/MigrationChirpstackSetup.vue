<template>
    <div>       

        <b-card>
            <b-row><b-col cols="12" class="mb-2" style="font-weight: 600;">
                {{ $t('mig_loaded_data')}}
            </b-col></b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_labels') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countLabels() }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_devices') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countDevices() }}</b-col>
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

    </div>
</template>
<style>
</style>

<script lang="ts">
import Vue from 'vue'
import { HeliumConsoleService } from '~/services/console';
import { TenantDcBalancesReqItf, TenantApiKeyRespItf } from 'vue/types/tenantSearch';
import { ChirpstackService } from '~/services/chirpstack';

export default Vue.extend({
    name: "MigrationChirpstackSetup",
    props : {
        consoleObject : HeliumConsoleService,
        chirpstackObject : ChirpstackService,
    },
    components: {
    },
    async fetch() {
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

        };
    },
    methods : {
        reset() {
            this.errorMessage = "";
            this.apiMessage = "";
            this.apiState = 0;
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
                  this.isBusy = false;
                }
            }).catch((err) =>{
               this.apiState=2;
               this.apiMessage='mig_err_'+err.response.data.message;
               this.chirpstackObject.setApiKey("");
            })

        },
    },
    mounted() {
    },
    created () {
    },
    beforeDestroy () {
    },
})
</script>