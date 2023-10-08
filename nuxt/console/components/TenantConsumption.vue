<template>
    <b-overlay :show="isBusy" rounded="sm">
        <b-card 
            :header="$t('tenantConsumption_card_title')"
            class="TenantConsumption mt-3"
        >
           <apexchart type="bar" height="200" :options="tenantConsumptionOption" :series="tenantConsumptionData"></apexchart>
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


interface data {
    isBusy : boolean,
    tenantConsumptionOption : {},
    tenantConsumptionData: any [],
}

export default Vue.extend({
    name: "TenantConsumption",
    components: {
        apexchart: VueApexCharts,
    },
    data() : data {
        return {
            isBusy : false,
            tenantConsumptionOption : {
                chart: { type: 'bar', height: 200, stacked: true, stackType: '100%'},
                plotOptions: { bar: { horizontal: true, }, },
                stroke: { width: 1, colors: ['#fff'] },
                title: { text: 'Tenant activity history' },
                fill: { opacity: 1 },
                legend: { position: 'bottom', horizontalAlign: 'left', offsetX: 40 }
            },
            tenantConsumptionData: [
                { name: 'serie 1 ', data : [ 1,2,3 ] },
                { name: 'serie 2 ', data : [ 4,5,6 ] },
            ],
        };
    },
    async fetch() {
        let config = {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
            }
        };
        /*
        this.isBusy = true;
        this.$axios.get<TicketListRespItf[]>(this.$config.ticketListGet,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.tickets = response.data;
                  this.isBusy = false;
                }
            }).catch((err) =>{
               this.errorMessage = 'error_load_transactions';
               this.tickets = [];
            })
            */
    },
    methods : {
    },
    mounted() {
    },
    created () {
    },
    beforeDestroy () {
    },
})
</script>