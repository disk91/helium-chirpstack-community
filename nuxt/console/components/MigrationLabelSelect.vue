<template>
    <div>       

        <b-card>
            <b-row><b-col cols="12" class="mb-2" style="font-weight: 600;">
                {{ $t('mig_loaded_data')}}
            </b-col></b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_labels') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countLabels() }}</b-col>

                <b-col cols="2">{{ $t('mig_loaded_eu868') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countEU868() }}</b-col>
                <b-col cols="2">{{ $t('mig_loaded_au915') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countAU915() }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_devices') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countDevices() }}</b-col>

                <b-col cols="2">{{ $t('mig_loaded_us915') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countUS915() }}</b-col>
                <b-col cols="2">{{ $t('mig_loaded_as923') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countAS923() }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_active') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countActive() }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_live') }}</b-col>
                <b-col cols="2" class="text-success">{{ consoleObject.countInOui() }}</b-col>
                <b-col cols="2">{{ $t('mig_loaded_impact') }}</b-col>
                <b-col cols="2" class="text-danger">{{ consoleObject.countIncompatible() }}</b-col>
                <b-col cols="2">{{ $t('mig_loaded_uregion') }}</b-col>
                <b-col cols="2" class="text-danger">{{ consoleObject.countUnknownRegion() }}</b-col>
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

                <b-col cols="2">{{ $t('mig_loaded_eu868') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countEU868(targetLabel) }}</b-col>
                <b-col cols="2">{{ $t('mig_loaded_au915') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countAU915(targetLabel) }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2"></b-col>
                <b-col cols="2" class="text-primary"></b-col>

                <b-col cols="2">{{ $t('mig_loaded_us915') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countUS915(targetLabel) }}</b-col>
                <b-col cols="2">{{ $t('mig_loaded_as923') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countAS923(targetLabel) }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_active') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countActive(targetLabel) }}</b-col>
            </b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_live') }}</b-col>
                <b-col cols="2" class="text-success">{{ consoleObject.countInOui(targetLabel) }}</b-col>
                <b-col cols="2">{{ $t('mig_loaded_impact') }}</b-col>
                <b-col cols="2" class="text-danger">{{ consoleObject.countIncompatible(targetLabel) }}</b-col>
                <b-col cols="2">{{ $t('mig_loaded_uregion') }}</b-col>
                <b-col cols="2" class="text-danger">{{ consoleObject.countUnknownRegion(targetLabel) }}</b-col>
            </b-row>

        </b-card>



    </div>
</template>
<style>
</style>

<script lang="ts">
import Vue from 'vue'
import { HeliumConsoleService } from '~/services/console';

export default Vue.extend({
    name: "MigrationLabelSelect",
    props : {
        consoleObject : HeliumConsoleService,
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
        };
    },
    methods : {
        reset() {
            this.errorMessage = "";
            this.apiMessage = "";
            this.apiState = 0;
            this.selectLabelDisabled = false;
            this.targetLabel = "";
        },
        selectLabel() {
            this.selectLabelDisabled=true;
        },
    },
    mounted() {
        this.$root.$on("message-migration-validate-api", (msg:any) => {
            // configure the label selection
            this.sourceLabel = [];
            var labels = this.consoleObject.getLabels();
            this.consoleObject.getLabels().forEach((label) =>{
                var disable = !this.consoleObject.isLabelSingleUsed(label.id); 
                let o = {
                    value : label.id,
                    text : label.name,
                    disabled : disable,
                };
                this.sourceLabel.push(o);
            });
            if ( this.sourceLabel.length > 0 ) {
                this.targetLabel = this.sourceLabel[0].value;
            }
        });
    },
    created () {
    },
    beforeDestroy () {
    },
})
</script>