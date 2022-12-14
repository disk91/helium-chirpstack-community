<template>
        <b-modal id="add-tenant-modal" 
                 centered 
                 :title="$t('menu_add_tenant')"
                 content-class="shadow"
                 v-model="show"
                 @show="resetForm"
                 @ok="createTenant"
                 header-bg-variant="primary"
                 header-text-variant="white"
                 header-border-variant="primary"
                 footer-border-variant="white"
        >
            <b-form-group :state="tenantState" :invalid-feedback="$t('signup_verif_tenant')" style="text-align:left;" class="mb-1 small">
                <b-form-input v-model="tenantName"
                              id="signup-tenant"
                              type="text" 
                              :placeholder="$t('signup_tenant')"
                              :state="tenantState"
                              class="mb-1"
                ></b-form-input>
            </b-form-group>
            <b-form-group :description="$t('signup_desc_invite')" style="text-align:left;" class="mb-1 small">
                <b-form-input v-model="inviteCode"
                              id="signup-invite"
                              type="text" 
                              :placeholder="$t('signup_invite')"
                              class="mb-1"
                ></b-form-input>
            </b-form-group>
            <b-card-text class="text-danger" style="text-align:center;" >
                {{ $t(errorMessage)}} 
            </b-card-text>
            <b-card-text class="text-success" style="text-align:center;">
                {{ $t(successMessage)}} 
            </b-card-text>
        </b-modal>   
</template>

<script>
// Local storage contains the Bearer 
import Vue from 'vue'

export default Vue.extend({
    data () {
	    return {
		    show: false, 
            tenantName : '',
            inviteCode : '',
            errorMessage : '',
            successMessage : '',
	    }
    },
    computed: {
        tenantState() {
            if ( this.tenantName.length == 0 ) return null;
            return ( this.tenantName.length > 3 );
        }
    },
    created() {
    },
    mounted() {
        this.$root.$on("message-display-add-tenant", (msg) => {
            this.resetForm();
            this.show = true;
        });
    },
    methods: {
        resetForm() {
            this.tenantName='';
            this.inviteCode='';
            this.errorMessage='';
            this.successMessage='';
        }, 
        async createTenant(bvModalEvent) {
            if ( this.tenantState == null || ! this.tenantState ) {
                bvModalEvent.preventDefault();
            } else {
                this.errorMessage='';
                this.successMessage='';
                let body = {
                    tenantName: this.tenantName,
                    couponCode: this.inviteCode
                };
                let config = {
                        headers: {
                            'Content-Type': 'application/json',  
                            'Authorization': 'Bearer '+this.$store.state.consoleBearer,
                        }
                };
                this.$axios.post(this.$config.addTenantEndpoint, body, config)
                    .then((response) =>{
                        if (response.status == 200 ) {
                            this.successMessage = "addtenant_vmessage_success";
                            this.tenantName=''; // avoid reentering
                            var self = this;
                            setTimeout(function() { 
                                self.$data.show = false;
                            }, 1500);
                        }
                    }).catch((err) =>{
                        if ( err.response.status == 400 ) {
                            let message = err.response.data.errorMessage;
                            this.errorMessage = 'sret_'+message;
                        }
                    })
                bvModalEvent.preventDefault();
            }
        }
    },
})

</script>
