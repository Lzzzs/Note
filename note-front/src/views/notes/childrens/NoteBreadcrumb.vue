<template>
  <div class="note-breadcrumb">
    <el-breadcrumb class="breadcrumb" separator="/">
      <el-breadcrumb-item>{{ info.tag }}</el-breadcrumb-item>
      <el-breadcrumb-item>{{ info.title }}</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
</template>

<script>
export default {
  props: {
    info: Object,
  },
};
</script>

<style lang="less" scoped>
.note-breadcrumb {
  margin-bottom: 30px;
  padding-left: 10px;
  & > .breadcrumb {
    font-size: 16px;
  }
}
</style>
