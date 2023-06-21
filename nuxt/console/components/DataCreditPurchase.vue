<template>
    <div>
        <b-modal id="DataCreditPurchase" 
                 centered 
                 content-class="shadow"
                 v-model="show"
                 @show="preShowModal"
                 header-border-variant="white"
                 header-text-variant="dark"
                 footer-border-variant="white"
                 class="text-center"
                 button-size="sm"
                 hide-footer
        >
            <template #modal-header>
              <h5 style="text-align:center;width:100%;margin-top:1rem;margin-left:2rem;margin-right:2rem;">{{$t('dc_purch_title')}}</h5>
            </template>

            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                       style="font-size:0.8rem;"
                       class="mb-2"
                >
                {{ $t('dc_purch_select_tenant') }}
                </b-col>
                <b-col cols="1"></b-col>
            </b-row>
            <b-row>
                <b-col cols="2"></b-col>
                <b-col cols="8"
                >
                    <b-form-select v-model.number="sourceTenant" 
                        :options="sourceOption"
                        @change="updateTenantConfiguration"
                        size="sm"
                        class="mb-3"
                    ></b-form-select>
                </b-col>
                <b-col cols="2"></b-col>
            </b-row>

            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                       style="font-size:0.8rem;font-weight: 300;text-align:center;"
                       class="mb-3"
                > 1 DC = ${{ tenantSetup.dcPrice }} USD <br/>
                {{ $t('dc_purch_minimum') }} ${{ tenantSetup.dcMin * tenantSetup.dcPrice}} 
                </b-col>
                <b-col cols="1"></b-col>
            </b-row>

            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                       style="font-size:0.8rem;font-weight: 500;text-align:center;"
                       class="mb-2"
                > {{  $t('dc_purch_nontransfer') }} 
                </b-col>
                <b-col cols="1"></b-col>
            </b-row>


            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                    class="bg-light p-3"
                    style="border-radius: 0.8rem;"
                >
                    <b-row><b-col cols="1"></b-col><b-col cols="9">

                        <span class="text-primary" style="font-size:0.8rem;">
                            {{ $t('dc_purch_select_quantity') }}
                        </span>
                        <b-form-input v-model.number="quantity"
                                    type="number" 
                                    :placeholder="$t('dc_trans_quantity')"
                                    @keyup="quantityChanged"
                                    class="mb-2"
                                    size="sm"
                        ></b-form-input>
                    </b-col><b-col cols="1">
                        <img src="/static/front/dc_icon.svg" style="width: 20px; position: relative; top: 25px ; left:-24px; margin-right: 2px;"/>
                    </b-col><b-col cols="1"></b-col></b-row>

                    <b-row><b-col cols="1"></b-col><b-col
                         cols="9"
                         class="mx-3"
                         style="border-radius: 0.3rem;background-color:rgb(186, 231, 255);margin-top:5px;padding:5px 10px 5px 10px;"
                    >
                            <span class="text-primary" style="font-size:0.8rem;">{{ $t('dc_trans_cost') }}</span>
                            <span class="text-primary" style="font-size:1.1rem;float:right;clear:right;">{{ price | currency }}</span>
                    </b-col><b-col cols="1"></b-col></b-row>

                </b-col>
                <b-col cols="1"></b-col>
            </b-row>
            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10">
                    <b-form-group style="text-align:left;" class="mb-1 small">
                        <b-form-input v-model="memo"
                                    type="text" 
                                    :placeholder="$t('dc_trans_memo')"
                                    class="mb-1 mt-3"
                                    size="sm"
                        ></b-form-input>
                        <b-form-text style="font-size:0.6rem;">{{ $t('dc_trans_memo_desc') }}</b-form-text>
                    </b-form-group>
                </b-col>
                <b-col cols="1s"></b-col>
            </b-row>


            <b-row class="mb-2 mt-4">
                <b-col cols="1"></b-col>
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
                <b-col cols="6" class="py-2" style="font-size:0.8rem;">
                    <b-button block
                        variant="primary"
                        size="sm"
                        @click="gotopay()"
                        style="text-align: right;font-size:0.8rem;"
                        :disabled="!canTransfer()"
                    >
                        {{ $t('dc_trans_pay') }}
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

        <!-- ###### -->

        <b-modal id="DataCreditCB" 
                 centered 
                 content-class="shadow"
                 v-model="showCb"
                 @shown="initStripe"
                 header-border-variant="white"
                 header-text-variant="dark"
                 footer-border-variant="white"
                 class="text-center"
                 button-size="sm"
                 hide-footer
        >
            <template #modal-header>
              <h5 style="text-align:center;width:100%;margin-top:1rem;margin-left:2rem;margin-right:2rem;">{{$t('dc_purch_bycb')}}</h5>
            </template>

            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                    class="py-3"
                    style="border-radius: 0.6rem;background-color:rgb(186, 231, 255);"
                >
                    <b-row>
                        <b-col
                         cols="8"
                         class="mx-1"
                         style="border-radius: 0.3rem;background-color:rgb(186, 231, 255);line-height:1rem;"
                        >
                          <img src="/static/front/dc_icon.svg" style="width: 14px; position: relative; top: -1px ; left:0px; margin-right: 2px;"/>
                          <span class="text-dark" style="font-size:0.9rem;">{{ quantity }}</span><br/>
                          <span class="text-secondary" style="font-size:0.7rem;">{{ $t('dc_transac_dcs_label') }}</span>
                        </b-col>
                        <b-col cols="3" style="vertical-align:middle;" class="mx-1">
                            <span class="text-primary" style="font-size:1.1rem;float:right;clear:right;position:relative;top:4px;">{{ price | currency }}</span>
                        </b-col>
                    </b-row>
                </b-col>
                <b-col cols="1"></b-col>
            </b-row>

            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                    class="pt-3"
                >
                    {{ $t('dc_purch_add_card') }}
                </b-col>
                <b-col cols="1"></b-col>
            </b-row>
            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                    class="py-2 bg-light"
                    style="border-radius: 0.2rem;"
                >
                    <div id="card-element"><!--Stripe.js injects the Card Element--></div>
                </b-col>
                <b-col cols="1"></b-col>
            </b-row>


            <b-row class="mb-2 mt-4">
                <b-col cols="1"></b-col>
                <b-col cols="4" class="py-2" style="font-size:0.8rem;">
                    <b-button block
                        variant="outline-dark"
                        size="sm"
                        @click="backModal()"
                        style="text-align: left;font-size:0.8rem;"
                    >
                        <b-icon icon="reply" variant="secondary"></b-icon>
                        {{ $t('dc_trans_back') }}
                    </b-button>
                </b-col>
                <b-col cols="6" class="py-2" style="font-size:0.8rem;">
                    <b-button block
                        variant="primary"
                        size="sm"
                        @click="pay()"
                        style="text-align: right;font-size:0.8rem;"
                        :disabled="!isValidCb"
                    >
                        {{ $t('dc_trans_pay') }}
                        <b-icon icon="arrow-right-circle" variant="white"></b-icon>
                    </b-button>
                </b-col>
            </b-row>
            <b-card-text class="text-danger" style="text-align:center;" >
                <p id="card-error" role="alert"></p>
                {{ $t(errorMessageMod)}} 
            </b-card-text>
            <b-card-text class="text-success" style="text-align:center;">
                {{ $t(successMessageMod)}} 
            </b-card-text>
        </b-modal>



    </div>
