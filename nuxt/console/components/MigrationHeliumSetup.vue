<template>
    <div>       
        <b-row>
            <b-col cols="11"
                   class="bg-light p-4 m-3"
                   style="border-radius: 0.5rem;margin-top:5px;"
            >
            {{ $t('mig_setup_apiexplained_1') }} <br/>
            <br/>
            {{ $t('mig_setup_apiexplained_2') }}
            </b-col>
        </b-row>

        <b-row>
            <b-col cols="8"
            >
                <img src="/static/front/create_api_key.png" style="width: 100%;"/>
            </b-col>
            <b-col cols="3"
            >
                <img src="/static/front/get_api_key.png" style="width: 100%;"/>
            </b-col>
        </b-row>

        <b-row>
            <b-col cols="11"
                   class="bg-light p-4 m-3"
                   style="border-radius: 0.5rem;margin-top:5px;"
            >
            <b-row>
                <b-col cols="5">
                    {{ $t('mig_setup_setkey') }} <br/>
                    <b-form-input v-model="apiKey"
                            type="text" 
                            :placeholder="$t('mig_setup_setkeyinput')"
                            class="mb-2"
                            size="sm"
                    ></b-form-input>
                </b-col>
                <b-col cols="4">
                    {{ $t('mig_setup_setapi') }} <br/>
                    <b-form-select v-model.number="apiService" 
                        :options="apiServiceOptions"
                        @change="onUpdateApiService"
                        size="sm"
                        class="mb-3"
                    ></b-form-select>
                    <b-form-input v-model="apiServiceOtherUrl"
                            v-if="showOtherApiService"
                            type="text" 
                            :placeholder="$t('mig_setup_setotherapi')"
                            class="mb-2"
                            size="sm"
                    ></b-form-input>
                </b-col>
                <b-col cols="3"  style="font-size:0.8rem;margin-top:5px;">
                    <br/>  
                    <b-button block
                        variant="primary"
                        size="sm"
                        @click="validateConsoleAccess()"
                        style="text-align: right;font-size:0.8rem;"
                        :disabled="!isValidApi()"
                    >
                        {{ $t('mig_setup_testapi') }}
                        <b-icon icon="arrow-right-circle" variant="white"></b-icon>
                    </b-button>
                </b-col>

            </b-row>
            </b-col>
        </b-row>
    </div>
</template>
<style>
 
</style>

<script lang="ts">
import Vue from 'vue'
import { HeliumConsoleService } from '~/services/console';


export default Vue.extend({
    name: "MigrationHeliumSetup",
    props : {
        consoleObject : HeliumConsoleService,
    },
    components: {
    },
    data() {
        return {
            apiKey : "" as string,
            apiService : "https://console.helium-iot.eu/api/" as string,
            apiServiceOptions : [
              {
                value : "https://console.helium-iot.eu/api/",
                text : "Helium IoT.eu"
              },
              {
                value : "https://console.helium.com/api/",
                text : "Foundation Console"
              },
              {
                value : "https://console-vip.helium.com/api/",
                text : "Vip Console"
              },
              {
                value : "",
                text : "Other"
              },
            ],
            showOtherApiService : false as boolean,
            apiServiceOtherUrl : "" as string,

        };
    },
    methods : {
        onUpdateApiService() {
            if ( this.apiService == "" ) {
                this.showOtherApiService = true;
            } else {
                this.showOtherApiService = false;
            }
        },
        isValidApi() : boolean {
            return ( this.apiKey.length > 0 && ( this.apiService != "" || this.apiServiceOtherUrl.length > 0 ) )
        },
        validateConsoleAccess() {
            alert("go");
        },
    },
    mounted() {
    },
    created () {
    },
    beforeDestroy () {
    },
})
</script>