<template>
  <div class="self-collect">
    <div class="title">My Collect</div>
    <div class="collect-select" v-if="!isEmpty">
      <NoteItem
        v-for="(item, index) in collectList"
        :itemData="item"
        :key="index"
      />

      <div class="page">
        <el-pagination
          background
          layout="prev, pager, next"
          :hide-on-single-page="isSinglePage"
          :page-size="pageSize"
          :total="total"
          :current-page="currentPage"
          @current-change="updateCurrentPage"
        >
        </el-pagination>
      </div>
    </div>
    <el-empty style="flex: 1" :image-size="300" v-else></el-empty>
  </div>
</template>

<script>
import NoteItem from "./NoteItem.vue";
import { getUserAllCollectNotesByUserId } from "@/network/self/index.js";
export default {
  data() {
    return {
      collectList: [],
      total: 0,
      pageSize: 6, // 一页显示6个
      currentPage: 1, // 当前页
    };
  },
  components: { NoteItem },
  created() {
    this.getData();
  },
  computed: {
    // 只有一页时是否隐藏分页
    isSinglePage() {
      return this.total <= this.pageSize;
    },
    isEmpty() {
      return this.total == 0;
    },
  },
  methods: {
    getData() {
      getUserAllCollectNotesByUserId(
        this.currentPage,
        this.pageSize,
        this.$store.state.userInfo.userid
      ).then(({ data }) => {
        this.collectList = data.rows;
        this.total = data.total;
      });
    },
    updateCurrentPage(currentPage) {
      this.currentPage = currentPage;
      this.getData();
    },
  },
};
</script>

<style lang="less" scoped>
.self-collect {
  padding: 40px 60px 0px 30px;
  display: flex;

  .title {
    font-size: 25px;
    font-weight: 900;
    padding-right: 80px;
    padding-top: 30px;
  }

  .collect-select {
    flex: 1;
    display: flex;
    flex-wrap: wrap;

    .page {
      width: 100%;
      display: flex;
      justify-content: center;
      margin: 20px 0px;
    }
  }
}
</style>
