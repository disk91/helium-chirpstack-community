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
              <b-card-text class="mb-2 text-success" v-show="printSuccess">
                <b-icon icon="check-square" variant="success"></b-icon>
                {{ $t(successMessage) }}
              </b-card-text>
              <b-button block @click="redirectLogin"
                              variant="success" 
                              class="my-2"
                              v-show="printSuccess"
                              >
                              {{ $t('signup_login_button') }}</b-button>
              <b-card-text class="small mb-2 text-danger" v-show="printError">
                <b-icon icon="exclamation-circle-fill" variant="danger"></b-icon>
                {{ $t(errorMessage) }}
              </b-card-text>
            </b-card>
        </div>
  </div>
</template>


<script lang="ts">
import Vue from 'vue'
import { Store } from 'vuex/types/index';

export default Vue.extend({
    name: "SignupValidationPage",
    auth: false,
    data() {
      return {
        successMessage:'',
        errorMessage:''
      }
    },
    computed : {
      printError() {
          return (this.$data.errorMessage.length > 0);
      },
      printSuccess() {
          return (this.$data.successMessage.length > 0);
      }
    },
    created() {
      let key = this.$route.query.key;
      if ( key == undefined ) this.$data.errorMessage = "signup_vmessage_nokey";
      else if ( key.length != 80 ) this.$data.errorMessage = "signup_vmessage_badkey";
      else {
        this.$data.errorMessage='';
        this.$data.successMessage='';
        this.$axios.get(this.$config.validationEndpoint+'/'+key+'/')
            .then((response) =>{
                if (response.status == 200 ) {
                    this.$data.successMessage = "signup_vmessage_success";
                }
            }).catch((err) =>{
                if ( err.response.status == 400 ) {
                    let message = err.response.data.errorMessage;
                    this.$data.errorMessage = 'sret_'+message;
                }
            })
      }
    },
    methods : {
      async redirectLogin() {
        this.$router.push('/front/login');
      }
    }
})

</script>