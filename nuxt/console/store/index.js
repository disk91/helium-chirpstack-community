export const state = () => ({
    chirpstackBearer:'',
    consoleBearer:'',
    currentTenant:'',
    lastChirpstackUrl:'',
    admin:false,
    currentDevice:'',
})

export const getters = {
  getChirpstackBearer(state) {
    return state.chirpstackBearer;
  },
  getConsoleBearer(state) {
    return state.consoleBearer;
  },
  getCurrentTenant(state) {
    return state.currentTenant;
  },
  isAuthenticated(state) {
    return state.auth.loggedIn
  },
  getLastChirpstackUrl(state) {

  },
  loggedInUser(state) {
    return state.auth.user
  }, 
  isAdmin(state) {
    return state.admin;
  },
  getCurrentDevice(state) {
    return state.currentDevice;
  },

}

export const mutations = {

    setChirpstackBearer(state, b) {
        state.chirpstackBearer = b;
    },
    setConsoleBearer(state, b) {
        state.consoleBearer = b;
    },
    setCurrentTenant(state, b) {
        state.currentTenant = b;
    },
    setLastChirpstackUrl(state, b) {
        state.lastChirpstackUrl = b;
    },
    setUserAdmin(state, b) {
        state.admin = b;
    },
    setCurrentDevice(state, b) {
      state.currentDevice = b;
    },


}


export const actions = {

}