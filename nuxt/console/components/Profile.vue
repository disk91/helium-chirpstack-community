<template>
    <div>
        <b-card-text class="m-4"><h5>{{$t('profile_title')}}</h5></b-card-text>
        <b-card
                :header="$t('profile_detail')"
                class="m-4"
        >
            <b-row cols="1" v-if="!isComplete" align-h="center" class="mb-3">
                <b-col cols="6" class="bg-warning py-2" style="border-radius: 0.7rem;font-size:0.8rem;">
                    {{  $t('profile_uncomplete') }}
                </b-col>
            </b-row>

            <b-form-group 
                        :label="$t('tsl_username')"
                        label-for="username"
                        label-cols="2"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1"
            >
                    <b-form-input 
                                v-model="profile.username"
                                id="username"
                                type="text" size="sm"
                                :disabled="true"
                    ></b-form-input>
                    <b-form-text style="font-size:0.5rem;">{{ $t('tip_username') }}</b-form-text>
            </b-form-group>
            <b-form-group 
                        :label="$t('tsl_company')"
                        label-for="company"
                        label-cols="2"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1"
            >
                    <b-form-input 
                                v-model="profile.company"
                                id="company"
                                type="text" size="sm"
                    ></b-form-input>
                    <b-form-text style="font-size:0.5rem;">{{ $t('tip_company') }}</b-form-text>
            </b-form-group>
            <b-form-group 
                        :label="$t('tsl_firstname')"
                        label-for="firstname"
                        label-cols="2"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1"
            >
                    <b-form-input 
                                v-model="profile.firstName"
                                id="firstname"
                                type="text" size="sm"
                    ></b-form-input>
                    <b-form-text style="font-size:0.5rem;">{{ $t('tip_firstname') }}</b-form-text>
            </b-form-group>
            <b-form-group 
                        :label="$t('tsl_lastname')"
                        label-for="lastname"
                        label-cols="2"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1"
            >
                    <b-form-input 
                                v-model="profile.lastName"
                                id="lastname"
                                type="text" size="sm"
                    ></b-form-input>
                    <b-form-text style="font-size:0.5rem;">{{ $t('tip_lastname') }}</b-form-text>
            </b-form-group>
            <b-form-group 
                        :label="$t('tsl_address')"
                        label-for="address"
                        label-cols="2"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1"
            >
                    <b-form-textarea 
                                v-model="profile.address"
                                id="address"
                                size="sm"
                                rows="3"
                    ></b-form-textarea>
                    <b-form-text style="font-size:0.5rem;">{{ $t('tip_address') }}</b-form-text>
            </b-form-group>
            <b-form-group 
                        :label="$t('tsl_citycode')"
                        label-for="citycode"
                        label-cols="2"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1"
            >
                    <b-form-input 
                                v-model="profile.cityCode"
                                id="citycode"
                                type="text" size="sm"
                    ></b-form-input>
                    <b-form-text style="font-size:0.5rem;">{{ $t('tip_citycode') }}</b-form-text>
            </b-form-group>
            <b-form-group 
                        :label="$t('tsl_cityname')"
                        label-for="cityname"
                        label-cols="2"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1"
            >
                    <b-form-input 
                                v-model="profile.cityName"
                                id="cityname"
                                type="text" size="sm"
                    ></b-form-input>
                    <b-form-text style="font-size:0.5rem;">{{ $t('tip_cityname') }}</b-form-text>
            </b-form-group>
            <b-form-group 
                        :label="$t('tsl_country')"
                        label-for="country"
                        label-cols="2"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1"
            >
                    <b-form-input 
                                v-model="profile.country"
                                id="country"
                                type="text" size="sm"
                    ></b-form-input>
                    <b-form-text style="font-size:0.5rem;">{{ $t('tip_country') }}</b-form-text>
            </b-form-group>
            <b-form-group 
                        label-cols="2"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1"
            >
                    <b-button
                            variant="primary"
                            size="sm"
                            @click="updateUser()"
                    >
                    {{ $t('profile_upt_but') }}
                    </b-button>
                    <b-card-text class="small mb-2 text-danger" v-show="errorMessageMod!=''">
                        <b-icon icon="exclamation-circle-fill" variant="danger"></b-icon>
                        {{ $t(errorMessageMod) }}
                    </b-card-text>
                    <b-card-text class="mb-2 text-success" v-show="successMessageMod!=''">
                        <b-icon icon="check-square" variant="success"></b-icon>
                        {{ $t(successMessageMod) }}
                    </b-card-text>
            </b-form-group>
        </b-card>
    </div>
</template>
<style>
.card-header {
    font-size: 1rem;
    font-weight: 600;
    font-variant: small-caps;
}
</style>

<script lang="ts">
import { tsImportEqualsDeclaration } from '@babel/types';
import Vue from 'vue'
import { UserDetails } from 'vue/types/userProfile';

interface data {
    profile : UserDetails,
    isBusy : boolean,
    errorMessage : string,
    errorMessageMod : string,
    successMessageMod : string,
    isComplete: boolean,
}

export default Vue.extend({
    name: "UserProfile",
    data() : data {
        return {
            profile : {} as UserDetails,
            isBusy : false,
            errorMessage : '',
            errorMessageMod : '',
            successMessageMod : '',
            isComplete : true,
        };
    },
    async fetch() {
        let config = {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
            }
        };
        this.isBusy = true;
        this.$axios.get<UserDetails>(this.$config.userDetailsGet,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.profile = response.data;
                  this.isBusy = false;
                  this.checkComplete();
                }
            }).catch((err) =>{
               this.errorMessage = 'error_load_profile';
               this.profile = {} as UserDetails;
            })
    },
    methods : {
        checkComplete() {
            if ( this.profile.lastName == '' || this.profile.cityName == '' || this.profile.cityCode == '' || this.profile.country == '' ) {
                this.isComplete = false;
            } else {
                this.isComplete = true;
            }
        },
        updateUser() {
          this.errorMessageMod='';
          this.successMessageMod='';
          let config = {
              headers: {
                'Content-Type': 'application/json',  
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,
              }
          };
          this.$axios.put<UserDetails>(this.$config.userDetailsUpd, this.profile, config)
                .then((response) =>{
                    if (response.status == 200 ) {
                        this.successMessageMod = "updprofile_vmessage_success";
                        this.profile=response.data; // avoid reentering
                        this.checkComplete();
                    }
                }).catch((err) =>{
                    if ( err.response.status == 403 ) {
                        let message = err.response.data.errorMessage;
                        this.errorMessageMod = "updprofile_vmessage_error";
                    }
                })                
        }
    }
})
</script>