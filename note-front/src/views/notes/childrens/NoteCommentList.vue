<template>
  <div class="comment-list">
    <NoteCommentItem
      :itemData="listData"
      :itemLike="likeData"
      :imgSize="imgInitSize"
      @reloadLikeData="reloadLikeData"
      @replyUser="replyUser"
      @reloadComment="reloadComment"
    />
    <div class="children-comment" v-if="existChildren">
      <NoteCommentItem
        v-for="(item, index) in listData.childrens"
        :key="index"
        :itemData="item"
        :itemLike="getLikeItem(item)"
        :imgSize="imgInitSize - 8"
        class="children-comment-item"
        @reloadLikeData="reloadLikeData"
        @replyUser="replyUser"
        @reloadComment="reloadComment"
      />
    </div>
  </div>
</template>

<script>
import NoteCommentItem from './NoteCommentItem.vue'

export default {
  props: ['listData', 'likeData'],
  data() {
    return {
      imgInitSize: 50
    }
  },
  components: { NoteCommentItem },
  computed: {
    existChildren() {
      return this.listData && this.listData.childrens.length != 0
    },
    // 根据评论id去匹配对应的like
    getLikeItem() {
      if (!this.likeData) return () => ({})
      return (item) => {
        return this.likeData.childrens.find(
          (childrenLikeItem) => childrenLikeItem.id === item.id
        )
      }
    }
  },
  methods: {
    reloadLikeData() {
      this.$emit('reloadLikeData')
    },
    replyUser(replyCommentData) {
      this.$emit('replyUser', replyCommentData)
    },
    reloadComment() {
      this.$emit('reloadComment')
    }
  }
}
</script>

<style lang="less" scoped>
.comment-list {
  font-size: 14px;
  .children-comment {
    padding-left: 60px;
    font-size: 13px;
  }
}
</style>
