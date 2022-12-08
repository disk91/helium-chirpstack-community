<template>
  <div style="height: 100vh; width: 100%; 
        z-index: 1; overflow: hidden; position: relative; 
        display: flex; 
        background: url('/static/background.svg') center center / cover rgb(240, 242, 245); 
        box-sizing: border-box;
        justify-content: center;">
        <div style="max-width: 500px; position: absolute; 
            top: 47%; left: 50%; transform: translate(-50%, -50%); 
            height: auto;">
            <b-card align="center" style="border-radius: 1.5rem;padding: 2rem;">
              <b-card-img src="/static/logo.svg" 
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
                    <b-button block variant="primary" class="mb-2">{{ $t('submit') }}</b-button>
              </form> 
              <b-button block 
                        variant="outline-primary"
                        @click="redirectToSignup()"
                        >{{ $t('signup_message') }}</b-button>
            </b-card>
        </div>
  </div>
</template>


<script lang="ts">
import Vue from 'vue'
import { Store } from 'vuex/types/index';

export default Vue.extend({
    name: "LoginPage",
    auth: false,
    data() {
        return {
            login: {
                username: '',
                password: ''
            }
        }
    },
    methods: {
        async userLogin() {
            try {
                await this.$auth.loginWith(
                    'local',
                    {data: this.login }
                ).then( resp => {
                    if ( resp != null ) {
                       let chBearer = resp.data.chirpstackBearer
                       this.$store.commit('setChirpstackBearer', chBearer)
                       localStorage.setItem("token", chBearer)
                       console.log(chBearer);
                       let conBearer = resp.data.consoleBearer
                       this.$store.commit('setConsoleBearer', conBearer)
                    }
                    this.$router.push('/front/');
                });
            } catch (err) {
                console.log(err);
            }
        },
        async redirectToSignup() {
            this.$router.push('/front/signup');
        }
    }
})

</script>