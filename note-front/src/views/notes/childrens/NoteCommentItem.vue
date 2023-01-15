<template>
  <div class="comment-item">
    <div class="avatar">
      <el-avatar :size="imgSize" :src="itemData.avatarUrl"> </el-avatar>
    </div>
    <div class="user-info">
      <div :class="isChild ? 'user-name child' : 'user-name'">
        <span>
          {{ itemData.userId }}
        </span>
        <span class="grade">LV {{ itemData.grade }}</span>
      </div>

      <div class="comment" v-if="!isChild">
        Comments：
        {{ itemData.commentContent }}
      </div>
      <div class="comment" v-else>
        Reply <span style="color: #79c9fc">{{ itemData.replyUserName }}</span
        >：
        {{ itemData.commentContent }}
      </div>

      <div class="other-info">
        <span class="time">{{ itemData.createdTime }}</span>
        <span class="dianzan" :class="isLiked">
          <i class="iconfont icon-dianzan dianzan" @click="handleDianzan"> </i>
          {{ itemLike ? itemLike.likeNum : '' }}
        </span>
        <i
          class="el-icon-delete delete-icon"
          v-if="isDelete"
          @click="deleteComment"
        ></i>
        <span class="reply" @click="handleReply">Reply</span>
      </div>
    </div>
  </div>
</template>

<script>
import {
  addDianzanComment,
  reduceDianzanComment,
  deleteCommentById
} from '@/network/note/index.js'
export default {
  props: ['itemData', 'itemLike', 'imgSize'],
  data() {
    return {}
  },
  computed: {
    /**
     * 该item是否是子评论
     */
    isChild() {
      return this.itemData.level != 0
    },
    isLiked() {
      if (!this.itemLike) return ''
      return this.itemLike.isLiked == 1 ? 'active' : ''
    },
    isDelete() {
      if (!this.$store.state.userInfo) {
        return false
      }
      return this.itemData.userId === this.$store.state.userInfo.userid
    }
  },
  methods: {
    handleDianzan() {
      if (!this.isLogin()) {
        this.$message.warning('请登录之后再进行操作')
        return
      }
      if (this.itemLike.isLiked === 0) {
        // 当前用户还未给这篇笔记点过赞
        addDianzanComment(this.getDianZanDto()).then((res) => {
          console.log(res)
          // 刷新数据
          this.reloadGetData()
        })
      } else {
        // 当前用户已经给这篇笔记点过赞
        reduceDianzanComment(this.getDianZanDto()).then((res) => {
          console.log(res)
          // 刷新数据
          this.reloadGetData()
        })
      }
    },
    handleReply() {
      this.$emit('replyUser', {
        id: this.itemData.id,
        userName: this.itemData.userId,
        parentId: this.itemData.parentId,
        noteId: this.itemData.noteId
      })
    },
    reloadGetData() {
      this.$emit('reloadLikeData')
    },
    isLogin() {
      return this.$store.state.userInfo !== null
    },
    getDianZanDto() {
      const obj = {
        userId: this.$store.state.userInfo.userid,
        commentId: this.itemData.id
      }
      return obj
    },
    deleteComment() {
      deleteCommentById({ id: this.itemData.id }).then(() => {
        this.$message.success('delete success')
        this.$emit('reloadComment')
      })
    }
  }
}
</script>

<style lang="less" scoped>
.comment-item {
  display: flex;
  justify-content: left;
  margin-bottom: 35px;
  .avatar {
    margin-right: 10px;
  }
  .user-info {
    display: flex;
    flex-direction: column;
    width: 80%;

    .user-name {
      margin-bottom: 15px;
      font-weight: bold;

      .grade {
        color: #fff;
        font-size: 12px;
        background: #616ee8;
        border-radius: 2px;
        padding: 1px;
      }
    }

    .child {
      margin-bottom: 8px;
    }

    .comment {
      margin-bottom: 5px;
      word-wrap: break-word;
    }

    .other-info {
      color: #d1d0d2;
      font-size: 12px;
      .dianzan {
        user-select: none;
        i {
          &:hover {
            cursor: pointer;
          }
        }
      }
      .delete-icon {
        cursor: pointer;
        margin-left: 5px;
        user-select: none;
      }

      .active {
        color: #409eff;
      }
      .time {
        margin-right: 10px;
      }
      .reply {
        margin-left: 10px;

        &:hover {
          text-decoration: underline;
          cursor: pointer;
          user-select: none;
        }
      }
    }
  }
}
</style>
