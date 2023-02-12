<template>
    <div>       

        <b-row  class="mx-1 my-3">
            <b-col cols="12"
                   class="bg-light p-3"
                   style="border-radius: 0.5rem;"    
            >
                <b-row>
                    <b-col cols="12" class="mb-3">
                        <div v-html="$t('mig_device_explain_1')"></div>
                    </b-col>
                </b-row>

                <b-row>
                    <b-col cols="12">
                        <b-table 
                            :items="allDevices" 
                            :fields="fields" 
                            :busy="isBusy"
                            :filter="filterByName"
                            :filter-function="onFilterDevices"
                            responsive 
                            small
                            striped
                            headVariant="dark"
                            class="pt-1 pb-5"
                            style="font-size:0.7rem;">
                            <template #cell(selected)="row">
                                <b-form-checkbox
                                    class="m0"
                                    variant="outline-secondary"
                                    style="font-size:0.5rem;margin:2px;"
                                    v-model="row.value"
                                    @change="onChangeSelect(row)"
                                >
                                </b-form-checkbox>
                            </template>
                            <template #cell(rawDevice.labels)="row">  
                                <div v-html="getLabelStr(row.value)"></div>
                            </template>
                            <template #cell(region)="region">
                                <b-form-select 
                                    v-model="region.value" 
                                    :options="regionOption"
                                    @change="onChangeRegion(region)"
                                    size="sm"
                                    class="m0"
                                ></b-form-select>
                            </template>
                            <template #cell(devProfile)="devProfile">
                                <b-form-select 
                                    v-model="devProfile.value" 
                                    :options="profileOption[devProfile.item.rawDevice.id]"
                                    size="sm"
                                    class="m0"
                                ></b-form-select>
                            </template>
                            <template #cell(application)="application">
                                <b-form-select 
                                    v-model="application.value" 
                                    :options="applicationOption"
                                    size="sm"
                                    class="m0"
                                ></b-form-select>
                            </template>
                            <template #cell(status)="row">  
                                <b-icon v-if="row.value == 0" icon="dash-circle-fill" variant="danger" font-scale="1.7"></b-icon>
                                <b-icon v-if="row.value == 1" icon="play-circle-fill" variant="warning" font-scale="1.7"></b-icon> 
                                <b-icon v-if="row.value == 2" icon="play-circle-fill" variant="primary" font-scale="1.7"></b-icon> 
                            </template>
                            <template #head()="data">
                                {{ data.label }}
                            </template>
                            <template #head(selected)="data">
                                {{ data.label }}
                                <b-form-checkbox
                                    class="m0"
                                    variant="outline-secondary"
                                    style="font-size:0.5rem;margin:2px;"
                                    v-model="globalSelect"
                                    @change="onChangeGlobalSelect()"
                                >
                                </b-form-checkbox>
                            </template>
                            <template #head(rawDevice.name)="data">
                                {{ data.label }}
                                <b-form-input v-model="filterByName"
                                            type="text" 
                                            class="m0"
                                            size="sm"
                                            @change="onChangeGlobalName()"
                                ></b-form-input>
                            </template>
                        </b-table>
                    </b-col>
                </b-row>

            </b-col>
        </b-row>
    </div>
</template>
<style>
.table .thead-dark th {
    vertical-align: top;
}
</style>

