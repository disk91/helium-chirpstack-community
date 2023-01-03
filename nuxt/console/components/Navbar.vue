<template>
<div>
  <b-navbar toggleable="lg" type="dark" variant="dark" class="py-0">
    <b-navbar-brand to="/front/">{{ $config.consoleName }}</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item to="/front/" exact active-class="active" >{{ $t('menu_chirpstack') }}</b-nav-item>
        <b-nav-item to="/front/stats" exact active-class="active" >{{ $t('menu_user_stats') }}</b-nav-item>
        <b-nav-item to="/front/profiles" exact active-class="active" v-if="$store.state.admin" class="ml-4">{{ $t('menu_admin_profiles') }}</b-nav-item>
        <b-nav-item to="/front/messages" exact active-class="active" v-if="$store.state.admin" class="sm">{{ $t('menu_admin_messages') }}</b-nav-item>
      </b-navbar-nav>
      <b-navbar-nav class="ml-auto">
        <b-nav-text right class="mr-1">
          <div>
            <span class="text-light" style="font-size:0.6rem;height:12px;float:right;clear:right;font-weight: 400;">{{ $auth.user.username }}</span>
            <span class="text-primary" style="font-size:0.6rem;float:right;clear:right;font-weight: 400;">{{ tenantName }}</span>
          </div>    
        </b-nav-text>
        <b-nav-text right> 
           <b-badge pill :variant="color" style="padding:5px 10px 5px 10px;min-width:100px;display:flex;justify-content: space-between;" @click="dataCreditAction">
            <img src="/static/front/dc_icon.svg" style="width: 15px; position: relative; top: 0px ; margin-right: 2px;"/>
            <span style="font-weight: 300;position: relative; top: 1px ;">{{formatedDc}}</span>
          </b-badge>
        </b-nav-text>
      </b-navbar-nav>
      <!-- Right aligned nav items -->
      <b-navbar-nav>
        <b-nav-item-dropdown right>
          <template #button-content>
            <img src="/static/front/home_icon.svg" style="width: 24px;position: relative; top: -1px;"/>
          </template>
          <b-dropdown-item to="/front/datacredits">{{$t('menu_purchase_dc')}}</b-dropdown-item>
          <b-dropdown-item to="/front/user">{{$t('menu_edit_profile')}}</b-dropdown-item>
          <b-dropdown-item href="#" @click="addTenantAction">{{$t('menu_add_tenant')}}</b-dropdown-item>
          <b-dropdown-item href="#" @click="signoutAction">{{$t('menu_sign_out')}}</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
  <b-modal id="umessage-modal" ref="umessage-modal"
                 centered 
                 :title="$t('message_title')"
                 content-class="shadow"
                 v-model="umessage.showModal"
                 @ok="ackMessage"
                 :header-bg-variant="umessage.bgTitle"
                 header-text-variant="white"
                 :header-border-variant="umessage.bgTitle"
                 footer-border-variant="white"
                 size="small"
        >
        {{ umessage.content }}
  </b-modal>
  <b-alert 
    v-model="umessage.showAlert"
    class="position-fixed fixed-bottom m-0 rounded-0"
    style="z-index:2000;"
    :variant="umessage.bgTitle"
    dismissible
    @dismissed="ackMessage"
  >
    {{ umessage.content }}
  </b-alert>
</div>
</template>
<style>
.dropdown-toggle::after {
    display:none;
}
.navbar-brand {
  font-size: 1rem;
}
.navbar-nav .nav-link {
  font-size: 0.7rem;
  font-weight: 300;
}
.navbar-expand-lg .navbar-nav .nav-link {
  padding: 0px 0px 0px 8px;
}
</style>

<script>
import { throwStatement } from '@babel/types'
import Vue from 'vue'
import { mapMutations } from 'vuex'

export default Vue.extend({
    data () {
	    return {
		    polling: null,
        dc : 0,
        formatedDc : 0,
        mindc : 0,
        tenantName : "NA",
        color : "secondary",
        messages : [],
        umessage : {
          showModal : false,
          showAlert : false,
          bgTitle : 'info',
          content : '',
        } 
	    }
    },
    async fetch() {
        let config = {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
            }
        };
        this.$data.messages = [];
        this.$axios.get(this.$config.messageGet,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  response.data.forEach( element => {
                    this.$data.messages.push(element)
                  });
                  this.displayMessages();
                }
            }).catch((err) =>{
            })
    },
    methods: {
      displayMessages() {
        if ( this.$data.messages.length > 0 ) {
          this.$data.messages.forEach( (message) => {
            var variant = "info";
            switch (message.category) {
              default:
              case 0 : variant = "primary"; break;
              case 1 : variant = "warning"; break;
              case 2 : variant = "danger"; break;
            }
            var type = "modal";
            switch (message.type) {
              default:
              case 1: type = "modal"; break;
              case 2: type = 'b-toaster-bottom-full'; break;
            }

            if ( type == "modal" ) {
              this.$data.umessage.showModal = true;
              this.$data.umessage.bgTitle = variant;
              this.$data.umessage.content = message.content;
            } else {
              this.$data.umessage.showAlert = true;
              this.$data.umessage.bgTitle = variant;
              this.$data.umessage.content = message.content;
            }
          })
        }
      },
      ackMessage() {
        let config = {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
            }
        };
        this.$data.messages = [];
        this.$axios.put(this.$config.messageAck,config)
            .then((response) =>{
            }).catch((err) =>{
            })
        this.umessage.content='';
      },
      isChirpStackActive() {
          return false;
      },
	    pollData () {
		      this.polling = setInterval(() => {
          let tenantId = this.$store.state.currentTenant;
          if ( tenantId != undefined && tenantId != null && tenantId.length > 5 ) {
            let config = {
                  headers: {
                      Authorization: 'Bearer '+this.$store.state.consoleBearer,
                  }
              };
            this.$axios.get(this.$config.dcbalanceEndpoint+'/'+tenantId+'/')
            .then((response) =>{
                const dcb = response.data;
                this.tenantName = dcb.tenantName;
                this.dc = dcb.dcBalance;
                this.formatedDc = this.dc.toLocaleString("en-US");
                this.mindc = dcb.minBalance;
                if (( this.dc - this.mindc ) > 5000 ) this.color="secondary"
                else if (( this.dc - this.mindc ) > 1000 ) this.color="warning"
                else this.color = "danger";
            })
          } else {
            this.tenantName = "NA";
          }
		    } , 3000)
	    },
      addTenantAction() {
        this.$root.$emit("message-display-add-tenant", "");
      },
      editProfileAction() {
        this.$root.$emit("message-edit-profile", "");
      },
      dataCreditAction() {
        this.$router.push('/front/datacredits');
      },
      signoutAction() {
        // clear the token and back to login page
        this.$auth.logout();
        this.$store.commit('setChirpstackBearer', '');
        localStorage.setItem("token", '');
        this.$store.commit('setConsoleBearer', '');
        this.$router.push('/front/login');
      }
    },
    created () {
	    this.pollData()   
    },
    beforeDestroy () {
      // clean DC poller
  	  clearInterval(this.polling)
    },
})

</script>

