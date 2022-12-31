<template>
  <div class="note-item">
    <!-- 实际要展示的内容只能放外面，放里面 skeleton内部实现是用v-if 
      那么image就永远没办法被渲染，那么load方法就不可能被回调，所以放外面先进行加载-->
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

      <div class="note-info">
        <div class="title" @click="handleGetNote">{{ itemData.title }}</div>
        <div class="user-info" @click="handleGetNote">
          {{ itemData.user.userid }} / {{ itemData.createdTime }}
        </div>
      </div>
    </div>
    <el-skeleton :loading="isLoading" animated>
      <template> </template>
      <template slot="template">
        <div class="skeleton">
          <div style="padding: 20px 20px 10px 20px">
            <el-skeleton-item variant="image" class="skeleton-img" />
          </div>
          <div class="skeleton-info">
            <el-skeleton-item variant="h3" style="width: 50%" />
            <div class="skeleton-text">
              <el-skeleton-item variant="text" style="width: 30%" />
              <el-skeleton-item variant="text" style="width: 30%" />
            </div>
          </div>
        </div>
      </template>
    </el-skeleton>
  </div>
</template>

<script>
export default {
  props: {
    itemData: {
      type: Object,
      default: () => {},
    },
  },
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
      this.$router.push({ path: "/notes", query: { id: this.itemData.id } });
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
    .skeleton-info {
      // padding: 14px;
      display: flex;
      flex-direction: column;
      align-items: center;

      .skeleton-text {
        display: flex;
        width: 100%;
        margin-top: 16px;
        height: 16px;
        justify-content: center;
      }
    }
  }

  .note-info {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    .title {
      margin-bottom: 5px;
      font-size: 18px;
      &:hover {
        cursor: pointer;
      }
    }
    .user-info {
      &:hover {
        cursor: pointer;
      }
    }
  }
}
</style>
