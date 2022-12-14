<template>
  <div>
    <Navbar/>
    <AddTenant/>
    <Chirpstack/>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import Navbar from '~/components/Navbar.vue';
import Chirpstack from '~/components/Chirpstack.vue';
import AddTenant from '~/components/AddTenant.vue';

export default Vue.extend({
    name: "IndexPage",
    //auth: false,
    data() {
      return {
        AddTenantAction: false,
      }
    },
    components: { 
      'Navbar' : Navbar,
      'Chirpstack' : Chirpstack,
      'AddTenant' : AddTenant,
    }
})

</script>
