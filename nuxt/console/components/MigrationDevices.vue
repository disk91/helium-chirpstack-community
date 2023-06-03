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
                            id="dev-table"
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
                                    @change="onChangeProfile(devProfile.item)"
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
                                <b-icon v-if="row.value == 2" @click="onClickMigrateOne(row.item)" icon="play-circle-fill" variant="primary" font-scale="1.7"></b-icon> 
                                <b-icon v-if="row.value == 3" icon="check-lg" variant="success" font-scale="1.7"></b-icon> 
                                <b-icon v-if="row.value == 4" icon="exclamation-lg" variant="danger" font-scale="1.7"></b-icon> 
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
                            <template #head(region)="data">
                                {{ data.label }}
                                <b-form-select 
                                    v-model="regionGlobal" 
                                    :options="regionOption"
                                    @change="onChangeGlobalRegion()"
                                    size="sm"
                                    class="m0"
                                ></b-form-select>
                            </template>
                            <template #head(devProfile)="data">
                                {{ data.label }}
                                <b-form-select 
                                    v-model="profileGlobal" 
                                    :options="profileOptionGlobal"
                                    @change="onChangeGlobalProfile()"
                                    size="sm"
                                    class="m0"
                                ></b-form-select>
                            </template>
                            <template #head(application)="data">
                                {{ data.label }}
                                <b-form-select 
                                    v-model="applicationGlobal" 
                                    :options="applicationOption"
                                    @change="onChangeGlobalApplication()"
                                    size="sm"
                                    class="m0"
                                ></b-form-select>
                            </template>
                            <template #head(status)="data">
                                {{ data.label }}
                                <b-icon 
                                    icon="play-circle-fill" 
                                    variant="white" 
                                    font-scale="1.9"
                                    class="mt-1"
                                    @click="onClickMigrateAll()"
                                ></b-icon> 
                            </template>
                        </b-table>
                    </b-col>
                </b-row>

            </b-col>
        </b-row>

        <b-modal id="MigrateGroupModal" 
                 centered 
                 content-class="shadow"
                 v-model="showGroupModal"
                 header-border-variant="white"
                 header-text-variant="dark"
                 footer-border-variant="white"
                 class="text-center"
                 hide-footer
        >
            <template #modal-header>
              <h5 style="text-align:center;width:100%;margin-top:1rem;">{{$t('mig_modal_devices')}}</h5>
            </template>

            <b-card-text class="text-danger" style="text-align:center;" >
                {{ $t("mig_modal_description")}} 
            </b-card-text>

            <b-button v-if="! isMigrationRunning"
                    block
                    variant="primary"
                    size="sm"
                    @click="runMigration()"
                    style="text-align: center;font-size:1.0rem;"
            >
                    {{ $t('mig_start_migrate_1') }} {{ devicesToBeMigated }} {{ $t('mig_start_migrate_2') }}
                    <b-icon icon="caret-right-square" variant="white" class="ml-2"></b-icon>
            </b-button>

            <b-button v-if="! isMigrationRunning"
                    block
                    variant="success"
                    size="sm"
                    @click="cancelModal()"
                    style="text-align: center;font-size:1.0rem;"
            >
                    <b-icon icon="reply" variant="white" class="ml-2"></b-icon>
                    {{ $t('mig_stop_migrate') }}
            </b-button>

            <b-progress v-if="isMigrationRunning" class="my-2" :max="devicesToBeMigated" show-value>
                <b-progress-bar :value="devicesMigrated" variant="success" show-progress animated></b-progress-bar>
                <b-progress-bar :value="devicesWarning" variant="warning" show-progress animated></b-progress-bar>
                <b-progress-bar :value="devicesError" variant="danger" show-progress animated></b-progress-bar>
            </b-progress>

            <b-button v-if="isMigrationCompleted"
                    block
                    variant="success"
                    size="sm"
                    @click="cancelModal()"
                    style="text-align: center;font-size:1.0rem;"
            >
                    {{ $t('mig_end_migrate') }}
            </b-button>
        </b-modal>   

    </div>

