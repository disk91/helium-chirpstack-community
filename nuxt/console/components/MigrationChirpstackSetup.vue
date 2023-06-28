<template>
    <div>       

        <b-card>
            <b-row><b-col cols="12" class="mb-2" style="font-weight: 600;">
                {{ $t('mig_data_select')}}
            </b-col></b-row>
            <b-row style="font-size:0.7rem;">
                <b-col cols="2">{{ $t('mig_loaded_labels') }}</b-col>
                <b-col cols="2" class="text-primary">{{ targetLabel.name }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_eu868') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("EU868",targetLabel.id) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_eu433') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("EU433",targetLabel.id) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_us915') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("US915",targetLabel.id) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_as923_1') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("AS923_1",targetLabel.id) }}</b-col>
            </b-row>
            <b-row style="font-size:0.7rem;">
                <b-col cols="2">{{ $t('mig_loaded_devices') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countDevices(targetLabel.id) }}</b-col>

                <b-col cols="1">{{ $t('mig_loaded_as923_1B') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("AS923_1B",targetLabel.id) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_as923_2') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("AS923_2",targetLabel.id) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_as923_3') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("AS923_3",targetLabel.id) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_as923_4') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("AS923_4",targetLabel.id) }}</b-col>
            </b-row>
            <b-row style="font-size:0.7rem;">
                <b-col cols="2">{{ $t('mig_loaded_active') }}</b-col>
                <b-col cols="2" class="text-primary">{{ consoleObject.countActive(targetLabel.id) }}</b-col>

                <b-col cols="1">{{ $t('mig_loaded_cn470') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("CN470",targetLabel.id) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_au915_1') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("AU915_1",targetLabel.id) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_au915_sb1') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("AU915_SB1",targetLabel.id) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_au915_6') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("AU915_6",targetLabel.id) }}</b-col>
            </b-row>
            <b-row style="font-size:0.7rem;">
                <b-col cols="2">{{ $t('mig_loaded_live') }}</b-col>
                <b-col cols="2" class="text-success">{{ consoleObject.countInOui(targetLabel.id) }}</b-col>

                <b-col cols="1">{{ $t('mig_loaded_in865') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("IN865",targetLabel.id) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_cd900') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("CD900_1A",targetLabel.id) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_kr920') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("KR920",targetLabel.id) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_au915_sb5') }}</b-col>
                <b-col cols="1" class="text-primary">{{ consoleObject.countZone("AU915_SB5",targetLabel.id) }}</b-col>

            </b-row>
            <b-row style="font-size:0.7rem;">
                <b-col cols="2">{{ $t('mig_loaded_impact') }}</b-col>
                <b-col v-if="consoleObject.countIncompatible(targetLabel.id) > 0" cols="2" class="text-danger">{{ consoleObject.countIncompatible(targetLabel.id) }}</b-col>
                <b-col v-if="consoleObject.countIncompatible(targetLabel.id) == 0" cols="2" class="text-success">{{ consoleObject.countIncompatible(targetLabel.id) }}</b-col>
            </b-row>
            <b-row style="font-size:0.7rem;">
                <b-col cols="2">{{ $t('mig_loaded_uregion') }}</b-col>
                <b-col v-if="consoleObject.countUnknownRegion(targetLabel.id) > 0" cols="2" class="text-warning">{{ consoleObject.countUnknownRegion(targetLabel.id) }}</b-col>
                <b-col v-if="consoleObject.countUnknownRegion(targetLabel.id) == 0" cols="2" class="text-success">{{ consoleObject.countUnknownRegion(targetLabel.id) }}</b-col>
            </b-row>
        </b-card>

        <b-row class="mx-1 my-3">
            <b-col cols="12"
                   class="bg-light p-3"
                   style="border-radius: 0.5rem;"
            >
                <b-row>
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_tenant_select')"></div>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col cols="4" class="mb-3">
                        <b-form-select v-model.number="targetTenant" 
                                :options="sourceOption"
                                size="sm"
                                class="mt-2"
                                :disabled="selectTenantDisabled"
                        ></b-form-select>
                        <b-form-text style="font-size:0.6rem;color:#DC3545 !important;">{{ $t(errorMessage) }}</b-form-text>
                    </b-col>
                    <b-col cols="2">
                        <b-button block
                            variant="primary"
                            size="sm"
                            @click="selectTenant()"
                            style="text-align: right;font-size:0.8rem;"
                            class="mt-2"
                            :disabled="selectTenantDisabled"
                        >
                            {{ $t('mig_select_tenant') }}
                            <b-icon icon="arrow-right-circle" variant="white"></b-icon>
                        </b-button>
                    </b-col>
                    <b-col cols="6" class="mt-2">
                        <b-icon v-if="(apiState==0)" icon="question-circle" variant="secondary"></b-icon>
                        
                        <b-icon v-if="(apiState==1)" icon="check-circle" variant="success"></b-icon>
                        <span v-if="(apiState==1)" class="text-success">{{ $t(apiMessage) }}</span>

                        <b-icon v-if="(apiState==2)" icon="x-circle" variant="danger"></b-icon>
                        <span v-if="(apiState==2)" class="text-danger">{{ $t(apiMessage) }}</span>
                    </b-col>
                </b-row>
                <b-row>
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_tenant_api')"></div>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>

        <b-card v-if="loadedChirpstack">
            <b-row><b-col cols="12" class="mb-2" style="font-weight: 600;">
                {{ $t('mig_loaded_chirpdata')}}
            </b-col></b-row>
            <b-row style="font-size:0.7rem;">
                <b-col cols="2">{{ $t('mig_loaded_devprofile') }}</b-col>
                <b-col cols="2" class="text-primary">{{ chirpstackObject.countDeviceProfile("",false) }}</b-col>

                <b-col cols="1">{{ $t('mig_loaded_eu868') }}</b-col>
                <b-col cols="1" class="text-primary"><span class="text-dark">OTAA</span> {{ chirpstackObject.countDeviceProfile("EU868", true) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_eu433') }}</b-col>
                <b-col cols="1" class="text-primary"><span class="text-dark">OTAA</span> {{ chirpstackObject.countDeviceProfile("EU433", true) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_us915') }}</b-col>
                <b-col cols="1" class="text-primary"><span class="text-dark">OTAA</span> {{ chirpstackObject.countDeviceProfile("US915", true) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_as923_1') }}</b-col>
                <b-col cols="1" class="text-primary"><span class="text-dark">OTAA</span> {{ chirpstackObject.countDeviceProfile("AS923", true) }}</b-col>
            </b-row>
            <b-row style="font-size:0.7rem;">
                <b-col cols="2"></b-col>
                <b-col cols="2" class="text-primary"></b-col>

                <b-col cols="1">{{ $t('mig_loaded_as923_2') }}</b-col>
                <b-col cols="1" class="text-primary"><span class="text-dark">OTAA</span> {{ chirpstackObject.countDeviceProfile("AS923_2", true) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_as923_3') }}</b-col>
                <b-col cols="1" class="text-primary"><span class="text-dark">OTAA</span> {{ chirpstackObject.countDeviceProfile("AS923_3", true) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_as923_4') }}</b-col>
                <b-col cols="1" class="text-primary"><span class="text-dark">OTAA</span> {{ chirpstackObject.countDeviceProfile("AS923_4", true) }}</b-col>
            </b-row>

            <b-row style="font-size:0.7rem;">
                <b-col cols="2"></b-col>
                <b-col cols="2" class="text-primary"></b-col>

                <b-col cols="1">{{ $t('mig_loaded_cn470') }}</b-col>
                <b-col cols="1" class="text-primary"><span class="text-dark">OTAA</span> {{ chirpstackObject.countDeviceProfile("CN470", true) }}</b-col>
                <b-col cols="1">{{ $t('mig_loaded_au915_1') }}</b-col>
                <b-col cols="1" class="text-primary"><span class="text-dark">OTAA</span> {{ chirpstackObject.countDeviceProfile("AU915", true) }}</b-col>
            </b-row>

            <b-row style="font-size:0.7rem;">
                <b-col cols="2"></b-col>
                <b-col cols="2" class="text-primary"></b-col>

                <b-col cols="1">{{ $t('mig_loaded_in865') }}</b-col>
                <b-col cols="1" class="text-primary"><span class="text-dark">OTAA</span> {{ chirpstackObject.countDeviceProfile("IN865", true) }}</b-col>
                <b-col cols="2"></b-col>
                <b-col cols="1">{{ $t('mig_loaded_kr920') }}</b-col>
                <b-col cols="1" class="text-primary"><span class="text-dark">OTAA</span> {{ chirpstackObject.countDeviceProfile("KR920", true) }}</b-col>
            </b-row>

        </b-card>

        <b-row  v-if="loadedChirpstack" class="mx-1 my-3">
            <b-col cols="12"
                   class="bg-light p-3"
                   style="border-radius: 0.5rem;"    
            >
                <b-row>
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_devprofexplained_1')"></div>
                    </b-col>
                </b-row>

                <b-row v-if="consoleObject.countInOui() != 0 && false" class="mt-2 mb-1">
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_devprofexplained_2')"></div>
                    </b-col>
                </b-row>
                <b-row v-if="consoleObject.countInOui() != 0 && false" class="mb-1">
                    <b-col cols="2"> <!-- EU868-->
                        <b-button block
                            :variant="getVariant('EU868',false)"
                            size="sm"
                            @click="createDevProfile('EU868', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('EU868',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - EU868
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- EU433-->
                        <b-button block
                            :variant="getVariant('EU433',false)"
                            size="sm"
                            @click="createDevProfile('EU433', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('EU433',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - EU433
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- US915-->
                        <b-button block
                            :variant="getVariant('US915',false)"
                            size="sm"
                            @click="createDevProfile('US915', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('US915',false)"
                        >
                        <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - US915
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- AU915-->
                        <b-button block
                            :variant="getVariant('AU915',false)"
                            size="sm"
                            @click="createDevProfile('AU915', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('AU915',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - AU915
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- AU915_SB1 -->
                        <b-button block
                            :variant="getVariant('AU915_SB1',false)"
                            size="sm"
                            @click="createDevProfile('AU915_SB1', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('AU915_SB1',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - AU915_SB1
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- AU915_SB2 -->
                        <b-button block
                            :variant="getVariant('AU915_SB2',false)"
                            size="sm"
                            @click="createDevProfile('AU915_SB2', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('AU915_SB2',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - AU915_SB2
                        </b-button>
                    </b-col>
                </b-row>
                <b-row v-if="consoleObject.countInOui() != 0 && false"  class="mb-1">
                    <b-col cols="2"> <!-- CN470 -->
                        <b-button block
                            :variant="getVariant('CN470',false)"
                            size="sm"
                            @click="createDevProfile('CN470', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('CN470',false)"
                        >
                        <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - CN470
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- AS923_1 -->
                        <b-button block
                            :variant="getVariant('AS923_1',false)"
                            size="sm"
                            @click="createDevProfile('AS923_1', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('AS923_1',false)"
                        >
                        <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - AS923_1
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- AS923_2 -->
                        <b-button block
                            :variant="getVariant('AS923_2',false)"
                            size="sm"
                            @click="createDevProfile('AS923_2', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('AS923_2',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - AS923_2
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- AS923_3 -->
                        <b-button block
                            :variant="getVariant('AS923_3',false)"
                            size="sm"
                            @click="createDevProfile('AS923_3', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('AS923_3',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - AS923_3
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- AS923_4 -->
                        <b-button block
                            :variant="getVariant('AS923_4',false)"
                            size="sm"
                            @click="createDevProfile('AS923_4', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('AS923_4',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - AS923_4
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- AS923_1B -->
                        <b-button block
                            :variant="getVariant('AS923_1B',false)"
                            size="sm"
                            @click="createDevProfile('AS923_1B', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('AS923_1B',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - AS923_1B
                        </b-button>
                    </b-col>
                </b-row>
                <b-row v-if="consoleObject.countInOui() != 0 && false" class="mb-1">
                    <b-col cols="2"> <!-- IN865 -->
                        <b-button block
                            :variant="getVariant('IN865',false)"
                            size="sm"
                            @click="createDevProfile('IN865', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('IN865',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - IN865
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- KR920-->
                        <b-button block
                            :variant="getVariant('KR920',false)"
                            size="sm"
                            @click="createDevProfile('KR920', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('KR920',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - KR920
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- CD900_1A -->
                        <b-button block
                            :variant="getVariant('CD900_1A',false)"
                            size="sm"
                            @click="createDevProfile('CD900_1A', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('CD900_1A',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - CD900_1A
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- AU915_SB5 -->
                        <b-button block
                            :variant="getVariant('AU915_SB5',false)"
                            size="sm"
                            @click="createDevProfile('AU915_SB5', false)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('AU915_SB5',false)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            ABP - AU915_SB5
                        </b-button>
                    </b-col>
                </b-row>

                <b-row class="mt-2 mb-1">
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_devprofexplained_3')"></div>
                    </b-col>
                </b-row>
                <b-row class="mb-1">
                    <b-col cols="2">
                        <b-button block
                            variant="primary"
                            size="sm"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            disabled=true
                        >
                         {{ $t('mig_setup_shouldbecreated') }}
                        </b-button>
                    </b-col>
                    <b-col cols="2"></b-col>
                    <b-col cols="2"> <!-- EU868-->
                        <b-button block
                            :variant="getVariant('EU868',true)"
                            size="sm"
                            @click="createDevProfile('EU868', true)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('EU868',true)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            OTAA - EU868
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- EU433-->
                        <b-button block
                            :variant="getVariant('EU433',true)"
                            size="sm"
                            @click="createDevProfile('EU433', true)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('EU433',true)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            OTAA - EU433
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- US915-->
                        <b-button block
                            :variant="getVariant('US915',true)"
                            size="sm"
                            @click="createDevProfile('US915', true)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('US915',true)"
                        >
                        <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            OTAA - US915
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- AS923_1 -->
                        <b-button block
                            :variant="getVariant('AS923',true)"
                            size="sm"
                            @click="createDevProfile('AS923', true)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('AS923',true)"
                        >
                        <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            OTAA - AS923
                        </b-button>
                    </b-col>
                </b-row>
                <b-row class="mb-1">
                    <b-col cols="2">
                        <b-button block
                            variant="info"
                            size="sm"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            disabled=true
                        >
                         {{ $t('mig_setup_canbecreated') }}
                        </b-button>
                    </b-col>
                    <b-col cols="2"></b-col>
                    <b-col cols="2"> <!-- AS923_2 -->
                        <b-button block
                            :variant="getVariant('AS923_2',true)"
                            size="sm"
                            @click="createDevProfile('AS923_2', true)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('AS923_2',true)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            OTAA - AS923_2
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- AS923_3 -->
                        <b-button block
                            :variant="getVariant('AS923_3',true)"
                            size="sm"
                            @click="createDevProfile('AS923_3', true)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('AS923_3',true)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            OTAA - AS923_3
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- AS923_4 -->
                        <b-button block
                            :variant="getVariant('AS923_4',true)"
                            size="sm"
                            @click="createDevProfile('AS923_4', true)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('AS923_4',true)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            OTAA - AS923_4
                        </b-button>
                    </b-col>
                    <b-col cols="2"></b-col>
                </b-row>
                <b-row class="mb-1">
                    <b-col cols="2">
                        <b-button block
                            variant="secondary"
                            size="sm"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            disabled=true
                        >
                         {{ $t('mig_setup_allreadyexist') }}
                        </b-button>
                    </b-col>
                    <b-col cols="2"></b-col>
                    <b-col cols="2"> <!-- CN470 -->
                        <b-button block
                            :variant="getVariant('CN470',true)"
                            size="sm"
                            @click="createDevProfile('CN470', true)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('CN470',true)"
                        >
                        <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            OTAA - CN470
                        </b-button>
                    </b-col>
                    <b-col cols="2"> <!-- AU915-->
                        <b-button block
                            :variant="getVariant('AU915',true)"
                            size="sm"
                            @click="createDevProfile('AU915', true)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('AU915',true)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            OTAA - AU915
                        </b-button>
                    </b-col>
                </b-row>
                <b-row class="mb-1">
                    <b-col cols="4"></b-col>
                    <b-col cols="2"> <!-- IN865 -->
                        <b-button block
                            :variant="getVariant('IN865',true)"
                            size="sm"
                            @click="createDevProfile('IN865', true)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('IN865',true)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            OTAA - IN865
                        </b-button>
                    </b-col>
                    <b-col cols="2"></b-col>
                    <b-col cols="2"> <!-- KR920-->
                        <b-button block
                            :variant="getVariant('KR920',true)"
                            size="sm"
                            @click="createDevProfile('KR920', true)"
                            style="text-align: center;font-size:0.6rem;"
                            class=""
                            :disabled="existDevProfile('KR920',true)"
                        >
                            <b-icon icon="plus-circle-dotted" variant="white" style="font-size:0.6rem;"></b-icon>
                            OTAA - KR920
                        </b-button>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>

        <b-card v-if="loadedChirpstack">
            <b-row><b-col cols="12" class="mb-2" style="font-weight: 600;">
                {{ $t('mig_loaded_chirpdata')}}
            </b-col></b-row>
            <b-row>
                <b-col cols="2">{{ $t('mig_loaded_applications') }}</b-col>
                <b-col cols="2" class="text-primary">{{ chirpstackObject.countApplications() }}</b-col>
            </b-row>
        </b-card>

        <b-row  v-if="loadedChirpstack" class="mx-1 my-3">
            <b-col cols="12"
                   class="bg-light p-3"
                   style="border-radius: 0.5rem;"    
            >
                <b-row>
                    <b-col cols="12">
                        <div v-html="$t('mig_setup_appexplained_1')"></div>
                    </b-col>
                </b-row>

                <b-row>

                    <b-col cols="3">
                        <b-button block
                            variant="primary"
                            size="sm"
                            @click="addApplication()"
                            style="text-align: right;font-size:0.8rem;"
                            class="mt-2"
                            :disabled="createApplicationDisabled"
                        >
                            <b-icon icon="plus-circle" variant="white"></b-icon>
                            {{ $t('mig_add_application') }}
                        </b-button>
                    </b-col>

                    <b-col cols="2" class="mt-2">
                        <div v-html="$t('mig_setup_defaultapp')"></div>
                    </b-col>

                    <b-col cols="3" class="mb-3">
                        <b-form-select v-model="targetApplication" 
                                :options="applicationOption"
                                size="sm"
                                class="mt-2"
                                @change="onAppplicationSelectChange($event)"
                        ></b-form-select>
                        <b-form-text style="font-size:0.6rem;color:#DC3545 !important;">{{ $t(appErrorMessage) }}</b-form-text>
                    </b-col>

                    <b-col cols="3">
                        <b-button block
                            variant="primary"
                            size="sm"
                            @click="selectApplication()"
                            style="text-align: right;font-size:0.8rem;"
                            class="mt-2"
                            :disabled="selectApplicationDisabled"
                        >
                            {{ $t('mig_select_application') }}
                            <b-icon icon="arrow-right-circle" variant="white"></b-icon>
                        </b-button>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>

        <b-row  v-if="loadedChirpstack" class="mx-1 my-3">
            <b-col cols="12"
                   class="bg-light p-3"
                   style="border-radius: 0.5rem;"    
            >
                <b-row>
                    <b-col cols="12">
                        <b-button block
                            variant="primary"
                            size="sm"
                            @click="gotoMigration()"
                            style="text-align: center;font-size:0.8rem;"
                            class="mt-2"
                            :disabled="gotoMigrationDisabled()"
                        >
                            {{ $t('mig_goto_migration') }}
                            <b-icon icon="arrow-right-circle" variant="white"></b-icon>
                        </b-button>
                    </b-col>
                </b-row>
            </b-col>
        </b-row>

        <b-modal id="LoadChirpsatckModal" 
                 centered 
                 content-class="shadow"
                 v-model="loadChirpstack"
                 header-border-variant="white"
                 header-text-variant="dark"
                 footer-border-variant="white"
                 class="text-center"
                 hide-footer
                 hide-header
        >
            <b-card-text class="text-dark" style="text-align:center;font-size:1.5rem;" >
                {{ $t('mig_loading_chirpstack') }}
                <b-icon icon="three-dots" animation="cylon" font-scale="2" style="position:relative;top:10px;left:5px;"></b-icon>
            </b-card-text>
        </b-modal>

        <b-modal id="NewApplicationName" 
                 centered 
                 content-class="shadow"
                 v-model="newAppNameModal"
                 header-border-variant="white"
                 header-text-variant="dark"
                 footer-border-variant="white"
                 class="text-center"
                 button-size="sm"
                 hide-header
                 @ok="createNewApplication"
        >
            <b-card-text class="text-dark" style="text-align:center;font-size:1.2rem;" >
                {{ $t('mig_new_app_setname') }}
            </b-card-text>
            <b-form-input v-model="newAppName"
                          type="text" 
                          :placeholder="$t('mig_new_app_expl')"
                          class="mb-2"
                          size="sm"
            ></b-form-input>

        </b-modal>

        <b-modal id="LoadDevicesModal" 
                 centered 
                 content-class="shadow"
                 v-model="loadDevices"
                 header-border-variant="white"
                 header-text-variant="dark"
                 footer-border-variant="white"
                 class="text-center"
                 hide-footer
                 hide-header
        >
            <b-card-text class="text-dark" style="text-align:center;font-size:1.5rem;" >
                {{ $t('mig_processing_devices') }}
            </b-card-text>
        </b-modal>

    </div>
</template>
<style>
div#NewApplicationName___BV_modal_content_ {
    -webkit-border-radius: 1rem !important;
    -moz-border-radius: 1rem !important;
    border-radius: 1rem !important; 
}
</style>

<script lang="ts">
import Vue from 'vue'
import { HeliumConsoleService } from '~/services/console';
import { TenantDcBalancesReqItf, TenantApiKeyRespItf } from 'vue/types/tenantSearch';
import { ChirpstackService } from '~/services/chirpstack';
import { LabelItf } from 'vue/types/proxy';
import { Application } from 'vue/types/chirpstack';

export default Vue.extend({
    name: "MigrationChirpstackSetup",
    props : {
        consoleObject : HeliumConsoleService,
        chirpstackObject : ChirpstackService,
    },
    components: {
    },
    async fetch() {

        // Get the possible tenants for the target
        let config = {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
            }
        };
        this.isBusy = true;
        this.$axios.get<TenantDcBalancesReqItf[]>(this.$config.tenantsDcGet,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.ownedTenants = response.data;
                  this.sourceOption=[];
                  if ( this.ownedTenants.length == 0 ) {
                    this.errorMessage = 'mig_err_empty_tenants';
                  }
                  for ( let i = 0 ; i < this.ownedTenants.length ; i++ ) {
                    this.sourceOption.push( {
                        value: i,
                        text: this.ownedTenants[i].tenantName,
                    })
                  }
                  this.isBusy = false;
                }
            }).catch((err) =>{
               this.errorMessage = 'error_load_dc_tenants';
               this.ownedTenants = [];
            })

    },
    data() {
        return {
            isBusy : false as boolean,
            errorMessage : "" as string,
            ownedTenants : [] as TenantDcBalancesReqItf[],
            sourceOption : [] as any,
            targetTenant : 0 as number,
            apiState : 0 as number,
            apiMessage : '',
            loadChirpstack : false as boolean,
            loadedChirpstack : false as boolean,
            targetLabel : {} as LabelItf,
            selectTenantDisabled : false as boolean,
            applicationOption : [] as any,
            targetApplication : 0 as number,
            appErrorMessage : "" as string,
            selectApplicationDisabled : false as boolean,
            createApplicationDisabled : false as boolean,
            newAppNameModal : false as boolean,
            newAppName : "" as string,
            integrationReady : false as boolean,
            loadDevices : false as boolean,
        };
    },
    methods : {
        reset() {
            this.errorMessage = "";
            this.appErrorMessage = "";
            this.apiMessage = "";
            this.apiState = 0;
            this.loadChirpstack = false;
            this.loadedChirpstack = false;
            this.selectTenantDisabled = false;
            this.targetLabel = {} as LabelItf;
            this.targetApplication = 0 as number;
            this.selectApplicationDisabled = false;
            this.createApplicationDisabled = false;
            this.newAppNameModal = false;
            this.newAppName = "";
            this.integrationReady = false;
            this.loadDevices = false;
        },
        selectTenant() {

            let config = {
                headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                'X-Chripstack-Bearer' : this.$store.state.chirpstackBearer,
                }
            };
            this.isBusy = true;

            let tenantUUID = this.ownedTenants[this.targetTenant].tenantUUID;

            this.apiMessage='';
            this.apiState=0;
            this.$axios.get<TenantApiKeyRespItf>(this.$config.tenantKeyCreate+'/'+tenantUUID+'/',config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.apiState=1;
                  this.apiMessage='mig_chip_api_ok';
                  this.chirpstackObject.setApiKey(response.data.tenantApiKey);
                  this.chirpstackObject.setTenantId(tenantUUID);

                  // Now we can get the data from chirpstack
                  this.loadChirpstack = true;
                  this.chirpstackObject.loadDeviceProfile()
                  .then ( (mess) => {
                    this.chirpstackObject.loadApplications()
                    .then ( (mess) => {
                        if ( mess == "" ) {
                            this.applicationOption = [];
                            for ( var i = 0 ; i < this.chirpstackObject.getApplications().length ; i++ ) {
                                this.applicationOption.push( {
                                    value: i,
                                    text: this.chirpstackObject.getApplications()[i].name,
                                });
                            };
                            if ( this.applicationOption.length > 0 ) {
                                this.targetApplication = 0;
                                this.onAppplicationSelectChange(0);
                                //this.selectApplicationDisabled = false;
                            } else {
                                //this.selectApplicationDisabled = true;
                                this.onAppplicationSelectChange(null);
                            }
                        } else {
                            this.appErrorMessage = mess;
                            this.selectApplicationDisabled = true;
                        }
                        this.isBusy = false;
                        this.loadChirpstack = false;
                        this.loadedChirpstack = true;
                        this.selectTenantDisabled = true;
                    });
                  });
                }
            }).catch((err) =>{
               this.apiState=2;
               this.apiMessage='mig_err_'+err.response.data.message;
               this.chirpstackObject.setApiKey("");
               this.loadedChirpstack = false;
            })
        },
        getVariant(zone:string,otaa:boolean) : string {
            let r : string = "primary";
            if ( !otaa && this.consoleObject.countInOui() == 0 ) return "secondary";
            if ( this.chirpstackObject.existsDevProfile(zone,otaa,this.targetLabel.name) ) return "secondary";
            if ( this.chirpstackObject.countDeviceProfile(zone,otaa) > 0 ) r = "info";
            else {
                if ( this.consoleObject.countZone(zone) == 0 ) r = "info"; 
            }
            return r;
        },
        createDevProfile(zone:string, otaa:boolean) {

            this.chirpstackObject.addDevProfile(zone,otaa,this.targetLabel.name)
            .then((response:string) => {
                if ( response.length == 0 ) {
                    this.chirpstackObject.loadDeviceProfile();
                } else {
                    console.log(response);
                }
            });

        },
        existDevProfile(zone:string, otaa:boolean) : boolean {
            return this.chirpstackObject.existsDevProfile(zone,otaa,this.targetLabel.name);
        },
        selectApplication() {
            // store selected app for next step
            this.chirpstackObject.setDefaultApplication(this.chirpstackObject.getApplications()[this.targetApplication]);
            this.appErrorMessage="";
            //console.log("Integration : "+this.chirpstackObject.getIntegration());
            if ( this.chirpstackObject.getIntegration() != null ) {
                // create the integation when required
                this.chirpstackObject.createApplicationIntegration()
                .then ( (result) => {
                    if ( result == "" ) {
                        this.integrationReady = true;
                        this.selectApplicationDisabled = true;
                        this.createApplicationDisabled = true;
                    } else {
                        this.appErrorMessage = result;
                    }
                });
            } else {
                this.integrationReady = true;
                this.selectApplicationDisabled = true;
                this.createApplicationDisabled = true;
            }
        },
        addApplication() {
            // need to modal for the name and create it
            this.newAppNameModal = true;
        },
        createNewApplication() {
            if ( this.newAppName != "" ) {
                this.chirpstackObject.addApplication(this.newAppName)
                .then ( (mess) => {
                    if (mess == "" ) {
                        this.reloadApplication();
                        this.newAppName = "";
                    }
                });
            }
        },
        reloadApplication() {
            this.chirpstackObject.loadApplications()
            .then ( (mess) => {
                if ( mess == "" ) {
                    this.applicationOption = [];
                    for ( var i = 0 ; i < this.chirpstackObject.getApplications().length ; i++ ) {
                        this.applicationOption.push( {
                            value: i,
                            text: this.chirpstackObject.getApplications()[i].name,
                        });
                    };
                    if ( this.applicationOption.length > 0 ) {
                        this.targetApplication = 0;
                        //this.selectApplicationDisabled = false;
                        this.onAppplicationSelectChange(0);
                    } else {
                        //this.selectApplicationDisabled = true;
                        this.onAppplicationSelectChange(null);
                    }
                } else {
                    this.appErrorMessage = mess;
                    this.selectApplicationDisabled = true;
                }
            });
        },
        onAppplicationSelectChange(event:any) {
            if ( this.chirpstackObject.getIntegration() != null ) {
                if ( event != null ) {
                    let app = this.chirpstackObject.getApplications()[event];
                    this.chirpstackObject.getApplicationIntegration(app.id)
                    .then( (integ) => {
                        if ( integ == null ) {
                            // no integration, no problem
                            this.selectApplicationDisabled = false;
                        } else {
                            // is it the same integration ?
                            if ( integ.integration.headers.hid != undefined && integ.integration.headers.hid == this.chirpstackObject.getIntegration().id ) {
                                // yes, no problem
                                this.selectApplicationDisabled = false;

                            } else {
                                // impossible to select this application
                                this.selectApplicationDisabled = true;
                            }
                        }
                    });
                }
            } else {
                // no applications, selected, we can go whatever
                this.selectApplicationDisabled = false;
            }
        },
        gotoMigrationDisabled() : boolean {
            return ! (   this.selectApplicationDisabled && this.applicationOption.length > 0 
                      && this.selectTenantDisabled && this.sourceOption.length > 0 
                      && this.chirpstackObject.haveDeviceProfile() 
                      && this.integrationReady
                     );
        },
        gotoMigration() {
            this.loadDevices = true;
            setTimeout( () => {
                this.$root.$emit("message-migration-validate-chirpstack", "");
                this.$root.$emit("message-migration-next-tab", "");
            }, 1000);
        }
    },
    mounted() {
        this.$root.$on("message-migration-validate-label", (msg:any) => {
            let l = this.consoleObject.getLabelById(this.consoleObject.getCurrentLabel());
            this.targetLabel = l;
        });
        this.$root.$on("message-migration-cancel", (msg:any) => {
            this.reset();
        });
        this.$root.$on("message-close-dev-modal", (msg:any) => {
            this.loadDevices = false;
        });
    },
    created () {
    },
    beforeDestroy () {
    },
})
</script>