<template>
  <div class="note-item">
    <div v-show="!isLoading">
      <div class="note-img">
        <el-image
          :src="itemData.noteImg"
          class="img"
          fit="cover"
          @click="handleGetNote"
          @load="handleLoad"
        >
          <div slot="error" class="image-slot">
            <i class="el-icon-picture-outline"></i>
          </div>
        </el-image>
      </div>
    </div>

    <el-skeleton :loading="isLoading" animated>
      <template> </template>
      <template slot="template">
        <div class="skeleton">
          <div style="padding: 20px 20px 10px 20px">
            <el-skeleton-item variant="image" class="skeleton-img" />
          </div>
        </div>
      </template>
    </el-skeleton>
  </div>
</template>

<script>
export default {
  props: ["itemData"],
  data() {
    return {
      isLoading: true,
    };
  },
  beforeCreate() {
    this.isLoading = true;
  },
  methods: {
    handleGetNote() {
      this.$router.push({
        path: "/notes",
        query: { id: this.itemData.noteId },
      });
    },
    // 图片加载完成回调
    handleLoad() {
      // 延时调用，防止页面抖动
      setTimeout(() => {
        this.isLoading = false;
      }, 500);
    },
  },
};
</script>

<style lang="less" scoped>
.note-item {
  width: calc(100% / 3);
  display: inline-block;
  .note-img {
    display: flex;
    justify-content: center;
    padding: 20px 20px 10px 20px;
    .img {
      width: 80%;
      height: 300px;
      border-radius: 5px;
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      &:hover {
        cursor: pointer;
      }
    }
  }

  .skeleton {
    text-align: center;
    .skeleton-img {
      width: 80%;
      height: 300px;
      margin: auto;
    }
  }
}
</style>
