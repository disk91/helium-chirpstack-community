<template>
  <div><Navbar/></div>
</template>

<script lang="ts">
import Vue from 'vue'
import Navbar from '~/components/Navbar.vue';

export default Vue.extend({
    name: "IndexPage",
    auth: false,
  //  middleware: 'auth', // not needed as it is globally setup
    components: { Navbar },
    data() {
      console.log("sur page index");
      console.log(this.$auth.loggedIn);
    }
})


</script>
