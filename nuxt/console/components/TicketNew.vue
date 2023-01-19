<template>
    <div>
        <b-modal id="NewTicket" 
                 centered 
                 content-class="shadow"
                 v-model="show"
                 @show="resetForm"
                 header-border-variant="white"
                 header-text-variant="dark"
                 footer-border-variant="white"
                 class="text-center"
                 button-size="sm"
                 hide-footer
        >
            <template #modal-header>
              <h5 style="text-align:center;width:100%;margin-top:1rem;">{{$t('tick_new_title')}}</h5>
            </template>

            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                       style="font-size:0.8rem;"
                       class="mb-2"
                >
                {{ $t('ticket_new_topic') }}
                </b-col>
                <b-col cols="1"></b-col>
            </b-row>
            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                    class="bg-light p-3"
                    style="border-radius: 0.8rem;"

                >
                <b-row><b-col cols="1"></b-col><b-col cols="10">

                    <b-form-input v-model="ticket.topic"
                                    type="text" 
                                    :placeholder="$t('ticket_new_topic_field')"
                                    class="mb-2"
                                    size="sm"
                    ></b-form-input>

                </b-col><b-col cols="1"></b-col></b-row>

                </b-col>
                <b-col cols="1"></b-col>
            </b-row>

            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                       style="font-size:0.8rem;"
                       class="mb-2"
                >
                {{ $t('ticket_new_details') }}
                </b-col>
                <b-col cols="1"></b-col>
            </b-row>
            <b-row>
                <b-col cols="1"></b-col>
                <b-col cols="10"
                    class="bg-light p-3"
                    style="border-radius: 0.8rem;"

                >
                <b-row><b-col cols="1"></b-col><b-col cols="10">

                    <b-form-textarea 
                                v-model="ticket.details"
                                size="sm"
                                rows="5"
                                class="mb-2"
                    ></b-form-textarea>

                </b-col><b-col cols="1"></b-col></b-row>

                </b-col>
                <b-col cols="1"></b-col>
            </b-row>

            <b-row>
                <b-col cols="2"></b-col>
                <b-col cols="8"
                >
                </b-col>
                <b-col cols="2"></b-col>
            </b-row>
            <b-row class="mb-2 mt-4">
                <b-col cols="2"></b-col>
                <b-col cols="4" class="py-2" style="font-size:0.8rem;">
                    <b-button block
                        variant="outline-dark"
                        size="sm"
                        @click="cancelModal()"
                        style="text-align: left;font-size:0.8rem;"
                    >
                        <b-icon icon="reply" variant="secondary"></b-icon>
                        {{ $t('ticket_new_cancel') }}
                    </b-button>
                </b-col>
                <b-col cols="4" class="py-2" style="font-size:0.8rem;">
                    <b-button block
                        variant="primary"
                        size="sm"
                        @click="createTicket()"
                        style="text-align: right;font-size:0.8rem;"
                        :disabled="!canCreateTicket()"
                    >
                        {{ $t('ticket_new_create') }}
                        <b-icon icon="arrow-right-circle" variant="white"></b-icon>
                    </b-button>
                </b-col>
            </b-row>
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
div#NewTicket___BV_modal_content_ {
    -webkit-border-radius: 1rem !important;
    -moz-border-radius: 1rem !important;
    border-radius: 1rem !important; 
}
</style>

<script lang="ts">
import { data } from 'browserslist';
import Vue from 'vue'
import { TicketCreationReqItf } from 'vue/types/ticket';

interface data {
    ticket : TicketCreationReqItf,
    isBusy : boolean,
    errorMessage : string,
    errorMessageMod : string,
    successMessageMod : string,
    show : boolean,
}

export default Vue.extend({
    name: "DataCreditTransfer",
    data() : data {
        return {
            ticket : {
                topic : '',
                details : '',
            } as TicketCreationReqItf,
            isBusy : false,
            errorMessage : '',
            errorMessageMod : '',
            successMessageMod : '',
            show : false,
        };
    },
    methods : {
        resetForm() {
            this.ticket = {
                topic : '',
                details : '',
            } as TicketCreationReqItf,
            this.successMessageMod = '';
            this.errorMessageMod = '';
        },
        createTicket() {
            
            let config = {
                headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                }
            };        
            this.isBusy = true;
            this.$axios.post(this.$config.ticketCreatePost,this.ticket,config)
                .then((response) =>{
                    if (response.status == 201 ) {
                    this.isBusy = false;
                    this.successMessageMod = this.$t('ticket_new_success') as string;
                    var self = this;
                    setTimeout(function() { 
                            self.$data.show = false;
                            self.resetForm();
                            self.$root.$emit("message-close-ticket-add", "");
                    }, 1500);
                    }
                }).catch((err) =>{
                    this.errorMessageMod = 'ticket_new_failed';
                })

        },
        cancelModal() {
            this.show = false;
            this.resetForm();
        },
        canCreateTicket() {
            if ( this.ticket.topic.length > 0 && this.ticket.details.length > 0 ) {
                return true;
            }
            return false;
        }

    },
    mounted() {
        this.$root.$on("message-display-tick-new", (msg:any) => {
            this.resetForm();
            this.show = true;
        });
    },
})
</script>