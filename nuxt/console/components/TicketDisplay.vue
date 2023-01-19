<template>
    <div v-show="isDisplayed()">       
        <b-row col="3" class="mb-3">
            <b-col cols="12" class="py-1" style="font-size:0.8rem;">
                <b-card
                        :header="$t('ticket_details')+ticket.topic" 
                        class="ml-0 TicketList"
                >
                <b-row>
                    <b-col cols="2" class="bg-secondary text-white ml-3" style="margin-right:2px;border-radius: 0.2rem;">
                        {{ $t('tick_time_label') }}
                    </b-col>
                    <b-col cols="2" class="bg-secondary text-white" style="margin-right:2px;border-radius: 0.2rem;">
                        {{ $t('tick_status_label') }}
                    </b-col>
                    <b-col cols="4" class="bg-secondary text-white" style="margin-right:2px;border-radius: 0.2rem;">
                        {{ $t('tick_topic_label') }}
                    </b-col>
                    <b-col cols="3" class="bg-secondary text-white" style="margin-right:2px;border-radius: 0.2rem;">
                        {{ $t('tick_owner_label') }}
                    </b-col>
                </b-row>
                <b-row 
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
                    <b-col cols="4"
                        style="text-align:left;margin-right:2px;font-size:0.7rem;"
                        class="text-dark bg-light"
                    >
                        {{ ticket.topic }} 
                    </b-col>
                    <b-col cols="3"
                        style="text-align:left;font-size:0.7rem;"
                        class="text-dark bg-light"
                    >
                        {{ ticket.owner }} 
                    </b-col>
                  </b-row>
                  <b-row 
                    style="margin-top:2px;margin-right:-21px;"
                  >
                    <b-col cols="11"
                        class="ml-3 bg-light text-dark"
                        style="font-size:0.8rem;padding-right:0px;font-weight: 600;"
                    >
                        {{ ticket.details }} 
                    </b-col>
                  </b-row>


                  <b-row v-for="response in ticket.responses"
                    v-bind:data="response"
                    v-bind:key="response.createdAt"
                    style="margin-top:2px;"
                  > <b-col>
                    <b-row style="margin-top:2px;margin-right:-21px;">
                        <b-col cols="11" class="bg-info ml-3" style="height: 2px;"></b-col>
                    </b-row>
                    <b-row 
                        style="margin-top:2px;margin-right:-21px;"
                    >
                        <b-col cols="2"
                            class="ml-3 bg-light text-dark"
                            style="margin-right:2px;font-size:0.8rem;border-radius: 0.2rem;"
                        >
                            {{ dateFormatter(response.createdAt) }} 
                        </b-col>

                        <b-col cols="9" v-if="! response.adminReponse"
                            class="bg-light text-dark"
                            style="font-size:0.8rem;padding-right:0px;border-radius: 0.2rem;"
                        >
                            {{ response.content }} 
                        </b-col>
                        <b-col cols="9" v-if="response.adminReponse"
                            class="bg-success text-white"
                            style="font-size:0.8rem;padding-right:0px;border-radius: 0.2rem;"
                        >
                            {{ response.content }} 
                        </b-col>
                    </b-row>
                   </b-col>
                  </b-row>

                  <b-row v-if="ticket.status != 3" style="margin-top:8px;margin-right:-21px;">
                    <b-col cols="11" class="bg-secondary text-white ml-3" style="border-radius: 0.2rem;">
                      {{ $t('tick_add_response') }}
                    </b-col>
                  </b-row>

                  <b-row v-if="ticket.status != 3"  style="margin-top:2px;margin-right:-21px;">
                    <b-col cols="8" class="bg-light ml-3" style="border-radius: 0.2rem;margin-right:2px;">
                        <b-form-textarea 
                                v-model="response.content"
                                size="sm"
                                rows="5"
                                class="m-2"
                        ></b-form-textarea>
                    </b-col>
                    <b-col cols="3" class="bg-light px-4" style="border-radius: 0.2rem;">
                        <b-button block
                            variant="primary"
                            size="sm"
                            @click="addResponse(false)"
                            style="text-align: center;font-size:0.8rem;"
                            :disabled="!canAddTicket()"
                            class="mt-4"
                        >
                            {{ $t('ticket_response_add') }}
                        </b-button>
                        <b-button block
                            variant="primary"
                            size="sm"
                            @click="addResponse(true)"
                            style="text-align: center;font-size:0.8rem;"
                            class="mt-2"
                        >
                            {{ $t('ticket_response_close') }}
                        </b-button>
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
import { TicketDetailRespItf, TicketResponseReqItf } from 'vue/types/ticket';

interface data {
    tickets : any[],
    ticket : TicketDetailRespItf,
    response : TicketResponseReqItf,
    isBusy : boolean,
    errorMessage : string,
    errorMessageMod : string,
    successMessageMod : string,
}

export default Vue.extend({
    name: "TicketDetail",
    components: {
    },
    data() : data {
        return {
            tickets : [],
            ticket : {} as TicketDetailRespItf,
            response : {} as TicketResponseReqItf,
            isBusy : false,
            errorMessage : '',
            errorMessageMod : '',
            successMessageMod : '',
        };
    },
    methods : {
        isDisplayed() {
            return ( this.ticket != undefined && this.ticket.ticketUUID != undefined && this.ticket.ticketUUID.length > 0 );
        },
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
        loadTicket(ticketId:string) {
            let config = {
                    headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                    }
                };
            this.isBusy = true;
            this.$axios.get<TicketDetailRespItf>(this.$config.ticketDetailGet+'/'+ticketId+'/',config)
                    .then((response) =>{
                        if (response.status == 200 ) {
                        this.ticket = response.data;
                        this.isBusy = false;
                        }
                    }).catch((err) =>{
                    this.errorMessage = 'error_load_tiket';
                    this.ticket = {} as TicketDetailRespItf;
            })
        },
        canAddTicket() {
            return (this.response.content != undefined && this.response.content.length > 0);
        },
        addResponse(closing:boolean) {
            this.response.ticketUUID = this.ticket.ticketUUID;
            this.response.closing = closing;
            let config = {
                    headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                    }
                };
            this.isBusy = true;
            this.$axios.put(this.$config.ticketCreateResponsePut,this.response,config)
                    .then((response) =>{
                        if (response.status == 201 ) {
                            this.successMessageMod = this.$t('ticket_response_saved') as string;
                            this.loadTicket(this.ticket.ticketUUID);
                            this.response = {} as TicketResponseReqItf;
                            this.$root.$emit("message-ticket-addresponse", "");
                        }
                    }).catch((err) =>{
                        this.errorMessage = 'error_load_tiket';
                        this.response = {} as TicketResponseReqItf;
                    })
        }
    },
    mounted() {
        this.$root.$on("message-ticket-display", (msg:string) => {
            this.response = {} as TicketResponseReqItf;
            this.loadTicket(msg);
        });
    },
})
</script>