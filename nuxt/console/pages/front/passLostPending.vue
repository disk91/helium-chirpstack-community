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
              <b-card-text class="small mb-2">
                <span style="color: rgb(56,162,255);" class="mb-2">
                  {{ $t('passlost_pmessage_1') }}
                </span><br/>
              </b-card-text>
              <b-card-text class="small mb-2">
                  <b>{{ $t('passlost_pmessage_2') }}</b>
              </b-card-text>
              <b-card-text class="small">
                  {{ $t('passlost_pmessage_3') }}
              </b-card-text>
            </b-card>
        </div>
  </div>
</template>


<script lang="ts">
import Vue from 'vue'
import { Store } from 'vuex/types/index';

export default Vue.extend({
    name: "PassLostPendingPage",
    auth: false,
})

</script>