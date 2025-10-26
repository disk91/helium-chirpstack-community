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

              <b-card v-if="registrationKey" style="border-radius: 1.5rem;padding: 2rem;">
                <img :src="$config.captchaGetChallenge + '/' + registrationKey +'/'" 
                     alt="captcha" 
                     style="width: 75%; margin: 10px 0px 10px 0px;border-radius: 1.5rem;border:solid 2px #000;" 
                     />
                <div v-if="holdOn" style="margin-top:10px;">
                  <b-card-text class="small mb-2">
                    {{ $t('captcha_instruction') }}
                  </b-card-text>
                  <img :src="$config.captchaGetPad + '/0/' + registrationKey +'/'" alt="captcha pad0" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(0)+';'" @click="selectPad(0)"/>
                  <img :src="$config.captchaGetPad + '/1/' + registrationKey +'/'" alt="captcha pad1" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(1)+';'" @click="selectPad(1)"/>
                  <img :src="$config.captchaGetPad + '/2/' + registrationKey +'/'" alt="captcha pad2" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(2)+';'" @click="selectPad(2)"/>
                  <img :src="$config.captchaGetPad + '/3/' + registrationKey +'/'" alt="captcha pad3" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(3)+';'" @click="selectPad(3)"/>
                  <img :src="$config.captchaGetPad + '/4/' + registrationKey +'/'" alt="captcha pad4" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(4)+';'" @click="selectPad(4)"/>
                  <img :src="$config.captchaGetPad + '/5/' + registrationKey +'/'" alt="captcha pad5" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(5)+';'" @click="selectPad(5)"/>
                  <img :src="$config.captchaGetPad + '/6/' + registrationKey +'/'" alt="captcha pad6" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(6)+';'" @click="selectPad(6)"/>
                  <img :src="$config.captchaGetPad + '/7/' + registrationKey +'/'" alt="captcha pad7" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(7)+';'" @click="selectPad(7)"/>
                  <img :src="$config.captchaGetPad + '/8/' + registrationKey +'/'" alt="captcha pad8" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(8)+';'" @click="selectPad(8)"/>
                  <img :src="$config.captchaGetPad + '/9/' + registrationKey +'/'" alt="captcha pad9" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(9)+';'" @click="selectPad(9)"/>
                  <img :src="$config.captchaGetPad + '/10/' + registrationKey +'/'" alt="captcha pad10" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(10)+';'" @click="selectPad(10)"/>
                  <img :src="$config.captchaGetPad + '/11/' + registrationKey +'/'" alt="captcha pad11" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(11)+';'" @click="selectPad(11)"/>
                  <img :src="$config.captchaGetPad + '/12/' + registrationKey +'/'" alt="captcha pad12" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(12)+';'" @click="selectPad(12)"/>
                  <img :src="$config.captchaGetPad + '/13/' + registrationKey +'/'" alt="captcha pad13" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(13)+';'" @click="selectPad(13)"/>
                  <img :src="$config.captchaGetPad + '/14/' + registrationKey +'/'" alt="captcha pad14" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(14)+';'" @click="selectPad(14)"/>
                  <img :src="$config.captchaGetPad + '/15/' + registrationKey +'/'" alt="captcha pad15" :style="'width: 20%; margin: 10px 0px 0px 0px;border-radius: 1.5rem;border:solid 3px ' + getBorder(15)+';'" @click="selectPad(15)"/>
                </div>
              </b-card>

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
        errorMessage:'',
        registrationKey: undefined as string | undefined,
        holdOn:false as boolean,
        bColor: [] as string[],
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

        if ( this.$config.disableCaptcha == 'true' ) {

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
        } else {
          // no direct validation if captcha enabled
          this.$data.registrationKey = key as string;
          for ( let i=0; i<16; i++ ) {
            this.$data.bColor.push( '#000000' );
          }
          // Just because we want to be sure the captcha is loaded
          setTimeout(() => {
            this.$data.holdOn = true;
          }, 1000);
        }
      }
    },
    methods : {
      async redirectLogin() {
        this.$router.push('/front/login');
      },
      selectPad(padIndex: number) {
        this.$data.bColor[padIndex] = '#40F040';
        this.$forceUpdate();
        
        // Call server to validate
        this.$data.errorMessage='';
        this.$data.successMessage='';
        this.$axios.get(this.$config.captchaGetValidation+'/'+padIndex+'/'+this.$data.registrationKey+'/')
              .then((response) =>{
                  if (response.status == 201 ) {
                    // accepted, we can go for validation
                    this.$data.errorMessage='';
                    this.$data.successMessage='';
                    this.$axios.get(this.$config.validationEndpoint+'/'+this.$data.registrationKey+'/')
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
                    // When 200, the response is accepted but the challenge is not yet completed  
              }).catch((err) =>{
                  if ( err.response.status == 400 ) {
                      // the captcha is not verified ... error & reset
                      for ( let i=0; i<16; i++ ) {
                        this.$data.bColor[i] = '#000000';
                        this.$forceUpdate();
                      }
                      this.$data.errorMessage = 'captcha_wrong_selection';
                      setTimeout(() => {
                        this.$data.errorMessage = '';
                      }, 3000);
                  }
              })
      },
      getBorder(padIndex: number) {
        return this.$data.bColor[padIndex];
      },
    }
})

</script>s