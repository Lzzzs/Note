<template>
  <div class="tag-select">
    <div
      class="tag-list"
      v-for="(item, index) of infoList"
      :key="index"
      @click="selectTagValue(item)"
      @mouseenter="handleMouseEnter(item)"
      @mouseleave="handleMouseLeave(item)"
    >
      {{ item }}
      <div class="dropdown" v-if="item == 'More' && isShowMore">
        <div v-for="item in moreList" :key="item" class="dropdown-list">
          {{ item }}
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      currentTag: '',
      infoList: ['HTML', 'MySQL', 'PHP', 'Java', 'Python', 'More'],
      moreList: ['JavaScript', 'NodeJs', 'Go'],
      isShowMore: false
    }
  },
  methods: {
    selectTagValue(currentTag) {
      if (currentTag == 'More') return
      this.currentTag = currentTag
      this.$emit('updateTag', currentTag)
    },
    handleMouseEnter(item) {
      if (item != 'More') return
      this.isShowMore = true
    },
    handleMouseLeave(item) {
      if (item != 'More') return
      this.isShowMore = false
    }
  }
}
</script>

<style lang="less" scoped>
.tag-select {
  display: flex;
  justify-content: center;

  .tag-list {
    position: relative;
    background: #fff;
    padding: 7px 25px;
    border: 1px solid #666768;
    border-radius: 30px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 37%), 0 0 6px rgba(0, 0, 0, 0.04);
    margin-right: 10px;
    font-size: 15px;
    user-select: none;

    &:hover {
      cursor: pointer;
    }
  }

  .dropdown {
    position: absolute;
    z-index: 9999;
    top: 33px;
    right: -14px;
    padding: 10px 0;
    background-color: #fff;
    border: 1px solid #ebeef5;
    border-radius: 4px;

    .dropdown-list {
      list-style: none;
      line-height: 36px;
      padding: 0 20px;
      margin: 0;
      font-size: 14px;
      color: #606266;
      cursor: pointer;
      outline: none;

      &:hover {
        background-color: #ecf5ff;
        color: #66b1ff;
      }
    }
  }
}
</style>