</template>
<style>
div#DataCreditPurchase___BV_modal_content_, div#DataCreditCB___BV_modal_content_ {
    -webkit-border-radius: 1rem !important;
    -moz-border-radius: 1rem !important;
    border-radius: 1rem !important; 
}
</style>
<script async src="https://js.stripe.com/v3/" ></script>

<script lang="ts">
import { BooleanTypeAnnotation } from '@babel/types';
import { data } from 'browserslist';
import Vue from 'vue'
import { TenantDcBalancesReqItf, TenantSetupRespItf } from 'vue/types/tenantSearch';
import { TransactionStripeReqItf, TransactionStripeRespItf} from 'vue/types/transaction';
//import { Stripe } from '@types/stripe-v3';

interface data {
    ownedTenants : TenantDcBalancesReqItf[],
    tenantSetup : TenantSetupRespItf,
    sourceOption : any,
    sourceTenant : number,
    quantity : number,
    destOption : any,
    isBusy : boolean,
    errorMessage : string,
    errorMessageMod : string,
    successMessageMod : string,
    show : boolean,
    showCb : boolean,
    price : number,
    memo : string,
    backFromCB : boolean,
    destroyed : boolean,
    isValidCb : boolean,

    stripe : any,
    card : any,
    secret : string,

    transaction : string,

}

