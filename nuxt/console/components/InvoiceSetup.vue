<template>
    <b-card 
        :header="$t('inv_setup_title')"
        class="mt-2 mr-2"
    >
    <b-row>
       <b-col cols="12">
        <b-form-group 
                        :label="$t('inv_companyName')"
                        label-for="companyName"
                        label-cols="3"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1 mr-1"
            >
                    <b-form-input 
                                v-model="setup.companyName"
                                id="companyName"
                                type="text" size="sm"
                    ></b-form-input>
                    <b-form-text style="font-size:0.5rem;">{{ $t('inv_companyName_det') }}</b-form-text>
        </b-form-group>

        <b-form-group 
                        :label="$t('inv_companyAddress')"
                        label-for="companyAddress"
                        label-cols="3"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1 mr-1"
            >

                    <b-form-textarea 
                                v-model="setup.companyAddress"
                                id="companyAddress"
                                size="sm"
                                rows="3"
                    ></b-form-textarea>
                    <b-form-text style="font-size:0.5rem;">{{ $t('inv_companyAddress_det') }}</b-form-text>
        </b-form-group>

        <b-form-group 
                        :label="$t('inv_companyVAT')"
                        label-for="companyVAT"
                        label-cols="3"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1 mr-1"
            >
                    <b-form-input 
                                v-model="setup.companyVAT"
                                id="companyVAT"
                                type="text" size="sm"
                    ></b-form-input>
                    <b-form-text style="font-size:0.5rem;">{{ $t('inv_companyVAT_det') }}</b-form-text>
        </b-form-group>

        <b-form-group 
                        :label="$t('inv_companyRegistration')"
                        label-for="companyRegistration"
                        label-cols="3"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1 mr-1"
            >
                    <b-form-input 
                                v-model="setup.companyRegistration"
                                id="companyRegistration"
                                type="text" size="sm"
                    ></b-form-input>
                    <b-form-text style="font-size:0.5rem;">{{ $t('inv_companyRegistration_det') }}</b-form-text>
        </b-form-group>
        <b-form-group 
                        label-cols="3"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1"
            >
                    <b-button
                            variant="primary"
                            size="sm"
                            @click="updateSetup()"
                    >
                    {{ $t('inv_upt_but') }}
                    </b-button>
                    <b-card-text class="small mb-2 text-danger" v-show="errorMessage!=''">
                        <b-icon icon="exclamation-circle-fill" variant="danger"></b-icon>
                        {{ $t(errorMessage) }}
                    </b-card-text>
                    <b-card-text class="mb-2 text-success" v-show="successMessage!=''">
                        <b-icon icon="check-square" variant="success"></b-icon>
                        {{ $t(successMessage) }}
                    </b-card-text>
            </b-form-group>
          </b-col>
        </b-row>
    </b-card>
</template>

<script lang="ts">
import Vue from 'vue'
import { InvoiceSetupGetRespItf, InvoiceSetupPutReqItf } from 'vue/types/invoice';

interface data {
    isBusy : boolean,
    setup : InvoiceSetupGetRespItf,
    errorMessage : string,
    successMessage : string,
}

export default Vue.extend({
    name: "InvoiceSetup",
    components: {
    },
    data() : data {
        return {
            isBusy : false,
            setup : {} as InvoiceSetupGetRespItf,
            errorMessage : '',
            successMessage : '',
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
        this.$axios.get<InvoiceSetupGetRespItf>(this.$config.invoiceSetupGet,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.setup = response.data;
                  this.isBusy = false;
                }
            }).catch((err) =>{
               this.errorMessage = 'error_load_invoice_setup';
               this.setup = {} as InvoiceSetupGetRespItf;
            })
    },
    methods : {
        updateSetup() {
            this.errorMessage='';
            this.successMessage='';
            let config = {
              headers: {
                'Content-Type': 'application/json',  
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,
              }
            };
            this.$axios.put(this.$config.invoiceSetupGet, this.setup, config)
                .then((response) =>{
                    if (response.status == 200 ) {
                        this.successMessage = "inv_update_success";
                    }
                }).catch((err) =>{
                    if ( err.response.status == 403 ) {
                        this.errorMessage = "inv_update_error";
                    }
                })  
        }
    },
    mounted() {
    },
})
</script>