<template>
    <div>
    <b-row>
        <b-col cols="4">
            <b-row v-if="!isBusy">
                <b-col cols="12" class="py-1" style="font-size:0.7rem;">
                    <b-card
                        header=""
                        class="ml-0 ShieldCard"
                    >
                    <b-row class="py-1">
                        <b-col cols="12"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                        >
                        </b-col>
                     </b-row>
                     <b-row class="py-1">
                        <b-col cols="12"
                            style="text-align:left;margin-right:2px;font-size:0.8rem;"
                            class="text-info bg-light"
                        >
                        </b-col>
                     </b-row>
                    </b-card>
                </b-col>
            </b-row>
            <b-row>
                <b-col cols="12" class="py-1" style="font-size:0.7rem;">
                </b-col>
            </b-row>
        </b-col>
        <b-col cols="8">
            <b-row>
                <b-col cols="12" class="py-1" style="font-size:0.8rem;">
                    <CheckSkfs v-if="!$config.community"/>
                </b-col>
            </b-row>
        </b-col>
    </b-row>
    </div>
</template>
<style>
 .ShieldCard .card-header  {
    font-size: 0.8rem;
    font-weight: 600;
    font-variant: small-caps;
}
</style>

<script lang="ts">
  import Vue from 'vue'

  interface context {
    errorMessage: string,
    isBusy: boolean,
  };
  export default Vue.extend({
      name: "ShieldMain",
      components: { 
      },
      data() : context {
        return {
            errorMessage: '',
            isBusy: false,
        }
      },
      async fetch() {
      },
      methods: {
      },
      mounted() {
      }
   })
  
</script>
 