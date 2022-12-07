<template>
<div>
    <form @submit.prevent="userLogin">
      <div>
        <label>Username</label>
        <input type="text" v-model="login.username" />
      </div>
      <div>
        <label>Password</label>
        <input type="text" v-model="login.password" />
      </div>
      <div>
        <button type="submit">Submit</button>
      </div>
    </form>
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
        }
    }
})

</script>