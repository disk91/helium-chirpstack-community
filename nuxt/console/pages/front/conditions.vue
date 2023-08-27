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
                <span style="color: rgb(148, 24, 51);">
                  {{ $t('uc_condition_message') }}
                </span><br/>
                <b-link :href="$config.termAndUse">{{ $t('uc_condition_visit') }}</b-link>
                <br/>
                <b-form>
                <b-form-group style="text-align:center;" class="mb-1 small mt-2">
                        <b-form-checkbox v-model="conditionAccepted"
                                  id="signup-conditions"
                                  value='true'
                                  unchecked-value='false'
                                  class="mb-1"
                                  style="text-align:center;"
                                  :state="conditionState"
                                  >
                                  {{ $t('uc_condition_valid') }}
                        </b-form-checkbox>
                </b-form-group>
                <b-button block @click="conditionValidated"
                              :disabled="acceptSubmit"
                              variant="primary" 
                              class="mb-2">
                              {{ $t('uc_condition_submit') }}</b-button>
               </b-form>
              </b-card-text>
            </b-card>
        </div>

  </div>
</template>


<script lang="ts">
import Vue from 'vue'

export default Vue.extend({
    name: "UserConditionPage",
    auth: false,
    data() {
        return {
          polling : null,
          conditionAccepted : 'false',
          conditionState : true,
          errorMessage : '',
        }
    },
    computed: {
      acceptSubmit() {
            return ! (this.$data.conditionAccepted == 'true');
      }
    },
    methods: {
      async conditionValidated() {
        this.errorMessage='';
        let config = {
            headers: {
                'Content-Type': 'application/json',  
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,
            }
        };
        this.$axios.put(this.$config.userConditionPut, {}, config)
            .then((response) =>{
              if (response.status == 200 ) {
                this.$router.push('/front/');
              }
            }).catch((err) =>{
            })  
      }
    },
    mounted() {
    },
})

</script>