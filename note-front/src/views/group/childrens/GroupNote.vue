<template>
  <div class="note-container">
    <div class="header">
      <div class="currentGroup">{{ groupName }}{{ this.currentTag }}</div>
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
        @current-change="updateCurrentPage"
      >
      </el-pagination>
    </div>
  </div>
</template>

<script>
import NoteItem from '../../index/childrens/NoteItem.vue'
import TagSelect from '@/components/TagSelect.vue'
import { getOrganizeNotes } from '@/network/group'

export default {
  data() {
    return {
      currentTag: '',
      notesData: [],
      total: 0,
      pageSize: 6, // 一页显示6个
      currentPage: 1, // 当前页
      groupName: ''
    }
  },
  mounted() {
    this.init()
  },
  beforeDestroy() {
    this.$bus.$off('updated-body')
    this.$bus.$off('getCurrentGroupName')
  },
  components: { TagSelect, NoteItem },
  methods: {
    init() {
      // 初始化获取笔记
      this.getNotes(this.currentPage, this.pageSize)

      this.$bus.$on('updated-body', () => {
        this.getNotes(this.currentPage, this.pageSize)
      })
      this.$bus.$on('getCurrentGroupName', (groupName) => {
        this.groupName = groupName + '/'
      })
    },
    getNotes(page, size, tag) {
      const id = this.$route.query.id
      if (!id) return

      // 路由保存状态
      this.$router.push({
        path: `/group`,
        query: {
          id,
          page,
          tag
        }
      })
      getOrganizeNotes(id, page, size, tag).then((res) => {
        this.transitionTimeFormat(res.data.rows)
        this.notesData = res.data.rows
        this.total = res.data.total
      })
    },
    // 将时间的时分秒去掉，只留下年月日
    transitionTimeFormat(rows) {
      rows.forEach((row) => {
        row.createdTime = row.createdTime.split(' ')[0]
      })
    },
    initPageConfig() {
      this.pageSize = 6
      this.currentPage = 1
    },
    updateCurrentTag(currentTag) {
      this.currentTag = currentTag
    },
    updateCurrentPage(currentPage) {
      this.currentPage = currentPage
      this.getNotes(this.currentPage, this.pageSize, this.tagValue)
    }
  },
  computed: {
    // 只有一页时是否隐藏分页
    isSinglePage() {
      return this.total <= this.pageSize
    }
  },
  watch: {
    currentTag(newTagValue) {
      this.initPageConfig() // 切换tag的时候 把pageSize和currentPage初始化
      this.getNotes(this.currentPage, this.pageSize, newTagValue)
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

  .page {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 20px 0px;
  }
}
</style>
