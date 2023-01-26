<template>
    <div>
      <Navbar/>
      <AddTenant/>
      <b-card-text class="m-4"><h5>{{$t('mig_title')}}</h5></b-card-text>

      <b-row>
        <b-col cols="1"></b-col>
        <b-col cols="10">        

          <b-card no-body>
            <b-tabs v-model="tabIndex" small card>
              <b-tab 
                :disabled="disableSetupTab()"
              >
                <template #title> 
                  <b-icon icon="link45deg" variant="primary"></b-icon> {{ $t('mig_setup') }}
                </template>
               
                <MigrationHeliumSetup :consoleObject="heliumConsoleService"/>

              </b-tab>
              <b-tab 
                :disabled="disableChirpstackTab()"
              >
                <template #title> 
                  <b-icon icon="hdd-network" variant="primary"></b-icon> {{ $t('mig_chirpstack') }}
                </template>
              
                <MigrationChirpstackSetup :consoleObject="heliumConsoleService" :chripstackObject="chirpstackService"/>
              
              </b-tab>
              <b-tab title="Premium Plan" disabled>Sibzamini!</b-tab>
            </b-tabs>
          </b-card>

        </b-col>
        <b-col cols="1"></b-col>
      </b-row>
    </div>
</template>

<script lang="ts">
  import Vue from 'vue'
  import Navbar from '~/components/Navbar.vue';
  import AddTenant from '~/components/AddTenant.vue';
  import MigrationHeliumSetup from '~/components/MigrationHeliumSetup.vue';
  import MigrationChirpstackSetup from '~/components/MigrationChirpstackSetup.vue';
  import { HeliumConsoleService } from '~/services/console';
  import { ChirpstackService } from '~/services/chirpstack';
  import { ProxyConfig } from 'vue/types/proxy';

  export default Vue.extend({
      name: "ManageTenantProfiles",
      components: { 
        'Navbar' : Navbar,
        'AddTenant' : AddTenant,
        'MigrationHeliumSetup' : MigrationHeliumSetup,
        'MigrationChirpstackSetup' : MigrationChirpstackSetup,
      },
      data() {
        return {
          heliumConsoleService : new HeliumConsoleService(this.$axios, { 
            bearer: this.$store.state.consoleBearer,
            getterUrl: this.$config.proxyGet,
          } as ProxyConfig),
          chirpstackService : new ChirpstackService(this.$axios),
          tabIndex: 0,
          step: 0,
        }
      },
      methods : {
        disableSetupTab() : boolean {
          return ( this.step > 0 );
        },
        disableChirpstackTab() : boolean {
          if ( this.step == 0 ) return true;
          return false;
        }
      },
      mounted() {
        this.$root.$on("message-migration-validate-api", (msg:any) => {
            this.step=1;
        });
        this.$root.$on("message-migration-next-tab", (msg:any) => {
            this.tabIndex++;
        });
      },
    })
</script>
  