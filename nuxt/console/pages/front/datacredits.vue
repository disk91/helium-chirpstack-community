<template>
    <div>
      <Navbar/>
      <AddTenant/>
      <b-card-text class="m-4"><h5>{{$t('dc_title')}}</h5></b-card-text>
      <b-card
               class="m-4"
      >
        <DataCredit/>
        <TransactionList/>
        <AdminTransactionHistory v-if="$store.state.admin" />
      </b-card>
    </div>
</template>

<script lang="ts">
  import Vue from 'vue'
  import Navbar from '~/components/Navbar.vue';
  import AddTenant from '~/components/AddTenant.vue';
  import DataCredit from '~/components/DataCredit.vue';
  import TransactionList from "~/components/TransactionList.vue";
  import AdminTransactionHistory from "~/components/TransactionAdmin.vue";
  import { UserLogin } from 'vue/types/userProfile';

interface data {
    isBusy : boolean,
}

export default Vue.extend({
      name: "ManageDataCredits",
      components: { 
        'Navbar' : Navbar,
        'AddTenant' : AddTenant,
        'DataCredit' : DataCredit,
        'TransactionList' : TransactionList,
        'AdminTransactionHistory' : AdminTransactionHistory,
      },
      data() : data {
        return {
            isBusy : false,
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
        this.$axios.get<UserLogin>(this.$config.userStatusGet,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.isBusy = false;
                  if ( response.data.completion != "completed") {
                    this.$router.push('/front/user');
                  }
                }
            }).catch((err) =>{
            })
    },
    })
</script>
  