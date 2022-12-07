<template>
  <div>
    <Navbar/>
    <Chirpstack/>
  </div>
</template>

<script lang="ts">
import Vue from 'vue'
import Navbar from '~/components/Navbar.vue';
import Chirpstack from '~/components/Chirpstack.vue';

export default Vue.extend({
    name: "IndexPage",
    
    //auth: false,
    //middleware: 'auth', // not needed as it is globally setup
    components: { 
      'Navbar' : Navbar,
      'Chirpstack' : Chirpstack 
    }
})

</script>
