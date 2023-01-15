<template>
  <div class="comment-container">
    <div class="self-comment" v-if="isLogin">
      <div>
        <el-avatar :size="60" :src="currentUserAvatar"> </el-avatar>
      </div>
      <div style="width: 60%">
        <el-input
          ref="postCommentInputRef"
          :placeholder="replyPlaceholder"
          v-model="commentContent"
        >
        </el-input>
      </div>
      <div>
        <el-button type="primary" size="medium" @click="postComment"
          >Post Comment</el-button
        >
      </div>
    </div>
    <div v-else class="login-tip">
      <el-link type="primary" :underline="false" href="/login"
        >登录之后才能进行评论，点我跳转登录页面</el-link
      >
    </div>
    <div class="comments">
      <div v-if="commentData.length != 0">
        <NoteCommentList
          v-for="(item, index) in commentData"
          :key="index"
          :listData="item"
          :likeData="getLikeData(item)"
          @reloadLikeData="reloadLikeData"
          @replyUser="replyUser"
          @reloadComment="reloadComment"
        />
      </div>
      <div v-else style="text-align: center">当前笔记下还未有评论~</div>
    </div>
  </div>
</template>

<script>
import NoteCommentList from './NoteCommentList.vue'
import {
  getCommentByNoteId,
  getCommentLikeRelation,
  saveComment
} from '@/network/note/index.js'
export default {
  data() {
    return {
      commentContent: '',
      commentData: [],
      likeRelation: [],
      isReply: false,
      currentReplyData: {}
    }
  },
  created() {
    this.getData()
  },
  computed: {
    // 根据评论id去匹配对应的like
    getLikeData() {
      if (!this.likeRelation) return () => ({})
      return (item) => {
        return this.likeRelation.find((likeItem) => likeItem.id === item.id)
      }
    },
    replyPlaceholder() {
      return this.isReply
        ? `Reply @${this.currentReplyData.userName}`
        : 'Post A Friendly Comment'
    },
    isLogin() {
      return this.$store.state.userInfo
    },
    currentUserAvatar() {
      if (!this.$store.state.userInfo)
        return 'https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png'
      return this.$store.state.userInfo.avatarUrl
    }
  },
  methods: {
    async getData() {
      const { data } = await getCommentByNoteId(this.$route.query.id)
      this.commentData = data
      const { data: relationData } = await this.getLikeRelation()
      this.likeRelation = relationData
    },
    getLikeRelation() {
      return getCommentLikeRelation(
        this.$store.state.userInfo?.userid,
        this.$route.query.id
      )
    },
    postComment() {
      let commentData = {
        userId: this.$store.state.userInfo.userid,
        commentContent: this.commentContent,
        noteId: this.$route.query.id
      }
      if (this.commentContent.length > 130) {
        this.$message.warning('评论内容长度不能超过130个字符')
        return
      }
      if (this.commentContent.trim().length == 0) {
        this.$message.warning('不能发空白内容')
        return
      }
      if (this.isReply) {
        commentData.parentId =
          this.currentReplyData.parentId == 0
            ? this.currentReplyData.id
            : this.currentReplyData.parentId
        commentData.replyId = this.currentReplyData.id
      } else {
        commentData.parentId = 0
        commentData.replyId = 0
      }
      saveComment(commentData).then(() => {
        this.isReply = false
        this.commentContent = ''
        this.$message.success('发布评论成功')
        this.getData()
      })
    },
    async reloadLikeData() {
      const { data: relationData } = await this.getLikeRelation()
      relationData.forEach((item, index) => {
        this.$set(this.likeRelation, index, item)
      })
    },
    replyUser(replyCommentData) {
      if (!this.$store.state.userInfo) {
        this.$message.warning('请登录之后再进行操作')
        return
      }

      if (this.commentContent) this.commentContent = ''
      this.currentReplyData = replyCommentData
      this.isReply = true
      this.$refs.postCommentInputRef.focus()
    },
    reloadComment() {
      this.getData()
    }
  },
  components: { NoteCommentList }
}
</script>

<style lang="less" scoped>
.comment-container {
  padding: 40px 20px;
  .self-comment {
    display: flex;
    justify-content: space-between;
    align-items: center;
    margin-bottom: 15px;
  }
  .login-tip {
    height: 30px;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .comments {
    border-top: 1px solid #d9d9da;
    padding-top: 40px;
    margin-left: 30px;
  }
}
</style>
