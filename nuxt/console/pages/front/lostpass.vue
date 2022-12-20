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
                  {{ $t('passlost_wmessage') }}
                </span>
              </b-card-text>
              <b-form>
                    <b-form-group :state="emailState" :invalid-feedback="$t('signup_verif_email')" style="text-align:left;" class="mb-1 small">
                        <b-form-input v-model="signup.username"
                                  id="signup-email"
                                  type="text" 
                                  :placeholder="$t('username')"
                                  :state="emailState"
                                  ></b-form-input>
                    </b-form-group>
               <b-button block @click="userPassLost"
                              :disabled="acceptSubmit"
                              variant="primary" 
                              class="mb-2">
                              {{ $t('ploss_button') }}</b-button>
               </b-form>

               <b-card-text>
                <span style="color: rgb(150,50,50);">
                    {{ $t(signup.errorMessage)}} 
                </span>
              </b-card-text>

              <b-card-text @click="redirectLogin()" style="text-align:left;">
                <span style="color: rgb(50,150,50);">
                    &lt;&lt; {{ $t('back_to_login_page')}} 
                </span>
              </b-card-text>
            </b-card>
        </div>
  </div>
</template>


<script lang="ts">
import Vue from 'vue'
import { Store } from 'vuex/types/index';

export default Vue.extend({
    name: "PLostPage",
    auth: false,
    data() {
        return {
            signup: {
                username: '',
                errorMessage: '',
            }
        }
    },
    computed: {
        emailState() {
            if ( this.$data.signup.username.length < 5 ) return null;
            return ( this.$data.signup.username.indexOf('@') >= 0 );
        },
        acceptSubmit() {
            if ( this.emailState != true ) return true;
            return false;
        }
    },
    methods: {
        async userPassLost() {
            let body = {
                username: this.signup.username,
            };
            this.signup.errorMessage='';
            let config = {
                  headers: {
                      'Content-Type': 'application/json'  
                  }
              };
            this.$axios.post(this.$config.passLostEndpoint,body,config)
            .then((response) =>{
                if (response.status == 200 ) {
                    this.$router.push('/front/passLostPending');
                }
            }).catch((err) =>{
                if ( err.response.status == 400 ) {
                    let message = err.response.data.errorMessage;
                    this.signup.errorMessage = 'sret_'+message;
                }
            })
        },
        async redirectLogin() {
            this.$router.push('/front/login');
        },
    }
})

</script>