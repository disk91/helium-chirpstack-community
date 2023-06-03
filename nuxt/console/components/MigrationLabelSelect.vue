<template>
    <div>       

        <b-card>
            <b-row><b-col cols="12" class="mb-2" style="font-weight: 600;">
                {{ $t('mig_loaded_data')}}
            </b-col></b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_labels') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countLabels() }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_devices') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countDevices() }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_impact') }}</b-col>
                <b-col v-if="consoleObject.countIncompatible() > 0" cols="2" class="text-danger">{{ consoleObject.countIncompatible() }}</b-col>
                <b-col v-if="consoleObject.countIncompatible() == 0" cols="2" class="text-success">{{ consoleObject.countIncompatible() }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_active') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countActive() }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_live') }}</b-col>
                <b-col cols="2" class="text-success">{{ consoleObject.countInOui() }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_integration') }}</b-col>
                <b-col cols="2" class="text-success">{{ consoleObject.accountIntegration.length }}</b-col>
            </b-row>

        </b-card>

        <b-row class="mx-1 my-3">
            <b-col cols="12"
                   class="bg-light p-3"
                   style="border-radius: 0.5rem;"
            >
                <b-row>
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_label_select')"></div>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col cols="4" class="mb-3">
                        <b-form-select v-model="targetLabel" 
                                :options="sourceLabel"
                                size="sm"
                                class="mt-2"
                                :disabled="selectLabelDisabled"
                        ></b-form-select>
                    </b-col>
                    <b-col cols="2">
                        <b-button block
                            variant="primary"
                            size="sm"
                            @click="selectLabel()"
                            style="text-align: right;font-size:0.8rem;"
                            class="mt-2"
                            :disabled="selectLabelDisabled"
                        >
                            {{ $t('mig_select_label') }}
                        </b-button>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>

        <b-card v-if="selectLabelDisabled">
            <b-row><b-col cols="12" class="mb-2" style="font-weight: 600;">
                {{ $t('mig_label_data')}}
            </b-col></b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_devices') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countDevices(targetLabel) }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_impact') }}</b-col>
                <b-col v-if="consoleObject.countIncompatible(targetLabel) > 0" cols="2" class="text-danger">{{ consoleObject.countIncompatible(targetLabel) }}</b-col>
                <b-col v-if="consoleObject.countIncompatible(targetLabel) == 0" cols="2" class="text-success">{{ consoleObject.countIncompatible(targetLabel) }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_active') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countActive(targetLabel) }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_live') }}</b-col>
                <b-col cols="2" class="text-success">{{ consoleObject.countInOui(targetLabel) }}</b-col>
            </b-row>
        </b-card>

        <b-row v-if="selectLabelDisabled" class="mx-1 my-3">
            <b-col cols="12"
                   class="bg-light p-3"
                   style="border-radius: 0.5rem;"
            >
                <b-row>
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_integration_select')"></div>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col cols="4" class="mb-3">
                        <b-form-select v-model="targetIntegration" 
                                :options="sourceIntegration"
                                size="sm"
                                class="mt-2"
                                :disabled="selectIntegrationDisabled"
                                @change="onIntegrationSelectChange($event)"
                        ></b-form-select>
                    </b-col>
                    <b-col cols="2">
                        <b-button block
                            variant="primary"
                            size="sm"
                            @click="selectIntegration()"
                            style="text-align: right;font-size:0.8rem;"
                            class="mt-2"
                            :disabled="selectIntegrationDisabled"
                        >
                            {{ $t('mig_select_integation') }}
                        </b-button>
                    </b-col>
                    <b-col cols="1">

                    </b-col>
                    <b-col cols="4">
                        <div v-html="$t('mig_setup_integration_info')"></div>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>


        <b-row v-if="selectIntegrationDisabled" class="mx-1 my-3">
            <b-col cols="12"
                   class="bg-light p-3"
                   style="border-radius: 0.5rem;"
            >
                <b-row>
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_function_select')"></div>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col cols="4" class="mb-3">
                        <b-form-select v-model="targetFunction" 
                                :options="sourceFunction"
                                size="sm"
                                class="mt-2"
                                :disabled="selectFunctionDisabled"
                                @change="onFunctionSelectChange($event)"
                        ></b-form-select>
                    </b-col>
                    <b-col cols="2">
                        <b-button block
                            variant="primary"
                            size="sm"
                            @click="selectFunction()"
                            style="text-align: right;font-size:0.8rem;"
                            class="mt-2"
                            :disabled="selectFunctionDisabled"
                        >
                            {{ $t('mig_select_function') }}
                        </b-button>
                    </b-col>
                    <b-col cols="4"></b-col>
                    <b-col cols="2" v-if="selectFunctionDisabled">
                        <b-button block
                            variant="primary"
                            size="sm"
                            @click="commitFunction()"
                            style="text-align: right;font-size:0.8rem;"
                            class="mt-2"
                        >
                            {{ $t('mig_commit_function') }}
                        </b-button>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col cols="6" class="mb-2">
                        <client-only>
                        <CodeEditor 
                            :hide_header="true" 
                            v-model="leftEditor" 
                            height="600px"
                            border_radius="10px"
                            font_size="12px"
                            width="100%"
                            :read_only="true"
                        ></CodeEditor>
                        </client-only>
                    </b-col>
                    <b-col cols="6" class="mb-2">
                        <b-row class="h-25">
                            <b-col cols="12">
                                <div class="bg-warning p-3 mx-1 mb-2" style="border-radius: 0.5rem;" v-html="$t('mig_setup_function_rewrite')"></div>
                            </b-col>
                        </b-row>
                        <b-row class="h-75">
                            <b-col cols="12">
                                <client-only>
                                <CodeEditor 
                                    :hide_header="true" 
                                    v-model="rightEditor" 
                                    border_radius="10px"
                                    font_size="12px"
                                    width="100%"
                                    height="450px"
                                    :read_only="false"
                                ></CodeEditor>
                                </client-only>
                            </b-col>
                        </b-row>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>


    </div>