<script lang="ts">
import Vue from 'vue'
import { HeliumConsoleService } from '~/services/console';
import { ChirpstackService } from '~/services/chirpstack';
import { LabelItf } from 'vue/types/proxy';
import { Application } from 'vue/types/chirpstack';
import { Device } from 'vue/types/console';
import { DeviceItf } from 'vue/types/proxy';


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
                {key: 'selected', label : this.$t('mig_device_t_select')},
                {key: 'rawDevice.name', sortable: true, label : this.$t('mig_device_t_name')},
                {key: 'rawDevice.dev_eui', sortable: true, label : this.$t('mig_device_t_deveui')},
                {key: 'rawDevice.labels', sortable: false, label : this.$t('mig_device_t_labels')},
                {key: 'region', sortable: false, label : this.$t('mig_device_t_region')},
                {key: 'devProfile', sortable: false, label : this.$t('mig_device_t_devtype')},
                {key: 'application', sortable: false, label : this.$t('mig_device_t_application')},
                {key: 'status', sortable: true, label : this.$t('mig_device_t_status')},
            ],
            regionOption : [] as any,
            applicationOption : [] as any,
            profileOption: [] as any,       // option profile []
            globalSelect: false as boolean,
            filterByName: "" as string,
        };
    },
    async fetch() {
    },
    methods : {
        reset() {
            this.isBusy = false;
            this.errorMessage = "";
            this.allDevices = [] as Device[];
            this.applicationOption = [];
            this.regionOption = [];
            this.filterByName = "";
            this.globalSelect = false;
        },
        getLabelStr(labels:LabelItf[]) : string {
            let r = "" as string;
            if ( labels == undefined ) return r;

            if ( labels.length > 1) {
                r += '<span class="text-danger">'
            }
            for ( var i = 0 ; i < labels.length ; i++ ) {
                r += labels[i].name+ " ";
            }
            if ( labels.length > 1 ) {
                r += '</span>'
            }
            return r;
        },
        createRegion() {
            this.regionOption = [
                { value : "EU868", text: "EU868" },
                { value : "US915", text: "US915" },
                { value : "AS923_1", text: "AS923_1" },
                { value : "AS923_1b", text: "AS923_1b" },
                { value : "AS923_2", text: "AS923_2" },
                { value : "AS923_3", text: "AS923_3" },
                { value : "AS923_4", text: "AS923_4" },
                { value : "CN470", text: "CN470" },
                { value : "AU915_1", text: "AU915_1" },
                { value : "AU915_6", text: "AU915_6" },
                { value : "Unknown", text: "Unknown" },
            ];
        },
        onFilterDevices(dev : Device) : boolean {
            console.log(dev);
            if ( ! dev.rawDevice.name.match(this.filterByName) ) {
                dev.filtered = true;
                return false;
            } else {
                dev.filtered = false;
                return true;
            }
        },
        onChangeGlobalName() {
            // filter the device List with by device Name
            this.allDevices.forEach( (dev) => {
                if ( ! dev.rawDevice.name.match(this.filterByName) ) {
                    dev.filtered = true;
                } else {
                    dev.filtered = false;
                }
            });
        },
        onChangeGlobalSelect() {
            // select all device listed
            this.allDevices.forEach( (dev) => {
                dev.selected=this.globalSelect;
                this.updateStatus(dev);
            });
        },
        onChangeSelect(row : any) {
            let dev = row.item as Device;
            dev.selected = row.value;
            this.updateStatus(dev);
        },
        onChangeRegion(element : any) {
            let dev = element.item as Device;
            dev.region = element.value;
            this.createProfile(dev);
            this.updateStatus(dev);
        },
        createApplication() {
            this.applicationOption = [];
            this.chirpstackObject.getApplications().forEach( (app) => {
                let o = {
                    value : app.id,
                    text : app.name
                };
                this.applicationOption.push(o);
            });
        },
        createProfile(dev : Device) {
            
            let labelStr = this.consoleObject.getLabelById(this.consoleObject.getCurrentLabel()).name;
            let dps = this.chirpstackObject.getDevProfiles( 
                    dev.region, 
                    true, 
                    labelStr
            );
            let r = [] as any;
            dps.forEach ((dp) => {
                let o = {
                    value : dp.profile.id,
                    text : dp.profile.name + " (" + dp.profile.region + ")"
                };
                r.push(o);
            });
            this.profileOption[dev.rawDevice.id] = r;
            dev.devProfile = this.chirpstackObject.getBestProfiles(dev.region, true, labelStr).profile.id;

        },
        updateStatus(dev : Device) {
            if ( dev.selected && dev.region != "Unknown" && this.profileOption[dev.rawDevice.id].length > 0 && this.applicationOption.length > 0 ) {
                dev.status = 2;
            } else if  ( dev.region != "Unknown" && this.profileOption[dev.rawDevice.id].length > 0 && this.applicationOption.length > 0 ) {
                dev.status = 1; 
            } else {
                dev.status = 0;
            }
        }
    },
    mounted() {
        this.$root.$on("message-migration-validate-chirpstack", (msg:any) => {
            this.allDevices = this.consoleObject.getSelectedDevices();
            this.allDevices.forEach( (dev) => {
                dev.application = this.chirpstackObject.getDefaultApplication().id;
                this.createProfile(dev);
                this.updateStatus(dev);
                dev.filtered = false;
            });
            this.createRegion();
            this.createApplication();
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