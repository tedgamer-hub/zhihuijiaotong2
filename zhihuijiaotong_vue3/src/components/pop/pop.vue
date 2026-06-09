<template>
  <div>
    <div class="popWin" :style="{ zIndex: zIndex }" v-if="show">
      <div class="popWinBg" v-if="show" @click="getHide"></div>
      <div class="popWinMain" ref="popWinMain" v-if="show" :style="{height:this.height}">
        <bg></bg>
        <div class="popClose" ref="popClose" v-if="showInner" @click="getHide"></div>
        <div class="popWinMainInner" v-if="showInner">
          <div class="titleInfo">
            <div class="title">{{ title }}</div>
          </div>
          <div class="slot" v-if="showInner">
            <slot></slot>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import gsap from 'gsap'
import bg from './bg/index.vue'

export default {
  data() {
    return {
      show: false,
      showInner: false,
    }
  },
  components: {bg},
  mounted() {
    if (this.appendToBody) {
      const bodyElement = document.querySelector('body');
      bodyElement.appendChild(this.$el);
    }
  },
  props: {
    title: {
      type: String,
      default() {
        return '标题';
      }
    },
    width: {
      type: String,
      default() {
        return '85%';
      }
    },
    height: {
      type: String,
      default() {
        return '85%';
      }
    },
    // 弹窗层级,默认最大值
    zIndex: {
      type: Number,
      default: 2147483647
    },
// 是否将组件插入到body中 默认true
    appendToBody: {
      type: Boolean,
      default: true
    }
  },
  methods: {
    getShow() {
      var that = this;
      that.show = true
      that.$nextTick(() => {
        gsap.to(this.$refs.popWinMain, {
          duration: 1, width: this.width, onComplete: () => {
            this.showInner = true
            this.$nextTick(() => {
              gsap.to(this.$refs.popClose, {
                duration: 1, transform: ' rotateX(180deg)', onComplete: () => {
                  this.showInner = true
                }
              })
            })
          }
        })
      })

    },
    getHide() {
      this.showInner = false
      this.$nextTick(() => {
        gsap.to(this.$refs.popWinMain, {
          duration: 1, width: '0%', onComplete: () => {
            this.show = false
          }
        })
      })
    }
  },
}
</script>

<style lang="scss" scoped>
.popWinBg {
  position: fixed;
  z-index: 8;
  width: 100%;
  background: rgba(0, 0, 0, 0.9);
  height: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  pointer-events: initial;
}

.popWin {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: nowrap;
  flex-direction: row;
  align-content: flex-start;
  position: fixed;
  z-index: 10;
  width: 100%;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  height: 100%;
  pointer-events: none;

}

.popWinMain {
  pointer-events: initial;
  position: relative;
  overflow: hidden;
  width: 0%;
  max-width: 1642px;
  height: 85%;
  max-height: 1004px;
  z-index: 100;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: nowrap;
  flex-direction: row;
  align-content: flex-start;

  .popClose {
    position: absolute;
    right: 20px;
    top: 20px;
    background: url("./assets/icon_close.png") no-repeat;
    width: 16px;
    height: 16px;
    background-size: 100% 100%;
    cursor: pointer;
    z-index: 1000;
    //transform: rotateX(180deg);
  }

  .popWinMainInner {
    position: relative;
    width: calc(100% - 40px);
    height: calc(100% - 0px);
    display: flex;
    justify-content: flex-start;
    align-items: flex-start;
    flex-wrap: nowrap;
    flex-direction: column;
    align-content: flex-start;

    .slot {
      position: relative;
      width: 100%;
      height: calc(100% - 100px);
      overflow: auto;
      padding: 0 20px;
      box-sizing: border-box;
      display: flex;
      justify-content: flex-start;
      align-items: flex-start;
      flex-wrap: nowrap;
      flex-direction: column;
      align-content: flex-start;
      //background: red;
    }
  }
}

.titleInfo {
  position: relative;
  width: 671px;
  height: 70px;
  margin: 0px auto;
  display: flex;
  z-index: 10;
  justify-content: center;
  align-items: center;
  flex-wrap: nowrap;
  flex-direction: row;
  align-content: flex-start;
  //background: url("./assets/titleBg.png") no-repeat;
  //background-size: 100% 100%;
  .title {
    font-size: 24px;
    font-weight: bold;
    color: #FFFFFF;
    text-shadow: 0 0 0.05208rem #2ad4f0;
  }
}
</style>
