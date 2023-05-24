<template>
    <div>
        <h5><b-badge variant="secondary" class="m-2">{{ $t('t_search_title') }}</b-badge></h5>

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

    </div>
</template>
<script lang="ts">
import Vue from 'vue'
import { TenantSearchResponse, TenantDcBalanceReqItf } from 'vue/types/tenantSearch';

interface data {
    keyword : string,
    tenants : TenantSearchResponse[],
    tenant : TenantSearchResponse,
    fields : any,
    isBusy : boolean,
    showModal : boolean,
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
            fields : [
                {key: 'edit', label : this.$t('tsl_edit')},
                {key: 'tenantName', sortable: false, label : this.$t('tsl_tenantName')},
                {key: 'ownerEmail', sortable: false, label : this.$t('tsl_ownerEmail')},
                {key: 'dcBalance', sortable: false, label : this.$t('tsl_dcBalance')},
                {key: 'tenantUUID', sortable: false, label : this.$t('tsl_tenantID')},
                {key: 'routeId', sortable: false, label : this.$t('tsl_routeId')},
            ],
            isBusy : false,
            showModal : false,
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
            if ( this.keyword.length > 3 && this.keyword.length < 15 ) {
                this.isBusy = true;
                console.log(this.keyword);
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