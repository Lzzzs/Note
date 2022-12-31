const errorHandler = function (err, vm, info) {
  console.log(
    "-----------------------进入了全局异常捕获中-----------------------"
  );

  console.log("err", err);
  vm.$message.error("系统异常，请联系管理员");
  console.log("vm", vm);
  console.log("info", info);
};

export default function (vue) {
  vue.config.errorHandler = errorHandler;
  // this.$throw(err, vm, info)
  vue.prototype.$throw = errorHandler;
}
