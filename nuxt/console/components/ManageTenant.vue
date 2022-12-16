<template>
    <div>
        <h5><b-badge variant="secondary" class="m-2">{{ $t('t_search_title') }}</b-badge></h5>

        <div class="col-md-4">
            <b-form-group :description="$t('tip_search_tenant')" style="text-align:left;" class="mb-1 small">
                <b-form-input v-model="keyword"
                              type="text" size="sm"
                              @keyup="onSearchChange"
                              :placeholder="$t('tsl_search_tenant')"
                ></b-form-input>
            </b-form-group>
        </div>

        <div class="col-md-6">
        <b-table 
          :items="tenants" 
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
              {{ data.label }}
          </template>
        </b-table>
        </div>

    </div>
</template>
<script lang="ts">
import Vue from 'vue'
import { TenantSearchResponse } from 'vue/types/tenantSearch';

interface data {
    keyword : string,
    tenants : TenantSearchResponse[],
    fields : any,
    isBusy : boolean
}

export default Vue.extend({
    name: "ManageTenant",
    data() : data {
        return {
            keyword : '',
            tenants : [],
            fields : [
                {key: 'edit', label : this.$t('tsl_edit')},
                {key: 'tenantName', sortable: false, label : this.$t('tsl_tenantName')},
                {key: 'ownerEmail', sortable: false, label : this.$t('tsl_ownerEmail')},
                {key: 'dcBalance', sortable: false, label : this.$t('tsl_dcBalance')},
                {key: 'tenantUUID', sortable: false, label : this.$t('tsl_tenantID')},
            ],
            isBusy : false,
        };
    },
    methods : {
        onSearchChange() {
            if ( this.keyword.length > 3 && this.keyword.length < 15 ) {
                console.log(this.keyword);
                let config = {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                    }
                };
                this.isBusy = true;
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
                    })
            }
        },
        onLineClick(row : TenantSearchResponse) {
            console.log(row);
        }
    }
})
</script>