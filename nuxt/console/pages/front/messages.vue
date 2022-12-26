<template>
    <div>
      <Navbar/>
      <ManageMessages/>
    </div>
</template>

<script lang="ts">
  import Vue from 'vue'
  import Navbar from '~/components/Navbar.vue';
  import ManageMessages from '~/components/ManageMessages.vue';

  export default Vue.extend({
      name: "ManageTenants",
      components: { 
        'Navbar' : Navbar,
        'ManageMessages' : ManageMessages,
      },
    })
</script>
  