<template>
    <div>
      <Navbar/>
      <ManageTenantTemplate/>
      <ManageTenant/>
    </div>
</template>

<script lang="ts">
  import Vue from 'vue'
  import Navbar from '~/components/Navbar.vue';
  import ManageTenantTemplate from '~/components/ManageTenantTemplate.vue'; 
  import ManageTenant from '~/components/ManageTenant.vue';

  export default Vue.extend({
      name: "ManageTenants",
      components: { 
        'Navbar' : Navbar,
        'ManageTenantTemplate' : ManageTenantTemplate,
        'ManageTenant' : ManageTenant,
      },
    })
</script>
  