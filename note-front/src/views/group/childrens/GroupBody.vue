<template>
  <div class="group-body">
    <div class="group-members">
      <div class="group-members-text">Group Members</div>
      <div class="group-members-list">
        <div class="member">
          <el-avatar
            size="small"
            src="http://localhost:8888/static/avatarTwo.png"
          >
          </el-avatar>
          <span class="member-name">admin</span>
        </div>
        <div class="member">
          <el-avatar
            size="small"
            src="http://localhost:8888/static/avatarTwo.png"
          >
          </el-avatar>
          <span class="member-name">admin</span>
        </div>
        <div class="member">
          <el-avatar
            size="small"
            src="http://localhost:8888/static/avatarTwo.png"
          >
          </el-avatar>
          <span class="member-name">admin</span>
        </div>
        <div class="member">
          <el-avatar
            size="small"
            src="http://localhost:8888/static/avatarTwo.png"
          >
          </el-avatar>
          <span class="member-name">admin</span>
        </div>

        <div class="member">
          <el-avatar
            size="small"
            src="http://localhost:8888/static/avatarTwo.png"
          >
          </el-avatar>
          <span class="member-name">admin</span>
        </div>
        <div class="member">
          <el-avatar
            size="small"
            src="http://localhost:8888/static/avatarTwo.png"
          >
          </el-avatar>
          <span class="member-name">admin</span>
        </div>
        <div class="member">
          <el-avatar
            size="small"
            src="http://localhost:8888/static/avatarTwo.png"
          >
          </el-avatar>
          <span class="member-name">admin</span>
        </div>
        <div class="member">
          <el-avatar
            size="small"
            src="http://localhost:8888/static/avatarTwo.png"
          >
          </el-avatar>
          <span class="member-name">admin</span>
        </div>
      </div>
    </div>
    <div class="note-container">body</div>
  </div>
</template>

<script>
export default {}
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
    }
  }
  .note-container {
    flex: 1;
  }
}
</style>
