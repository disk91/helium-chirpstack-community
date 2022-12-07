<template>
   <div class="container">
    <h1 v-if="error.statusCode === 404">Page not found !</h1>
    <h1 v-else>An error occurred</h1>
    <NuxtLink to="/front/">Home page</NuxtLink>
  </div>
</template>

<script>
export default {
  props: ['error'],
  layout: 'error_layout' // you can set a custom layout for the error page
} 
</script>