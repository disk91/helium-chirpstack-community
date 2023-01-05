<template>
    <div>
        <b-modal id="DataCreditTransfer" 
                 centered 
                 content-class="shadow"
                 v-model="show"
                 @show="resetForm"
                 header-border-variant="white"
                 header-text-variant="dark"
                 footer-border-variant="white"
                 class="text-center"
                 button-size="sm"
                 hide-footer
        >
            <template #modal-header>
              <h5 style="text-align:center;width:100%;margin-top:1rem;">{{$t('dc_trans_title')}}</h5>
            </template>

            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                       style="font-size:0.8rem;"
                       class="mb-2"
                >
                {{ $t('dc_trans_select_source') }}
                </b-col>
                <b-col cols="1"></b-col>
            </b-row>
            <b-row>
                <b-col cols="2"></b-col>
                <b-col cols="8"
                >
                    <b-form-select v-model.number="sourceTenant" 
                        :options="sourceOption"
                        size="sm"
                        class="mb-3"
                    ></b-form-select>
                </b-col>
                <b-col cols="2"></b-col>
            </b-row>

            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                    class="bg-light p-3"
                    style="border-radius: 0.8rem;"
                >
                    <b-row><b-col cols="1"></b-col><b-col cols="9">

                        <span class="text-primary" style="font-size:0.8rem;">
                            {{ $t('dc_trans_select_quantity') }}
                        </span>
                        <b-form-input v-model.number="quantity"
                                    type="number" 
                                    :placeholder="$t('dc_trans_quantity')"
                                    class="mb-2"
                                    size="sm"
                        ></b-form-input>
                    </b-col><b-col cols="1">
                        <img src="/static/front/dc_icon.svg" style="width: 20px; position: relative; top: 25px ; left:-24px; margin-right: 2px;"/>
                    </b-col><b-col cols="1"></b-col></b-row>
                </b-col>
                <b-col cols="1"></b-col>
            </b-row>

            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                       style="font-size:0.8rem;"
                       class="my-3"
                >
                {{ $t('dc_trans_select_dest') }}
                </b-col>
                <b-col cols="1"></b-col>
            </b-row>
            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                       style="font-size:0.8rem;font-weight: 600;"
                       class="my-2"
                >
                {{ $t('dc_trans_select_dest2') }}
                </b-col>
                <b-col cols="1"></b-col>
            </b-row>
            <b-row>
                <b-col cols="2"></b-col>
                <b-col cols="8"
                >
                    <b-form-select v-model.number="destTenant" 
                                    :options="destOption"
                                    size="sm"
                    ></b-form-select>
                </b-col>
                <b-col cols="2"></b-col>
            </b-row>
            <b-row class="mb-2 mt-4">
                <b-col cols="2"></b-col>
                <b-col cols="4" class="py-2" style="font-size:0.8rem;">
                    <b-button block
                        variant="outline-dark"
                        size="sm"
                        @click="cancelModal()"
                        style="text-align: left;font-size:0.8rem;"
                    >
                        <b-icon icon="reply" variant="secondary"></b-icon>
                        {{ $t('dc_trans_cancel') }}
                    </b-button>
                </b-col>
                <b-col cols="4" class="py-2" style="font-size:0.8rem;">
                    <b-button block
                        variant="primary"
                        size="sm"
                        @click="transferDc()"
                        style="text-align: right;font-size:0.8rem;"
                        :disabled="!canTransfer()"
                    >
                        {{ $t('dc_trans_make') }}
                        <b-icon icon="arrow-right-circle" variant="white"></b-icon>
                    </b-button>
                </b-col>
            </b-row>
            <b-card-text class="text-danger" style="text-align:center;" >
                {{ $t(errorMessageMod)}} 
            </b-card-text>
            <b-card-text class="text-success" style="text-align:center;">
                {{ $t(successMessageMod)}} 
            </b-card-text>
        </b-modal>

    </div>
