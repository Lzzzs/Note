<template>
  <div class="award-item">
    <div class="item" @click="handleExchangeAward">
      <img
        src="../../../assets/images/奖品.png"
        style="width: 100%; height: 100%"
      />
    </div>
    <span class="need"
      >Need: <span style="color: red">{{ point }}</span> Points</span
    >
  </div>
</template>

<script>
export default {
  props: {
    point: Number,
  },
  methods: {
    handleExchangeAward() {
      this.$emit("buy", this.point);
    },
  },
};
</script>

<style lang="less" scoped>
.award-item {
  display: flex;
  flex-direction: column;
  margin-left: 100px;

  &:hover {
    cursor: pointer;
  }

  .item {
    height: 200px;
    border-radius: 10px;
    margin-bottom: 20px;
  }

  .need {
    text-align: center;
    margin-bottom: 30px;
  }
}
</style>
