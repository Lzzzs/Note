<template>
  <div class="self-notes">
    <div class="title">My Notes</div>

    <div class="delete-notes-btn">
      <el-button type="primary" @click="deleteNotes">delete notes</el-button>
    </div>
    <div class="notes-select" v-if="!isEmpty">
      <NoteItem
        v-for="(item, index) in notesList"
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
import NoteItem from './NoteItem.vue'
import { getUserAllNotesByUserId, deleteNotes } from '@/network/self/index.js'
export default {
  data() {
    return {
      notesList: [],
      total: 0,
      pageSize: 6, // 一页显示6个
      currentPage: 1 // 当前页
    }
  },
  components: { NoteItem },
  created() {
    this.getData()
  },
  computed: {
    // 只有一页时是否隐藏分页
    isSinglePage() {
      return this.total <= this.pageSize
    },
    isEmpty() {
      return this.total == 0
    }
  },
  methods: {
    getData() {
      getUserAllNotesByUserId(
        this.currentPage,
        this.pageSize,
        this.$store.state.userInfo.userid
      ).then(({ data }) => {
        this.notesList = data.rows
        this.total = data.total
      })
    },
    updateCurrentPage(currentPage) {
      this.currentPage = currentPage
      this.getData()
    },
    deleteNotes() {
      const notes = this.$store.state.self.deleteNotes
      if (notes.length === 0) {
        this.$message.warning('请选择要删除的笔记')
        return
      }

      this.$confirm('确定删除已选择的笔记吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteNotes({ notes }).then(() => {
          this.$message.success('删除成功')
          this.getData()
        })
      })
    }
  }
}
</script>

<style lang="less" scoped>
.self-notes {
  padding: 40px 60px 0px 30px;
  display: flex;

  position: relative;

  .title {
    font-size: 25px;
    font-weight: 900;
    padding-right: 80px;
    padding-top: 30px;
  }

  .delete-notes-btn {
    position: absolute;
    top: 0px;
    right: 0px;
    padding: 10px 15px 0px 0px;
  }

  .notes-select {
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
