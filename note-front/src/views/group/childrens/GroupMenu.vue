<template>
  <div class="group-menu">
    <el-button round class="create-group" @click="createGroup"
      >Create a Group</el-button
    >
    <el-button type="primary" round class="join-group" @click="handleJoinGroup"
      >Join a Group</el-button
    >
    <el-dropdown trigger="click">
      <el-button round class="dropdown-btn">
        Your Group<i class="el-icon-arrow-down el-icon--right"></i>
      </el-button>
      <el-dropdown-menu slot="dropdown" class="menu-item">
        <el-dropdown-item>group1</el-dropdown-item>
        <el-dropdown-item>group2</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <el-button type="primary" class="post-note-in-group"
      >Post Notes in Group</el-button
    >

    <el-drawer
      title="Join a Group"
      :visible.sync="drawer"
      direction="ltr"
      class="group-drawer"
    >
      <div class="groups">
        <div class="group" v-for="(item, index) in groups" :key="index">
          <div class="group-name">{{ item }}</div>
          <div class="add-group">
            <el-button type="primary" size="small">join group</el-button>
          </div>
        </div>
      </div>
    </el-drawer>

    <el-dialog title="Create a Group" :visible.sync="dialogVisible" width="30%">
      <div class="create-group-form">
        <span class="label">Group Name</span>
        <el-input type="text" v-model="addGroupName"></el-input>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancelAddGroup">Cancel</el-button>
        <el-button type="primary" @click="confirmAddGroup">Create</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { addGroup } from '@/network/group'

export default {
  data() {
    return {
      drawer: false,
      dialogVisible: false,
      addGroupName: '',
      groups: [
        'group1',
        'group2',
        'group3',
        'group4',
        'group5',
        'group6',
        'group7',
        'group8',
        'group9',
        'group10',
        'group11',
        'group12',
        'group13'
      ]
    }
  },
  methods: {
    handleJoinGroup() {
      this.drawer = true
    },
    createGroup() {
      this.dialogVisible = true
    },
    cancelAddGroup() {
      this.dialogVisible = false
      this.addGroupName = ''
    },
    confirmAddGroup() {
      addGroup({
        name: this.addGroupName,
        userId: this.$store.state.userInfo.userid
      }).then(() => {
        this.dialogVisible = false
        this.$message.success('create group success')
      })
    }
  }
}
</script>

<style lang="less">
.group-menu {
  display: flex;
  margin-top: 35px;
  padding: 10px 50px;
  align-items: center;
  justify-content: space-between;

  .create-group {
    width: 180px;
    height: 45px;
  }

  .join-group {
    width: 300px;
    height: 45px;
  }

  .dropdown-btn {
    width: 300px;
    height: 45px;
  }

  .post-note-in-group {
    width: 300px;
    height: 45px;
  }
}

.group-drawer {
  .groups {
    padding: 0px 20px;
    .group {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 15px;
      margin-bottom: 10px;
    }
  }
}

.create-group-form {
  display: flex;
  align-items: center;
  & > .label {
    display: block;
    margin-right: 20px;
    width: 140px;
    font-size: 16px;
  }
}

.menu-item li {
  width: 270px !important;
}
</style>
