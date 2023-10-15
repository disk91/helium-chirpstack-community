<template>
    <b-card 
        :header="$t('uc_version_title')"
        class="mt-3 mr-3 myCard"
    >
        <b-form-group 
                        :label="$t('uc_version')"
                        label-for="conditionVersion"
                        label-cols="3"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1 mr-1"
            >
                    <b-form-input 
                                v-model="versionRead.message"
                                id="conditionVersion"
                                type="text" size="sm"
                    ></b-form-input>
                    <b-form-text style="font-size:0.5rem;">{{ $t('uc_version_desc') }}</b-form-text>
        </b-form-group>
        <b-form-group 
                        label-cols="3"
                        label-align="right"
                        label-class="w-25"
                        label-size="sm"
                        class="mb-1"
            >
                    <b-button
                            variant="primary"
                            size="sm"
                            @click="updateCondition()"
                    >
                    {{ $t('inv_upt_but') }}
                    </b-button>
                    <b-card-text class="small mb-2 text-danger" v-show="errorMessage!=''">
                        <b-icon icon="exclamation-circle-fill" variant="danger"></b-icon>
                        {{ $t(errorMessage) }}
                    </b-card-text>
                    <b-card-text class="mb-2 text-success" v-show="successMessage!=''">
                        <b-icon icon="check-square" variant="success"></b-icon>
                        {{ $t(successMessage) }}
                    </b-card-text>
            </b-form-group>
        </b-card>
</template>
<style>
.myCard .card-header  {
    font-size: 0.8rem;
    font-weight: 600;
    font-variant: small-caps;
}
.myCard .card-body  {
    padding: 20px 5px 20px 5px;
}
</style>

<script lang="ts">
import Vue from 'vue'
import { UserConditionVersionGetReqItf, UserConditionVersionUpdateReqItf } from 'vue/types/conditions';

interface data {
    isBusy : boolean,
    versionRead : UserConditionVersionGetReqItf,
    errorMessage : string,
    successMessage : string,
}

export default Vue.extend({
    name: "ConditionSetup",
    components: {
    },
    data() : data {
        return {
            isBusy : false,
            versionRead : {} as UserConditionVersionGetReqItf,
            errorMessage : '',
            successMessage : '',
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
        this.$axios.get<UserConditionVersionGetReqItf>(this.$config.conditionGet,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  this.versionRead = response.data;
                  this.isBusy = false;
                }
            }).catch((err) =>{
               this.errorMessage = 'uc_error_load_condition';
               this.versionRead = {} as UserConditionVersionGetReqItf;
            })
    },
    methods : {
        updateCondition() {
            this.errorMessage='';
            this.successMessage='';
            let config = {
              headers: {
                'Content-Type': 'application/json',  
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,
              }
            };
            let newCondition = {
                conditionVersion : this.versionRead.message
            } as UserConditionVersionUpdateReqItf;

            this.$axios.put(this.$config.conditionPut, newCondition, config)
                .then((response) =>{
                    if (response.status == 200 ) {
                        this.successMessage = "uc_update_success";
                    }
                }).catch((err) =>{
                    if ( err.response.status == 403 ) {
                        this.errorMessage = "uc_update_error";
                    }
                })  
        }
    },
    mounted() {
    },
})
</script>