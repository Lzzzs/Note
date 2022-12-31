<template>
  <div class="note-content" ref="contentRef">
    <!-- <el-scrollbar class="scrollbar"> -->
    <div class="scrollbar-wrap">
      <el-skeleton :loading="loading" animated>
        <template v-if="contentData">
          <div class="header">{{ contentData.title }}</div>
          <div class="note-detail">
            <span>{{ contentData.tag }}</span>
            <span>{{ contentData.createdTime }}</span>
            <span :class="isLike">
              {{ contentData.likeNum }}
              <i
                class="iconfont icon-dianzan dianzan"
                @click="handleDianZan"
              ></i>
            </span>
            <span :class="isCollect">
              {{ contentData.collectNum }}
              <i
                class="iconfont icon-jiaxingshoucang jiaxingshoucang"
                @click="handleCollect"
              ></i>
            </span>
          </div>
          <div class="author">
            <el-avatar :size="28" :src="contentData.user.avatarUrl">
            </el-avatar>
            <span>{{ contentData.user.userid }}</span>
            <div class="grade">LV 1</div>
          </div>
        </template>
        <template slot="template">
          <div style="text-align: center">
            <el-skeleton-item
              class="note-content-skeleton-title"
              variant="h1"
            />
            <div class="note-content-skeleton-note-detail">
              <el-skeleton-item
                variant="text"
                style="margin-right: 20px; width: 50px"
              />
              <el-skeleton-item
                variant="text"
                style="margin-right: 20px; width: 150px"
              />
              <el-skeleton-item
                variant="text"
                style="margin-right: 20px; width: 30px"
              />
              <el-skeleton-item
                variant="text"
                style="margin-right: 20px; width: 30px"
              />
            </div>
            <div class="note-content-skeleton-author">
              <el-skeleton-item
                variant="image"
                style="margin-right: 10px; width: 32px; height: 32px"
              />
              <el-skeleton-item
                variant="text"
                style="margin-right: 5px; width: 60px"
              />
              <el-skeleton-item variant="text" style="width: 40px" />
            </div>
          </div>
        </template>
      </el-skeleton>

      <div class="notes"><Note :contentText="contentText" /></div>

      <div class="like">
        <i
          class="iconfont icon-dianzan dianzan"
          :class="isLike"
          @click="handleDianZan"
        ></i>
        <span v-if="!isLike">Click Here if You Like It</span>
        <span v-else>Liked</span>
      </div>
      <div class="comment">
        <NoteCommentContainer />
      </div>
    </div>
    <!-- </el-scrollbar> -->
  </div>
</template>

<script>
import NoteCommentContainer from './NoteCommentContainer.vue';
import Note from './Note.vue';
import {
  insertDianzanOne,
  insertCollectOne,
  deleteDianzanOne,
  deleteCollectOne,
} from '@/network/note/index.js';

export default {
  props: {
    contentData: {
      type: Object,
      default: () => {},
    },
  },
  data() {
    return {
      loading: true,
    };
  },
  components: { NoteCommentContainer, Note },
  computed: {
    contentText() {
      return this.contentData ? this.contentData.content : '';
    },
    isLike() {
      if (!this.contentData) return; // 没有数据 直接return
      return this.contentData.relation.isLike === 1 ? 'active' : '';
    },
    isCollect() {
      return this.contentData.relation.isCollect === 1 ? 'active' : '';
    },
  },
  methods: {
    handleDianZan() {
      if (!this.isLogin()) {
        this.$message.warning('请登录之后再进行操作');
        return;
      }
      if (this.isLike === '') {
        // 当前用户还未给这篇笔记点过赞
        insertDianzanOne(this.getDianZanDto()).then((res) => {
          console.log(res);
          // 刷新数据
          this.reloadGetData();
        });
      } else {
        // 当前用户已经给这篇笔记点过赞
        deleteDianzanOne(this.getDianZanDto()).then((res) => {
          console.log(res);
          // 刷新数据
          this.reloadGetData();
        });
      }
    },
    handleCollect() {
      if (!this.isLogin()) {
        this.$message.warning('请登录之后再进行操作');
        return;
      }
      if (this.isCollect === '') {
        // 当前用户还未给这篇笔记点过赞
        insertCollectOne(this.getDianZanDto()).then((res) => {
          console.log(res);
          // 刷新数据
          this.reloadGetData();
        });
      } else {
        // 当前用户已经给这篇笔记点过赞
        deleteCollectOne(this.getDianZanDto()).then((res) => {
          console.log(res);
          // 刷新数据
          this.reloadGetData();
        });
      }
    },
    isLogin() {
      return this.$store.state.userInfo !== null;
    },
    // 父组件重新拿数据
    reloadGetData() {
      this.$emit('reloadGetData');
    },
    getDianZanDto() {
      const obj = {
        userId: this.$store.state.userInfo.userid,
        noteId: this.$route.query.id,
      };
      return obj;
    },
  },
  watch: {
    contentData() {
      // 防止页面闪动
      setTimeout(() => {
        this.loading = false;
      }, 300);
    },
  },
};
</script>

<style lang="less">
.note-content {
  flex: 1;
  background: #fff;
  border-radius: 5px;
  border: 1px solid #dcdcdc;
  border-bottom: none;
  // 解决flex：1被撑开导致滚动条显示异常
  // overflow-y: hidden;

  // .scrollbar {
  // height: 100%;

  // 隐藏横向滚动条
  // & > .el-scrollbar__wrap {
  //   overflow-x: hidden;
  // }

  .scrollbar-wrap {
    padding: 20px 90px;

    .note-content-skeleton-title {
      width: 40%;
      margin: auto;
      margin-bottom: 10px;
    }
    .note-content-skeleton-note-detail {
      display: flex;
      justify-content: flex-start;
    }
    .note-content-skeleton-author {
      display: flex;
      justify-content: flex-start;
      align-items: center;
      margin-top: 10px;
    }

    .header {
      font-size: 20px;
      text-align: center;
      margin-bottom: 10px;
    }

    .note-detail {
      color: #b7b7b7;
      font-size: 14px;
      display: flex;
      align-items: center;
      // padding-left: 20px;

      .active {
        color: #409eff;
      }

      & > span {
        margin-right: 20px;

        .dianzan {
          font-size: 14px;
          user-select: none;
          &:hover {
            cursor: pointer;
          }
        }

        .jiaxingshoucang {
          font-size: 14px;
          font-weight: bold;
          user-select: none;
          &:hover {
            cursor: pointer;
          }
        }
      }
    }

    .author {
      display: flex;
      align-items: center;
      margin: 10px 0px;

      & > span {
        font-size: 13px;
        margin: 0px 5px;
      }

      .grade {
        background: #ea3869;
        color: #fff;
        font-size: 12px;
        border-radius: 2px;
        padding: 2px 1px;
      }
    }

    .like {
      display: flex;
      align-items: center;
      justify-content: center;
      padding: 8px 0px;

      color: #bbbbbb;
      .active {
        color: #409eff;
      }
      .dianzan {
        font-size: 20px;
        margin-right: 20px;
        user-select: none;
        &:hover {
          cursor: pointer;
        }
      }
      font-size: 13px;
    }
    .comment {
      background: #fafbfd;
    }
  }
  // }
}
</style>
