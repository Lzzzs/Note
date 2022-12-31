<template>
  <div class="note">
    <el-skeleton :rows="16" animated v-if="loading" style="padding: 50px" />
    <div v-else v-html="transitionContentText" class="markdown-body"></div>
  </div>
</template>

<script>
import "github-markdown-css/github-markdown-light.css";
import MarkdownIt from "markdown-it";
export default {
  props: {
    contentText: String,
  },
  data() {
    return {
      transitionContentText: "",
      loading: true,
    };
  },
  watch: {
    contentText(newContent) {
      this.transitionContentText = new MarkdownIt().render(newContent);
      // 防止页面闪动
      setTimeout(() => {
        this.loading = false;
      }, 300);
    },
  },
};
</script>

<style lang="less" scoped>
.note {
  box-shadow: 0 2px 8px 0 rgba(0, 0, 0, 0.1);
  width: 100%;
  min-height: 700px;
  margin-top: 30px;
}

.markdown-body {
  box-sizing: border-box;
  min-width: 200px;
  max-width: 980px;
  margin: 0 auto;
  padding: 45px;
}
</style>