</template>
<style>
</style>

<script lang="ts">
import { conditionalExpression } from '@babel/types';
import Vue from 'vue'
import { HeliumConsoleService } from '~/services/console';
import { ChirpstackService } from '~/services/chirpstack';
// doc code-editor https://github.com/justcaliturner/simple-code-editor 

export default Vue.extend({
    name: "MigrationLabelSelect",
    props : {
        consoleObject : HeliumConsoleService,
        chirpstackObject : ChirpstackService,
    },
    components: {
    },
    async fetch() {


    },
    data() {
        return {
            isBusy : false as boolean,
            errorMessage : "" as string,
            apiState : 0 as number,
            apiMessage : '',
            sourceLabel : [] as any,
            targetLabel : "" as string,
            selectLabelDisabled : false as boolean,
            leftEditor : '' as string,
            rightEditor : '' as string,
            sourceFunction : [] as any,
            targetFunction : "" as string,
            selectFunctionDisabled : false as boolean,
            sourceIntegration : [] as any,
            targetIntegration : "" as string,
            selectIntegrationDisabled : false as boolean,
        };
    },
    methods : {
        reset() {
            this.errorMessage = "";
            this.apiMessage = "";
            this.apiState = 0;
            this.selectLabelDisabled = false;
            this.targetLabel = "";
            this.sourceLabel = [];
            this.sourceFunction = [];
            this.targetFunction = "";
            this.sourceIntegration = [];
            this.targetIntegration = "";
            this.selectIntegrationDisabled = false;
            this.leftEditor = "";
            this.rightEditor = "";
        },
        selectLabel() {
            this.selectLabelDisabled=true;
            this.consoleObject.setCurrentLabel(this.targetLabel);
            this.selectFunctionDisabled=false;
            let notusedFunction = [] as any;
            this.sourceFunction = [];
            let none = {
                    value : "no_function",
                    text : "None",
            };

            let found = false;
            this.consoleObject.getDownloadedFunction().forEach((func) => {
                let o = {
                    value : func.id,
                    text : func.name
                }
                if ( this.consoleObject.isFunctionUsedInALabel(func.id,this.targetLabel) ) {
                    found = true;
                    this.sourceFunction.push(o);
                } else {
                    notusedFunction.push(o);
                }
            });
            if ( ! found ) {
                this.sourceFunction = [none].concat(this.sourceFunction);
                this.sourceFunction = this.sourceFunction.concat(notusedFunction);
            } else {
                this.sourceFunction = this.sourceFunction.concat(notusedFunction);
                this.sourceFunction.push(none);
                this.onFunctionSelectChange(this.sourceFunction[0].value);
            }
            this.targetFunction = this.sourceFunction[0].value;

            let o = {
                        value : "0",
                        text : "none", 
            };
            this.sourceIntegration.push(o);
            this.consoleObject.getDownloadedIntegration().forEach( (integ) => {
                if ( integ.type == "http" || integ.type == "tago" ) {
                    let o = {
                        value : integ.id,
                        text : integ.name, 
                    };
                    this.sourceIntegration.push(o);
                } 
            });
            this.targetIntegration = this.sourceIntegration[0].value;

        },
        onFunctionSelectChange(event:any) {
            let f = this.consoleObject.getOneFunction(event);
            if ( f != undefined ) {
                if ( f.format == "cayenne" ) {
                    this.leftEditor = "Cayenne LPP Generic decoder";
                    this.rightEditor = "Nothing needs to be migrated";
                } else {
                    this.leftEditor = f.body;
                    this.rightEditor = this.chirpstackObject.migrateFunction(f.body);
                }
            } else {
                this.leftEditor = "";
                this.rightEditor = "";
            }
        },
        selectFunction() {
            this.selectFunctionDisabled=true;

        },
        commitFunction() {
            // go to the next step
            let f = this.consoleObject.getOneFunction(this.targetFunction);
            if ( f != undefined ) {
                if ( f.format == "cayenne" ) {
                    this.chirpstackObject.setFunction("cayenne");
                } else {
                    this.chirpstackObject.setFunction(this.rightEditor);
                }
            } else {
                this.chirpstackObject.setFunction("none");
            }
            this.$root.$emit("message-migration-validate-label", "");
            this.$root.$emit("message-migration-next-tab", "");
        },
        onIntegrationSelectChange(event:any) {
        },
        selectIntegration() {
            this.selectIntegrationDisabled=true;
            let integ = this.consoleObject.getOneIntegration(this.targetIntegration);
            if ( integ == undefined || this.targetIntegration == "0" ) {
                this.chirpstackObject.setIntegration(null as any);
            } else {
                this.chirpstackObject.setIntegration(
                {
                    type : integ.type,
                    id: integ.id,
                    name : integ.name,
                    verb : integ.credentials.method,
                    endpoint : integ.credentials.endpoint,
                    ulrparams : integ.credentials.url_params,
                    headers : integ.credentials.headers,
                    topic_up : (integ.credentials.uplink != undefined)?integ.credentials.uplink.topic:"",
                    topic_down : (integ.credentials.downlink != undefined)?integ.credentials.downlink.topic:"",
                });
            }
            
        },

    },
    mounted() {
        this.$root.$on("message-migration-validate-api", (msg:any) => {
            // configure the label selection
            this.sourceLabel = [];
            this.consoleObject.getDownloadedLabels().forEach((label) =>{
                var multiLabel = !this.consoleObject.isLabelSingleUsed(label.id); 
                if ( multiLabel ) {
                    let o = {
                        value : label.id,
                        html : '<em>'+label.name+'</em>',
                    };
                    this.sourceLabel.push(o);
                } else {
                    let o = {
                        value : label.id,
                        text : label.name,
                    };
                    this.sourceLabel.push(o);
                }
            });
            if ( this.sourceLabel.length > 0 ) {
                this.targetLabel = this.sourceLabel[0].value;
            }
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