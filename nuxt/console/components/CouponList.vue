<template>
    <b-card 
        :header="$t('u_coupon_list')"
        class="mt-3 ml-3 myCard"
    >
        <b-table 
          :items="coupons" 
          :fields="fields" 
          :busy="isBusy"
          responsive 
          small
          striped
          headVariant="dark"
          class="pt-1 pb-5"
          style="font-size:8px;">
          <template #head()="data">
              {{ data.label }}
          </template>
          <template #cell(stop)="row">
            {{ dateFormatter(row.value) }} 
          </template>
        </b-table>
    </b-card>
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
import { BIconHandThumbsDown } from 'bootstrap-vue';
import Vue from 'vue'
import { CouponListRespItf } from 'vue/types/tenantSetup';

interface data {
    coupons : CouponListRespItf[],
    fields : any,
    isBusy : boolean,
    errorMessage : string,
    successMessage : string,
}

export default Vue.extend({
    name: "ManageUsers",
    data() : data {
        return {
            coupons : [],
            fields : [
                {key: 'couponID', label : this.$t('ccf_couponid')},
                {key: 'tenantUUID', sortable: false, label : this.$t('ccf_tenantuuid')},
                {key: 'inUse', sortable: false, label : this.$t('ccf_inuse')},
                {key: 'maxUse', sortable: false, label : this.$t('ccf_maxuse')},
                {key: 'stop', sortable: false, label : this.$t('ccf_stopdate')},
                {key: 'couponFor', sortable: false, label : this.$t('ccf_couponFor')},
            ],
            isBusy : false,
            errorMessage : '',
            successMessage : ''
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
        this.$axios.get<CouponListRespItf[]>(this.$config.couponGet+'/1/',config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.coupons = response.data;
                  this.isBusy = false;
                }
            }).catch((err) =>{
               this.errorMessage = 'error_load_coupons';
               this.coupons = [];
            })

    },
    methods : {
        dateFormatter(time:number) : string {
            let m = new Date(time as any);
            var dateString = m.getUTCFullYear() + "/" +
                            ("0" + (m.getMonth()+1)).slice(-2) + "/" +
                            ("0" + m.getDate()).slice(-2) + " " +
                            ("0" + m.getHours()).slice(-2) + ":" +
                            ("0" + m.getMinutes()).slice(-2) + ":" +
                            ("0" + m.getSeconds()).slice(-2);
            return dateString;
        },
    }
})
</script>