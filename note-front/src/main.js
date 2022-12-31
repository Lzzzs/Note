import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import Router from "vue-router";

Vue.config.productionTip = false;

// elementUI register
import "@/global/ElementUIRegister/index.js";

// global exception
import handlerGlobalError from "@/global/Exception";
handlerGlobalError(Vue);

// mavonEditor
import mavonEditor from "mavon-editor";
import "mavon-editor/dist/css/index.css";
Vue.use(mavonEditor);

import "normalize.css";

import "@/assets/iconfont/iconfont.css";

// 路由重复点击报错
const originalPush = Router.prototype.push;
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch((err) => err);
};

new Vue({
  router,
  store,
  render: (h) => h(App),
  beforeCreate() {
    Vue.prototype.$bus = this; // 安装事件总线
  },
}).$mount("#app");
