<template>
  <div style="height: 100vh; width: 100%; 
        z-index: 1; overflow: hidden; position: relative; 
        display: flex; 
        background: url('/static/front/background.svg') center center / cover rgb(240, 242, 245); 
        box-sizing: border-box;
        justify-content: center;">
        <div style="max-width: 500px; position: absolute; 
            top: 47%; left: 50%; transform: translate(-50%, -50%); 
            height: auto;">
            <b-card align="center" style="border-radius: 1.5rem;padding: 2rem;">
              <b-card-img src="/static/front/logo.svg" 
                          alt="logo" 
                          style="width: 25%; margin: 20px 0px 20px 0px;" 
                          top></b-card-img>
              <b-card-text>
                <h1 class="mb-2">{{ $config.consoleName }}</h1>
              </b-card-text>
              <b-card-text class="small mb-2 text-danger">
                <b-icon icon="exclamation-circle-fill" variant="danger"></b-icon>
                <span v-if="error.statusCode === 404" class="mb-3">{{ $t('page_not_found') }}</span>
                <span v-else class="mb-3">{{ $t('page_error') }}</span>
              </b-card-text>
              <b-card-text class="small mb-2">
                <NuxtLink to="/front/">{{ $t('page_home') }}</NuxtLink>
              </b-card-text>
            </b-card>
        </div>
  </div>
</template>

<script>
export default {
  props: ['error'],
} 
</script>