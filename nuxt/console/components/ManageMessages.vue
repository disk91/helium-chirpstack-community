<template>
    <div>
        <h5><b-badge variant="secondary" class="m-2">{{ $t('mess_list_title') }}</b-badge></h5>
        <b-button size="sm" 
                      @click="addNewClick()"
                      class="m0 ml-2" 
                      pill 
                      variant="outline-secondary"
                      style="font-size:0.5rem;margin:2px;"
            >
              {{ $t('mess_add_new') }} 
        </b-button>
        <b-table 
          :items="messages" 
          :fields="fields" 
          :busy="isBusy"
          responsive 
          small
          striped
          headVariant="dark"
          class="p-2 pb-5"
          style="font-size:8px;">
          <template #cell(edit)="row">
            <b-button size="sm" 
                      @click="onLineClick(row)"
                      class="m0" 
                      pill 
                      variant="outline-secondary"
                      style="font-size:0.5rem;margin:2px;"
            >
              {{ $t('tsl_edit') }} 
            </b-button>
          </template>

          <template #head()="data">
            <span v-b-tooltip.hover="{ variant: 'secondary'}"
                  :title="getTipFromLabel(data)"
            >
              {{ data.label }}
            </span>
          </template>
        </b-table>


        <b-modal id="message-modal" ref="message-modal"
                 centered 
                 :title="$t('message_modal_title')"
                 content-class="shadow"
                 v-model="showModal"
                 @ok="updateModal"
                 header-bg-variant="dark"
                 header-text-variant="white"
                 header-border-variant="dark"
                 footer-border-variant="white"
        >
            <b-form-group v-if="!disableIndex" :description="$t('tip_mes_index')" style="text-align:left;" class="mb-1 small">
                <b-form-input v-model="message.index"
                              type="text" size="sm"
                              :placeholder="$t('tmes_index')"
                              :disabled=true
                ></b-form-input>
            </b-form-group>

            <b-form-group :description="$t('tip_mes_type')" style="text-align:left;" class="mb-1 small">
                <b-form-select v-model.number="message.type" 
                              :options="type_option"
                              :placeholder="$t('tmes_type')"
                              size="sm"
                ></b-form-select>
            </b-form-group>

            <b-form-group :description="$t('tip_mes_category')" style="text-align:left;" class="mb-1 small">
                <b-form-select v-model.number="message.category" 
                              :options="category_option"
                              :placeholder="$t('tmes_category')"
                              size="sm"
                ></b-form-select>
            </b-form-group>

            <b-form-group :description="$t('tip_mes_content')" style="text-align:left;" class="mb-1 small">
                  <b-form-textarea v-model="message.content" size="sm"
                                :placeholder="$t('tmes_content')"
                                rows="3"
                                max-rows="6"
                  ></b-form-textarea>
            </b-form-group>

            <b-form-group :description="$t('tip_mes_onlyone')" style="text-align:left;" class="mb-1 small">
              <b-form-checkbox v-model="message.onlyone"
                                  value='true' size="sm"
                                  unchecked-value='false'
                                  style="text-align:left;"
                                  >
                                  {{$t('tmes_onlyone')}}
              </b-form-checkbox>
            </b-form-group>

            <b-form-group :description="$t('tip_mes_until')" style="text-align:left;" class="mb-1 small">
              <b-form-datepicker v-model="selected_date">
              </b-form-datepicker>
              <b-form-timepicker v-model="selected_time">
              </b-form-timepicker>
              <b-form-checkbox v-model="unlimited_time"
                                  :value=true size="sm"
                                  :unchecked-value=false
                                  style="text-align:left;"
                                  >
                                  {{$t('tmes_unlimited')}}
              </b-form-checkbox>
            </b-form-group>

            <b-button block @click="messageDelete"
                      v-if="! disableIndex"
                      variant="outline-danger" 
                      class="mb-2"
            >
                {{ $t('mess_del_button') }}
            </b-button>
            <b-card-text class="text-danger" style="text-align:center;" >
                {{ $t(errorMessageMod)}} 
            </b-card-text>
            <b-card-text class="text-success" style="text-align:center;">
                {{ $t(successMessageMod)}} 
            </b-card-text>
            
        </b-modal>   

    </div>
  </template>
  <style>
  .tooltip {
    font-size: 0.6rem;
  }
  .table > tbody > tr > td {
     vertical-align: middle;
  }
  </style>
  
  <script lang="ts">
  import Vue from 'vue'
  import { Message } from 'vue/types/message';
  
  interface data {
    messages : Message[],
    message : Message,
    fields : any,
    errorMessage : string,
    isBusy : boolean,
    showModal : boolean,
    errorMessageMod : string,
    successMessageMod : string,
    disableIndex : boolean,
    type_option : any,
    category_option : any,
    selected_date : string,
    selected_time : string,
    unlimited_time : boolean,
  }

  export default Vue.extend({
      name: "ManageMessage",
      data() : data {
        return {
          messages : [],
          message : {} as Message,
          fields : [
            {key: 'edit', label : this.$t('tsl_edit')},
            {key: 'index', sortable: false, label : this.$t('tmes_index'), formatter : "dateFormatter"},
            {key: 'type', sortable: false, label : this.$t('tmes_type'), formatter: "typeFormatter"},
            {key: 'category', sortable: false, label : this.$t('tmes_category'), formatter: "categoryFormatter"},
            {key: 'content', sortable: false, label : this.$t('tmes_content'), formatter: "contentFormatter"},
            {key: 'until', sortable: false, label : this.$t('tmes_until'), formatter : "dateFormatter"},
            {key: 'onlyone', sortable: false, label : this.$t('tmes_onlyone')},
          ],
          errorMessage: '',
          isBusy : true,
          showModal : false,
          errorMessageMod : '',
          successMessageMod: '',
          disableIndex: true,
          type_option : [
            {value: 0, text : this.$t('tmes_type') },
            {value: 1, text : "Modal"},
            {value: 2, text : "Toaster"},
            {value: 3, text : "FrontPage"},
          ],
          category_option : [
            {value: -1, text : this.$t('tmes_category') },
            {value: 0, text : "Info"},
            {value: 1, text : "Warning"},
            {value: 2, text : "Danger"}
          ],
          selected_date : '',
          selected_time : '',
          unlimited_time : false,
        }
      },
      async fetch() {
        let config = {
            headers: {
              'Content-Type': 'application/json',
              'Authorization': 'Bearer '+this.$store.state.consoleBearer,  
            }
        };
        this.$data.isBusy = true;
        this.$axios.get<Message []>(this.$config.messageAdmGet,config)
            .then((response) =>{
                if (response.status == 200 ) {
                  response.data.forEach( element => {
                    this.$data.messages.push(element)
                  });
                  this.$data.isBusy = false;

                }
            }).catch((err) =>{
               this.$data.errorMessage = 'error_load_messages';
               this.$data.messages = [];
            })
      },
      methods: {
        getTipFromLabel(d:any) : string {
          switch ( d.column ) {
            default: return '';
            case 'index': return this.$t('tip_mes_index').toString();
            case 'type' : return this.$t('tip_mes_type').toString();
            case 'category' : return this.$t('tip_mes_category').toString();
            case 'content' : return this.$t('tip_mes_content').toString();
            case 'until' : return this.$t('tip_mes_until').toString();
            case 'onlyone' : return this.$t('tip_mes_onlyone').toString();
          }
        },
        dateFormatter(value:string,key:string,item:Message) {
            let m = new Date(value);
            var dateString = m.getUTCFullYear() + "/" +
                            ("0" + (m.getMonth()+1)).slice(-2) + "/" +
                            ("0" + m.getDate()).slice(-2) + " " +
                            ("0" + m.getHours()).slice(-2) + ":" +
                            ("0" + m.getMinutes()).slice(-2) + ":" +
                            ("0" + m.getSeconds()).slice(-2);
            return dateString;
        },
        typeFormatter(value:number,key:string,item:Message) {
           if ( value == 1 ) return "Modal";
           if ( value == 2 ) return "Toaster";
           if ( value == 3 ) return "FrontPage";
           return "NA"; 
        },  
        categoryFormatter(value:number,key:string,item:Message) {
          if ( value == 0 ) return "Info";
          if ( value == 1 ) return "Warning";
          if ( value == 2 ) return "Danger";
          return "NA"; 
        },  
        contentFormatter(value:string,key:string,item:Message) {
          if ( value.length > 80 ) return value.substring(0,80)+'...';
          return value;
        },
        onLineClick(d:any) {
          // clone
          this.$data.message = Object.assign({},d.item);
          let dt = new Date(this.$data.message.until);
          this.$data.selected_date=
              dt.getFullYear()+'-'+
              ("0" + (dt.getMonth()+1)).slice(-2)+'-'+
              ("0" + dt.getDate()).slice(-2);
          this.$data.selected_time= ( "0"+dt.getHours() ).slice(-2)+':'+
              ("0" + dt.getMinutes()).slice(-2);
          this.$data.showModal = true;
          this.$data.disableIndex = false;
          this.$data.unlimited_time = ( this.$data.message.until == 0 );
        },
        addNewClick() {
          this.$data.message = {} as Message;
          this.$data.message.type = BigInt(0);
          this.$data.message.category = BigInt(-1);
          this.$data.showModal = true;
          this.$data.disableIndex = true;
          this.$data.unlimited_time = false;
        },
        async updateModal(bvModalEvent: any) {
          this.$data.errorMessageMod='';
          this.$data.successMessageMod='';
          let config = {
              headers: {
                'Content-Type': 'application/json',  
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,
              }
          };
          if ( ((this.$data.unlimited_time as unknown ) as string ) == "true" ) this.$data.unlimited_time = true;
          if ( ((this.$data.unlimited_time as unknown ) as string ) == "false" ) this.$data.message = false;
          if ( this.$data.unlimited_time ) {
            this.$data.message.until = 0;
          } else {
            if ( this.$data.selected_date == '' ) {
              this.$data.message.until = 0;
            } else {
              if ( this.$data.selected_time == '' ) {
                this.$data.selected_time = '00:00:00';
              } 
              let dt = new Date(this.$data.selected_date+'T'+this.$data.selected_time);
              this.$data.message.until=dt.getTime();
            }
          }
          // because JS is not respecting types, even if ts ... ts should learn this
          if ( ((this.$data.message.onlyone as unknown ) as string ) == "true" ) this.$data.message.onlyone = true;
          if ( ((this.$data.message.onlyone as unknown ) as string ) == "false" ) this.$data.message.onlyone = false;
          if ( this.$data.message.id != null && this.$data.message.id != '' ) {
            this.$axios.put(this.$config.messageAdmUpd, this.$data.message, config)
                .then((response) =>{
                    if (response.status == 200 ) {
                        this.successMessageMod = "updmessage_vmessage_success";
                        this.$data.message={} as Message; // avoid reentering
                        var self = this;
                        setTimeout(function() { 
                            self.$data.showModal = false;
                            self.$data.errorMessageMod='';
                            self.$data.successMessageMod='';
                        }, 1500);
                        this.$data.messages = [] as Message[];
                        this.$fetch();
                    }
                }).catch((err) =>{
                    if ( err.response.status == 400 ) {
                        let message = err.response.data.message;
                        this.$data.errorMessageMod = 'sret_'+message;
                    }
                })
                bvModalEvent.preventDefault();
          } else {
            this.$axios.post(this.$config.messageAdmNew, this.$data.message, config)
                .then((response) =>{
                    if (response.status == 201 ) {
                        this.successMessageMod = "addmessage_vmessage_success";
                        this.$data.message={} as Message; // avoid reentering
                        var self = this;
                        setTimeout(function() { 
                            self.$data.showModal = false;
                            self.$data.errorMessageMod='';
                            self.$data.successMessageMod='';
                        }, 1500);
                        this.$data.messages = [] as Message[];
                        this.$fetch();
                    }
                }).catch((err) =>{
                    if ( err.response.status == 400 ) {
                        let message = err.response.data.message;
                        this.errorMessageMod = 'sret_'+message;
                    }
                })
                bvModalEvent.preventDefault();
          }
        },
        messageDelete(bvModalEvent: any) {
          this.errorMessageMod='';
          this.successMessageMod='';
          let config = {
              headers: {
                'Content-Type': 'application/json',  
                'Authorization': 'Bearer '+this.$store.state.consoleBearer,
              }
          };
          this.$axios.delete(this.$config.messageAdmDel+'/'+this.message.id+'/', config)
                .then((response) =>{
                    if (response.status == 200 ) {
                        this.successMessageMod = "delmessage_vmessage_success";
                        this.message={} as Message; // avoid reentering
                        var self = this;
                        setTimeout(function() { 
                            self.$data.showModal = false;
                            self.errorMessageMod='';
                            self.successMessageMod='';
                        }, 1500);
                        this.messages = [] as Message[];
                        this.$fetch();
                    }
                }).catch((err) =>{
                    if ( err.response.status == 400 ) {
                        let message = err.response.data.errorMessage;
                        this.errorMessageMod = 'sret_'+message;
                    }
                    if ( err.response.status == 403 ) {
                        this.errorMessageMod = 'sret_error_forbidden';
                    }
                    var self = this;
                        setTimeout(function() { 
                            self.$data.showModal = false;
                            self.errorMessageMod='';
                            self.successMessageMod='';
                        }, 1500);
                })
        }
      },
  })
</script>
  