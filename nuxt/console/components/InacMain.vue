<template>
    <div>
    <b-row v-if="! isSet">
        <b-col cols="12" class="text-danger">
            <b-icon icon="exclamation-circle-fill" variant="danger"></b-icon>
            {{ $t('inac_missing_tenant_info') }}
        </b-col>
    </b-row>
    <b-row v-if="isSet">
        <b-col cols="12">
            <b-row>
                <b-col cols="6" class="py-1" style="font-size:0.8rem;">
                    <b-form-group 
                        :label="$t('inac_select_period')"
                        label-for="deviceAddr"
                        label-cols="2"
                        label-align="left"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1 mr-1"
                    >
                        <b-form-select v-model="pastHour" :options="options" size="sm" class="" :select-size="1"></b-form-select>
                        <b-form-text style="font-size:0.5rem;">{{ $t('inac_select_period_txt') }}</b-form-text>
                    </b-form-group>
                </b-col>
                <b-col cols="2" class="py-1" style="font-size:0.8rem;">
                    <b-button variant="outline-secondary" size="sm" @click="updatePeriod()">
                        {{ $t('inac_apply_button') }}
                    </b-button>
                    <b-spinner v-if="isBusy" small type="grow" class="ml-2"></b-spinner>
                </b-col>
            </b-row>
            <b-row>
                <b-col cols="12" class="py-1" style="font-size:0.7rem;">
                    <b-card
                        :header="$t('inac_list_title')"
                        class="ml-0 TenantInfo"
                    >
                    <div v-if="dctx.data!=undefined" class="overflow-auto">
                        {{ dctx.data.tenantName }}
                        <b-pagination
                            v-model="currentPage"
                            :total-rows="dctx.data.inactivCount"
                            :per-page="dctx.data.perPage"
                            aria-controls="my-table"
                            @change="onPageChange"
                        >
                        </b-pagination>
                        <b-table
                            :items="dctx.data.inactives"
                            :per-page="dctx.data.perPage"
                            current-page=0
                            :fields="fields" 
                            style="font-size:8px;"
                            small
                            headVariant="dark"
                        >
                            <template #head()="data">
                                {{ data.label }}
                                <span v-if="tips.get(data.field.key)!=undefined" v-b-tooltip.hover.html="getHelp(data.field)">
                                    <b-icon icon="info-circle" variant="white" class="ml-1"></b-icon>
                                </span>
                            </template>

                            <template #cell(creationDate)="row">
                                {{ getTimeStr(row.value) }} 
                            </template>
                            <template #cell(lastSeenDate)="row">
                                {{ getTimeStr(row.value) }} 
                            </template>
                            <template #cell(disabled)="row" style="font-size:10px;" >
                                <b-icon v-if="row.value" icon="x-circle" variant="danger"></b-icon>
                                <b-icon v-if="!row.value" icon="check-circle" variant="success"></b-icon>
                            </template>
                            <template #cell(routeEui)="row" style="font-size:10px;" >
                                <b-icon v-if="row.value==0" icon="x-circle" variant="danger"></b-icon>
                                <b-icon v-if="row.value==1" icon="question-circle" variant="warning"></b-icon>
                                <b-icon v-if="row.value==2" icon="check-circle" variant="success"></b-icon>
                            </template>
                            <template #cell(routeSkfs)="row" style="font-size:10px;" >
                                <b-icon v-if="row.value==0" icon="x-circle" variant="danger"></b-icon>
                                <b-icon v-if="row.value==1" icon="question-circle" variant="warning"></b-icon>
                                <b-icon v-if="row.value==2" icon="check-circle" variant="success"></b-icon>
                            </template>
                            <template #cell(skfsCollision)="row" style="font-size:10px;" >
                                <b-icon v-if="row.value==2" icon="x-circle" variant="danger"></b-icon>
                                <b-icon v-if="row.value==1" icon="question-circle" variant="warning"></b-icon>
                                <b-icon v-if="row.value==0" icon="check-circle" variant="success"></b-icon>
                            </template>
                            <template #cell(neverSeen)="row" style="font-size:10px;" >
                                <b-icon v-if="row.value" icon="x-circle" variant="danger"></b-icon>
                                <b-icon v-if="!row.value" icon="check-circle" variant="success"></b-icon>
                            </template>
                            <template #cell(neverUplink)="row" style="font-size:10px;" >
                                <b-icon v-if="row.value" icon="x-circle" variant="danger"></b-icon>
                                <b-icon v-if="!row.value" icon="check-circle" variant="success"></b-icon>
                            </template>
                            <template #cell(onlyJoinReq)="row" style="font-size:10px;" >
                                <b-icon v-if="row.value==2" icon="x-circle" variant="danger"></b-icon>
                                <b-icon v-if="row.value==1" icon="question-circle" variant="warning"></b-icon>
                                <b-icon v-if="row.value==0" icon="check-circle" variant="success"></b-icon>
                            </template>
                            <template #cell(coverageRisk)="row" style="font-size:10px;" >
                                <b-icon v-if="row.value==2" icon="x-circle" variant="danger"></b-icon>
                                <b-icon v-if="row.value==1" icon="question-circle" variant="warning"></b-icon>
                                <b-icon v-if="row.value==0" icon="check-circle" variant="success"></b-icon>
                            </template>                            
                        </b-table>
                    </div>
                    </b-card>
                </b-col>
            </b-row>
        </b-col>
    </b-row>
    </div>
