<template>
  <div class="notes">
    <NoteBreadcrumb
      :info="
        contentData ? { tag: contentData.tag, title: contentData.title } : {}
      "
    />

    <NoteContent :contentData="contentData" @reloadGetData="reloadGetData" />
  </div>
</template>

<script>
import NoteBreadcrumb from "./childrens/NoteBreadcrumb.vue";
import NoteContent from "./childrens/NoteContent.vue";
import {
  getNoteByNoteId,
  getUserNoteAllRelation,
} from "@/network/note/index.js";

export default {
  data() {
    return {
      contentData: null,
    };
  },
  components: { NoteBreadcrumb, NoteContent },
  created() {
    this.getData();
  },
  methods: {
    async getData() {
      const { data: noteData } = await getNoteByNoteId(this.$route.query.id);

      if (this.$store.state.userInfo === null) {
        // 用户未登录 关系全部都是无
        noteData.relation = { isLike: false, isCollect: false };
      } else {
        // 用户登录，获取对应的关系
        const { data: relation } = await getUserNoteAllRelation(
          this.$store.state.userInfo.userid,
          noteData.id
        );
        noteData.relation = relation;
      }

      this.contentData = noteData;
    },
    reloadGetData() {
      this.getData();
    },
  },
};
</script>

<style lang="less" scoped>
.notes {
  display: flex;
  flex-direction: column;
  padding: 10px 120px 0px;
  // height: calc(100% - 10px);
  // overflow-y: hidden;
}
</style>
