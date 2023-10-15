<template>
    <b-card 
        :header="$t('t_search_title')"
        class="mt-2 ml-2"
    >

        <div class="col-md-8">
            <b-form-group :description="$t('tip_search_tenant')" style="text-align:left;" class="mb-1 small">
                <b-form-input v-model="keyword"
                              type="text" size="sm"
                              @keyup="onSearchChange"
                              :placeholder="$t('tsl_search_tenant')"
                ></b-form-input>
            </b-form-group>
        </div>

        <div class="col-md-12 ml-0">
        <b-table 
          :items="tenants" 
          :fields="fields" 
          :busy="isBusy"
          responsive 
          small
          striped
          headVariant="dark"
          class="pt-1 pb-5"
          style="font-size:8px;">
          <template #cell(credit)="row">
            <b-button size="sm" 
                      @click="onLineClick(row)"
                      class="m0" 
                      pill 
                      variant="outline-secondary"
                      style="font-size:0.5rem;margin:2px;"
            >
              {{ $t('tsl_credit') }} 
            </b-button>
          </template>

          <template #cell(edit)="row">
            <b-button size="sm" 
                      @click="onLineEdit(row)"
                      class="m0" 
                      pill 
                      variant="outline-secondary"
                      style="font-size:0.5rem;margin:2px;"
            >
              {{ $t('tsl_edit') }} 
            </b-button>
          </template>

          <template #head()="data">
              {{ data.label }}
          </template>
        </b-table>
        </div>

        <b-modal id="balance-modal" ref="balance-modal"
                 centered 
                 :title="$t('balance_modal')"
                 content-class="shadow"
                 v-model="showModal"
                 @ok="updateModal"
                 header-bg-variant="dark"
                 header-text-variant="white"
                 header-border-variant="dark"
                 footer-border-variant="white"
        >
            <b-form-group :description="$t('tsl_tenantName')" style="text-align:left;" class="mb-1 small">
                <b-form-input v-model="tenant.tenantName"
                              type="text" size="sm"
                              :placeholder="$t('tsl_tenantName')"
                              disabled="true"
                ></b-form-input>
            </b-form-group>
            <b-form-group :description="$t('tsl_dcBalance')" style="text-align:left;" class="mb-1 small">
                <b-form-input v-model.number="tenant.dcBalance" type="number" size="sm"
                                :placeholder="$t('tsl_dcBalance')"
                                disabled="true"
                ></b-form-input>
            </b-form-group>
            <b-form-group :description="$t('tsl_addBalance')" style="text-align:left;" class="mb-1 small">
                <b-form-input v-model.number="modalDcChange" type="number" size="sm"
                                :placeholder="$t('tsl_addBalance')"
                                @keyup="onDcChange"
                ></b-form-input>
            </b-form-group>
            <b-form-group :description="$t('tsl_newBalance')" style="text-align:left;" class="mb-1 small">
                <b-form-input v-model.number="modalDcTotal" type="number" size="sm"
                                :placeholder="$t('tsl_newBalance')"
                                disabled="true"
                ></b-form-input>
            </b-form-group>
            <b-card-text class="text-danger" style="text-align:center;" >
                {{ $t(errorMessageMod)}} 
            </b-card-text>
            <b-card-text class="text-success" style="text-align:center;">
                {{ $t(successMessageMod)}} 
            </b-card-text>
        </b-modal>


        <b-modal id="tenant-edit-modal" ref="tenant-edit-modal"
                 centered 
                 :title="$t('tenant_edit_modal')"
                 content-class="shadow"
                 v-model="showEditModal"
                 @ok="updateEditModal"
                 header-bg-variant="dark"
                 header-text-variant="white"
                 header-border-variant="dark"
                 footer-border-variant="white"
        >
            <b-form-group :description="$t('tip_tenantUUID')" style="text-align:left;" class="mb-1 small">
                <b-form-input v-model="tenantDetails.tenantUUID"
                              type="text" size="sm"
                              :placeholder="$t('tsl_tenantUUID')"
                              disabled="true"
                ></b-form-input>
            </b-form-group>
            <b-form-group :description="$t('tip_signupAllowed')" style="text-align:left;" class="mb-1 small">
              <b-form-checkbox v-model="tenantDetails.signupAllowed"
                                  value='true' size="sm"
                                  unchecked-value='false'
                                  style="text-align:left;"
                                  >
                                  {{$t('tsl_signupAllowed')}}
              </b-form-checkbox>
            </b-form-group>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_maxDevices')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.maxDevices" type="number" size="sm"
                                :placeholder="$t('tsl_maxDevices')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_maxOwnedTenants')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.maxOwnedTenants" type="number" size="sm"
                                :placeholder="$t('tsl_maxOwnedTenants')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_freeTenantDc')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.freeTenantDc" type="number" size="sm"
                                :placeholder="$t('tsl_freeTenantDc')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPrice')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.dcPrice" type="number" step="0.000001" size="sm"
                                :placeholder="$t('tsl_dcPrice')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_dcMin')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.dcMin" type="number" size="sm"
                                :placeholder="$t('tsl_dcMin')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcBalanceStop')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.dcBalanceStop" type="number" size="sm"
                                :placeholder="$t('tsl_dcBalanceStop')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPer24BMessage')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.dcPer24BMessage" type="number" size="sm"
                                :placeholder="$t('tsl_dcPer24BMessage')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPer24BDuplicate')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.dcPer24BDuplicate" type="number" size="sm"
                                :placeholder="$t('tsl_dcPer24BDuplicate')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPer24BDownlink')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.dcPer24BDownlink" type="number" size="sm"
                                :placeholder="$t('tsl_dcPer24BDownlink')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPerJoinRequest')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.dcPerJoinRequest" type="number" size="sm"
                                :placeholder="$t('tsl_dcPerJoinRequest')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_maxJoinRequestDup')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.maxJoinRequestDup" type="number" size="sm"
                                :placeholder="$t('tsl_maxJoinRequestDup')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPerJoinAccept')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.dcPerJoinAccept" type="number" size="sm"
                                :placeholder="$t('tsl_dcPerJoinAccept')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <div class="row"><div class="col-md-6">
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPerDeviceInserted')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.dcPerDeviceInserted" type="number" size="sm"
                                :placeholder="$t('tsl_dcPerDeviceInserted')"
                  ></b-form-input>
              </b-form-group>
            </div></div>


            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_inactivityBillingPeriodMs')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.inactivityBillingPeriodMs" type="number" size="sm"
                                :placeholder="$t('tsl_inactivityBillingPeriodMs')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPerInactivityPeriod')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.dcPerInactivityPeriod" type="number" size="sm"
                                :placeholder="$t('tsl_dcPerInactivityPeriod')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_activityBillingPeriodMs')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.activityBillingPeriodMs" type="number" size="sm"
                                :placeholder="$t('tsl_activityBillingPeriodMs')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPerActivityPeriod')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.dcPerActivityPeriod" type="number" size="sm"
                                :placeholder="$t('tsl_dcPerActivityPeriod')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <b-form-group :description="$t('tip_maxDcPerDevice')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.maxDcPerDevice" type="number" size="sm"
                                :placeholder="$t('tsl_maxDcPerDevice')"
                  ></b-form-input>
            </b-form-group>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_limitDcRatePeriodMs')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.limitDcRatePeriodMs" type="number" size="sm"
                                :placeholder="$t('tsl_limitDcRatePeriodMs')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_limitDcRatePerDevice')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="tenantDetails.limitDcRatePerDevice" type="number" size="sm"
                                :placeholder="$t('tsl_limitDcRatePerDevice')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <b-card-text class="text-danger" style="text-align:center;" >
                {{ $t(errorMessageMod)}} 
            </b-card-text>
            <b-card-text class="text-success" style="text-align:center;">
                {{ $t(successMessageMod)}} 
            </b-card-text>
        </b-modal>
    </b-card>
