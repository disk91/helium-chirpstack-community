<template>
</template>

<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
    name: "IndexMain",
    mounted: function() {
      this.$router.push('/front/login');
    }

})
</script>