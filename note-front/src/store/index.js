import Vue from 'vue'
import Vuex from 'vuex'
import self from './modules/self'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    userInfo: null,
    token: null
  },
  mutations: {
    SET_USER_INFO(state, newUserInfo) {
      state.userInfo = newUserInfo
    },
    SET_TOKEN(state, newToken) {
      state.token = newToken
    }
  },
  modules: { self }
})
