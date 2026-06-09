<template>
  <div class="progressBar">
    <div class="progressBarInner"
         ref="progressBarInner"
         :class="getColorClass"></div>
    <div class="line"></div>
  </div>
</template>

<script>
import gsap from "gsap";

export default {
  name: "progressBarBody",
  props: {
    item: {
      type: Object,
      default() {
        return {}
      }
    },
    index: {
      type: Number,
      default() {
        return 0
      }
    },
    total: {
      type: Number,
      default() {
        return 0
      }
    },
  },
  data() {
    return {}
  },
  computed: {
    index2: function () {
      return Number(this.index) + 1
    },
    width: function () {
      // 直接使用item.num计算百分比，范围0-10，转换为0-100%
      // 确保值在0-10范围内
      const num = Math.max(0, Math.min(10, this.item.num));
      return (num / 10) * 100;
    },
    getColorClass: function() {
      const num = this.item.num;
      if (num > 9) {
        return 'high-congestion';
      } else if (num >= 7 && num <= 9) {
        return 'medium-congestion';
      } else {
        return 'normal-congestion';
      }
    }
  },
  mounted() {
    setTimeout(() => {
      gsap.to(this.$refs.progressBarInner, {
        duration: 1.5, width: this.width + '%', onComplete: () => {
          console.log('动画完成')
        }
      })
    }, 1000)
  },
  methods: {}
}
</script>


<style lang="scss" scoped>
.progressBar {
  width: calc(100% - 0px);
  height: 10px;
  background: #20385c;
  border-radius: 3px;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-wrap: nowrap;
  flex-direction: row;
  align-content: flex-start;
  position: relative;

  .progressBarInner {
    height: 10px;
    opacity: 1;
    width: 0%;
    border-radius: 3px;
    max-width: calc(100% - 0px);

    &.normal-congestion {
      background: linear-gradient(180deg, #04CEF7 0%, #1263FF 100%);
    }

    &.medium-congestion {
      background: linear-gradient(180deg, #FFD700 0%, #FFA500 100%);
    }

    &.high-congestion {
      background: linear-gradient(180deg, #FF6B6B 0%, #FF0000 100%);
    }
  }

  .line {
    background: url("./assets/dot.png");
    width: 26px;
    height: 26px;
    opacity: 1;
    margin-left: -13px;
  }
}
</style>
