<template>
  <div class="group-body">
    <div class="group-members">
      <div class="group-members-text">Group Members</div>
      <div class="group-members-list" v-if="members.length !== 0">
        <div class="member" v-for="item in members" :key="item.userid">
          <el-avatar size="small" :src="item.avatarUrl"> </el-avatar>
          <span class="member-name">{{ item.userid }}</span>
        </div>
      </div>
      <div class="group-members-list" v-else>
        <span class="tip">请选择group</span>
      </div>
    </div>

    <GroupNote />
  </div>
</template>

<script>
import { selectOrganizeUserById } from '@/network/group'
import GroupNote from './GroupNote.vue'

export default {
  data() {
    return {
      members: []
    }
  },
  components: { GroupNote },
  mounted() {
    this.getMembers()

    this.$bus.$on('updated-body', () => {
      this.getMembers()
    })
  },
  beforeDestroy() {
    this.$bus.$off('updated-body')
  },
  methods: {
    getMembers() {
      const id = this.$route.query.id

      if (!id) return

      selectOrganizeUserById(id).then((res) => {
        this.members = res.data
      })
    }
  }
}
</script>

<style lang="less" scoped>
.group-body {
  border-top: 1px solid red;
  flex: 1;

  display: flex;
  border: 1px solid #dcdcdc;
  border-bottom: none;
  border-radius: 5px;

  .group-members {
    display: flex;
    flex-direction: column;
    .group-members-text {
      color: #222222;
      font-size: 20px;
      padding: 15px;
      background: #dad9d9;
    }
    .group-members-list {
      background: #f0f4f4;
      flex: 1;
      padding-top: 5px;

      .member {
        margin-bottom: 10px;
        padding: 0px 10px;
        display: flex;
        align-items: center;
        cursor: pointer;

        & > .member-name {
          display: block;
          margin-left: 10px;
        }
      }

      .tip {
        display: block;
        display: flex;
        height: 100px;
        align-items: center;
        justify-content: center;
        font-weight: bold;
        font-size: 18px;
      }
    }
  }
}
</style>