</template>
<script lang="ts">
import Vue from 'vue'
import { TenantSearchResponse, TenantDcBalanceReqItf } from 'vue/types/tenantSearch';
import { TenantTemplate } from 'vue/types/tenantSetup';

interface data {
    keyword : string,
    tenants : TenantSearchResponse[],
    tenant : TenantSearchResponse,
    tenantDetails : TenantTemplate,
    fields : any,
    isBusy : boolean,
    showModal : boolean,
    showEditModal : boolean,
    errorMessageMod : string,
    successMessageMod : string,
    modalDcChange: bigint,
    modalDcTotal: bigint,
}

export default Vue.extend({
    name: "ManageTenant",
    data() : data {
        return {
            keyword : '',
            tenants : [],
            tenant : {} as TenantSearchResponse,
            tenantDetails : {} as TenantTemplate,
            fields : [
                {key: 'credit', label : this.$t('tsl_credit')},
                {key: 'edit', label : this.$t('tsl_edit')},
                {key: 'tenantName', sortable: false, label : this.$t('tsl_tenantName')},
                {key: 'ownerEmail', sortable: false, label : this.$t('tsl_ownerEmail')},
                {key: 'dcBalance', sortable: false, label : this.$t('tsl_dcBalance')},
                {key: 'tenantUUID', sortable: false, label : this.$t('tsl_tenantID')},
                {key: 'routeId', sortable: false, label : this.$t('tsl_routeId')},
            ],
            isBusy : false,
            showModal : false,
            showEditModal : false,
            errorMessageMod : '',
            successMessageMod : '',
            modalDcChange : BigInt(0),
            modalDcTotal : BigInt(0),
        };
    },
    methods : {
        onDcChange() {
            this.modalDcTotal = this.tenant.dcBalance + this.modalDcChange;
        },
        onSearchChange() {
            if ( this.isBusy ) return;
            if ( this.keyword.length >= 3 && this.keyword.length <= 15 ) {
                this.isBusy = true;
                //console.log(this.keyword);
                let config = {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                    }
                };
                this.tenants = [];
                this.$axios.get<TenantSearchResponse[]>(this.$config.tenantSearch+'/?keyword='+this.keyword,config)
                    .then((response) =>{
                        if (response.status == 200 ) {
                            response.data.forEach( element => {
                                this.tenants.push(element)
                            });
                            this.isBusy = false;
                        }
                    }).catch((err) =>{
                       this.tenants = [];
                       this.isBusy = false;
                    })
            }
        },
        onLineClick(row : any) {
            this.tenant = Object.assign({},row.item);
            this.modalDcChange = BigInt(0);
            this.modalDcTotal = this.tenant.dcBalance;
            this.showModal = true;
        },
        onLineEdit(row : any) {
            // get the tenant setup details
            this.tenant = Object.assign({},row.item);
            let config = {
              headers: {
                'Content-Type': 'application/json',  
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,
              }
            };
            this.$axios.get<TenantTemplate>(this.$config.tenantSetupGetDetails+'/'+this.tenant.tenantUUID+'/',config)
                .then((response) =>{
                    if (response.status == 200 ) {
                        this.tenantDetails = response.data;
                        this.isBusy = false;
                        this.showEditModal = true;
                        console.log(this.tenantDetails);
                    }
                }).catch((err) =>{
                    this.tenantDetails = {} as TenantTemplate;
                    this.isBusy = false;
                });
        },
        updateEditModal(bvModalEvent: any) {
            this.errorMessageMod='';
          this.successMessageMod='';
          let config = {
              headers: {
                'Content-Type': 'application/json',  
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,
              }
          };
          // because JS is not respecting types, even if ts ... ts should learn this
          if ( ((this.tenantDetails.signupAllowed as unknown ) as string ) == "true" ) this.tenantDetails.signupAllowed = true;
          if ( ((this.tenantDetails.signupAllowed as unknown ) as string ) == "false" ) this.tenantDetails.signupAllowed = false;
          this.$axios.put(this.$config.tenantSetupUpdate, this.tenantDetails, config)
            .then((response) =>{
                if (response.status == 200 ) {
                    this.successMessageMod = "updtenant_vmessage_success";
                    this.tenantDetails={} as TenantTemplate; // avoid reentering
                    var self = this;
                    setTimeout(function() { 
                        self.$data.showEditModal = false;
                        self.errorMessageMod='';
                        self.successMessageMod='';
                    }, 1500);
                }
            }).catch((err) =>{
                if ( err.response.status == 400 ) {
                    let message = err.response.data.errorMessage;
                    this.errorMessageMod = 'sret_'+message;
                }
            })
            bvModalEvent.preventDefault();
        },
        updateModal(bvModalEvent: any) {
          this.errorMessageMod='';
          this.successMessageMod='';
          let config = {
              headers: {
                'Content-Type': 'application/json',  
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,
              }
          };
          
          let body = {
            tenantUUID : this.tenant.tenantUUID,
            dcs : this.modalDcChange
          };
          this.$axios.put(this.$config.tenantDcUpdate, body, config)
                .then((response) =>{
                    if (response.status == 200 ) {
                        this.successMessageMod = "updtenant_vmessage_success";
                        this.tenant={} as TenantSearchResponse; // avoid reentering
                        var self = this;
                        setTimeout(function() { 
                            self.$data.showModal = false;
                            self.errorMessageMod='';
                            self.successMessageMod='';
                        }, 1500);
                        this.onSearchChange();
                    }
                }).catch((err) =>{
                    if ( err.response.status == 400 ) {
                        let message = err.response.data.errorMessage;
                        this.errorMessageMod = 'sret_'+message;
                    }
                })
                bvModalEvent.preventDefault();
                
        }
    }
})
</script>