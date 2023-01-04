<template>
    <div>
        <b-card-text class="m-4"><h5>{{$t('dc_title')}}</h5></b-card-text>
       
        <b-card
                class="m-4"
        >

        <b-row cols="3" class="mb-2">
            <b-col cols="2" class="py-2" style="font-size:0.8rem;">
                <b-button block
                    variant="outline-dark"
                    size="sm"
                    @click=""
                >
                    <b-icon icon="arrow-right-circle" variant="secondary"></b-icon>
                    {{ $t('dc_transfer') }}
                </b-button>
            </b-col>
            <b-col cols="2" class="py-2" style="font-size:0.8rem;">
                <b-button block
                    variant="primary"
                    size="sm"
                    @click=""
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
                <div v-for="item in tenants">
                      {{ item.tenantName }}
                    </div>

                </b-card>
            </b-col>
            <b-col cols="4" class="py-1" style="font-size:0.8rem;">
                <b-card
                        :header="$t('dc_payment_dc_title')"
                        class=" DataCredit"
                >
                </b-card>
            </b-col>
        </b-row>



        </b-card>
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

interface data {
    tenants : TenantDcBalancesReqItf[],
    isBusy : boolean,
    errorMessage : string,
    errorMessageMod : string,
    successMessageMod : string,
    isComplete: boolean,
}

export default Vue.extend({
    name: "UserProfile",
    data() : data {
        return {
            tenants : [],
            isBusy : false,
            errorMessage : '',
            errorMessageMod : '',
            successMessageMod : '',
            isComplete : true,
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
    },
    methods : {

    }
})
</script>