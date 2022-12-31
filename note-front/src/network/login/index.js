import instance from "../index.js";

export function login(loginInfo) {
  return instance.post("/login", loginInfo);
}

export function register(registerInfo) {
  return instance.post("/register", registerInfo);
}

/**
 * 刷新token
 * @param {*} userInfo
 * @returns
 */
export function refreshTokenApi(userInfo) {
  return instance.post("/refresh-token", userInfo);
}
