<template>
  <div class="index">
    <el-container class="container" direction="vertical">
      <Header />
      <el-main class="main">
        <!-- 只缓存info组件，因为info的状态需要被保存，用户从别的页面返回之后，还是原来的搜索结果 -->
        <keep-alive include="info">
          <router-view />
        </keep-alive>
      </el-main>
      <Footer />
    </el-container>
  </div>
</template>

<script>
import Header from "../../components/Header.vue";
import Footer from "../../components/Footer.vue";
export default {
  components: { Header, Footer },
  created() {
    /**
     * 根据localStorage解决vuex刷新数据丢失问题
     */
    const { userInfo, token } = this.$store.state;
    if (userInfo == null) {
      this.$store.commit(
        "SET_USER_INFO",
        JSON.parse(localStorage.getItem("userInfo"))
      );
    }
    if (token == null) {
      this.$store.commit(
        "SET_TOKEN",
        JSON.parse(localStorage.getItem("token"))
      );
    }
  },
};
</script>

<style lang="less" scoped>
.index,
.container {
  width: 100%;
  height: 100%;
}
.main {
  height: calc(100% - 50px - 60px);
  width: 100%;
  padding: 0px 50px;
  background: #fafbfd;
  // overflow-y: hidden;
}
</style>
