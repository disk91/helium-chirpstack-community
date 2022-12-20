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
                  {{ $t('signup_wmessage') }}
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
                    <b-form-group :state="passwordState" :invalid-feedback="$t('signup_verif_password')" style="text-align:left;" class="mb-1 small">
                        <b-form-input v-model="signup.password"
                                  id="signup-password"
                                  type="password" 
                                  :placeholder="$t('signup_password')"
                                  :state="passwordState"
                                  class="mb-1"
                                  ></b-form-input>
                    </b-form-group>
                    <b-form-group :state="rpasswordState" :invalid-feedback="$t('signup_verif_rpassword')" style="text-align:left;" class="mb-1 small">
                        <b-form-input v-model="signup.rpassword"
                                  id="signup-rpassword"
                                  type="password" 
                                  :placeholder="$t('signup_rpassword')"
                                  :state="rpasswordState"
                                  class="mb-1"
                                  ></b-form-input>
                    </b-form-group>
                    <b-form-group :state="tenantState" :invalid-feedback="$t('signup_verif_tenant')" style="text-align:left;" class="mb-1 small">
                        <b-form-input v-model="signup.tenantName"
                                  id="signup-tenant"
                                  type="text" 
                                  :placeholder="$t('signup_tenant')"
                                  :state="tenantState"
                                  class="mb-1"
                                  ></b-form-input>
                    </b-form-group>
                    <b-form-group :description="$t('signup_desc_invite')" style="text-align:left;" class="mb-1 small">
                        <b-form-input v-model="signup.inviteCode"
                                  id="signup-invite"
                                  type="text" 
                                  :placeholder="$t('signup_invite')"
                                  class="mb-1"
                                  ></b-form-input>
                    </b-form-group>
                    <b-form-group :state="conditionState" :invalid-feedback="$t('signup_verif_conditions')" style="text-align:left;" class="mb-1 small">
                        <b-form-checkbox v-model="signup.conditionAccepted"
                                  id="signup-conditions"
                                  value='true'
                                  unchecked-value='false'
                                  class="mb-1"
                                  style="text-align:left;"
                                  :state="conditionState"
                                  >
                                  {{ $t('signup_accept_1') }}
                                  <b-link :href="$config.termAndUse">{{ $t('signup_accept_2') }}</b-link>
                                  {{ $t('signup_accept_3') }}
                                </b-form-checkbox>
                    </b-form-group>
               <b-button block @click="userSignup"
                              :disabled="acceptSubmit"
                              variant="primary" 
                              class="mb-2">
                              {{ $t('signup_button') }}</b-button>
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
    name: "SignupPage",
    auth: false,
    data() {
        return {
            signup: {
                username: '',
                password: '',
                rpassword: '',
                tenantName: '',
                inviteCode: '',
                conditionAccepted: 'false',
                errorMessage: '',
            }
        }
    },
    computed: {
        emailState() {
            if ( this.$data.signup.username.length < 5 ) return null;
            return (this.$data.signup.username.indexOf('@') >= 0);
        },
        passwordState() {
            if ( this.$data.signup.password.length == 0 ) return null;
            return (this.$data.signup.password.length >= 12 );
        },
        rpasswordState() {
            if ( this.$data.signup.rpassword.length == 0 ) return null;
            return ( this.$data.signup.password == this.$data.signup.rpassword );
        },
        tenantState() {
            if ( this.$data.signup.tenantName.length == 0 ) return null;
            return ( this.$data.signup.tenantName.length > 3 );
        },
        conditionState() {
            if ( this.emailState != true || this.passwordState != true || this.rpasswordState != true || this.tenantState != true ) return null;
            return ( this.$data.signup.conditionAccepted == 'true');
        },
        acceptSubmit() {
            if ( this.emailState != true || this.passwordState != true || this.rpasswordState != true || this.tenantState != true || this.conditionState != true ) return true;
            return false;
        }
    },
    methods: {
        async userSignup() {
            let body = {
                username: this.$data.signup.username,
                password: this.$data.signup.password,
                tenantName: this.$data.signup.tenantName,
                inviteCode: this.$data.signup.inviteCode,
                conditionsAccepted: (this.$data.signup.conditionAccepted == 'true'),
            };
            this.$data.signup.errorMessage='';
            let config = {
                  headers: {
                      'Content-Type': 'application/json'  
                  }
              };
            this.$axios.post(this.$config.signupEndpoint,body,config)
            .then((response) =>{
                if (response.status == 200 ) {
                    this.$router.push('/front/signupPending');
                }
            }).catch((err) =>{
                if ( err.response.status == 400 ) {
                    let message = err.response.data.errorMessage;
                    this.$data.signup.errorMessage = 'sret_'+message;
                }
            })
        },
        async printData() {
            //console.log(this.signup);
        },
        async redirectLogin() {
            this.$router.push('/front/login');
        },
    }
})

</script>