<template>
    <div>
      <Navbar/>
      <AddTenant/>
      <b-card-text class="m-4"><h5>{{$t('menu_user_stats')}}</h5></b-card-text>
      <BasicStat/>
    </div>
  </template>
  
  <script lang="ts">
  import Vue from 'vue'
  import Navbar from '~/components/Navbar.vue';
  import BasicStat from '~/components/BasicStats.vue';
  import AddTenant from '~/components/AddTenant.vue';

  export default Vue.extend({
      name: "TestPage",
      
      auth: false,
      //middleware: 'auth', // not needed as it is globally setup
      components: { 
        'Navbar' : Navbar,
        'BasicStat' : BasicStat,
        'AddTenant' : AddTenant,
      }
  })
  
  </script>
  