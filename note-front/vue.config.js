module.exports = {
  devServer: {
    proxy: {
      // 如果请求地址以/api打头,就出触发代理
      "/api": {
        target: "http://localhost:8888",
        changeOrigin: true,
        pathRewrite: {
          "^/api": "",
        },
      },
    },
  },
};
