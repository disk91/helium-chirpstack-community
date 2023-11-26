<template>
    <b-card 
        :header="$t('u_search_title')"
        class="mt-3 ml-3 myCard"
    >
        <div class="col-md-8">
            <b-form-group :description="$t('tip_search_user')" style="text-align:left;" class="mb-1 small">
                <b-form-input v-model="keyword"
                              type="text" size="sm"
                              @keyup="onSearchChange"
                              :placeholder="$t('tsl_search_user')"
                ></b-form-input>
            </b-form-group>
        </div>

        <div class="col-md-12 ml-0">
        <b-table 
          :items="users" 
          :fields="fields" 
          :busy="isBusy"
          responsive 
          small
          striped
          headVariant="dark"
          class="pt-1 pb-5"
          style="font-size:8px;">
          <template #head()="data">
              {{ data.label }}
          </template>
          <template #cell(ban)="row">
            <b-button size="sm" 
                      @click="onLineClick(row)"
                      class="m0" 
                      pill 
                      variant="outline-secondary"
                      style="font-size:0.5rem;margin:2px;"
                      :disabled=false
            >
              {{ $t('tsl_ban_user') }} 
            </b-button>
          </template>
          <template #cell(registration)="row">
            {{ dateFormatter(row.value) }} 
          </template>
          <template #cell(lastLogin)="row">
            {{ dateFormatter(row.value) }} 
          </template>
          <template #cell(tenants)="row">
            {{ tenantsFormatter(row.value) }} 
          </template>
        </b-table>
        <b-card-text class="small mb-2 text-danger" v-show="errorBan!=''">
                <b-icon icon="exclamation-circle-fill" variant="danger"></b-icon>
                {{ $t(errorBan) }}
            </b-card-text>
            <b-card-text class="mb-2 text-success" v-show="successBan!=''">
                <b-icon icon="check-square" variant="success"></b-icon>
                {{ $t(successBan) }}
            </b-card-text>
        </div>
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
import { BIconHandThumbsDown } from 'bootstrap-vue';
import Vue from 'vue'
import { UserListRespItf, TenantEntry, UserBanReqItf } from 'vue/types/userSearch';

interface data {
    keyword : string,
    users : UserListRespItf[],
    user : UserListRespItf,
    fields : any,
    isBusy : boolean,
    errorMessageMod : string,
    successMessageMod : string,
    errorBan : string,
    successBan : string,
}

export default Vue.extend({
    name: "ManageUsers",
    data() : data {
        return {
            keyword : '',
            users : [] as UserListRespItf[],
            user : {} as UserListRespItf,
            fields : [
                {key: 'ban', label : this.$t('tsl_ban_user')},
                {key: 'disable', sortable: false, label : this.$t('tsl_dis_user')},
                {key: 'userLogin', sortable: false, label : this.$t('tsl_login_user')},
                {key: 'registration', sortable: false, label : this.$t('tsl_reg_user')},
                {key: 'lastLogin', sortable: false, label : this.$t('tsl_lastl_user')},
                {key: 'tenants', sortable: false, label : this.$t('tsl_tenants')},
            ],
            isBusy : false,
            errorMessageMod : '',
            successMessageMod : '',
            errorBan : '',
            successBan : '',
        };
    },
    methods : {
        dateFormatter(time:BigInt) : string {
            let m = new Date(time as any);
            var dateString = m.getUTCFullYear() + "/" +
                            ("0" + (m.getMonth()+1)).slice(-2) + "/" +
                            ("0" + m.getDate()).slice(-2) + " " +
                            ("0" + m.getHours()).slice(-2) + ":" +
                            ("0" + m.getMinutes()).slice(-2) + ":" +
                            ("0" + m.getSeconds()).slice(-2);
            return dateString;
        },
        tenantsFormatter(tenants:TenantEntry[]) : string {
            let s = "";
            for ( let t of tenants ) {
                s += t.name+' ';
                if ( t.admin ) s+= '(A)';
                s += " / "
            }
            return s;
        },
        onSearchChange() {
            if ( this.isBusy ) return;
            if ( this.keyword.length >= 3 && this.keyword.length <= 20 ) {
                this.isBusy = true;
                //console.log(this.keyword);
                let config = {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
                    }
                };
                this.users = [];
                this.$axios.get<UserListRespItf[]>(this.$config.userListGet+'?keyword='+this.keyword,config)
                    .then((response) =>{
                        if (response.status == 200 ) {
                            response.data.forEach( element => {
                                this.users.push(element)
                            });
                            this.users = this.users.reverse();
                            this.isBusy = false;
                        }
                    }).catch((err) =>{
                       this.users = [];
                       this.isBusy = false;
                    })
            }
        },
        onLineClick(row : any) {
            this.user = Object.assign({},row.item);
            let config = {
              headers: {
                'Content-Type': 'application/json',  
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,
              }
            };
            let body = {
                username: this.user.userLogin,
            } as UserBanReqItf;
            this.isBusy = true;
            this.errorBan = '';
            this.successBan = '';
            this.$axios.put(this.$config.userBanPut,body,config)
                .then((response) =>{
                    if (response.status == 200 ) {
                        this.successBan = 'success';
                    } else {
                        this.errorBan = 'failed';
                    }
                    this.isBusy = false;
                }).catch((err) =>{
                    this.errorBan = 'failed';
                    this.isBusy = false;
                });
        }
    }
})
</script>