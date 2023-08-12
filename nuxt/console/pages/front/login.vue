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
                  {{ $t('welcome_message') }}
                </span>
              </b-card-text>
              <b-card-text class="small">
                <b-icon v-if="(errorMessage.length > 0)" icon="x-circle" variant="danger"></b-icon>
                <span v-if="(errorMessage.length > 0)" class="text-danger">
                  {{ errorMessage }}
                </span>
              </b-card-text>
              <form @submit.prevent="userLogin">
                    <b-form-input v-model="login.username"
                                  type="text" 
                                  :placeholder="$t('username')"
                                  class="mb-1"
                                  ></b-form-input>
                    <b-form-input v-model="login.password"
                                  type="password" 
                                  :placeholder="$t('password')"
                                  class="mb-1"
                                  ></b-form-input>
                    <b-button block 
                              variant="primary" 
                              @click="userLogin()"
                              class="mb-2">
                              {{ $t('submit') }}</b-button>
              </form> 
              <b-button block 
                        v-if="status.openForRegistration"
                        variant="outline-primary"
                        @click="redirectToSignup()"
                        >{{ $t('signup_message') }}</b-button>
              <b-card-text @click="redirectPassLost()" style="text-align:right;">
                <span style="color: rgb(150,50,50);text-decoration: underline;">
                    {{ $t('lost_password')}} 
                </span>
              </b-card-text>
            </b-card>
        </div>
        <b-alert 
            v-model="umessage.showAlert"
            class="position-fixed fixed-top m-0 rounded-0"
            style="z-index:2000;"
            :variant="umessage.bgTitle"
            dismissible
        >
            {{ umessage.content }}
        </b-alert>

  </div>
</template>


<script lang="ts">
import Vue from 'vue'
import { Store } from 'vuex/types/index';
import { UserMessage } from 'vue/types/message';
import { ConsoleStatusRespItf } from 'vue/types/misc';

interface data {
    messages : UserMessage[],
    login : {
        username : string,
        password : string,
    },
    umessage : {
          showAlert : boolean,
          bgTitle : string,
          content : string,
    },
    errorMessage : string,
    status : ConsoleStatusRespItf,
}

export default Vue.extend({
    name: "LoginPage",
    auth: false,
    data() {
        return {
            login: {
                username: '',
                password: ''
            },
            messages : [],
            umessage : {
                showAlert : false,
                bgTitle : 'info',
                content : '',
            },
            errorMessage : '',
            status : {
                openForRegistration : true
            },
        }
    },
    async fetch() {
        let config = {
            headers: {
              'Content-Type': 'application/json',
            }
        };
        this.$data.messages = [];
        this.$axios.get(this.$config.messagePublicGet,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.$data.messages = response.data;
                  this.displayMessages();
                }
            }).catch((err) =>{
            })
            this.$data.messages = [];
        this.$axios.get(this.$config.statusGet,config)
            .then((response) => {
                if ( response.status == 200 ) {
                    this.$data.status = response.data;
                }
            }).catch((err) => {
                this.$router.push('/front/failed');
            })
    },
    methods: {
        async displayMessages() {
            if ( this.$data.messages.length > 0 ) {
                this.$data.messages.forEach( (message : UserMessage) => {
                    var variant = "info";
                    switch (message.category) {
                    default:
                    case 0 as any : variant = "primary"; break;
                    case 1 as any : variant = "warning"; break;
                    case 2 as any : variant = "danger"; break;
                    }
                    var type = "b-toaster-top-full";
                    this.$data.umessage.showAlert = true;
                    this.$data.umessage.bgTitle = variant;
                    this.$data.umessage.content = message.content;
                })
            }
        },
        async userLogin() {
            this.errorMessage='';
            try {
                await this.$auth.loginWith(
                    'local',
                    {data: this.login }
                ).then( resp => {
                    if ( resp != null ) {
                       let chBearer = resp.data.chirpstackBearer;
                       this.$store.commit('setChirpstackBearer', chBearer);
                       localStorage.setItem("token", chBearer);
                       let conBearer = resp.data.consoleBearer;
                       this.$store.commit('setConsoleBearer', conBearer);
                       this.$store.commit('setUserAdmin',resp.data.admin);
                       if ( resp.data.userConditionChanged ) {
                         this.$router.push('/front/conditions/');
                       } else {
                        this.$router.push('/front/');
                       }
                    } else {
                        // @TODO should be an error page ?
                        this.$router.push('/front/');
                    }
                });
            } catch (err) {
                this.errorMessage=this.$t('login_error') as string;
            }
        },
        async redirectToSignup() {
            this.$router.push('/front/signup');
        },
        async redirectPassLost() {
            this.$router.push('/front/lostpass');
        }
    },
    mounted() {
        this.$store.commit('setChirpstackBearer', '');
        localStorage.setItem('token', '');
        this.$store.commit('setConsoleBearer', '');
        this.$store.commit('setUserAdmin',false);
    }
})

</script>