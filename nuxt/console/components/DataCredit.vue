<template>
    <div>
        <b-row cols="3" class="mb-2">
            <b-col cols="3" class="py-2" style="font-size:0.8rem;">
                <b-button block
                    variant="outline-dark"
                    size="sm"
                    @click="onTransferDcClick()"
                    style="text-align: left;font-size:0.8rem;"
                    :disabled="! transferEnable"
                >
                    <b-icon icon="arrow-right-circle" variant="secondary"></b-icon>
                    {{ $t('dc_transfer') }}
                </b-button>
            </b-col>
            <b-col cols="3" class="py-2" style="font-size:0.8rem;">
                <b-button block
                    variant="primary"
                    size="sm"
                    @click="onPurchaseDcClick()"
                    style="text-align: left;font-size:0.8rem;"
                    :disabled="! stripeEnable"
                >
                <b-icon icon="credit-card" variant="white"></b-icon>
                    {{ $t('dc_purchase') }}
                </b-button>
            </b-col>
        </b-row>

        <b-row cols="3" class="mb-3">
            <b-col cols="8" class="py-1" style="font-size:0.8rem;">
                <b-card
                        :header="$t('dc_remain_dc_title')"
                        class="ml-0 DataCredit"
                >
                <b-row>
                    <b-col cols="5" class="bg-secondary text-white ml-3" style="margin-right:2px;border-radius: 0.2rem;">
                        {{ $t('dc_tenant_label') }}
                    </b-col>
                    <b-col cols="3" class="bg-secondary text-white" style="margin-right:2px;border-radius: 0.2rem;">
                        {{ $t('dc_balance_label') }}
                    </b-col>
                    <b-col cols="3" class="bg-secondary text-white" style="border-radius: 0.2rem;">
                        {{ $t('dc_minbal_label') }}
                    </b-col>
                </b-row>
                <b-row v-for="tenant in tenants"
                    v-bind:data="tenant"
                    v-bind:key="tenant.tenantUUID"
                    style="margin-top:2px;"
                >
                    <b-col cols="5"
                        class="ml-3 bg-light text-dark"
                        style="margin-right:2px;font-size:0.9rem;"
                    >
                        {{ tenant.tenantName }} 
                    </b-col>
                    <b-col cols="3"
                        style="text-align:right;margin-right:2px;font-size:0.9rem;"
                        class="text-info bg-light"
                    >
                        {{ tenant.dcBalance.toLocaleString("en-US") }} 
                        <img src="/static/front/dc_icon.svg" style="width: 13px; position: relative; top: -2px ; margin-right: 2px;"/>
                    </b-col>
                    <b-col cols="3"
                        style="text-align:right;font-size:0.9rem;"
                        class="text-info bg-light"
                    >
                        {{ tenant.minBalance.toLocaleString("en-US") }} 
                        <img src="/static/front/dc_icon.svg" style="width: 13px; position: relative; top: -2px ; margin-right: 2px;"/>
                    </b-col>
                </b-row>
                </b-card>
            </b-col>
            <b-col cols="4" class="py-1" style="font-size:0.8rem;">
                <b-card
                        :header="$t('dc_payment_dc_title')"
                        class=" DataCredit"
                >
                <span style="font-size:2.5rem;font-weight:300;">N/A</span>
                </b-card>
            </b-col>
        </b-row>
        <DataCreditTransfer/>
        <DataCreditPurchase/>
    </div>
</template>
<style>
 .DataCredit .card-header  {
    font-size: 0.8rem;
    font-weight: 600;
    font-variant: small-caps;
}
</style>

<script lang="ts">
import { tsImportEqualsDeclaration } from '@babel/types';
import Vue from 'vue'
import { TenantDcBalancesReqItf } from 'vue/types/tenantSearch';
import DataCreditTransfer from '~/components/DataCreditTransfer.vue';
import DataCreditPurchase from './DataCreditPurchase.vue';
import { TransactionConfigRespItf } from 'vue/types/transaction';

interface data {
    tenants : TenantDcBalancesReqItf[],
    isBusy : boolean,
    errorMessage : string,
    errorMessageMod : string,
    successMessageMod : string,
    isComplete: boolean,
    stripeEnable: boolean,
    transferEnable: boolean,
}

export default Vue.extend({
    name: "UserProfile",
    components: {
       'DataCreditTransfer' : DataCreditTransfer,
       'DataCreditPurchase' : DataCreditPurchase,
    },
    data() : data {
        return {
            tenants : [],
            isBusy : false,
            errorMessage : '',
            errorMessageMod : '',
            successMessageMod : '',
            isComplete : true,
            stripeEnable : false,
            transferEnable : false,
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
                  this.tenants = response.data;
                  this.isBusy = false;
                }
            }).catch((err) =>{
               this.errorMessage = 'error_load_dc_tenants';
               this.tenants = [];
            })
            this.isBusy = true;
        this.$axios.get<TransactionConfigRespItf>(this.$config.transactionSetup,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.stripeEnable = response.data.stripeEnable;
                  this.transferEnable = response.data.transferEnable;
                  this.isBusy = false;
                }
            }).catch((err) =>{
               this.errorMessage = 'error_load_transac_config';
               this.tenants = [];
            })
            
    },
    methods : {
        onTransferDcClick() {
            this.$root.$emit("message-display-transfer-dc", "");
        },
        onPurchaseDcClick() {
            this.$root.$emit("message-display-purchase-dc", "");
        }
    },
    mounted() {
        this.$root.$on("message-close-transfer-dc", (msg:any) => {
            this.$fetch();
        });
        this.$root.$on("message-close-purchase-dc", (msg:any) => {
            this.$fetch();
        });
    },
})
</script>