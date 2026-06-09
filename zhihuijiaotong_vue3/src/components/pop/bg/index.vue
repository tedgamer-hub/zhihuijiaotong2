/**
* @Author: 858834013@qq.com
* @Name: gridView
* @Date: 2023年12月10日16:16:25
* @Desc: 仿安卓.9图九宫格效果
*/
<template>
  <div class="gridView">
    <!--    左上角菱形装饰-->
    <div class="lozengeDecoration">
      <div class="lozengeDecorationInner wow fadeInDown" :data-wow-delay="index*0.3+'s'" v-for="(item,index) in 5"
           :key="index"></div>
    </div>
    <!--    顶部左侧装饰-->
    <div class="iconTopLeft">
      <div class="iconTopLeftInner" ref="iconTopLeftInner"></div>
    </div>
    <!--    顶部右侧装饰-->
    <div class="iconTopRight">
      <div class="iconTopRightInner" ref="iconTopRightInner"></div>
    </div>
    <!--    右下角装饰-->
    <div class="iconBottomRight">
      <div class="iconBottomRightInner" ref="iconBottomRightInner"></div>
    </div>
    <!--    左下角装饰-->
    <div class="iconBottomLeft">
      <div class="iconBottomLeftInner" ref="iconBottomLeftInner"></div>
    </div>
    <!--    顶部-->
    <div class="gridViewTop">
      <div class="gTop1"></div>
      <div class="gTop2"></div>
      <div class="gTop3"></div>
    </div>
    <!--    中间-->
    <div class="gridViewCenter">
      <div class="gridViewCenter_01"></div>
      <div class="gridViewCenter_02"></div>
      <div class="gridViewCenter_03"></div>
    </div>
    <!--    底部-->
    <div class="gridViewBottom">
      <div class="gridViewBottom1"></div>
      <div class="gridViewBottom2"></div>
      <div class="gridViewBottom3"></div>
    </div>
  </div>
</template>

<script>
import {gsap} from 'gsap';

