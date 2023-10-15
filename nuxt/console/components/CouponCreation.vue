<template>
    <div>
    <b-card 
        :header="$t('coupon_creation_title')"
        class="mt-3 mr-3 myCard"
    >
        <b-form-group 
            :label="$t('cc_tenant_template')"
            label-for="ccTenantTemplate"
            label-cols="3"
            label-align="right"
            label-class="w-25"
            label-size="sm"
            class="mb-1 mr-1"
        >
            <b-form-select 
                v-model="couponInfo.tenantUUID" 
                :options="tenantTOption"
                size="sm"
            ></b-form-select>
            <b-form-text style="font-size:0.5rem;">{{ $t('cc_tenant_template_desc') }}</b-form-text>
        </b-form-group>

        <b-form-group 
            :label="$t('cc_tenant_prefix')"
            label-for="ccTenantPrefix"
            label-cols="3"
            label-align="right"
            label-class="w-25"
            label-size="sm"
            class="mb-1 mr-1"
        >
            <b-form-input 
                v-model="couponInfo.prefix" 
                type="text" size="sm"
            ></b-form-input>
            <b-form-text style="font-size:0.5rem;">{{ $t('cc_tenant_prefix_desc') }}</b-form-text>
        </b-form-group>

        <b-form-group 
            :label="$t('cc_tenant_maxUse')"
            label-for="ccTenantMaxUse"
            label-cols="3"
            label-align="right"
            label-class="w-25"
            label-size="sm"
            class="mb-1 mr-1"
        >
            <b-form-input 
                v-model.number="couponInfo.maxUse" 
                type="number" size="sm"
            ></b-form-input>
            <b-form-text style="font-size:0.5rem;">{{ $t('cc_tenant_maxUse_desc') }}</b-form-text>
        </b-form-group>

        <b-form-group 
            :label="$t('cc_tenant_couponFor')"
            label-for="ccTenantCouponFor"
            label-cols="3"
            label-align="right"
            label-class="w-25"
            label-size="sm"
            class="mb-1 mr-1"
        >
            <b-form-input 
                v-model="couponInfo.couponFor" 
                type="text" size="sm"
            ></b-form-input>
            <b-form-text style="font-size:0.5rem;">{{ $t('cc_tenant_couponFor_desc') }}</b-form-text>
        </b-form-group>

        <b-form-group 
            :label="$t('cc_tenant_validity')"
            label-for="ccTenantValidity"
            label-cols="3"
            label-align="right"
            label-class="w-25"
            label-size="sm"
            class="mb-1 mr-1"
        >
            <b-form-input 
                v-model="validity" 
                type="text" size="sm"
            ></b-form-input>
            <b-form-text style="font-size:0.5rem;">{{ $t('cc_tenant_validity_desc') }}</b-form-text>
        </b-form-group>

        <b-form-group 
            :label="$t('cc_tenant_toCreate')"
            label-for="ccTenantToCreate"
            label-cols="3"
            label-align="right"
            label-class="w-25"
            label-size="sm"
            class="mb-1 mr-1"
        >
            <b-form-input 
                v-model.number="couponInfo.toCreate" 
                type="number" size="sm"
            ></b-form-input>
            <b-form-text style="font-size:0.5rem;">{{ $t('cc_tenant_toCreate_desc') }}</b-form-text>
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
                @click="createCoupon()"
            >
                {{ $t('cc_create') }}
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
    </b-card>

    <b-modal id="show-coupon-modal" 
                 centered 
                 :title="$t('cc_show_coupons')"
                 content-class="shadow"
                 v-model="showCoupons"
                 header-bg-variant="primary"
                 header-text-variant="white"
                 header-border-variant="primary"
                 footer-border-variant="white"
        >
        <b-row 
            v-for="coupon in coupons"
            v-bind:data="coupon"
            v-bind:key="coupon.couponID"
            style="margin-top:2px;"
        >
            <b-col>{{ coupon.couponID }}</b-col>
        </b-row>
    </b-modal>
    </div>
</template>
<style>
.myCard .card-header  {
    font-size: 0.8rem;
    font-weight: 600;
    font-variant: small-caps;
}
.myCard .card-body  {
    padding: 20px 5px 20px 5px;
}
</style>

<script lang="ts">
import Vue from 'vue'
import { TenantTemplate } from 'vue/types/tenantSetup';
import { TenantSetupTemplateCouponReqItf, TenantSetupTemplateCouponRespItf } from 'vue/types/tenantSetup';

interface data {
    isBusy : boolean,
    templates : TenantTemplate[],
    errorMessage : string,
    successMessage : string,
    couponInfo : TenantSetupTemplateCouponReqItf,
    coupons : TenantSetupTemplateCouponRespItf[],
    tenantTOption : any,
    validity : number,
    showCoupons : boolean,
}

export default Vue.extend({
    name: "CouponCreation",
    components: {
    },
    data() : data {
        return {
            isBusy : false,
            templates : [],
            errorMessage : '',
            successMessage : '',
            couponInfo : {
                prefix :'',
                tenantUUID : '',
                toCreate : 1,
                maxUse : 1,
                start : 0,
                stop : 0,
                couponFor : '',
            },
            coupons : [],
            tenantTOption : [],
            validity : 30,
            showCoupons : false,
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
        this.$axios.get<TenantTemplate[]>(this.$config.tenantSetupTemplateList,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  response.data.forEach( element => {
                    if ( element.tenantUUID != 'default' ) {
                        this.templates.push(element)
                        this.tenantTOption.push({
                            value : element.tenantUUID,
                            text: element.tenantUUID,
                        });
                    }
                  });
                  this.couponInfo.tenantUUID = this.tenantTOption[0].value;
                  this.isBusy = false;
                }
            }).catch((err) =>{
               this.errorMessage = 'error_load_templates';
               this.templates = [];
            })
    },
    methods : {
        createCoupon() {
            this.errorMessage='';
            this.successMessage='';
            let config = {
              headers: {
                'Content-Type': 'application/json',  
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,
              }
            };
            this.couponInfo.start = Date.now();
            this.couponInfo.stop = this.couponInfo.start + ( this.validity*24*3600*1000 );
            this.$axios.post<TenantSetupTemplateCouponRespItf[]>(this.$config.couponPost, this.couponInfo, config)
                .then((response) =>{
                    if (response.status == 201 ) {
                        this.successMessage = "cc_create_success";
                        this.coupons = response.data;
                        this.showCoupons = true;
                    }
                }).catch((err) =>{
                    this.errorMessage = "cc_create_error";
                })
        },
    },
    mounted() {
    },
})
</script>