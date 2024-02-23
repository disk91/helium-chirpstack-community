<template>
    <div>
      <Navbar/>
      <AddTenant/>
      <b-card-text class="m-4"><h5>{{$t('inac_main_title')}}</h5></b-card-text>
      <b-card class="m-4">
        <InacMain/>
      </b-card>
    </div>
  </template>
  
  <script lang="ts">
  import Vue from 'vue'
  import Navbar from '~/components/Navbar.vue';
  import AddTenant from '~/components/AddTenant.vue';
  import InacMain from '~/components/InacMain.vue';

  export default Vue.extend({
      name: "InactivDevices",
      auth: false,
      components: { 
        'Navbar' : Navbar,
        'AddTenant' : AddTenant,
      }
  })
  
  </script>
  