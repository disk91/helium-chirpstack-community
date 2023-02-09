<template>
    <div>       

        <b-row  class="mx-1 my-3">
            <b-col cols="12"
                   class="bg-light p-3"
                   style="border-radius: 0.5rem;"    
            >
                <b-row>
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_devprofexplained_1')"></div>
                    </b-col>
                </b-row>

                <b-row>
                    <b-col cols="12">
                        <b-table 
                            :items="allDevices" 
                            :fields="fields" 
                            :busy="isBusy"
                            responsive 
                            small
                            striped
                            headVariant="dark"
                            class="pt-1 pb-5"
                            style="font-size:8px;">
                            <template #cell(edit)="row">
                                <b-button size="sm" 
                                        @click="alert(row)"
                                        class="m0" 
                                        pill 
                                        variant="outline-secondary"
                                        style="font-size:0.5rem;margin:2px;"
                                >
                                {{ $t('tsl_edit') }} 
                                </b-button>
                            </template>
                            <template #head()="data">
                                {{ data.label }}
                            </template>
                        </b-table>
                    </b-col>
                </b-row>

                <b-row v-for="device in allDevices"
                    v-bind:data="device"
                    v-bind:key="device.rawDevice.id"
                    style="margin-top:2px;"
                >
                    <b-col cols="1"
                        class="ml-3 bg-light text-dark"
                        style="margin-right:2px;font-size:0.9rem;"
                    >
                        check
                        {{ device.rawDevice.name }} 
                    </b-col>

                    <b-col cols="2"
                        class="ml-3 bg-light text-dark"
                        style="margin-right:2px;font-size:0.9rem;"
                    >
                        check
                        {{ device.rawDevice.name }} 
                    </b-col>
                    <b-col cols="3"
                        style="text-align:left;margin-right:2px;font-size:0.9rem;"
                        class="text-info bg-light"
                    >
                        {{ device.rawDevice.dev_eui }} 
                    </b-col>
                    <b-col cols="2"
                        style="text-align:left;margin-right:2px;font-size:0.9rem;"
                        class="text-info bg-light"
                    >
                        select Region
                    </b-col>

                    <b-col cols="2"
                        style="text-align:left;margin-right:2px;font-size:0.9rem;"
                        class="text-info bg-light"
                    >
                        select Application
                    </b-col>

                    <b-col cols="2"
                        style="text-align:left;margin-right:2px;font-size:0.9rem;"
                        class="text-info bg-light"
                    >
                        select Device Profile
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
import { ChirpstackService } from '~/services/chirpstack';
import { LabelItf } from 'vue/types/proxy';
import { Application } from 'vue/types/chirpstack';
import { Device } from 'vue/types/console';

export default Vue.extend({
    name: "MigrationDevices",
    props : {
        consoleObject : HeliumConsoleService,
        chirpstackObject : ChirpstackService,
    },
    components: {
    },
    data() {
        return {
            isBusy : false as boolean,
            errorMessage : "" as string,
            allDevices : [] as Device[],
            fields : [
                {key: 'edit', label : this.$t('tsl_edit')},
                {key: 'rawDevice.name', sortable: false, label : this.$t('tsl_tenantName')},
                {key: 'rawDevice.dev_eui', sortable: false, label : this.$t('tsl_ownerEmail')},
                {key: 'dcBalance', sortable: false, label : this.$t('tsl_dcBalance')},
                {key: 'tenantUUID', sortable: false, label : this.$t('tsl_tenantID')},
            ],
        };
    },
    async fetch() {
    },
    methods : {
        reset() {
            this.isBusy = false;
            this.errorMessage = "";
            this.allDevices = [] as Device[];
        },
    },
    mounted() {
        this.$root.$on("message-migration-validate-chirpstack", (msg:any) => {
            this.allDevices = this.consoleObject.getSelectedDevices();
        });
        this.$root.$on("message-migration-cancel", (msg:any) => {
            this.reset();
        });
    },
    created () {
    },
    beforeDestroy () {
    },
})
</script>