<template>
  <div v-if="show" class="numcard numcardItem1">
    <span class="real-time-num gsapNum" ref="number">{{ formattedNumber }}</span>
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
  },
  computed: {
    formattedNumber() {
      if (this.showComma) {
        return parseFloat(this.rawNumber).toLocaleString('en-US', { maximumFractionDigits: 2 }); // 显示最多两位小数
      }
      return parseFloat(this.rawNumber).toFixed(2); // 固定显示两位小数，可以根据需要调整
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
        // 不要四舍五入小数点，因此不使用roundProps
        ease: 'power1.out',
        onUpdate: () => {
          this.currentNumber = parseFloat(this.currentNumber).toFixed(2);
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
  .real-time-num {
    width: auto !important;
    font-size: 45px;
    font-family: led;
    font-weight: normal;
    color: #FFFFFF;
    font-style: normal;
    animation: slideUp 1s forwards;
    text-shadow:0 0 20px #0995ff;
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
