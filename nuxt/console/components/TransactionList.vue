<template>
    <div>       
        <b-row col="3" class="mb-3">
            <b-col cols="12" class="py-1" style="font-size:0.8rem;">
                <b-card
                        :header="$t('dc_payment_histo')"
                        class="ml-0 DataCredit"
                >
                <b-row>
                    <b-col cols="3" class="bg-secondary text-white ml-3" style="margin-right:2px;border-radius: 0.2rem;">
                        {{ $t('dc_transac_time_label') }}
                    </b-col>
                    <b-col cols="3" class="bg-secondary text-white" style="margin-right:2px;border-radius: 0.2rem;">
                        {{ $t('dc_transac_tenant_label') }}
                    </b-col>
                    <b-col cols="2" class="bg-secondary text-white" style="margin-right:2px;border-radius: 0.2rem;">
                        {{ $t('dc_transac_dcs_label') }}
                    </b-col>
                    <b-col cols="1" class="bg-secondary text-white" style="margin-right:2px;border-radius: 0.2rem;">
                        {{ $t('dc_transac_cost_label') }}
                    </b-col>
                    <b-col cols="2" class="bg-secondary text-white" style="border-radius: 0.2rem;">
                        {{ $t('dc_transac_status_label') }}
                    </b-col>
                </b-row>
                <b-row v-for="transaction in transactions"
                    v-bind:data="transaction"
                    v-bind:key="transaction.transactionUUID"
                    style="margin-top:2px;"
                >
                    <b-col cols="3"
                        class="ml-3 bg-light text-dark"
                        style="margin-right:2px;font-size:0.9rem;"
                    >
                        {{ dateFormatter(transaction.createAt) }} 
                    </b-col>
                    <b-col cols="3"
                        style="text-align:left;font-size:0.9rem;"
                        class="text-dark bg-light"
                    >
                        {{ transaction.tenantName }} 
                    </b-col>
                    <b-col cols="2"
                        style="text-align:right;margin-right:2px;font-size:0.9rem;"
                        class="text-info bg-light"
                    >
                        {{ transaction.dcs.toLocaleString("en-US") }} 
                        <img src="/static/front/dc_icon.svg" style="width: 13px; position: relative; top: -2px ; margin-right: 2px;"/>
                    </b-col>
                    <b-col cols="1"
                        style="text-align:right;margin-right:2px;font-size:0.9rem;"
                        class="text-info bg-light"
                    >
                        {{ costFormatter(transaction.type, transaction.cost) }}                  
                    </b-col>
                    <b-col cols="2"
                        style="text-align:right;font-size:0.9rem;"
                        class="text-info bg-light"
                    >
                        {{ $t('txst_'+transaction.status) }} 
                        <b-icon-file-pdf
                            v-if="transaction.status == 'succeeded' && transaction.type == 1"
                            class="text-dark"
                            @click="getInvoice(transaction.transactionUUID)"
                        />                 
                    </b-col>
                  </b-row>
                </b-card>              
            </b-col>
        </b-row>
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
import { TransactionListRespItf } from 'vue/types/transaction';

interface data {
    transactions : TransactionListRespItf[],
    isBusy : boolean,
    errorMessage : string,
    errorMessageMod : string,
    successMessageMod : string,
}

export default Vue.extend({
    name: "UserProfile",
    components: {
    },
    data() : data {
        return {
            transactions : [],
            isBusy : false,
            errorMessage : '',
            errorMessageMod : '',
            successMessageMod : '',
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
        let url = this.$config.transactionListGet;
        this.$axios.get<TransactionListRespItf[]>(url,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.transactions = response.data;
                  this.isBusy = false;
                }
            }).catch((err) =>{
               this.errorMessage = 'error_load_transactions';
               this.transactions = [];
            })
    },
    methods : {
        dateFormatter(time:BigInt) {
            let m = new Date(time as any);
            var dateString = m.getUTCFullYear() + "/" +
                            ("0" + (m.getMonth()+1)).slice(-2) + "/" +
                            ("0" + m.getDate()).slice(-2) + " " +
                            ("0" + m.getHours()).slice(-2) + ":" +
                            ("0" + m.getMinutes()).slice(-2) + ":" +
                            ("0" + m.getSeconds()).slice(-2);
            return dateString;
        },
        costFormatter(type:number, cost:number) {
            if ( type == 1 ) {
                return '$'+cost.toLocaleString("en-US") 
            } else return 'N/A';
        },
        getInvoice(txId:string) {
            let config = {
                headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/pdf',
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                }
            };
        this.$axios.get(this.$config.transactionInvoice+'/'+txId+'/',{
                responseType: 'blob',
                headers: config.headers
            })
            .then((response) =>{
                if (response.status == 200 ) {
                    const blob = new Blob([response.data], {
                        type: 'application/pdf',
                     });
                     const objectUrl = window.URL.createObjectURL(blob)
                     window.open(objectUrl)                    
                }
            }).catch((err) =>{
               this.errorMessage = 'error_load_transactions';
               this.transactions = [];
            })
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