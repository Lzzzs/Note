<template>
  <div class="info">
    <TagSelect @updateTag="updateTag" class="info-tag" />

    <InfoBody
      @clearTagValue="handleClearTagValue"
      :tagValue="currentTag"
      ref="infoBodyRef"
    />
  </div>
</template>

<script>
import InfoBody from './InfoBody.vue'
import TagSelect from '@/components/TagSelect.vue'
export default {
  name: 'info',
  data() {
    return {
      currentTag: ''
    }
  },
  components: { InfoBody, TagSelect },
  methods: {
    handleClearTagValue() {
      // 当搜索时，清除tag
      this.currentTag = ''
    },
    updateTag(currentTag) {
      this.currentTag = currentTag
    }
  }
}
</script>

<style lang="less" scoped>
.info {
  display: flex;
  height: 100%;
  flex-direction: column;

  .info-tag {
    padding: 30px 0px;
  }
}
</style>
