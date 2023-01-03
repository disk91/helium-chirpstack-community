<template>
    <div>
      <Navbar/>
      <AddTenant/>
      <Profile/>
    </div>
</template>

<script lang="ts">
  import Vue from 'vue'
  import Navbar from '~/components/Navbar.vue';
  import AddTenant from '~/components/AddTenant.vue'; 
  import Profile from '~/components/Profile.vue'

  export default Vue.extend({
      name: "ManageProfile",
      components: { 
        'Navbar' : Navbar,
        'AddTenant' : AddTenant,
        'Profile' : Profile,
      },
    })
</script>
  