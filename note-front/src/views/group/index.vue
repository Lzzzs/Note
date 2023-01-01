<template>
  <div class="group">
    <GroupMenu></GroupMenu>
    <GroupBody></GroupBody>
  </div>
</template>

<script>
import GroupBody from './childrens/GroupBody.vue'
import GroupMenu from './childrens/GroupMenu.vue'

export default { components: { GroupMenu, GroupBody } }
</script>

<style lang="less" scoped>
.group {
  height: 100%;
  display: flex;
  flex-direction: column;
}
</style>
