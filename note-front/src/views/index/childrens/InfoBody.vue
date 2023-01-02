<template>
  <div class="info-body">
    <div class="tag-title">{{ titleValue }}</div>

    <el-empty :image-size="300" v-if="notesData.length == 0"></el-empty>
    <div class="notes" v-else>
      <NoteItem v-for="item in notesData" :key="item.id" :itemData="item" />
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
import NoteItem from './NoteItem.vue'
import { getNotes, searchByLike } from '@/network/note/index.js'
export default {
  props: {
    tagValue: String
  },
  data() {
    return {
      notesData: [],
      total: 0,
      pageSize: 6, // 一页显示6个
      currentPage: 1, // 当前页
      isSearchIng: false, // 当前是否是搜索页的数据
      searchValue: ''
    }
  },
  components: { NoteItem },
  mounted() {
    // 初始化获取笔记
    this.getNotes(this.currentPage, this.pageSize)
    // 通过事件总线获取search的值
    this.$bus.$on('searchValue', (searchValue) => {
      this.searchValue = searchValue
      this.handleSearch(1)
    })
  },
  activated() {
    // 重新获取笔记
    this.getNotes(this.currentPage, this.pageSize)
  },
  beforeDestroy() {
    // 组件销毁前移除该事件
    this.$bus.$off('searchValue')
  },
  computed: {
    // 只有一页时是否隐藏分页
    isSinglePage() {
      return this.total <= this.pageSize
    },
    titleValue() {
      let str = ''
      if (this.searchValue != '') {
        str = `Current Search Value: ${this.searchValue}`
      } else {
        str = this.tagValue
      }
      return str
    }
  },
  methods: {
    getNotes(page, size, tag) {
      this.isSearchIng = false

      // 路由保存状态
      this.$router.push({
        path: '/info',
        query: {
          page,
          tag
        }
      })

      getNotes(page, size, tag).then((res) => {
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
    updateCurrentPage(currentPage) {
      this.currentPage = currentPage
      if (this.isSearchIng) {
        this.handleSearch(currentPage)
      } else {
        this.getNotes(this.currentPage, this.pageSize, this.tagValue)
      }
    },
    initPageConfig() {
      this.pageSize = 6
      this.currentPage = 1
    },
    handleSearch(currentPage) {
      this.isSearchIng = true
      // 清除tag
      this.$emit('clearTagValue')

      this.$router.push({
        path: '/info',
        query: {
          page: currentPage,
          searchValue: this.searchValue
        }
      })

      searchByLike(currentPage, this.pageSize, this.searchValue).then((res) => {
        if (res.data != null) {
          this.transitionTimeFormat(res.data.rows)
          this.notesData = res.data.rows
          this.total = res.data.total
          console.log(res)
        } else {
          this.notesData = []
          this.total = 0
        }
      })
    }
  },
  watch: {
    tagValue(newTagValue) {
      if (newTagValue == '') return // 搜索时会清除tag，这个时候不让tag进入查询中

      console.log(this.$route.query.id)
      this.initPageConfig() // 切换tag的时候 把pageSize和currentPage初始化
      this.getNotes(this.currentPage, this.pageSize, newTagValue)

      //  切换tag的时候先清空searchValue，要不然会影响到titleValue的判断
      this.searchValue = ''
    }
  }
}
</script>

<style lang="less" scoped>
.info-body {
  flex: 1;
  background: #fff;
  border: 1px solid #dcdcdc;
  border-bottom: none;
  border-radius: 5px;

  .tag-title {
    font-size: 22px;
    padding: 20px 30px 0px;
    height: 26px;
  }

  .notes {
    padding: 0px 100px;
  }

  .page {
    display: flex;
    align-items: center;
    justify-content: center;
    padding: 20px 0px;
  }
}
</style>
