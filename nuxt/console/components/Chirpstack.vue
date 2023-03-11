<template>
    <div style="height:100vh;">
      <iframe
        id="chirpstack-frame-id"
        :src="$config.chirpstackHost+'?override=true'"
        style="overflow:hidden;height:100%;width:100%" height="100%"
        width="100%"
        frameborder="0"
        scrolling="yes"
      ></iframe>
    </div>
</template>

<script>
// Local storage contains the Bearer 
import Vue from 'vue'

export default Vue.extend({
    data () {
	    return {
		    polling: null,
            chipstackUrl: '',
	    }
    },
    mounted() {
        this.$store.commit('setCurrentTenant','');
        if ( this.$store.state.lastChirpstackUrl == 'about:blank' ) {
            document.getElementById("chirpstack-frame-id").contentWindow.location.href = this.$config.chirpstackHost+'?override=true';
        } else if ( this.$store.state.lastChirpstackUrl != '' ) {
            //console.log(">>> "+this.$store.state.lastChirpstackUrl );
            document.getElementById("chirpstack-frame-id").contentWindow.location.href = this.$store.state.lastChirpstackUrl ;
        }
    },
    created() {
	    this.pollData()   
    },
    beforeDestroy() {
      // store chirpstack url for next refresh
      var iframeUrl = document.getElementById("chirpstack-frame-id").contentWindow.location.href;
      this.$store.commit('setLastChirpstackUrl',iframeUrl);  
      // clean poller
  	  clearInterval(this.polling);
    },
    methods: {
	    pollData () {
		  this.polling = setInterval(() => {
            if ( document != null ) {
                var iframeUrl = document.getElementById("chirpstack-frame-id").contentWindow.location.href;
                var start = iframeUrl.indexOf('\/tenants\/');
                if ( start > 0 ) {
                    iframeUrl = iframeUrl.substring(start+9);
                    start = iframeUrl.indexOf('\/');
                    if ( start > 0 ) iframeUrl = iframeUrl.substring(0,start);
                    this.$store.commit('setCurrentTenant',iframeUrl);
                } else {
                    iframeUrl = localStorage.getItem("tenantId");
                    if ( iframeUrl.length > 10  ) {
                       this.$store.commit('setCurrentTenant',iframeUrl);
                    } // else better to keep the previous one
                }
            }
		  } , 1000)
	    }
    },
})

</script>
