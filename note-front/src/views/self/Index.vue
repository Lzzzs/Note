<template>
  <div class="self">
    <div class="header">
      <el-avatar :size="50" :src="userInfo.avatarUrl"> </el-avatar>
      <span class="name">{{ userInfo.userid }}</span>
      <span class="grade">LV {{ gradeInfo.grade }}</span>
      <div class="progess-group">
        <div class="progess">
          <span class="progess-full" :style="progessFull"></span>
          <span class="progess-not-full" :style="progessNotFull"></span>
        </div>
        <span class="num"
          >{{ currentGradeContainer }}/{{ gradeInfo.experience }}</span
        >
      </div>
    </div>
    <div class="select">
      <div class="icon">
        <a href="/self/notes"
          ><i
            :class="
              $route.path.indexOf('notes') == -1
                ? 'el-icon-house'
                : 'el-icon-house active'
            "
          ></i
        ></a>
        <a href="/self/collect"
          ><i
            :class="
              $route.path.indexOf('collect') == -1
                ? 'el-icon-star-off'
                : 'el-icon-star-off active'
            "
          ></i
        ></a>
        <a href="/self/award"
          ><i
            :class="
              $route.path.indexOf('award') == -1
                ? 'el-icon-shopping-cart-2'
                : 'el-icon-shopping-cart-2 active'
            "
          ></i
        ></a>
      </div>
      <div class="select-input">
        <input type="text" placeholder="Search for... " />
        <i class="el-icon-search search-icon"></i>
      </div>
    </div>
    <div class="self-body">
      <router-view />
    </div>
  </div>
</template>

<script>
import { getGradeInfo } from "@/network/self/index.js";
import { getCurrentGradeContainer } from "@/utils/gradeUtil.js";
export default {
  data() {
    return {
      gradeData: null,
    };
  },
  computed: {
    userInfo() {
      return this.$store.state.userInfo
        ? this.$store.state.userInfo
        : { avatarUrl: "", userid: "" };
    },
    gradeInfo() {
      return this.gradeData ? this.gradeData : { grade: 1, experience: 0 };
    },
    currentGradeContainer() {
      return getCurrentGradeContainer(this.gradeInfo.grade);
    },
    progessFull() {
      if (!this.currentGradeContainer) return;
      return `width: ${
        (this.gradeInfo.experience / this.currentGradeContainer) * 100
      }%`;
    },
    progessNotFull() {
      if (!this.currentGradeContainer) return;
      return `width: ${
        ((this.currentGradeContainer - this.gradeInfo.experience) /
          this.currentGradeContainer) *
        100
      }%`;
    },
  },
  created() {
    this.getData();
  },
  methods: {
    async getData() {
      const { data } = await getGradeInfo(this.$store.state.userInfo.userid);
      this.gradeData = data;
    },
    format(percentage) {
      return percentage === 100 ? "满" : `100/${percentage}`;
    },
  },
};
</script>

<style lang="less" scoped>
.self {
  display: flex;
  flex-direction: column;
  height: 100%;
  .header {
    display: flex;
    justify-content: left;
    align-items: center;
    padding-top: 10px;

    .name {
      margin-left: 15px;
    }

    .grade {
      background: #eb386a;
      font-size: 12px;
      color: #fff;
      padding: 4px;
      border-radius: 2px;
      margin-left: 15px;
    }

    .progess-group {
      display: flex;
      align-items: center;
      margin-left: 10px;
      .progess {
        display: flex;
        width: 70px;
      }
      .progess-full {
        background: #dc4037;
        height: 10px;
      }
      .progess-not-full {
        background: #d8d9da;
        height: 10px;
      }

      .num {
        margin-left: 10px;
        font-size: 12px;
      }
    }
  }

  .select {
    background: #fff;
    border-radius: 3px;
    display: flex;
    justify-content: space-between;
    padding: 8px 200px 8px 30px;
    border: 1px solid #dce0e7;
    border-radius: 5px;
    margin: 10px 0px 30px;

    .icon {
      display: flex;
      justify-content: space-between;
      align-items: center;
      width: 270px;
      i {
        font-size: 34px;
      }

      .active {
        color: #5cb35b;
      }
    }

    .select-input {
      position: relative;
      margin-right: 10px;

      & > input {
        border: 1px solid #dcdcdc;
        border-radius: 20px;
        width: 260px;
        padding: 9px 20px;
        font-size: 13px;
      }

      .search-icon {
        position: absolute;
        right: 15px;
        top: 3px;

        color: #949494;
        font-weight: 900;
        border-radius: 50%;
        padding: 6px;
      }
    }
  }

  .self-body {
    flex: 1;
    background: #fff;
    border-radius: 3px;
    border: 1px solid #dce0e7;
    border-radius: 5px;
  }
}
</style>
