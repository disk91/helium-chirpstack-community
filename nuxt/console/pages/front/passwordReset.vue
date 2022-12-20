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
              <b-card-text class="small">
                <span style="color: rgb(56,162,255);">
                  {{ $t('passlost_emessage') }}
                </span>
              </b-card-text>
              <b-form>
                <b-form-group :state="passwordState" :invalid-feedback="$t('signup_verif_password')" style="text-align:left;" class="mb-1 small">
                        <b-form-input v-model="pReset.password"
                                  id="signup-password"
                                  type="password" 
                                  :placeholder="$t('signup_password')"
                                  :state="passwordState"
                                  class="mb-1"
                                  ></b-form-input>
                    </b-form-group>
                    <b-form-group :state="rpasswordState" :invalid-feedback="$t('signup_verif_rpassword')" style="text-align:left;" class="mb-1 small">
                        <b-form-input v-model="pReset.rpassword"
                                  id="signup-rpassword"
                                  type="password" 
                                  :placeholder="$t('signup_rpassword')"
                                  :state="rpasswordState"
                                  class="mb-1"
                                  ></b-form-input>
                    </b-form-group>
                    <b-card-text>
                      <span style="color: rgb(150,50,50);">
                          {{ $t(errorMessage)}} 
                      </span>
                    </b-card-text>
                    <b-button block @click="applyPasswordChange"
                              :disabled="acceptSubmit"
                              variant="primary" 
                              class="mb-2">
                              {{ $t('ploss_button') }}</b-button>
               </b-form>
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
            </b-card>
        </div>
  </div>
</template>


<script lang="ts">
import Vue from 'vue'
import { Store } from 'vuex/types/index';

export default Vue.extend({
    name: "PasswordResetPage",
    auth: false,
    data() {
      return {
        successMessage:'',
        errorMessage:'',
        pReset: {
          password: '',
          rpassword: ''
        }
      }
    },
    computed : {
      printError() {
          return ( this.$data.errorMessage.length > 0 );
      },
      printSuccess() {
          return ( this.$data.successMessage.length > 0 );
      },
      passwordState() {
            if ( this.$data.pReset.password.length == 0 ) return null;
            return (this.$data.pReset.password.length >= 12 );
        },
      rpasswordState() {
            if ( this.$data.pReset.rpassword.length == 0 ) return null;
            return ( this.$data.pReset.password == this.$data.pReset.rpassword );
      },
      acceptSubmit() {
            if ( this.passwordState != true || this.rpasswordState != true || this.errorMessage.length > 0 ) return true;
            return false;
      }
    },
    created() {
      let key = this.$route.query.key;
      if ( key == undefined ) this.$data.errorMessage = "passlost_vmessage_nokey";
      else if ( key.length != 80 ) this.$data.errorMessage = "passlost_vmessage_badkey";
    },
    methods : {
      async redirectLogin() {
        this.$router.push('/front/login');
      }, 
      async applyPasswordChange() {
        let key = this.$route.query.key;
        if ( key == undefined ) this.$data.errorMessage = "passlost_vmessage_nokey";
        else if ( key.length != 80 ) this.$data.errorMessage = "passlost_vmessage_badkey";
        else {
          this.$data.errorMessage='';
          this.$data.successMessage='';
          let body = {
                  password: this.$data.pReset.password,
                  validationKey: key,
              };
          let config = {
                  headers: {
                    'Content-Type': 'application/json'  
                  }
              };
          this.$axios.post(this.$config.passChangeEndpoint, body, config)
              .then((response) =>{
                  if (response.status == 200 ) {
                      this.$data.successMessage = "passlost_vmessage_success";
                      this.$data.pReset.password='';
                      this.$data.pReset.rpassword='';
                  }
              }).catch((err) =>{
                  if ( err.response.status == 400 ) {
                      let message = err.response.data.errorMessage;
                      this.$data.errorMessage = 'sret_'+message;
                  }
              })
        }
      }
    }
})

</script>