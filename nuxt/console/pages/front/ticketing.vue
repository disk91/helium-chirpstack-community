<template>
    <div>
      <Navbar/>
      <AddTenant/>
      <b-card-text class="m-4"><h5>{{$t('ticket_title')}}</h5></b-card-text>
      <b-card
               class="m-4"
      >
        <b-row cols="3" class="mb-2">
            <b-col cols="3" class="py-2" style="font-size:0.8rem;">
                <b-button block
                    variant="outline-dark"
                    size="sm"
                    @click="onCreateNewTicket()"
                    style="text-align: left;font-size:0.8rem;"
                >
                    <b-icon icon="plus-circle" variant="secondary"></b-icon>
                    {{ $t('tick_add_ticket') }}
                </b-button>
            </b-col>
        </b-row>
        <TicketList/>
        <TicketDetail/>
      </b-card>
      <TicketNew/>
    </div>
</template>

<script lang="ts">
  import Vue from 'vue'
  import Navbar from '~/components/Navbar.vue';
  import AddTenant from '~/components/AddTenant.vue';
  import TicketList from "~/components/TicketList.vue";
  import TicketNew from "~/components/TicketNew.vue";
  import TicketDetail from "~/components/TicketDisplay.vue";

interface data {
    isBusy : boolean,
}

export default Vue.extend({
      name: "ManageDataCredits",
      components: { 
        'Navbar' : Navbar,
        'AddTenant' : AddTenant,
        'TicketList' : TicketList,
        'TicketDetail' : TicketDetail,
      },
      data() : data {
        return {
            isBusy : false,
        };
      },
      methods: {
        onCreateNewTicket(){
          this.$root.$emit("message-display-tick-new", "");
        },
      },
      mounted() {
        this.$root.$on("message-close-ticker-new", (msg:any) => {
            this.$fetch();
        });
      },
    })
</script>
  