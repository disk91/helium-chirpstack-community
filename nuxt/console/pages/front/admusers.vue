<template>
    <div>
      <Navbar/>
      <b-row>
        <b-col cols="6">
          <ManageUsers/>
        </b-col>
        <b-col cols="6">
          <UserConditionVersion/>      
        </b-col>
      </b-row>
    </div>
</template>

<script lang="ts">
  import Vue from 'vue'
  import Navbar from '~/components/Navbar.vue';
  import ManageUsers from '~/components/ManageUsers.vue';
  import UserConditionVersion from '~/components/UserConditionSetup.vue';

  export default Vue.extend({
      name: "ManageUsersMain",
      components: { 
        'Navbar' : Navbar,
        'ManageUsers' : ManageUsers,
        'UserConditionVersion' : UserConditionVersion,
      },
    })
</script>
  