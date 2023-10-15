<template>    
    <div>
        <b-card
                :header=getTenantTitle()
                class="m-4 TenantDetails"
            >
            <b-row class="ml-0" v-if="loadBasicStatSuccess">
                <b-col cols="6">
                    <b-card 
                        v-if="!$config.disableStatTenant" 
                        :header="$t('bstat_card_title')"
                        class="TenantDetails">
                        <b-list-group class="m-0 p-0">
                            <b-list-group-item class="border-0 p-0" style="text-align:left;" variant="primary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1 small"> {{ $t('bstat_card_param') }}</div>
                                    <div class="col-md-3 p-1 small" style="text-align:right;"> {{ $t('bstat_card_value') }}</div>
                                    <div class="col-md-4 p-1 small" style="text-align:right;"> {{ $t('bstat_card_stat') }}</div>
                                </div>
                            </b-list-group-item>
                            <b-list-group-item v-if="(basicStat.freeTenantDc > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_freeTenantDc') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem;">{{ $t('tip_freeTenantDc') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right; vertical-align: middle;"> {{ basicStat.freeTenantDc }}</div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right; vertical-align: middle;"> </div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.dcPrice > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_dcPrice') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem;">{{ $t('tip_dcPrice') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right; vertical-align: middle;"> {{ basicStat.dcPrice }}</div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right; vertical-align: middle;"> </div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.dcMin > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_dcMin') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem;">{{ $t('tip_dcMin') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right; vertical-align: middle;"> {{ basicStat.dcMin }}</div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right; vertical-align: middle;"> </div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.dcBalanceStop > 0 || basicStat.dcBalanceStop < 0) " class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_dcBalanceStop') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem; ">{{ $t('tip_dcBalanceStop') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right; vertical-align: middle;"> {{ basicStat.dcBalanceStop }}</div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right; vertical-align: middle;"> </div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.dcPerDeviceInserted > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_dcPerDeviceInserted') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem; ">{{ $t('tip_dcPerDeviceInserted') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right; "> {{ basicStat.dcPerDeviceInserted }}</div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right; "> {{ basicStat.registrationDc }} DCs</div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.dcPer24BMessage > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_dcPer24BMessage') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem; ">{{ $t('tip_dcPer24BMessage') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right;"> {{ basicStat.dcPer24BMessage }}</div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right;">
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ basicStat.uplinkDc }} DCs</span><br/>
                                        <span class="text-dark p-0" style="font-size:0.6rem; ">{{ basicStat.uplink + basicStat.joinReq }} Uplinks</span>
                                    </div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.dcPer24BDuplicate > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_dcPer24BDuplicate') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem; ">{{ $t('tip_dcPer24BDuplicate') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right;"> {{ basicStat.dcPer24BDuplicate }}</div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right;">
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ basicStat.duplicateDc }} DCs</span><br/>
                                        <span class="text-dark p-0" style="font-size:0.6rem; ">{{ basicStat.duplicate }} Duplicates</span>
                                    </div>
                                </div>
                            </b-list-group-item>


                            <b-list-group-item v-if="(basicStat.dcPer24BDownlink > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_dcPer24BDownlink') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem; ">{{ $t('tip_dcPer24BDownlink') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right;"> {{ basicStat.dcPer24BDownlink }}</div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right;">
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ basicStat.downlinkDc }} DCs</span><br/>
                                        <span class="text-dark p-0" style="font-size:0.6rem; ">{{ basicStat.downlink }} Downlinks</span>
                                    </div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.dcPerJoinRequest > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_dcPerJoinRequest') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem; ">{{ $t('tip_dcPerJoinRequest') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right;"> 
                                        {{ basicStat.dcPerJoinRequest }}<br/>
                                        Max: {{ basicStat.maxJoinRequestDup }}
                                    </div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right;">
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ basicStat.joinDc }} DCs</span><br/>
                                        <span class="text-dark p-0" style="font-size:0.6rem; ">{{ basicStat.joinReq }} Valid Join Req</span>
                                    </div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.dcPerJoinAccept > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_dcPerJoinAccept') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem; ">{{ $t('tip_dcPerJoinAccept') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right;"> 
                                        {{ basicStat.dcPerJoinAccept }}
                                    </div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right;">
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ basicStat.joinAcceptDc }} DCs</span>
                                    </div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.inactivityBillingPeriodMs > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_inactivityBillingPeriodMs') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem; ">{{ $t('tip_inactivityBillingPeriodMs') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right;"> {{ (basicStat.inactivityBillingPeriodMs / (3600000*24)) }} {{ $t('days')}} </div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right;">
                                        <span class="text-dark p-0" style="font-size:0.8rem; "></span><br/>
                                        <span class="text-dark p-0" style="font-size:0.6rem; "></span>
                                    </div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.inactivityBillingPeriodMs > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_dcPerInactivityPeriod') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem; ">{{ $t('tip_dcPerInactivityPeriod') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right;"> {{ basicStat.dcPerInactivityPeriod }} DCs </div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right;">
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ basicStat.inactivityDc }} DCs</span><br/>
                                        <span class="text-dark p-0" style="font-size:0.6rem; "></span>
                                    </div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.activityBillingPeriodMs > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_activityBillingPeriodMs') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem; ">{{ $t('tip_activityBillingPeriodMs') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right;"> {{ (basicStat.activityBillingPeriodMs / (3600000*24)) }} {{ $t('days')}} </div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right;">
                                        <span class="text-dark p-0" style="font-size:0.8rem; "></span><br/>
                                        <span class="text-dark p-0" style="font-size:0.6rem; "></span>
                                    </div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.activityBillingPeriodMs > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_dcPerActivityPeriod') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem; ">{{ $t('tip_dcPerActivityPeriod') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right;"> {{ basicStat.dcPerActivityPeriod }} DCs </div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right;">
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ basicStat.activityDc }} DCs</span><br/>
                                        <span class="text-dark p-0" style="font-size:0.6rem; "></span>
                                    </div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.maxDcPerDevice > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_maxDcPerDevice') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem; ">{{ $t('tip_maxDcPerDevice') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right;"> {{ basicStat.maxDcPerDevice }} DCs </div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right;">
                                        <span class="text-dark p-0" style="font-size:0.8rem; "></span><br/>
                                        <span class="text-dark p-0" style="font-size:0.6rem; "></span>
                                    </div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.limitDcRatePeriodMs > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_limitDcRatePeriodMs') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem; ">{{ $t('tip_limitDcRatePeriodMs') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right;"> {{ (basicStat.limitDcRatePeriodMs / 3600000) }} {{ $t('hours') }} </div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right;">
                                        <span class="text-dark p-0" style="font-size:0.8rem; "></span><br/>
                                        <span class="text-dark p-0" style="font-size:0.6rem; "></span>
                                    </div>
                                </div>
                            </b-list-group-item>

                            <b-list-group-item v-if="(basicStat.limitDcRatePeriodMs > 0)" class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                <div class="d-flex w-100">
                                    <div class="col-md-5 p-1"> 
                                        <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('tsl_limitDcRatePerDevice') }}</span><br/>
                                        <span class="text-black-50 p-0" style="font-size:0.6rem; ">{{ $t('tip_limitDcRatePerDevice') }}</span>
                                    </div>
                                    <div class="col-md-3 text-dark p-2" style="font-size:0.8rem; text-align:right;"> {{ basicStat.limitDcRatePerDevice }} DCs</div>
                                    <div class="col-md-4 text-dark p-2" style="font-size:0.8rem; text-align:right;">
                                        <span class="text-dark p-0" style="font-size:0.8rem; "></span><br/>
                                        <span class="text-dark p-0" style="font-size:0.6rem; "></span>
                                    </div>
                                </div>
                            </b-list-group-item>


                        </b-list-group>
                    </b-card>
                    <TenantDevInactive/>
                    </b-col>
                    <b-col cols="6">
                        <b-card 
                        :header="$t('bcopy_card_title')"
                        class="TenantDetails">
                            <b-list-group class="m-0 p-0">
                                <b-list-group-item class="border-0 p-0" style="text-align:left;" variant="primary">
                                    <div class="d-flex w-100">
                                        <div class="col-md-4 p-1 small"> {{ $t('bcopy_parameter') }}</div>
                                        <div class="col-md-3 p-1 small" style="text-align:right;"> {{ $t('bcopy_curvalue') }}</div>
                                        <div class="col-md-5 p-1 small" style="text-align:right;"> {{ $t('bcopy_newvalue') }}</div>
                                    </div>
                                </b-list-group-item>
                                <b-list-group-item class="border-0 p-0" style="text-align:left; line-height: 1;" variant="secondary">
                                    <div class="d-flex w-100">
                                        <div class="col-md-4 p-1"> 
                                            <span class="text-dark p-0" style="font-size:0.8rem; ">{{ $t('bcopy_name') }}</span><br/>
                                            <span class="text-black-50 p-0" style="font-size:0.6rem;">{{ $t('bcopy_desc') }}</span>
                                        </div>
                                        <div class="col-md-3 text-dark p-2 mt-2" style="font-size:0.8rem; text-align:right; vertical-align: middle;">
                                             {{ basicStat.maxCopy }}
                                        </div>
                                        <div class="col-md-5 text-dark p-2" style="font-size:0.8rem; text-align:right; vertical-align: middle;">
                                            <b-form-input v-model.number="newMaxCopy"
                                                        type="number" 
                                                        class="mb-2"
                                                        size="sm"
                                            ></b-form-input>
                                            <b-button block
                                                variant="outline-dark"
                                                size="sm"
                                                @click="updateMaxCopy()"
                                                style="text-align: left;font-size:0.8rem;"
                                            >
                                                {{ $t('bcopy_update') }}
                                                <b-icon icon="pencil-square" variant="secondary"></b-icon>
                                            </b-button>
                                            <b-card-text v-if="updateSuccess != ''" class="small mb-2 text-success">
                                                {{ $t(updateSuccess) }}   
                                            </b-card-text>
                                            <b-card-text  v-if="updateError != ''" class="small mb-2 text-danger">
                                                {{ $t(updateError) }}
                                            </b-card-text>
                                        </div>
                                    </div>
                                </b-list-group-item>
                            </b-list-group>
                        </b-card>
                        <TenantConsumption/>
                        <TenantDevConsumption/>
                    </b-col>
            </b-row>
            <b-row cols="6" class="ml-0" v-if="! loadBasicStatSuccess">
                <b-col cols="6">
                <b-card style="border-radius: 0.6rem; height: 500px;" class = "text-center bg-light" body-class="d-flex flex-column">
                    <div style="position: absolute; top: 47%; left: 50%; transform: translate(-50%, -50%);">
                        <b-card-text class="small mb-2 text-danger">
                            <b-icon icon="exclamation-circle-fill" variant="danger"></b-icon>
                            {{ $t(errorMessage) }}
                        </b-card-text>
                    </div>
                </b-card>
                </b-col>
            </b-row>
        </b-card>
    </div>