</template>
<style>
.table .thead-dark th {
    vertical-align: top;
}
div#MigrateGroupModal___BV_modal_content_ {
    -webkit-border-radius: 1rem !important;
    -moz-border-radius: 1rem !important;
    border-radius: 1rem !important; 
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
import { anyTypeAnnotation, booleanLiteral } from '@babel/types';


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
                {key: 'selected', sortable: false, label : this.$t('mig_device_t_select')},
                {key: 'rawDevice.name', sortable: true, label : this.$t('mig_device_t_name')},
                {key: 'rawDevice.dev_eui', sortable: true, label : this.$t('mig_device_t_deveui')},
                {key: 'rawDevice.labels', sortable: false, label : this.$t('mig_device_t_labels')},
                {key: 'region', sortable: false, label : this.$t('mig_device_t_region')},
                {key: 'devProfile', sortable: false, label : this.$t('mig_device_t_devtype')},
                {key: 'application', sortable: false, label : this.$t('mig_device_t_application')},
                {key: 'status', sortable: false, label : this.$t('mig_device_t_status')},
            ],
            regionOption : [] as any,
            regionGlobal : "" as string,
            applicationOption : [] as any,
            applicationGlobal : "" as string,
            profileOption: [] as any,       // option profile []
            profileOptionGlobal: [] as any,
            profileGlobal: "" as string,
            globalSelect: false as boolean,
            filterByName: "" as string,
            showGroupModal : false as boolean,
            devicesToBeMigated : 0 as number,
            devicesMigrated : 0 as number,
            devicesWarning : 0 as number,
            devicesError : 0 as number,
            isMigrationRunning : false as boolean,
            isMigrationCompleted : false as boolean,
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
            this.profileOptionGlobal = [] as any;
            this.profileGlobal = "";
            this.applicationGlobal = "";
            this.showGroupModal = false;
            this.devicesToBeMigated = 0;
            this.devicesMigrated = 0;
            this.devicesWarning = 0;
            this.devicesError = 0;
            this.isMigrationRunning = false;
            this.isMigrationCompleted = false;
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
                { value : "AS923", text: "AS923" },
                { value : "AS923_2", text: "AS923_2" },
                { value : "AS923_3", text: "AS923_3" },
                { value : "AS923_4", text: "AS923_4" },
                { value : "CN470", text: "CN470" },
                { value : "CN779", text: "CN779" },
                { value : "AU915", text: "AU915" },
                { value : "IN865", text: "IN865" },
                { value : "KR920", text: "KR920" },
                { value : "RU864", text: "RU864" },
                { value : "Unknown", text: "Unknown" },
            ];
        },
        onFilterDevices(dev : Device) : boolean {
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
                if ( this.globalSelect ) {
                    dev.selected = ! dev.selected;
                } else {
                    dev.selected=this.globalSelect;
                }
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
        onChangeGlobalRegion() {
            // Update all visible devices with the given region
            if ( this.regionGlobal == "" ) return;
            this.profileGlobal="";
            this.allDevices.forEach( (dev) => {
                if ( ! dev.filtered ) {
                    dev.region = this.regionGlobal;
                    this.createProfile(dev);
                    this.updateStatus(dev);
                }
            });
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
        onChangeGlobalApplication() {
            // Update all visible devices with the given application
            this.allDevices.forEach( (dev) => {
                if ( ! dev.filtered ) {
                    dev.application = this.applicationGlobal;
                    this.updateStatus(dev);
                }
            });
            this.$root.$emit('bv::refresh::table', 'dev-table');
        },
        createProfileOptions() {

            let labelStr = this.consoleObject.getLabelById(this.consoleObject.getCurrentLabel()).name;
            let dps = this.chirpstackObject.getDevProfiles( 
                    "Unknown", 
                    true, 
                    labelStr
            );
            let r = [] as any;
            dps.forEach ((dp) => {
                let o = {
                    value : dp.profile.id,
                    text : dp.profile.name
                };
                r.push(o);
            });
            this.profileOptionGlobal = r;

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
                    text : dp.profile.name
                };
                r.push(o);
            });
            this.profileOption[dev.rawDevice.id] = r;
            let p = this.chirpstackObject.getBestProfiles(dev.region, true, labelStr);
            dev.devProfile = (p!=undefined)?p.profile.id:"";
        },
        onChangeProfile(dev : Device) {
            dev.region = this.chirpstackObject.getProfileById(dev.devProfile).profile.region;
        },
        onChangeGlobalProfile() {
            // Update all visible devices with the given profile
            if ( this.profileGlobal == "" ) return;
            this.regionGlobal = "";
            this.allDevices.forEach( (dev) => {
                if ( ! dev.filtered ) {
                    dev.devProfile = this.profileGlobal;
                    this.onChangeProfile(dev);
                    this.createProfile(dev);
                    this.updateStatus(dev);
                }
            });
        },
        updateStatus(dev : Device) {
            // does not update the status obtained after migration
            if ( dev.status > 2 ) return;

            if ( dev.selected && dev.region != "Unknown" && this.profileOption[dev.rawDevice.id].length > 0 && this.applicationOption.length > 0 ) {
                dev.status = 2;
            } else if  ( dev.region != "Unknown" && this.profileOption[dev.rawDevice.id].length > 0 && this.applicationOption.length > 0 ) {
                dev.status = 1; 
            } else {
                dev.status = 0;
            }
        },
        onClickMigrateOne(dev: Device) {
            if ( ! dev.filtered && dev.selected && dev.status == 2 ) {
                this.chirpstackObject.createdevice(dev)
                .then ( (ret:string) => {
                    if ( ret == "" ) {
                        // deactivate in the console
                        this.consoleObject.deactivateDevice(dev,true)
                        .then ((ret:string) =>{
                            if ( ret == "" ) {
                                // activate device on chirpstack
                                this.chirpstackObject.activatedevice(dev)
                                .then ( (ret:string) => {
                                    if ( ret == "" ) {
                                        dev.status = 3;
                                        this.devicesMigrated++;
                                    } else {
                                        // remove device
                                        this.chirpstackObject.deletedevice(dev)
                                        // reactivate on console
                                        this.consoleObject.deactivateDevice(dev,false)
                                        dev.status = 4;
                                        this.devicesError++;
                                    }
                                })
                            } else {
                                // remove device
                                this.chirpstackObject.deletedevice(dev)
                                dev.status = 4;
                                this.devicesError++;
                            }
                        })

                    } else {
                        dev.status = 4;
                        this.devicesError++;
                    }
                }).catch ((err:any) =>{
                    dev.status = 4;
                    this.devicesError++;
                })
            }
        },
        onClickMigrateAll() {
            this.devicesToBeMigated = 0;
            this.devicesMigrated = 0;
            this.devicesWarning = 0;
            this.devicesError = 0;
            this.isMigrationRunning = false;
            this.isMigrationCompleted = false;
            this.allDevices.forEach( (dev) => {
                if ( ! dev.filtered && dev.selected && dev.status == 2 ) {
                    this.devicesToBeMigated++;
                }
            });
            if ( this.devicesToBeMigated > 0 ) {
                this.showGroupModal = true;
            }
        },
        cancelModal() {
            this.showGroupModal = false;
        },
        runMigration() {
            this.isMigrationRunning = true;
            this.allDevices.forEach( (dev) => {
                if ( ! dev.filtered && dev.selected && dev.status == 2 ) {
                    this.onClickMigrateOne(dev);
                }
            });
            this.isMigrationCompleted = true;
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
            this.createProfileOptions();
            this.$root.$emit("message-close-dev-modal", "");
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