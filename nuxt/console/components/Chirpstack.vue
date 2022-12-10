<template>
    <div style='height:100vh;'>
      <iframe
        id="chirpstack-frame-id"
        :src="$config.chirpstackHost+'?override=true'"
        style="overflow:hidden;height:100%;width:100%" height="100%"
        width="100%"
        frameborder="0"
        scrolling="no"
      ></iframe>
    </div>
</template>

<script>
// Local storage contains the Bearer 
import Vue from 'vue'
import { mapMutations } from 'vuex'

export default Vue.extend({
    data () {
	    return {
		    polling: null
	    }
    },
    created() {
        this.$store.commit('setCurrentTenant','');
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
                    this.$store.commit('setCurrentTenant',iframeUrl);
                }
            }
		    } , 3000)
	    }
    },
    created () {
	    this.pollData()   
    },
    beforeDestroy () {
  	  clearInterval(this.polling)
    }
})

</script>
