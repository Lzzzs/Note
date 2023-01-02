import store from '@/store'

export function logout() {
  store.state.userInfo = null
  store.state.token = null
  localStorage.getItem('userInfo') && localStorage.removeItem('userInfo')
  localStorage.getItem('token') && localStorage.removeItem('token')
  localStorage.getItem('refreshToken') &&
    localStorage.removeItem('refreshToken')
}

/**
 * 判断用户是否登录
 */
export function isLogin() {
  return localStorage.getItem('userInfo') && localStorage.getItem('token')
}

/**
 * 获取当前登录用户的userId
 */
export function getUserId() {
  return store.state.userInfo?.userid
}