</template>
<style>
div#DataCreditTransfer___BV_modal_content_ {
    -webkit-border-radius: 1rem !important;
    -moz-border-radius: 1rem !important;
    border-radius: 1rem !important; 
}
</style>

<script lang="ts">
import { tsImportEqualsDeclaration } from '@babel/types';
import { data } from 'browserslist';
import Vue from 'vue'
import { TenantDcBalancesReqItf, TenantDcTransferReqItf, TenantDcTransferRespItf } from 'vue/types/tenantSearch';

interface data {
    ownedTenants : TenantDcBalancesReqItf[],
    allTenants : TenantDcBalancesReqItf[],
    sourceOption : any,
    sourceTenant : number,
    quantity : BigInt,
    destOption : any,
    destTenant: number,
    isBusy : boolean,
    errorMessage : string,
    errorMessageMod : string,
    successMessageMod : string,
    show : boolean,
}

export default Vue.extend({
    name: "DataCreditTransfer",
    data() : data {
        return {
            ownedTenants : [],
            allTenants : [],
            sourceOption : [],
            sourceTenant : 0,
            quantity : undefined as any,
            destOption : [],
            destTenant : 0,
            isBusy : false,
            errorMessage : '',
            errorMessageMod : '',
            successMessageMod : '',
            show : false,
        };
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
                        text: this.ownedTenants[i].tenantName+' ('+this.ownedTenants[i].dcBalance+' DCs)',
                    })
                  }
                  this.isBusy = false;
                }
            }).catch((err) =>{
               this.errorMessageMod = 'error_load_dc_tenants';
               this.ownedTenants = [];
            })
        this.$axios.get<TenantDcBalancesReqItf[]>(this.$config.tenantsDcGet+'?notOwned=true',config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.allTenants = response.data;
                  this.destOption=[];
                  for ( let i = 0 ; i < this.allTenants.length ; i++ ) {
                    this.destOption.push( {
                        value: i,
                        text: this.allTenants[i].tenantName,
                    })
                  }
                  this.isBusy = false;
                }
            }).catch((err) =>{
               this.errorMessageMod = 'error_load_dc_tenants';
               this.allTenants = [];
            })
    },
    methods : {
        resetForm() {
            this.quantity = BigInt(0);
            this.sourceTenant = 0;
            this.destTenant = 0;
            this.ownedTenants = [];
            this.allTenants = []; 
            this.successMessageMod = '';
            this.errorMessageMod = '';
        },
        transferDc() {
            
            let config = {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
            }
        };
        let body : TenantDcTransferReqItf = {
            tenantSrcUUID : this.ownedTenants[this.sourceTenant].tenantUUID,
            tenantDestUUID : this.allTenants[this.destTenant].tenantUUID,
            dcs : this.quantity
        };
        
        this.isBusy = true;
        this.$axios.put<TenantDcTransferRespItf>(this.$config.tenantDcTrans,body,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.isBusy = false;
                  this.successMessageMod = this.$t('succes_transfer_dc1') as string;
                  this.successMessageMod += response.data.dcs;
                  this.successMessageMod += this.$t('succes_transfer_dc2');
                  var self = this;
                  setTimeout(function() { 
                        self.$data.show = false;
                        self.resetForm();
                        self.$root.$emit("message-close-transfer-dc", "");
                  }, 1500);
                }
            }).catch((err) =>{
               this.errorMessageMod = 'error_transfer_dc';
            })
        },
        cancelModal() {
            this.show = false;
            this.resetForm();
        },
        canTransfer() {
            if ( this.sourceTenant != this.destTenant && this.quantity > BigInt(0) ) {
                return true;
            }
            return false;
        }

    },
    mounted() {
        this.$root.$on("message-display-transfer-dc", (msg:any) => {
            this.resetForm();
            this.$fetch();
            this.show = true;
        });
    },
})
</script>