Vue.filter('currency', function (value:number) {
  return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' })
})

declare function Stripe(key: string) : any;

export default Vue.extend({
    name: "DataCreditPurchase",
    data() : data {
        return {
            ownedTenants : [],
            tenantSetup : {} as TenantSetupRespItf,
            sourceOption : [],
            sourceTenant : 0,
            quantity : undefined as any,
            destOption : [],
            isBusy : false,
            errorMessage : '',
            errorMessageMod : '',
            successMessageMod : '',
            show : false,
            showCb : false,
            price : 0.0,
            memo : '',
            backFromCB : false,
            destroyed : true,
            isValidCb : false,

            stripe : undefined,
            card : undefined,
            secret : '',
            transaction : '',
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
                  this.sourceTenant=0;
                  for ( let i = 0 ; i < this.ownedTenants.length ; i++ ) {
                    this.sourceOption.push( {
                        value: i,
                        text: this.ownedTenants[i].tenantName+' ('+this.ownedTenants[i].dcBalance+' DCs)',
                    })
                  }
                  this.getTenantConfiguration(this.ownedTenants[this.sourceTenant].tenantUUID);
                  this.isBusy = false;
                }
            }).catch((err) =>{
               this.errorMessageMod = 'error_load_tenants_list';
               this.ownedTenants = [];
            })
    },
    methods : {
        resetForm() {
            this.quantity = undefined as any;
            this.sourceTenant = 0;
            this.ownedTenants = [];
            this.tenantSetup = {} as TenantSetupRespItf; 
            this.successMessageMod = '';
            this.errorMessageMod = '';
            this.price = 0.0;
            this.memo = '';
            this.showCb = false;
            this.isValidCb = false;
            this.stripe = undefined;
            this.card = undefined;
            this.secret = '';
            this.transaction = '';
        },
        updateTenantConfiguration() {
            this.isBusy = true;
            this.getTenantConfiguration(this.ownedTenants[this.sourceTenant].tenantUUID);
            this.isBusy = false;
        },
        getTenantConfiguration(tenantId:string) {
            let config = {
                headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                }
            };

            this.$axios.get<TenantSetupRespItf>(this.$config.tenantSetupGet+'/'+tenantId+'/',config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.tenantSetup = response.data;
                }
            }).catch((err) =>{
               this.tenantSetup = {} as TenantSetupRespItf;
               this.errorMessageMod = 'error_load_tenants_setup';
            })

        },
        quantityChanged() {
            this.price = this.quantity * this.tenantSetup.dcPrice;
        },
        initStripe() {

            let config = {
                headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                }
            };
            let body : TransactionStripeReqItf = {
                tenantUUID: this.ownedTenants[this.sourceTenant].tenantUUID,
                dcs: this.quantity as any,
                cost: this.price,
                memo: this.memo,
            };
        
            this.isBusy = true;
            this.$axios.post<TransactionStripeRespItf>(this.$config.transactionStripeCreate,body,config)
                .then((response) =>{
                    if (response.status == 200 ) {
                        this.isBusy = false;
                        let r : TransactionStripeRespItf = response.data;
                        this.transaction = r.transactionUUID;  // here is the key for update later
                        var stripe = Stripe(r.stripePublicKey);
                        var elements = stripe.elements();
                        var style = {
                            base: {
                                color: "#32325d",
                                fontFamily: 'Arial, sans-serif',
                                fontSmoothing: "antialiased",
                                fontSize: "16px",
                                "::placeholder": {
                                color: "#32325d"
                                }
                            },
                            invalid: {
                                fontFamily: 'Arial, sans-serif',
                                color: "#fa755a",
                                iconColor: "#fa755a"
                            }
                        };
                        var card = elements.create("card", { style: style });
                        card.mount("#card-element");

                        this.stripe = stripe;
                        this.card = card;
                        this.secret = r.stripeTxSecret;

                        var self = this;
                        card.on("change", function (event:any) {
                            if ( event.complete ) {
                                self.isValidCb = true;
                            } else self.isValidCb = false;

                            if ( event.error ) {
                                self.errorMessageMod = event.error.message;
                            } else {
                                self.errorMessageMod = '';
                            }
                        });  
                    }    
                }).catch((err) =>{
                    this.isBusy = false;
                    if ( err == undefined ) {
                        this.$data.errorMessageMod = 'sret_stripe_unknown';
                    } else {
                        if ( err.response.status == 400 || err.response.status == 403 ) {
                            let message = err.response.data.message;
                            this.$data.errorMessageMod = 'sret_'+message;
                        } else {
                            this.$data.errorMessageMod = 'sret_stripe_unknown';
                        }
                    }
                })
        },
        pay() {
            var self = this;
            self.errorMessageMod = '';
            this.stripe.confirmCardPayment(
                this.secret, {
                    payment_method: {
                        card: this.card
                    }
                }
            ).then(function(result:any) {
                if (result.error) {
                    self.$data.errorMessageMod = result.error.message;
                    self.$data.isValidCb = false;
                } else {
                    self.$data.successMessageMod = 'dc_stripe_success';
                    // refresh the backend intent
                    let config = {
                        headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer '+self.$store.state.consoleBearer,  
                        }
                    };
                    //self.$axios.put(self.$config.transactionStripeUpdate+'/'+result.paymentIntent.id+'/',null,config)
                    if ( self.transaction != '' ) {
                        self.$axios.put(self.$config.transactionStripeUpdate+'/'+self.transaction+'/',null,config)
                        .then((response) =>{})
                        .catch((err) => {})
                    }

                    // display success and quit
                    setTimeout(function() { 
                        self.$data.showCb = false;
                        self.resetForm();
                        self.$root.$emit("message-close-purchase-dc", "");
                  }, 1500);
                }
            });
        },
        gotopay() {
            this.show = false;
            this.showCb = true;
            this.backFromCB = true;
        },
        preShowModal() {
            // when the first modal is open, can come from
            // dc pannel (first open) - need to call reset
            // dc pannel (after cancel) - has been cleaned prevsouly
            // dc pannel (after destroy) - need to call reset
            // CB purchase ( back ) - must not be cleared
            if ( this.backFromCB ) {
                // no reset
                this.backFromCB = false;
            } else if ( this.destroyed ) {
                // need reset, this includes the first load as it is default value
                this.resetForm();
            }
            // open call after cancel may pass through the message handler doing the reset
            this.destroyed = true;
        },
        backModal() {
            this.show = true;
            this.showCb = false;
            this.errorMessageMod = '';            
        },
        cancelModal() {
            this.show = false;
            this.destroyed = false;
            this.resetForm();
        },
        canTransfer() {
            if ( ! this.isBusy && this.quantity >= this.tenantSetup.dcMin ) {
                return true;
            }
            return false;
        }

    },
    mounted() {
        this.$root.$on("message-display-purchase-dc", (msg:any) => {
            this.resetForm();
            this.$fetch();
            this.show = true;
        });
    },
})
</script>