<template>
  <div class="submit-note">
    <div class="title">Notes Submission</div>

    <SubmitNoteContent />
  </div>
</template>

<script>
import SubmitNoteContent from "./childrens/SubmitNoteContent.vue";
export default { components: { SubmitNoteContent } };
</script>

<style lang="less" scoped>
.submit-note {
  display: flex;
  flex-direction: column;
  padding: 10px 120px 0px;
  height: calc(100% - 10px);

  .title {
    font-size: 22px;
    color: #565c69;

    margin-bottom: 15px;
    padding-left: 10px;
  }
}
</style>
