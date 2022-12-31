import axios from "axios";
import { Message } from "element-ui";
import codeMap from "./ResultCode";
import router from "@/router";
import { logout } from "@/utils/userUtil.js";
import { isJSON } from "@/utils/jsonUtil.js";
import { refreshTokenApi } from "./login/index";
import NProgress from "nprogress";
import "nprogress/nprogress.css";

NProgress.configure({ showSpinner: false }); // 关闭进度条圆环

const instance = axios.create({
  baseURL: process.env.VUE_APP_SERVER_URL,
  timeout: 10000,
});

/**
 * 根据localStorage中的token生成对应后端响应的请求头
 * @param {*} config
 */
function generateAuthorizationHeader(config) {
  const token = localStorage.getItem("token");
  if (token) {
    config.headers.Authorization = `Bearer ${JSON.parse(token)}`;
  }

  return config;
}

let isRefreshing = true;
let subscribers = [];

function handleExpiredToken(res) {
  // 刷新token的函数,这需要添加一个开关，防止重复请求
  if (isRefreshing) {
    refreshTokenRequst();
  }
  isRefreshing = false;

  // 因为请求体中有数据，然后token失效之后，收集这个失败的请求，这个时候请求体的数据就会变成json格式，会影响到接口
  if (isJSON(res.config.data)) {
    res.config.data = JSON.parse(res.config.data);
  }
  // 这个Promise函数很关键
  const retryOriginalRequest = new Promise((resolve) => {
    addSubscriber(() => {
      resolve(instance(res.config));
    });
  });
  return retryOriginalRequest;
}

// 遍历执行需要执行的函数
function onAccessTokenFetched() {
  subscribers.forEach((callback) => {
    callback();
  });
  subscribers = [];
}

// 收集需要执行的函数
function addSubscriber(callback) {
  subscribers.push(callback);
}

function refreshTokenRequst() {
  const refreshToken = JSON.parse(localStorage.getItem("refreshToken"));
  const userInfo = JSON.parse(localStorage.getItem("userInfo"));
  refreshTokenApi({
    loginInfo: {
      userId: userInfo.userid,
    },
    refreshToken: refreshToken,
  }).then((res) => {
    localStorage.setItem("refreshToken", JSON.stringify(res.data.refreshToken));
    localStorage.setItem("token", JSON.stringify(res.data.token));
    onAccessTokenFetched();
    isRefreshing = true;
  });
}

// request intercep
instance.interceptors.request.use(
  (config) => {
    // 开启进度条
    NProgress.start();
    // 添加请求头，里面放入与服务器约定好的格式
    return generateAuthorizationHeader(config);
  },
  (err) => {
    Message.error(err);

    return Promise.reject(err);
  }
);

function responseSuccessHandler(res) {
  // 关闭进度条
  NProgress.done();

  const code = res.data.code;
  const message = res.data.message;

  // 错误信息都从接口获取
  if (code != 200) {
    // 配置前端配置的err映射表
    if (codeMap.has(code)) {
      // [errMessage, to?]
      const [errMessage, to] = codeMap.get(code);
      Message.error(errMessage);

      // 如果需要跳转
      if (to != "") {
        if (to == "/login") {
          /**
           * 跳转前 先把localStorage中关于用户的信息先删除
           * 因为在router.beforeEach拦截了关于用户有localStorage的信息无法跳转登录和注册页面
           */
          logout();
        }
        router.push(to);
      }
      return Promise.reject(errMessage);
    }

    // token过期处理
    if (code === 70004) {
      return handleExpiredToken(res);
    }

    Message.error(message);
    // 返回reject 不进入到业务中的then函数回调中
    return Promise.reject(message);
  } else {
    return res.data;
  }
}

instance.interceptors.response.use(responseSuccessHandler, (err) => {
  // 关闭进度条
  NProgress.done();
  // 只有refrehToken过期才会来到这，token过期只会返回对应的响应码，refreshToken过期则是服务器响应
  if (err.response.status === 401) {
    Message.warning("登录态已过期，请重新登陆");
    /**
     * 跳转前 先把localStorage中关于用户的信息先删除
     * 因为在router.beforeEach拦截了关于用户有localStorage的信息无法跳转登录和注册页面
     */
    logout();
    router.push("/login");

    return Promise.reject(err);
  }
  let errMessage = err.response.data.message;
  if (err.response.data.message == undefined) {
    errMessage = "请检查网络是否连接";
  }
  Message.error(errMessage);
  return Promise.reject(err);
});

export default instance;
