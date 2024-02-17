<template>
    <div>
      <Navbar/>
      <AddTenant/>
      <b-card-text class="m-4"><h5>{{$t('menu_showcontext')}}</h5></b-card-text>
      <b-card
               class="m-4"
      >
        <CtxMain/>
      </b-card>
    </div>
</template>
  
<script lang="ts">
  import Vue from 'vue';
  import Navbar from '~/components/Navbar.vue';
  import AddTenant from '~/components/AddTenant.vue';
  import CtxMain from '~/components/CtxMain.vue';

  export default Vue.extend({
      name: "ShowContextPage",
      
      auth: false,
      components: { 
        'Navbar' : Navbar,
        'AddTenant' : AddTenant,
        'CtxMain' : CtxMain,
      }
  })
  
</script>
  