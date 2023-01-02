<template>
  <div class="note-container">
    <div class="header">
      <div class="currentGroup">Group/HTML</div>
      <TagSelect @updateTag="updateCurrentTag" />
    </div>

    <div class="notes">
      <el-empty :image-size="300" v-if="notesData.length == 0"></el-empty>
      <div class="notes" v-else>
        <NoteItem v-for="item in notesData" :key="item.id" :itemData="item" />
      </div>
    </div>

    <div class="page">
      <el-pagination
        background
        layout="prev, pager, next"
        :hide-on-single-page="isSinglePage"
        :page-size="pageSize"
        :total="total"
        :current-page="currentPage"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import NoteItem from '../../index/childrens/NoteItem.vue'
import TagSelect from '@/components/TagSelect.vue'
export default {
  data() {
    return {
      currentTag: '',
      notesData: [],
      total: 0,
      pageSize: 6, // 一页显示6个
      currentPage: 1 // 当前页
    }
  },
  components: { TagSelect, NoteItem },
  methods: {
    updateCurrentTag(currentTag) {
      this.currentTag = currentTag
    }
  },
  computed: {
    // 只有一页时是否隐藏分页
    isSinglePage() {
      return this.total <= this.pageSize
    }
  }
}
</script>

<style lang="less" scoped>
.note-container {
  flex: 1;

  .header {
    padding: 5px 20px;
    margin-bottom: 18px;
  }

  .notes {
    padding: 0px 50px;
  }
}
</style>
