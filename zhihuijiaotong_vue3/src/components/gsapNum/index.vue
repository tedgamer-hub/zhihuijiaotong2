<template>
  <div v-if="show" class="gsapNum numcardItem1">
    <!-- 动态绑定 color 到 style -->
    <span class="gsapNum" ref="number" :style="{ color: color }">{{ formattedNumber }}</span>
  </div>
</template>

<script>
import { gsap } from 'gsap';

export default {
  data() {
    return {
      rawNumber: 0, // 更改为rawNumber，存储实际数字值
      show: true,
      showComma: false, // 是否显示千分位逗号
    };
  },
  props: {
    number: {
      type: [Number, String],
      default() {
        return 0;
      },
    },
    comma: { // 通过props控制是否显示千分位
      type: Boolean,
      default: false
    },
    delay: {
      type: [Number, String],
      default() {
        return 0;
      },
    },
    color: { // 新增 color 属性
      type: String,
      default: '#FFFFFF' // 默认颜色
    }
  },
  computed: {
    formattedNumber() {
      if (this.showComma) {
        return this.rawNumber.toLocaleString(); // 格式化数字为千分位格式
      }
      return this.rawNumber;
    },
    currentNumber: {
      get() {
        return this.rawNumber;
      },
      set(value) {
        this.rawNumber = value;
      }
    }
  },
  watch: {
    number(newVal) {
      if (this.show) {
        this.$nextTick(() => {
          this.currentNumber = 0
          this.animateNumber(this.currentNumber, newVal);
        });
      }
    },
    show() {
      this.$nextTick(() => {
        this.currentNumber = 0
        this.animateNumber(this.currentNumber, this.number);
      });
    },
    comma(newVal) {
      this.showComma = newVal; // 设置是否显示千分位
    }
  },
  methods: {
    animateNumber(start, end) {
      gsap.to(this, {
        currentNumber: end,
        duration: 2,
        delay: parseFloat(this.delay),
        roundProps: 'currentNumber',
        ease: 'power1.out',
        onUpdate: () => {
          this.currentNumber = Math.round(this.currentNumber);
        },
      });
    },
  },
  mounted() {
    this.showComma = this.comma; // 初始化时根据props设置是否显示千分位
    if (this.show) {
      this.currentNumber = 0
      this.animateNumber(this.currentNumber, this.number);
    }
  },
};
</script>

<style lang="scss" scoped>
.numcardItem1 {
  .gsapNum {
    width: auto !important;
    font-size: 22px;
    font-weight: normal;
    animation: slideUp 1s forwards;
  }
}

@keyframes slideUp {
  from {
    transform: translateY(100%);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}
</style>