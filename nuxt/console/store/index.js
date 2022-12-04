export const state = () => ({
    chirpstackBearer:''
})

export const getters = {
  getChirpstackBearer(state) {
    return state.chirpstackBearer;
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
        console.log("set cBearer to",b)
        state.getChirpstackBearer = b;
    }


}


export const actions = {

}