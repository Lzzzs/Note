<template>
  <div class="award">
    <div class="point">Your Rest Points: {{ pointValue }}</div>
    <div class="award-list">
      <AwardItem
        class="item"
        v-for="item in awardList"
        :point="item"
        :key="item"
        @buy="hanldeBuy"
      />
    </div>
  </div>
</template>

<script>
import AwardItem from "./AwardItem.vue";
import { getPointInfo, exchangeAward } from "@/network/self/index.js";
export default {
  data() {
    return {
      awardList: [100, 200, 300, 400, 500, 600],
      point: 0,
    };
  },
  components: { AwardItem },
  computed: {
    pointValue() {
      return this.point;
    },
  },
  created() {
    this.getData();
  },
  methods: {
    getData() {
      getPointInfo(this.$store.state.userInfo.userid).then(({ data }) => {
        this.point = data;
      });
    },
    hanldeBuy(selectAwardPoint) {
      if (this.point < selectAwardPoint) {
        this.$message.warning("当前积分不够");
      } else {
        this.$confirm(`确认兑换积分为${selectAwardPoint}的奖品吗？`, "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }).then(async () => {
          await exchangeAward(
            this.$store.state.userInfo.userid,
            selectAwardPoint
          );
          await this.getData();
          this.$message.success("兑换成功！");
        });
      }
    },
  },
};
</script>

<style lang="less" scoped>
.award {
  padding: 20px 100px;

  .point {
    color: #979292;
    font-weight: bold;
    margin-bottom: 20px;
  }

  .award-list {
    display: flex;
    flex-wrap: wrap;
    .item {
      width: 25%;
    }
  }
}
</style>