</template>
<style>
 .TenantDetails .card-header  {
    font-size: 0.8rem;
    font-weight: 600;
    font-variant: small-caps;
}
</style>

<script lang="ts">
    import Vue from 'vue'
    import { TenantBasicStat, TenantUpdateMaxCopyReqItf } from 'vue/types/tenantStat';
    import TenantConsumption from '~/components/TenantConsumption.vue'
    import TenantDevConsumption from '~/components/TenantDevConsumption.vue'
    import TenantDevInactive from '~/components/TenantDevInactive.vue'
  
    interface data {
        basicStat : TenantBasicStat,
        tenantTitle : string,
        loadBasicStatSuccess : boolean,
        errorMessage : string,
        isBusy : boolean,
        newMaxCopy : number,
        updateError : string,
        updateSuccess : string,
    }

    export default Vue.extend({
      name: "BasicStatComponent",
      components: { 
        'TenantConsumption' : TenantConsumption,
        'TenantDevConsumption' : TenantDevConsumption,
        'TenantDevInactive' : TenantDevInactive,
      },
      data() : data {
        return {
          basicStat : {} as TenantBasicStat,
          tenantTitle : "",
          loadBasicStatSuccess : false,
          errorMessage: '',
          isBusy : true,
          newMaxCopy : 0,
          updateError : '',
          updateSuccess: '',
        }
      },
      async fetch() {
        let tenantId = this.$store.state.currentTenant;
        if ( tenantId == undefined || tenantId == null || tenantId.length < 5 ) {
            this.errorMessage = 'error_find_basicstat';
            return;
        } 

        let config = {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
            }
        };
        this.errorMessage = '';
        this.isBusy = true;
        this.$axios.get<TenantBasicStat>(this.$config.tenantBasicStat+'/'+tenantId+'/',config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.basicStat = response.data;
                  this.tenantTitle = '' + this.basicStat.tenantName + ' ('+ this.basicStat.tenantUUID +')';
                  this.newMaxCopy = this.basicStat.maxCopy;
                  this.isBusy = false;
                  this.loadBasicStatSuccess = true;
                }
            }).catch((err) =>{
               this.basicStat = {} as TenantBasicStat;
               this.errorMessage = 'error_load_basicstat';
               this.loadBasicStatSuccess = false;
            });
      },
      methods: {
        getTenantTitle() : string {
            return this.tenantTitle;
        },
        updateMaxCopy() {

            let config = {
                headers: {
                'Content-Type': 'application/json',
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                }
            };
            let body : TenantUpdateMaxCopyReqItf = {
                tenantId: this.basicStat.tenantUUID,
                newMaxCopy: ~~this.newMaxCopy
            }
            this.updateError='';
            this.updateSuccess='';

            this.$axios.put(this.$config.tenantMaxCopyUpdate,body,config)
                .then((response) =>{
                    if ( response.status == 200 ) {
                        this.updateSuccess='bcopy_success';
                        this.$fetch();
                    } else {
                        this.updateError='bcopy_error';
                    }
                })
                .catch((err) => {
                    this.updateError='bcopy_error';
                });
        }
      },
    });
</script>