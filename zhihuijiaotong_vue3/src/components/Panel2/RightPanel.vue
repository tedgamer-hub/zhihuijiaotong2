<template>
  <div class="right-panel" ref="panel">
    <div class="bgInner">
    </div>
    <div class="bgInnerImg"></div>
    <div class="hideBut" @click="togglePanel">
      <img :class="{ 'rotate-180': isPanelHidden }" src="./assets/icon_right.png" alt="">
    </div>
    <slot></slot>
  </div>
</template>

<script>
import {gsap} from "gsap";

export default {
  data() {
    return {
      isPanelHidden: false,
    };
  },
  methods: {
    togglePanel() {
      const panel = this.$refs.panel;
      if (this.isPanelHidden) {
        gsap.to(panel, {x: 0, duration: 0.5});
      } else {
        gsap.to(panel, {x: `${panel.offsetWidth}px`, duration: 0.5});
      }
      this.isPanelHidden = !this.isPanelHidden;
    },
  },
};
</script>

<style lang="scss" scoped>
.right-panel {
  position: relative; /* 确保相对定位 */
  width: 100%; /* 不限制宽度 */
  height: 100%; /* 不限制高度 */
}

.hideBut {
  cursor: pointer;
  position: absolute;
  left: -20px;
  top: 50%;
  transform: translateY(-50%);
  background: url("./assets/butbg_right.png") no-repeat;
  background-size: 100% 100%;
  width: 15px;
  height: 633px;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: nowrap;
  flex-direction: row;
  align-content: flex-start;
  pointer-events: initial;

  img{
    margin-left: -30px;
    cursor: pointer;
  }
}
.bgInner {
  position: absolute;
  right: -30px;
  width: 90%;
  height: 100%;
  background: #04223f;
  pointer-events: none;
  z-index: -100;
}

.bgInnerImg {
  position: absolute;
  right: 80%;
  width: 1080px;
  height: 100%;
  background: url("./assets/bgRight.png") no-repeat;
  pointer-events: none;
  background-size: 100% 100%;
  z-index: -100;
}

.rotate-180 {
  transform: rotate(180deg);
}
</style>
