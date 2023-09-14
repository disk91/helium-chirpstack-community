<template>
    <div>
      <Navbar/>
      <ManageTenantTemplate/>
      <b-row>
        <b-col cols="6">
          <ManageTenant/>
        </b-col>
        <b-col cols="6">
         <InvoiceSetup/>
        </b-col>
      </b-row>
    </div>
</template>

<script lang="ts">
  import Vue from 'vue'
  import Navbar from '~/components/Navbar.vue';
  import ManageTenantTemplate from '~/components/ManageTenantTemplate.vue'; 
  import ManageTenant from '~/components/ManageTenant.vue';
  import InvoiceSetup from '~/components/InvoiceSetup.vue';

  export default Vue.extend({
      name: "ManageTenantProfiles",
      components: { 
        'Navbar' : Navbar,
        'ManageTenantTemplate' : ManageTenantTemplate,
        'ManageTenant' : ManageTenant,
        'InvoiceSetup' : InvoiceSetup,
      },
    })
</script>
  