<template>
    <div>
        <h5><b-badge variant="secondary" class="m-2">{{ $t('ts_list_title') }}</b-badge></h5>
        <b-button size="sm" 
                      @click="addNewClick()"
                      class="m0 ml-2" 
                      pill 
                      variant="outline-secondary"
                      style="font-size:0.5rem;margin:2px;"
            >
              {{ $t('profile_add_new') }} 
        </b-button>
        <b-table 
          :items="templates" 
          :fields="fields" 
          :busy="isBusy"
          responsive 
          small
          striped
          headVariant="dark"
          class="p-2 pb-5"
          style="font-size:8px;">
          <template #cell(edit)="row">
            <b-button size="sm" 
                      @click="onLineClick(row)"
                      class="m0" 
                      pill 
                      variant="outline-secondary"
                      style="font-size:0.5rem;margin:2px;"
            >
              {{ $t('tsl_edit') }} 
            </b-button>
          </template>

          <template #head()="data">
            <span v-b-tooltip.hover="{ variant: 'secondary'}"
                  :title="getTipFromLabel(data)"
            >
              {{ data.label }}
            </span>
          </template>
        </b-table>

        <b-modal id="template-modal" ref="template-modal"
                 centered 
                 :title="$t('profile_modal')"
                 content-class="shadow"
                 v-model="showModal"
                 @ok="updateModal"
                 header-bg-variant="dark"
                 header-text-variant="white"
                 header-border-variant="dark"
                 footer-border-variant="white"
        >
            <b-form-group :description="$t('tip_tenantUUID')" style="text-align:left;" class="mb-1 small">
                <b-form-input v-model="template.tenantUUID"
                              type="text" size="sm"
                              :placeholder="$t('tsl_tenantUUID')"
                              :disabled="disableName"
                ></b-form-input>
            </b-form-group>
            <b-form-group :description="$t('tip_signupAllowed')" style="text-align:left;" class="mb-1 small">
              <b-form-checkbox v-model="template.signupAllowed"
                                  value='true' size="sm"
                                  unchecked-value='false'
                                  style="text-align:left;"
                                  >
                                  {{$t('tsl_signupAllowed')}}
              </b-form-checkbox>
            </b-form-group>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_maxDevices')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.maxDevices" type="number" size="sm"
                                :placeholder="$t('tsl_maxDevices')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_maxOwnedTenants')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.maxOwnedTenants" type="number" size="sm"
                                :placeholder="$t('tsl_maxOwnedTenants')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_freeTenantDc')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.freeTenantDc" type="number" size="sm"
                                :placeholder="$t('tsl_freeTenantDc')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPrice')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.dcPrice" type="number" step="0.000001" size="sm"
                                :placeholder="$t('tsl_dcPrice')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_dcMin')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.dcMin" type="number" size="sm"
                                :placeholder="$t('tsl_dcMin')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcBalanceStop')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.dcBalanceStop" type="number" size="sm"
                                :placeholder="$t('tsl_dcBalanceStop')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPer24BMessage')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.dcPer24BMessage" type="number" size="sm"
                                :placeholder="$t('tsl_dcPer24BMessage')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPer24BDuplicate')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.dcPer24BDuplicate" type="number" size="sm"
                                :placeholder="$t('tsl_dcPer24BDuplicate')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPer24BDownlink')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.dcPer24BDownlink" type="number" size="sm"
                                :placeholder="$t('tsl_dcPer24BDownlink')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPerJoinRequest')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.dcPerJoinRequest" type="number" size="sm"
                                :placeholder="$t('tsl_dcPerJoinRequest')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_maxJoinRequestDup')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.maxJoinRequestDup" type="number" size="sm"
                                :placeholder="$t('tsl_maxJoinRequestDup')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPerJoinAccept')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.dcPerJoinAccept" type="number" size="sm"
                                :placeholder="$t('tsl_dcPerJoinAccept')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <div class="row"><div class="col-md-6">
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPerDeviceInserted')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.dcPerDeviceInserted" type="number" size="sm"
                                :placeholder="$t('tsl_dcPerDeviceInserted')"
                  ></b-form-input>
              </b-form-group>
            </div></div>


            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_inactivityBillingPeriodMs')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.inactivityBillingPeriodMs" type="number" size="sm"
                                :placeholder="$t('tsl_inactivityBillingPeriodMs')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPerInactivityPeriod')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.dcPerInactivityPeriod" type="number" size="sm"
                                :placeholder="$t('tsl_dcPerInactivityPeriod')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_activityBillingPeriodMs')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.activityBillingPeriodMs" type="number" size="sm"
                                :placeholder="$t('tsl_activityBillingPeriodMs')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_dcPerActivityPeriod')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.dcPerActivityPeriod" type="number" size="sm"
                                :placeholder="$t('tsl_dcPerActivityPeriod')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <b-form-group :description="$t('tip_maxDcPerDevice')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.maxDcPerDevice" type="number" size="sm"
                                :placeholder="$t('tsl_maxDcPerDevice')"
                  ></b-form-input>
            </b-form-group>
            <div class="row"><div class="col-md-6">
              <b-form-group :description="$t('tip_limitDcRatePeriodMs')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.limitDcRatePeriodMs" type="number" size="sm"
                                :placeholder="$t('tsl_limitDcRatePeriodMs')"
                  ></b-form-input>
              </b-form-group>
            </div><div class="col-md-6">
              <b-form-group :description="$t('tip_limitDcRatePerDevice')" style="text-align:left;" class="mb-1 small">
                  <b-form-input v-model.number="template.limitDcRatePerDevice" type="number" size="sm"
                                :placeholder="$t('tsl_limitDcRatePerDevice')"
                  ></b-form-input>
              </b-form-group>
            </div></div>
            <b-button block @click="tenantSDelete"
                              :disabled="! disableName"
                              variant="outline-danger" 
                              class="mb-2"
                      >
                {{ $t('updtenant_del_button') }}
            </b-button>
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
  .tooltip {
    font-size: 0.6rem;
  }
  .table > tbody > tr > td {
     vertical-align: middle;
  }
  </style>
  
  <script lang="ts">
  import Vue from 'vue'
  import { TenantTemplate } from 'vue/types/tenantSetup';
  
  interface data {
    templates : TenantTemplate[],
    template : TenantTemplate,
    fields : any,
    errorMessage : string,
    isBusy : boolean,
    showModal : boolean,
    errorMessageMod : string,
    successMessageMod : string,
    disableName : boolean,
  }

  export default Vue.extend({
      name: "ManageTenantTemplate",
      data() : data {
        return {
          templates : [],
          template : {} as TenantTemplate,
          fields : [
            {key: 'edit', label : this.$t('tsl_edit')},
            {key: 'tenantUUID', sortable: true, label : this.$t('tsl_tenantUUID')},
            {key: 'signupAllowed', sortable: false, label : this.$t('tsl_signupAllowed')},
            {key: 'maxDevices', sortable: false, label : this.$t('tsl_maxDevices')},
            {key: 'maxOwnedTenants', sortable: false, label : this.$t('tsl_maxOwnedTenants')},
            {key: 'freeTenantDc', sortable: false, label : this.$t('tsl_freeTenantDc')},
            {key: 'dcPrice', sortable: false, label : this.$t('tsl_dcPrice')},
            {key: 'dcMin', sortable: false, label : this.$t('tsl_dcMin')},
            {key: 'dcBalanceStop', sortable: false, label : this.$t('tsl_dcBalanceStop')},
            {key: 'dcPer24BMessage', sortable: false, label : this.$t('tsl_dcPer24BMessage')},
            {key: 'dcPer24BDuplicate', sortable: false, label : this.$t('tsl_dcPer24BDuplicate')},
            {key: 'dcPer24BDownlink', sortable: false, label : this.$t('tsl_dcPer24BDownlink')},
            {key: 'dcPerJoinRequest', sortable: false, label : this.$t('tsl_dcPerJoinRequest')},
            {key: 'maxJoinRequestDup', sortable: false, label : this.$t('tsl_maxJoinRequestDup')},
            {key: 'dcPerJoinAccept', sortable: false, label : this.$t('tsl_dcPerJoinAccept')},
            {key: 'dcPerDeviceInserted', sortable: false, label : this.$t('tsl_dcPerDeviceInserted')},
            {key: 'inactivityBillingPeriodMs', sortable: false, label : this.$t('tsl_inactivityBillingPeriodMs')},
            {key: 'dcPerInactivityPeriod', sortable: false, label : this.$t('tsl_dcPerInactivityPeriod')},
            {key: 'activityBillingPeriodMs', sortable: false, label : this.$t('tsl_activityBillingPeriodMs')},
            {key: 'dcPerActivityPeriod', sortable: false, label : this.$t('tsl_dcPerActivityPeriod')},
            {key: 'maxDcPerDevice', sortable: false, label : this.$t('tsl_maxDcPerDevice')},
            {key: 'limitDcRatePeriodMs', sortable: false, label : this.$t('tsl_limitDcRatePeriodMs')},
            {key: 'limitDcRatePerDevice', sortable: false, label : this.$t('tsl_limitDcRatePerDevice')},
          ],
          errorMessage: '',
          isBusy : true,
          showModal : false,
          errorMessageMod : '',
          successMessageMod: '',
          disableName: true
        }
      },
      async fetch() {
        let config = {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
            }
        };
        this.isBusy = true;
        this.$axios.get<TenantTemplate []>(this.$config.tenantSetupTemplateList,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  response.data.forEach( element => {
                    this.templates.push(element)
                  });
                  this.isBusy = false;

                }
            }).catch((err) =>{
               this.errorMessage = 'error_load_templates';
               this.templates = [];
            })
      },
      methods: {
        getTipFromLabel(d:any) : string {
          switch ( d.column ) {
            default: return '';
            case 'tenantUUID': return this.$t('tip_tenantUUID').toString();
            case 'signupAllowed' : return this.$t('tip_signupAllowed').toString();
            case 'maxDevices' : return this.$t('tip_maxDevices').toString();
            case 'maxOwnedTenants' : return this.$t('tip_maxOwnedTenants').toString();
            case 'freeTenantDc' : return this.$t('tip_freeTenantDc').toString();
            case 'dcPrice' : return this.$t('tip_dcPrice').toString();
            case 'dcMin' : return this.$t('tip_dcMin').toString();
            case 'dcBalanceStop' : return this.$t('tip_dcBalanceStop').toString();
            case 'dcPer24BMessage' : return this.$t('tip_dcPer24BMessage').toString();
            case 'dcPer24BDuplicate' : return this.$t('tip_dcPer24BDuplicate').toString();
            case 'dcPer24BDownlink' : return this.$t('tip_dcPer24BDownlink').toString();
            case 'dcPerDeviceInserted' : return this.$t('tip_dcPerDeviceInserted').toString();
            case 'inactivityBillingPeriodMs' : return this.$t('tip_inactivityBillingPeriodMs').toString();
            case 'dcPerInactivityPeriod' : return this.$t('tip_dcPerInactivityPeriod').toString();
            case 'activityBillingPeriodMs' : return this.$t('tip_activityBillingPeriodMs').toString();
            case 'dcPerActivityPeriod' : return this.$t('tip_dcPerActivityPeriod').toString();
            case 'maxDcPerDevice' : return this.$t('tip_maxDcPerDevice').toString();
            case 'limitDcRatePeriodMs' : return this.$t('tip_limitDcRatePeriodMs').toString();
            case 'limitDcRatePerDevice' : return this.$t('tip_limitDcRatePerDevice').toString();
          }
        },
        onLineClick(d:any) {
          // clone
          this.template = Object.assign({},d.item);
          this.showModal = true;
          this.disableName = true;
        },
        async updateModal(bvModalEvent: any) {
          this.errorMessageMod='';
          this.successMessageMod='';
          let config = {
              headers: {
                'Content-Type': 'application/json',  
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,
              }
          };
          // because JS is not respecting types, even if ts ... ts should learn this
          if ( ((this.template.signupAllowed as unknown ) as string ) == "true" ) this.template.signupAllowed = true;
          if ( ((this.template.signupAllowed as unknown ) as string ) == "false" ) this.template.signupAllowed = false;
          if ( this.template.id != null && this.template.id != '' ) {
            this.$axios.put(this.$config.tenantSetupUpdate, this.template, config)
                .then((response) =>{
                    if (response.status == 200 ) {
                        this.successMessageMod = "updtenant_vmessage_success";
                        this.template={} as TenantTemplate; // avoid reentering
                        var self = this;
                        setTimeout(function() { 
                            self.$data.showModal = false;
                            self.errorMessageMod='';
                            self.successMessageMod='';
                        }, 1500);
                        this.templates = [] as TenantTemplate[];
                        this.$fetch();
                    }
                }).catch((err) =>{
                    if ( err.response.status == 400 ) {
                        let message = err.response.data.errorMessage;
                        this.errorMessageMod = 'sret_'+message;
                    }
                })
                bvModalEvent.preventDefault();
          } else {
            this.$axios.post(this.$config.tenantSetupCreate, this.template, config)
                .then((response) =>{
                    if (response.status == 201 ) {
                        this.successMessageMod = "addtenantt_vmessage_success";
                        this.template={} as TenantTemplate; // avoid reentering
                        var self = this;
                        setTimeout(function() { 
                            self.$data.showModal = false;
                            self.errorMessageMod='';
                            self.successMessageMod='';
                        }, 1500);
                        this.templates = [] as TenantTemplate[];
                        this.$fetch();
                    }
                }).catch((err) =>{
                    if ( err.response.status == 400 ) {
                        let message = err.response.data.errorMessage;
                        this.errorMessageMod = 'sret_'+message;
                    }
                })
                bvModalEvent.preventDefault();
          }
        },
        addNewClick() {
          this.template = {} as TenantTemplate;
          this.showModal = true;
          this.disableName = false;
        },
        tenantSDelete(bvModalEvent: any) {
          this.errorMessageMod='';
          this.successMessageMod='';
          let config = {
              headers: {
                'Content-Type': 'application/json',  
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,
              }
          };
          this.$axios.delete(this.$config.tenantSetupDelete+'/'+this.template.id+'/', config)
                .then((response) =>{
                    if (response.status == 200 ) {
                        this.successMessageMod = "deltenantt_vmessage_success";
                        this.template={} as TenantTemplate; // avoid reentering
                        var self = this;
                        setTimeout(function() { 
                            self.$data.showModal = false;
                            self.errorMessageMod='';
                            self.successMessageMod='';
                        }, 1500);
                        this.templates = [] as TenantTemplate[];
                        this.$fetch();
                    }
                }).catch((err) =>{

                    if ( err.response.status == 400 ) {
                        let message = err.response.data.errorMessage;
                        this.errorMessageMod = 'sret_'+message;
                    }
                    if ( err.response.status == 403 ) {
                        this.errorMessageMod = 'sret_error_forbidden';
                    }
                    var self = this;
                        setTimeout(function() { 
                            self.$data.showModal = false;
                            self.errorMessageMod='';
                            self.successMessageMod='';
                        }, 1500);
                })
        }
      },
  })
</script>
  