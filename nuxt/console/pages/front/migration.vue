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
                :disabled="disableLabelSelectTab()"
              >
                <template #title> 
                  <b-icon icon="files" variant="primary"></b-icon> {{ $t('mig_label') }}
                </template>
              
                <MigrationLabelSelect :consoleObject="heliumConsoleService" :chirpstackObject="chirpstackService"/>              
              </b-tab>

              <b-tab 
                :disabled="disableChirpstackTab()"
              >
                <template #title> 
                  <b-icon icon="hdd-network" variant="primary"></b-icon> {{ $t('mig_chirpstack') }}
                </template>
              
                <MigrationChirpstackSetup :consoleObject="heliumConsoleService" :chirpstackObject="chirpstackService"/>
              
              </b-tab>

              <b-tab 
                :disabled="disableMigrationTab()"
              >
                <template #title> 
                  <b-icon icon="caret-right-square" variant="primary"></b-icon> {{ $t('mig_migration') }}
                </template>                

                <MigrationDevices :consoleObject="heliumConsoleService" :chirpstackObject="chirpstackService"/>

              </b-tab>

              <b-tab 
                 @click="onCancelClick()"
                 :disabled="disableCancelTab"
              >
                <template #title> 
                  <b-icon icon="backspace" variant="danger"></b-icon> <span class="text-danger">{{ $t('mig_cancel') }}</span>
                </template>                
              </b-tab>
            
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
  import MigrationLabelSelect from '~/components/MigrationLabelSelect.vue';
  import MigrationDevices from '~/components/MigrationDevices.vue';
  import { HeliumConsoleService } from '~/services/console';
  import { ChirpstackService } from '~/services/chirpstack';
  import { ProxyConfig } from 'vue/types/proxy';

  export default Vue.extend({
      name: "ManageTenantProfiles",
      components: { 
        'Navbar' : Navbar,
        'AddTenant' : AddTenant,
        'MigrationHeliumSetup' : MigrationHeliumSetup,
        'MigrationLabelSelect' : MigrationLabelSelect,
        'MigrationChirpstackSetup' : MigrationChirpstackSetup,
        'MigrationDevices' : MigrationDevices,
      },
      data() {
        return {
          heliumConsoleService : new HeliumConsoleService(this.$axios, { 
            bearer: this.$store.state.consoleBearer,
            getterUrl: this.$config.proxyGet,
            deactivaterUrl : this.$config.proxyDeact,
          } as ProxyConfig),
          chirpstackService : new ChirpstackService(this.$axios),
          tabIndex: 0,
          step: 0,
          disableCancelTab : false as boolean,
        }
      },
      methods : {
        disableSetupTab() : boolean {
          return ( this.step > 0 );
        },
        disableLabelSelectTab() : boolean {
          if ( this.step != 1 ) return true;
          return false;
        },
        disableChirpstackTab() : boolean {
          if ( this.step != 2 ) return true;
          return false;
        },
        disableMigrationTab() : boolean {
          if ( this.step != 3 ) return true;
          return false;
        },
        onCancelClick() {
          this.step = 0;
          this.tabIndex = 0;
          this.$root.$emit("message-migration-cancel", "");
        }
      },
      mounted() {
        this.$root.$on("message-migration-validate-api", (msg:any) => {
            this.step=1;
        });
        this.$root.$on("message-migration-validate-label", (msg:any) => {
            this.step=2;
        });
        this.$root.$on("message-migration-validate-chirpstack", (msg:any) => {
            this.step=3;
        });
        this.$root.$on("message-migration-next-tab", (msg:any) => {
            this.disableCancelTab = true;
            this.tabIndex++;
            let self = this;
            setTimeout( () => {
              self.disableCancelTab = false;
            },1000);
        });
      },
      async fetch() {
        // Get OUI parameter
        let config = {
          headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
          }
        };

        this.$axios.get(this.$config.ouiGet,config)
        .then((response) =>{
          if (response.status == 200 ) {
            this.chirpstackService.setOui(response.data.message);
            this.heliumConsoleService.setOui(response.data.message)
          }
        }).catch((err) =>{
            this.chirpstackService.setOui(-1);
            this.heliumConsoleService.setOui(-1);
        })
      }
    })
</script>
  