export default {
  name: "gridView",
  mounted() {
    this.getAnimateTopLeft();
  },
  methods: {
    // 左上角动画
    getAnimateTopLeft() {
      this.$nextTick(() => {
        let tl = gsap.timeline({
          onComplete: this.getAnimateTopRight // 动画完成后执行的函数
        });
        tl.to(this.$refs.iconTopLeftInner, {
          duration: 1, // 高度变化动画持续时间，单位为秒
          height: '100%', // 目标高度
        })
            .to(this.$refs.iconTopLeftInner, {
              duration: 1.5, // 宽度变化动画持续时间，单位为秒
              width: '100%', // 目标宽度
            });
      })
    },
    // 右上角动画
    getAnimateTopRight() {
      this.$nextTick(function () {
        let tl = gsap.timeline({
          onComplete: this.getAnimateBottomRight // 动画完成后执行的函数
        });
        tl.to(this.$refs.iconTopRightInner, {
          duration: 1, // 高度变化动画持续时间，单位为秒
          width: '100%', // 目标高度
        })
            .to(this.$refs.iconTopRightInner, {
              delay: 0,
              duration: 1, // 宽度变化动画持续时间，单位为秒
              height: '100%', // 目标宽度
            })

      })
    },

    // 右下角动画
    getAnimateBottomRight() {
      this.$nextTick(function () {
        let tl = gsap.timeline({
          onComplete: this.getAnimateBottomLeft // 动画完成后执行的函数
        });
        tl.to(this.$refs.iconBottomRightInner, {
          duration: 1, // 高度变化动画持续时间，单位为秒
          height: '100%', // 目标高度
        })
            .to(this.$refs.iconBottomRightInner, {
              delay: 0,
              duration: 1, // 宽度变化动画持续时间，单位为秒
              width: '100%', // 目标宽度
            })

      })
    },

    // 左下角动画
    getAnimateBottomLeft() {
      this.$nextTick(function () {
        let tl = gsap.timeline();
        tl.to(this.$refs.iconBottomLeftInner, {
          duration: 1, // 高度变化动画持续时间，单位为秒
          width: '100%', // 目标高度
        })
            .to(this.$refs.iconBottomLeftInner, {
              delay: 0,
              duration: 1, // 宽度变化动画持续时间，单位为秒
              height: '100%', // 目标宽度
            })
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.gridView {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  min-height: 80px;
  min-width: 250px;
  bottom: 0;
  right: 0;
  z-index: -1;
  pointer-events: none; //避免div层被点击
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: nowrap;
  flex-direction: column;
  align-content: flex-start;

  .lozengeDecoration {
    position: absolute;
    left: 0px;
    width: 86PX;
    top: 0;
    height: 9px;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    flex-wrap: nowrap;
    flex-direction: row;
    align-content: flex-start;

    .lozengeDecorationInner {
      background: url("./assets/ornament.png") no-repeat;
      background-size: 100% 100%;
      width: 17px;
      height: 9px;
      margin-right: -5px;
      opacity: 0.9;
    }

    .lozengeDecorationInner:nth-child(1) {
      opacity: 0.5;
    }
    .lozengeDecorationInner:nth-child(2) {
      opacity: 0.6;
    }
    .lozengeDecorationInner:nth-child(3) {
      opacity: 0.7;
    }
    .lozengeDecorationInner:nth-child(4) {
      opacity: 0.8;
    }
  }

  //顶部左侧装饰
  .iconTopLeft {
    width: 23PX;
    height: 25PX;
    flex-shrink: 0;
    position: absolute;
    left: 0;
    top: 10PX;
    display: flex;
    justify-content: flex-start;
    align-items: flex-end;
    flex-wrap: nowrap;
    flex-direction: row;
    align-content: flex-start;

    .iconTopLeftInner {
      background: url("./assets/icon_top_left.png") left bottom no-repeat;
      background-size: 23PX 25PX;
      width: 1%;
      height: 0%;
      position: relative;
      flex-shrink: 0;
    }
  }

  //顶部右侧装饰
  .iconTopRight {
    width: 12PX;
    height: 11PX;
    flex-shrink: 0;
    position: absolute;
    right: 0;
    top: 0PX;
    display: flex;
    justify-content: flex-start;
    align-items: flex-start;
    flex-wrap: nowrap;
    flex-direction: row;
    align-content: flex-start;

    .iconTopRightInner {
      background: url("./assets/icon_top_right.png") left top no-repeat;
      background-size: 12PX 11PX;
      width: 0%;
      height: 30%;
      position: relative;
      flex-shrink: 0;
    }
  }

  //右下角装饰
  .iconBottomRight {
    width: 67PX;
    height: 39PX;
    flex-shrink: 0;
    position: absolute;
    right: 0;
    bottom: 0PX;
    display: flex;
    justify-content: flex-end;
    align-items: flex-start;
    flex-wrap: nowrap;
    flex-direction: row;
    align-content: flex-start;

    .iconBottomRightInner {
      background: url("./assets/icon_bottom_right.png") right top no-repeat;
      background-size: 67PX 39PX;
      width: 30%;
      height: 0%;
      position: relative;
      flex-shrink: 0;
    }
  }

  //左下角装饰
  .iconBottomLeft {
    width: 11PX;
    height: 11PX;
    flex-shrink: 0;
    position: absolute;
    left: 0;
    bottom: 0PX;
    display: flex;
    justify-content: flex-end;
    align-items: flex-end;
    flex-wrap: nowrap;
    flex-direction: row;
    align-content: flex-start;

    .iconBottomLeftInner {
      background: url("./assets/icon_bottom_left.png") right bottom no-repeat;
      background-size: 11PX 11PX;
      width: 0%;
      height: 20%;
      position: relative;
      flex-shrink: 0;
    }
  }

  //顶部
  .gridViewTop {
    width: 100%;
    height: 24PX;
    flex-shrink: 0;
    position: relative;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: nowrap;
    flex-direction: row;
    align-content: flex-start;

    .gTop1 {
      background: url("./assets/bg/bg_01.png") no-repeat;
      background-size: 100% 100%;
      width: 96PX;
      height: 100%;
      position: relative;
      flex-shrink: 0;
    }

    .gTop2 {
      background: url("./assets/bg/bg_02.png") no-repeat;
      background-size: 100% 100%;
      width: calc(100% - 107PX);
      height: 100%;
      position: relative;
      transform: scale(1.0001);
    }

    .gTop3 {
      background: url("./assets/bg/bg_03.png") no-repeat;
      background-size: 100% 100%;
      width: 11PX;
      height: 100%;
      position: relative;
      flex-shrink: 0;
    }
  }

  //中间

  .gridViewCenter {
    width: 100%;
    height: calc(100% - 37PX);
    position: relative;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: nowrap;
    flex-direction: row;
    align-content: flex-start;

    .gridViewCenter_01 {
      background: url("./assets/bg/bg_04.png") no-repeat;
      background-size: 100% 100%;
      width: 96PX;
      height: 100%;
      flex-shrink: 0;
      transform: scale(1.000001);
    }

    .gridViewCenter_02 {
      background: url("./assets/bg/bg_05.png") no-repeat;
      background-size: 100% 100%;
      width: 100%;
      height: 100%;
      transform: scale(1.000001);
    }

    .gridViewCenter_03 {
      background: url("./assets/bg/bg_06.png") no-repeat;
      background-size: 100% 100%;
      width: 11PX;
      height: 100%;
      flex-shrink: 0;
      transform: scale(1.000001);
    }
  }

  //底部
  .gridViewBottom {
    width: 100%;
    height: 13PX;
    flex-shrink: 0;
    position: relative;
    display: flex;
    justify-content: space-between;
    align-items: center;
    flex-wrap: nowrap;
    flex-direction: row;
    align-content: flex-start;

    .gridViewBottom1 {
      background: url("./assets/bg/bg_07.png") no-repeat;
      background-size: 100% 100%;
      width: 96PX;
      height: 100%;
      flex-shrink: 0;
      transform: scale(1.000001);
    }

    .gridViewBottom2 {
      background: url("./assets/bg/bg_08.png") no-repeat;
      background-size: 100% 100%;
      width: 100%;
      height: 100%;
      transform: scale(1.000001);
    }

    .gridViewBottom3 {
      background: url("./assets/bg/bg_09.png") no-repeat;
      background-size: 100% 100%;
      width: 11PX;
      height: 100%;
      flex-shrink: 0;
      transform: scale(1.000001);
    }
  }
}

</style>
