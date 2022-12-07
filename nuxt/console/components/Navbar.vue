<template>
   <div>
  <b-navbar toggleable="lg" type="dark" variant="info">
    <b-navbar-brand href="#">{{ $config.consoleName }}</b-navbar-brand>

    <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>
    <b-collapse id="nav-collapse" is-nav>
      <b-navbar-nav>
        <b-nav-item href="#">Chirpstack</b-nav-item>
        <b-nav-item href="#" disabled>...</b-nav-item>
      </b-navbar-nav>

      <b-navbar-nav class="ml-auto">
        <b-nav-text right> 
           <b-badge pill :variant="color" style="padding:10px;">{{dc}} DCs</b-badge>
        </b-nav-text>
      </b-navbar-nav>
      <!-- Right aligned nav items -->
      <b-navbar-nav>
        <b-nav-item-dropdown right>
          <template #button-content>
            <em>{{ $auth.user.username }}</em>
          </template>
          <b-dropdown-item href="#">Profile</b-dropdown-item>
          <b-dropdown-item href="#">Sign Out</b-dropdown-item>
        </b-nav-item-dropdown>
      </b-navbar-nav>
    </b-collapse>
  </b-navbar>
</div>
</template>


<script>
import { throwStatement } from '@babel/types'
import Vue from 'vue'
import { mapMutations } from 'vuex'

export default Vue.extend({
    data () {
	    return {
		    polling: null,
        dc : 0,
        mindc : 0,
        color : "light"
	    }
    },
    methods: {
	    pollData () {
		  this.polling = setInterval(() => {
          //this.$nuxt.refresh();
          let tenantId = this.$store.state.currentTenant;
          console.log('### '+tenantId);
          let config = {
                headers: {
                    Authorization: 'Bearer '+this.$store.state.consoleBearer,
                }
            };
          this.$axios.get(/*process.env.API_HOST+*/'/console/1.0/tenant/'+tenantId+'/')
          .then((response) =>{
              const dcb = response.data;
              this.dc = dcb.dcBalance;
              this.mindc = dcb.minBalance;
              if (( this.dc - this.mindc ) > 5000 ) this.color="light"
              else if (( this.dc - this.mindc ) > 1000 ) this.color="warning"
              else this.color = "danger";
          })
		    } , 3000)
	    }
    },
    created () {
	    this.pollData()   
    },
    beforeDestroy () {
  	  clearInterval(this.polling)
    },
})

</script>

