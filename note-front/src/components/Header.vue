<template>
  <el-header class="header">
    <div class="header-left">
      <div @click="toInfo" class="logo">
        <span class="logo-image"></span>
        <span class="logo-text">NSSS</span>
      </div>
      <el-link style="font-size: 16px" @click="toGroup">group</el-link>
    </div>
    <div class="header-other">
      <div class="search">
        <input type="text" placeholder="Search for... " v-model="searchValue" />
        <i class="el-icon-search search-icon" @click="handleSearch"></i>
      </div>
      <div class="login-info">
        <div v-if="!userInfo" class="no-login">
          <span @click="toLogin" class="login-btn"> Login</span>
          <span> / </span>
          <span @click="toRegister" class="register-btn">Sign Up</span>
        </div>
        <div v-else class="logined-info">
          <div class="user-info" @click="toSelf">
            <el-avatar size="medium" :src="userInfo.avatarUrl"> </el-avatar>
            <span>{{ userInfo.userid }}</span>
          </div>
          <el-button type="primary" size="medium" @click="handleLogout"
            >Logout</el-button
          >
        </div>
      </div>
      <el-button
        type="primary"
        size="medium"
        v-if="isShowPostNoteBtn"
        @click="handlePostNote"
        >Post Notes</el-button
      >
    </div>
  </el-header>
</template>

<script>
import { logout } from '@/utils/userUtil.js'
export default {
  data() {
    return {
      searchValue: ''
    }
  },
  computed: {
    userInfo() {
      return this.$store.state.userInfo
    },
    isShowPostNoteBtn() {
      /**
       * 用户未登录不展示
       * 用户当前在提交笔记页面 不显示
       */
      if (this.$store.state.userInfo) {
        return this.$route.path !== '/submit-note'
      }
      return false
    }
  },
  methods: {
    toLogin() {
      this.$router.push('/login')
    },
    toRegister() {
      this.$router.push('/register')
    },
    handleLogout() {
      logout()
      // 如果在self的页面退出的话 直接跳转主页
      if (this.$route.path.indexOf('self') != -1) {
        this.toInfo()
      }
      this.$message.success('退出成功')
    },
    toSelf() {
      if (this.$route.path !== '/self/notes') {
        this.$router.push('/self')
      }
    },
    handlePostNote() {
      this.$router.push('/submit-note')
    },
    toInfo() {
      if (this.$route.path !== '/info') {
        this.$router.push('/info')
      }
    },
    handleSearch() {
      if (this.$route.path.indexOf('group') !== -1) {
        this.$message.warning('group does not support search yet')
        return
      }
      this.$bus.$emit('searchValue', this.searchValue)
    },
    toGroup() {
      this.$router.push('/group')
    }
  }
}
</script>

<style lang="less" scoped>
.header {
  display: flex;
  align-items: center;
  justify-content: space-between;
  background: #fafbfd;
  padding: 0px 50px;
  border-bottom: 1px solid #dcdcdc;
  .header-left {
    display: flex;
    align-items: center;
    user-select: none;

    .logo {
      display: flex;
      align-items: center;

      &:hover {
        cursor: pointer;
      }
      margin-right: 40px;

      .logo-image {
        width: 32px;
        height: 32px;
        display: block;
        background: url(../assets/logo.png) center no-repeat;
        background-size: 28px;
        border-radius: 7px;
        border: 1px solid #dcdcdc;
      }

      .logo-text {
        font-family: Sans-serif;
        font-weight: 900;
        margin-left: 10px;
      }
    }
  }

  .header-other {
    display: flex;
    align-items: center;

    .search {
      position: relative;
      margin-right: 10px;

      & > input {
        border: 1px solid #dcdcdc;
        border-radius: 20px;
        width: 350px;
        padding: 9px 20px;
        font-size: 13px;
      }

      .search-icon {
        position: absolute;
        right: 15px;
        top: 3px;
        &:hover {
          cursor: pointer;
        }

        background: #489bf4;
        color: #fff;
        font-weight: 900;
        border-radius: 50%;
        padding: 6px;
      }
    }

    .login-info {
      display: flex;
      width: 280px;
      font-size: 14px;

      .no-login {
        width: 100%;
        display: flex;
        justify-content: center;
        font-weight: 900;

        .login-btn {
          &:hover {
            cursor: pointer;
          }
        }
        .register-btn {
          &:hover {
            cursor: pointer;
          }
        }
      }

      .logined-info {
        width: 100%;
        display: flex;
        justify-content: space-between;
        align-items: center;
        padding: 0px 30px;

        .user-info {
          display: flex;
          align-items: center;
          &:hover {
            cursor: pointer;
            user-select: none;
          }

          span {
            margin-left: 10px;
          }
        }
      }
    }
  }
}
</style>
