<template>
  <div style="height: 100vh; width: 100%; 
        z-index: 1; overflow: hidden; position: relative; 
        display: flex; 
        background: url('/static/front/background.svg') center center / cover rgb(240, 242, 245); 
        box-sizing: border-box;
        justify-content: center;">
        <div style="max-width: 1600; position: absolute; 
            top: 47%; left: 10%; transform: translate(-5%, -50%); 
            height: auto;">
            <b-card align="center" style="border-radius: 1.5rem;padding: 2rem;">
              <b-card-img src="/static/front/logo.svg" 
                          alt="logo" 
                          style="width: 5%; margin: 0px 0px 10px 0px;" 
                          top></b-card-img>
              <b-card-text>
                <h2 class="mb-2">Helium Console migration is getting started</h2>
              </b-card-text>
              <b-card-text class="small">
                <span style="color: rgb(52, 58, 63);">
                  Earlier this year, helium community take the decision to move out from the helium legacy console (hosted on helium-iot.eu) for getting benefit of more mature
                  and advanced opensource LoRaWan Network Servers (LNS) also named openLNS in the community. 
                </span>
              </b-card-text>
              <b-card-text class="small">
                <span style="color: rgb(56,162,255);">
                  Chirpstack is the favorite openLNS, helium-IoT, decided to support. This is providing to helium user new features like support of Class B & C devices, support of ABP and a better
                  long term support for the new LoRaWan features like repeaters and much more.
                </span>
              </b-card-text>
              <b-card-text class="small">
                <span style="color: rgb(52, 58, 63);">
                  Since August 16th, the Chirpstack environment is open for registration and device migration. It already run 5000+ production devices and wait for your devices. Some documentation and 
                  tutorial will come soon. The Helium Legacy console will still be available at least to the end of 2023, but it will be later decommissioned.
                </span>
              </b-card-text>
              <b-card-text class="small">
                <span style="color: rgb(56,162,255); text-align:left;">
                    <ul>
                        <li>
                            New user on Helium-IoT, may directly register to the OpenLNS console.
                        </li>
                        <li>
                            Legacy console user, please shorty consider migration to Chirpstack, immediatly start investigating openLNS by registering to it.
                            Migration is not one-shot, you can declare new devices in openLNS, migrate a couple and later migrate all.
                        </li>
                    </ul>
                </span>
              </b-card-text>
              <b-button block 
                        variant="outline-primary"
                        @click="redirectTologin()"
                        >Continue with Legacy</b-button>
                        <b-button block 
                        variant="primary"
                        @click="redirectToChirp()"
                        >Go to OpenLNS</b-button>
              <b-card-text class="small mt-2">
                <span style="color: rgb(56,162,255);">
                  openLNS migration support is accessible on Helium Discord channel helium-iot.eu, through the Helium-IoT.eu contact form and through the Service Request menu in openLNS. Once your migration is 
                  completed, you can also contact us for getting a DC tranfer between your legacy account and your openLNS account. 
                </span>
              </b-card-text>
            </b-card>
        </div>
  </div>
</template>


<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
    name: "TransitionPage",
    auth: false,
    data() {
        return {
        }
    },
    async fetch() {
    },
    methods: {
        async redirectTologin() {
            window.location.href = 'https://console.helium-iot.eu';
        },
        async redirectToChirp() {
            this.$router.push('/front/login');
        }
    },
    mounted() {
    }
})

</script>