export const state = () => ({
    chirpstackBearer:'',
    consoleBearer:'',
    currentTenant:'',
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

  loggedInUser(state) {
    return state.auth.user
  }

}

export const mutations = {

    setChirpstackBearer(state, b) {
        state.getChirpstackBearer = b;
    },
    setConsoleBearer(state, b) {
        state.getConsoleBearer = b;
    },
    setCurrentTenant(state, b) {
        state.currentTenant = b;
    }

}


export const actions = {

}