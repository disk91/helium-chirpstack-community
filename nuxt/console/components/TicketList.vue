<template>
    <div>       
        <b-row col="3" class="mb-3">
            <b-col cols="12" class="py-1" style="font-size:0.8rem;">
                <b-card
                        :header="$t('ticket_histo_title')"
                        class="ml-0 TicketList"
                >
                <b-row>
                    <b-col cols="2" class="bg-secondary text-white ml-3" style="margin-right:2px;border-radius: 0.2rem;">
                        {{ $t('tick_time_label') }}
                    </b-col>
                    <b-col cols="2" class="bg-secondary text-white" style="margin-right:2px;border-radius: 0.2rem;">
                        {{ $t('tick_status_label') }}
                    </b-col>
                    <b-col cols="6" class="bg-secondary text-white" style="margin-right:2px;border-radius: 0.2rem;">
                        {{ $t('tick_topic_label') }}
                    </b-col>
                    <b-col cols="1" class="bg-secondary text-white" style="margin-right:2px;border-radius: 0.2rem;">
                        {{ $t('tick_edit_label') }}
                    </b-col>
                </b-row>
                <b-row v-for="ticket in tickets"
                    v-bind:data="ticket"
                    v-bind:key="ticket.ticketUUID"
                    style="margin-top:2px;"
                >
                    <b-col cols="2"
                        class="ml-3 bg-light text-dark"
                        style="margin-right:2px;font-size:0.8rem;"
                    >
                        {{ dateFormatter(ticket.createdAt) }} 
                    </b-col>
                    <b-col cols="2"
                        style="text-align:left;margin-right:2px;font-size:0.8rem;"
                        class="text-info bg-light"
                    >
                        {{ $t('tick_status_'+ticket.status) }}                  
                    </b-col>
                    <b-col cols="6"
                        style="text-align:left;margin-right:2px;font-size:0.8rem;"
                        class="text-dark bg-light"
                    >
                        {{ ticket.topic }} 
                    </b-col>
                    <b-col cols="1"
                        style="text-align:center;font-size:0.9rem;"
                        class="text-dark bg-light"
                    >
                        <b-icon 
                            icon="arrow-down-circle" 
                            variant="secondary"
                            @click="onTicketEdit(ticket.ticketUUID)"
                        >
                        </b-icon>
                    </b-col>
                  </b-row>
                </b-card>              
            </b-col>
        </b-row>
    </div>
</template>
<style>
 .TicketList .card-header  {
    font-size: 0.8rem;
    font-weight: 600;
    font-variant: small-caps;
}
</style>

<script lang="ts">
import Vue from 'vue'
import { TicketListRespItf } from 'vue/types/ticket';

interface data {
    tickets : TicketListRespItf[],
    polling : any,
    isBusy : boolean,
    errorMessage : string,
    errorMessageMod : string,
    successMessageMod : string,
}

export default Vue.extend({
    name: "TicketList",
    components: {
    },
    data() : data {
        return {
            tickets : [],
            polling : null,
            isBusy : false,
            errorMessage : '',
            errorMessageMod : '',
            successMessageMod : '',
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
    },
    methods : {
        dateFormatter(time:bigint) {
            let m = new Date(time as any);
            var dateString = m.getUTCFullYear() + "/" +
                            ("0" + (m.getMonth()+1)).slice(-2) + "/" +
                            ("0" + m.getDate()).slice(-2) + " " +
                            ("0" + m.getHours()).slice(-2) + ":" +
                            ("0" + m.getMinutes()).slice(-2) + ":" +
                            ("0" + m.getSeconds()).slice(-2);
            return dateString;
        },
        onTicketEdit(ticketId:string) {
            this.$root.$emit("message-ticket-display", ticketId);
        },
        pollData () {
		    this.polling = setInterval(() => {
                this.$fetch();
		    } , 30000)
	    },
    },
    mounted() {
        this.$root.$on("message-close-ticket-add", (msg:any) => {
            this.$fetch();
        });
        this.$root.$on("message-ticket-addresponse", (msg:any) => {
            this.$fetch();
        });
    },
    created () {
	    this.pollData()   
    },
    beforeDestroy () {
      // clean DC poller
      if ( this.polling != null ) {
  	     clearInterval(this.polling)
      }
    },
})
</script>