</template>
<style>
 .TenantInfo .card-header  {
    font-size: 0.8rem;
    font-weight: 600;
    font-variant: small-caps;
}
</style>

<script lang="ts">
  import Vue from 'vue';
  import { InactivContext, AdvDeviceInacGetItf } from 'vue/types/inactiv';
  import { SelectOption, TableField } from 'vue/types/standards';

  interface context {
    errorMessage: string,
    isBusy: boolean,
    isSet: boolean,
    dctx: InactivContext,
    pastHour: number,
    currentPage: number,            // what selector have
    pageToLoad: number,             // what to request to API
    options: SelectOption[],
    fields: TableField[],
    tips: Map<string,string | undefined>,
  };


  export default Vue.extend({
      name: "InacMain",
      components: { 
      },
      data() : context {
        return {
            errorMessage: '',
            isBusy: false,
            isSet: false,
            dctx: {
                tenantID: '',
                data: undefined,
            } as InactivContext,
            pastHour: 168,
            currentPage: 1,
            pageToLoad: 0,
            options:[
                { value: 1, text: "last hour" },
                { value: 4, text: "last 4 hours" },
                { value: 12, text: "last 12 hours" },
                { value: 24, text: "today" },
                { value: 48, text: "yesterday" },
                { value: 72, text: "last 3 days" },
                { value: 168, text: "last week" },
                { value: 336, text: "last 2 weeks" },
                { value: 720, text: "last month" },
            ],
            fields : [
                    // NB sortable is killing the sticky header
                    {key: 'devName', label: this.$t('inac_table_devname'), sortable: true},
                    {key: 'applicationName',  label: this.$t('inac_table_appName'), sortable: true},
                    {key: 'devEui', label: this.$t('inac_table_deveui')},
                    {key: 'devAddr', label: this.$t('inac_table_addr'), sortable: true},
                    {key: 'creationDate', label: this.$t('inac_table_created')},
                    {key: 'lastSeenDate', label: this.$t('inac_table_seen'), sortable: true },
                    {key: 'disabled', label: this.$t('inac_table_dis')},
                    {key: 'routeEui', label: this.$t('inac_table_rteui')},
                    {key: 'routeSkfs', label: this.$t('inac_table_rtskfs')},
                    {key: 'skfsCollision', label: this.$t('inac_table_skcol')},
                    {key: 'neverUplink', label: this.$t('inac_table_neverup')},
                    {key: 'neverSeen', label: this.$t('inac_table_never')},
                    {key: 'onlyJoinReq', label: this.$t('inac_table_joinonly')},
                    {key: 'coverageRisk', label: this.$t('inac_table_covrisk')},
                ],
            tips: new Map([
                ['devAddr',this.$t('inac_table_addr_tip') as string],
                ['creationDate',this.$t('inac_table_created_tip') as string],
                ['lastSeenDate',this.$t('inac_table_seen_tip') as string],
                ['disabled',this.$t('inac_table_dis_tip') as string],
                ['routeEui',this.$t('inac_table_rteui_tip') as string],
                ['routeSkfs',this.$t('inac_table_rtskfs_tip') as string],
                ['skfsCollision',this.$t('inac_table_skcol_tip') as string],
                ['neverUplink',this.$t('inac_table_neverup_tip') as string],
                ['neverSeen',this.$t('inac_table_never_tip') as string],
                ['onlyJoinReq',this.$t('inac_table_joinonly_tip') as string],
                ['coverageRisk',this.$t('inac_table_covrisk_tip') as string],
            ]),

        }
      },
      async fetch() {
        let tenantId = this.$store.state.currentTenant;
        this.currentPage = 1;
        this.pageToLoad = 0;
        if ( tenantId != undefined && tenantId != null && tenantId.length > 5 ) {
            this.isSet = true;
            this.dctx.tenantID = tenantId;
        } else {
            // nothing configured
            this.isSet = false;
        }
      },
      methods: {
        getHelp(field : TableField) : string {
            console.log(field);
            let t = this.tips.get(field.key);
            if (  t !== undefined ) {
                return t;
            }
            return '';
        },
        onPageChange(page : number) {
            this.pageToLoad = page - 1;
            if ( this.dctx.data != undefined ) this.dctx.data.inactives = [];
            this.loadData();
        },
        updatePeriod() {
            this.currentPage = 1;
            this.pageToLoad = 0;
            this.dctx.data = undefined;
            this.loadData();
        },
        loadData() {
            let config = {
                timeout: 60000,
                headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                }
            };
            this.errorMessage = '';
            this.isBusy = true;
            
            this.$axios.get<AdvDeviceInacGetItf>(this.$config.inacDeviceGet+'/'+this.dctx.tenantID+'/'+this.pastHour+'/'+this.pageToLoad+'/',config)
                .then((response) =>{
                    if (response.status == 200 ) {
                        this.dctx.data = response.data;   
                        this.isBusy = false;
                    } else {
                        // 204 response case
                        this.dctx.data = undefined;
                        this.currentPage = 1;
                        this.pageToLoad = 0;
                        this.isBusy = false;
                        this.errorMessage = 'inac_error_nodata';
                    }
                }).catch((err) =>{
                    this.dctx.data = undefined;
                    this.currentPage = 1;
                    this.pageToLoad = 0;
                    this.isBusy = false;
                    this.errorMessage = 'inac_error_load_data';
                });
        },
        getTimeStr(ref: number) : string {
            if ( ref == 0 ) return 'unknown';
            let now = Date.now();
            let delta = now - ref;
            if ( delta < 1000 ) return 'now';
            if ( delta < 60000 ) return ""+Math.floor(delta / 1000)+"s ago";
            if ( delta < 3600000 ) return ""+Math.floor(delta / 60000)+"m ago";
            if ( delta < 48*3600000 ) return ""+Math.floor(delta / 3600000)+"h ago";
            if ( delta < 10*24*3600000 ) return ""+Math.floor(delta / (24*3600000) )+" days ago";
            let date = new Date(ref);
            return ""+date.getFullYear()+"-"+(date.getMonth()+1).toString().padStart(2,'0')+"-"+date.getDate().toString().padStart(2,'0')+" "+date.getHours().toString().padStart(2,'0')+":"+date.getMinutes().toString().padStart(2,'0');
        },
      },
      mounted() {
      }
   })
  
</script>
 