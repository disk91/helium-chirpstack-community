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
        try {
            if ( this.$store.state.lastChirpstackUrl == 'about:blank' ) {
                document.getElementById("chirpstack-frame-id").contentWindow.location.href = this.$config.chirpstackHost+'?override=true';
            } else if ( this.$store.state.lastChirpstackUrl != '' ) {
                //console.log(">>> "+this.$store.state.lastChirpstackUrl );
                document.getElementById("chirpstack-frame-id").contentWindow.location.href = this.$store.state.lastChirpstackUrl ;
            }
        } catch (e) {
            console.log("Failed to get the chirpstack frame on mount");
        }
    },
    created() {
	    this.pollData()   
    },
    beforeDestroy() {
      // store chirpstack url for next refresh
      try {
        var iframeUrl = document.getElementById("chirpstack-frame-id").contentWindow.location.href;
        this.$store.commit('setLastChirpstackUrl',iframeUrl);  
      } catch (e) {
        console.log("Failed to get the chirpstack frame infos");
      }
      // clean poller
  	  clearInterval(this.polling);
    },
    methods: {
	    pollData () {
		  this.polling = setInterval(() => {
            if ( document != null ) {
                try {
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
                    iframeUrl = document.getElementById("chirpstack-frame-id").contentWindow.location.href;
                    var devstart = iframeUrl.indexOf('\/devices\/');
                    console.log("&& "+iframeUrl+" "+devstart);
                    if ( devstart > 0 ) {
                        iframeUrl = iframeUrl.substring(devstart+9);
                        devstart = iframeUrl.indexOf('\/');
                        console.log(">> "+devstart);
                        if ( devstart > 0 ) iframeUrl = iframeUrl.substring(0,devstart);
                        console.log("## "+iframeUrl);
                        this.$store.commit('setCurrentDevice',iframeUrl);
                    } else {
                        // todo, on a rien en localstorage, make it empty to
                        // display tenant informations
                        this.$store.commit('setCurrentDevice','');
                    }
                } catch (e) {
                    this.$store.commit('setCurrentDevice', '');
                    console.log("chirpstack param extraction failed ");
                }
            }
		  } , 1000)
	    }
    },
})

</script>
