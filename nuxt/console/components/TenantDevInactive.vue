<template>
    <b-overlay :show="isBusy" rounded="sm">
        <b-card 
            :header="$t('tenantDevInactive_card_title')"
            class="TenantConsumption mt-3"
            no-body 
        >
        <b-card-body>
           <b-row v-if="loadStatSuccess">
            <b-col cols="12">
              <apexchart type="bar" height="300" :options="tenantConsumptionOption" :series="tenantConsumptionData"></apexchart>
            </b-col>
           </b-row>
           <b-row class="ml-0" v-if="! loadStatSuccess">
             <b-col cols="12">
                <b-card style="border-radius: 0.6rem; height: 500px;" class = "text-center bg-light" body-class="d-flex flex-column" >
                    <div style="position: absolute; top: 47%; left: 50%; transform: translate(-50%, -50%);">
                        <b-card-text class="small mb-2 text-danger">
                            <b-icon icon="exclamation-circle-fill" variant="danger"></b-icon>
                            {{ $t(errorMessage) }}
                        </b-card-text>
                    </div>
                </b-card>
              </b-col>
            </b-row>
        </b-card-body>
        <b-card-footer v-if="!$config.community">
            <b-button 
                variant="outline-secondary"
                size="sm"
                @click="goToDetail()"
                block
            > 
                {{ $t('inac_show_all') }}
            </b-button>
        </b-card-footer>
        </b-card>
    </b-overlay>
</template>
<style>
 .TenantConsumption .card-header  {
    font-size: 0.8rem;
    font-weight: 600;
    font-variant: small-caps;
}
</style>

<script lang="ts">
import Vue from 'vue'
import VueApexCharts from 'vue-apexcharts';
import { TenantSetupStatsSerie, TenantSetupStatsRespItf } from 'vue/types/tenantStat';


interface data {
    isBusy : boolean,
    tenantConsumptionOption : any,
    tenantConsumptionData: any [],
    errorMessage: string,
    loadStatSuccess: boolean,
}

export default Vue.extend({
    name: "TenantDevInactive",
    components: {
        apexchart: VueApexCharts,
    },
    data() : data {
        return {
            isBusy : false,
            tenantConsumptionOption : {
                chart: { type: 'bar', height: 300, stacked: true},
                plotOptions: { bar: { horizontal: true, }, },
                stroke: { width: 1, colors: ['#fff'] },
                fill: { opacity: 1 },
                legend: { position: 'bottom', horizontalAlign: 'left', offsetX: 40 },
                xaxis: { categories: [] }
            },
            tenantConsumptionData: [
            ],
            errorMessage: '',
            loadStatSuccess: false,
        };
    },
    async fetch() {
        let tenantId = this.$store.state.currentTenant;

        if ( tenantId == undefined || tenantId == null || tenantId.length < 5 ) {
            this.errorMessage = 'error_find_basicstat';
            return;
        } 
        let config = {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
            }
        };
        this.isBusy = true;
        this.$axios.get<TenantSetupStatsRespItf>(this.$config.tenantBasicStat+'/'+tenantId+'/inactive',config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.tenantConsumptionOption.xaxis.categories = response.data.dateLabel;
                  this.tenantConsumptionData = response.data.series;
                  this.loadStatSuccess = true;
                  this.isBusy = false;
                }
            }).catch((err) =>{
                this.isBusy = false;
                this.loadStatSuccess = false;
                this.errorMessage = "error_load_stat_nodata";
            });
    },
    methods : {
        goToDetail() {
            this.$router.push('/front/advInactiv');
        }
    },
    mounted() {
    },
    created () {
    },
    beforeDestroy () {
    },
})
</script>