<template>
    <b-card 
        :header="$t('skfs_run_check_title')"
        class="mt-2 mr-2"
    >
        <b-row>
        <b-col cols="6">
            <b-form-group 
                :label="$t('skfs_devaddr')"
                label-for="deviceAddr"
                label-cols="4"
                label-align="right"
                label-class="w-25"
                label-size="sm"
                class="mb-1 mr-1"
            >
                <b-form-input 
                    v-model="devaddr"
                    id="deviceAddr"
                    type="text" size="sm"
                ></b-form-input>
                <b-form-text style="font-size:0.5rem;">{{ $t('skfs_devaddr_det') }}</b-form-text>
            </b-form-group>
            </b-col>
            <b-col cols="3">
                <b-form-group 
                    label-for="clearSkfs"
                    label-cols="0"
                    label-align="right"
                    label-class="w-25"
                    label-size="sm"
                    class="mb-1 mr-1"
                >
                    <b-form-checkbox v-model="clearSkfs"
                                  id="clearSkfs"
                                  value='true'
                                  unchecked-value='false'
                                  class="mb-1"
                                  style="text-align:left;"
                                  >
                    </b-form-checkbox>
                    <b-form-text style="font-size:0.5rem;">{{ $t('skfs_clear_det') }}</b-form-text>
                </b-form-group>
            </b-col>
            <b-col cols="3">
                <b-button block @click="checkSkfs"
                              variant="primary" 
                              class="mb-2">
                              {{ $t('skfs_button') }}</b-button>
            </b-col>
        </b-row>
    </b-card>
</template>
<style>
.card-header {
    font-size: 1rem;
    font-weight: 600;
    font-variant: small-caps;
}
</style>

<script lang="ts">
import Vue from 'vue'

interface data {
    devaddr : string,
    clearSkfs : boolean,
}

export default Vue.extend({
    name: "CheckSkfs",
    data() : data {
        return {
            devaddr : '',
            clearSkfs: false,
        };
    },
    methods : {
        async checkSkfs() {
            let config = {
                headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                }
            };
            this.$axios.get<any>(this.$config.skfsCheckGet+'/'+this.devaddr+'/'+this.clearSkfs+'/',config)
            .then((response) =>{
                if (response.status == 200 ) {
                    console.group("Running background skfs check, see backend container log");
                    this.$bvToast.toast('Skfs Check request success', {
                        title: 'In Progress',
                        variant: 'success',
                        solid: true
                    });
                }
            }).catch((err) =>{
            });
      },
    }
})
</script>