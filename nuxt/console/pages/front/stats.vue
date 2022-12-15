<template>
    <div>
      <Navbar/>
      Comming soon...
    </div>
  </template>
  
  <script lang="ts">
  import Vue from 'vue'
  import Navbar from '~/components/Navbar.vue';
  
  export default Vue.extend({
      name: "TestPage",
      
      auth: false,
      //middleware: 'auth', // not needed as it is globally setup
      components: { 
        'Navbar' : Navbar,
      }
  })
  
  </script>
  