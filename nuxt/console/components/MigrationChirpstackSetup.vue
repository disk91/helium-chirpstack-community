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


        <b-form-select v-model.number="targetTenant" 
                        :options="sourceOption"
                        size="sm"
                        class="mb-3"
                    ></b-form-select>




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
import { TenantDcBalancesReqItf } from 'vue/types/tenantSearch';


export default Vue.extend({
    name: "MigrationChirpstackSetup",
    props : {
        consoleObject : HeliumConsoleService,
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

        };
    },
    methods : {
        reset() {
            this.errorMessage = "";
        },
        testConnection() {
/*
            let self = this;
            this.consoleObject.testConnection()
                .then ((result:string) => {
                    if ( result == "" ) {
                        self.errorMessage = '';
                        self.cnxState = 1;
                        self.loadDevices = true;
                        this.consoleObject.getDevices()
                        .then ((result:string) => {
                            self.loadDevices = false;
                            if ( result == "" ) {
                                self.$root.$emit("message-migration-validate-api", "");
                                self.$root.$emit("message-migration-next-tab", "");
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
                */
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