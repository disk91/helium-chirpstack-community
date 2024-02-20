<template>
    <div>
      <Navbar/>
      <AddTenant/>
      <b-card-text class="m-4"><h5>{{$t('shield_admin_title')}}</h5></b-card-text>
      <b-card
               class="m-4"
      >
        <ShieldMain/>
      </b-card>
    </div>
</template>
  
<script lang="ts">
  import Vue from 'vue';
  import Navbar from '~/components/Navbar.vue';
  import AddTenant from '~/components/AddTenant.vue';
  import ShieldMain from '~/components/ShieldMain.vue';

  export default Vue.extend({
      name: "ShieldPage",
      
      auth: false,
      components: { 
        'Navbar' : Navbar,
        'AddTenant' : AddTenant,
        'ShieldMain' : ShieldMain,
      }
  })
  
</script>
  