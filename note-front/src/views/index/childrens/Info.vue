<template>
  <div class="info">
    <div class="info-header">
      <div
        class="info-header-list"
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
    <InfoBody
      @clearTagValue="handleClearTagValue"
      :tagValue="currentTag"
      ref="infoBodyRef"
    />
  </div>
</template>

<script>
import InfoBody from "./InfoBody.vue";
export default {
  name: "info",
  data() {
    return {
      currentTag: "",
      infoList: ["HTML", "MySQL", "PHP", "Java", "Python", "More"],
      moreList: ["JavaScript", "NodeJs", "Go"],
      isShowMore: false,
    };
  },
  components: { InfoBody },
  methods: {
    selectTagValue(currentTag) {
      if (currentTag == "More") return;
      this.currentTag = currentTag;
    },
    handleClearTagValue() {
      // 当搜索时，清除tag
      this.currentTag = "";
      console.log(123);
    },
    handleMouseEnter(item) {
      if (item != "More") return;
      this.isShowMore = true;
    },
    handleMouseLeave(item) {
      if (item != "More") return;
      this.isShowMore = false;
    },
  },
};
</script>

<style lang="less" scoped>
.info {
  display: flex;
  height: 100%;
  flex-direction: column;

  .info-header {
    padding: 30px 0px;
    display: flex;
    justify-content: center;

    .info-header-list {
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
}
</style>
