<template>
  <div class="pageItemBody">
    <div class="pageItemBodyInner" ref="inner" v-if="show">
      <titleCom :type="type" :icon="icon" :name="name"></titleCom>
      <!--      主题部分-->
      <div class="itemMain">
        <bg></bg>
        <slot></slot>
      </div>
      <!--      图片背景-->
      <div class="bgImg"></div>
    </div>
    <!--    向下展开的背景-->
    <div class="bg" ref="bg" v-if="!show"></div>
  </div>
</template>

<script>

import gsap from 'gsap'
import titleCom from './titleCom.vue'
import bg from './bg.vue'

export default {
  name: "title",
  data() {
    return {
      show: false
    }
  },
  components: {titleCom, bg},
  props: {
    name: {
      type: String,
      default() {
        return '';
      }
    },
    icon: {
      type: String,
      default() {
        return '';
      }
    },
    type: {
      type: String,
      default() {
        return 'min';
      }
    },
    delay: {
      type: Number,
      default() {
        return 0;
      }
    },
    duration: {
      type: Number,
      default() {
        return 0.5;
      }
    },
  },
  mounted() {
    var that = this;
    gsap.to(this.$refs.bg, {
      height: '100%',
      delay: that.delay,
      duration: that.duration,
      ease: 'none',
      onComplete: () => {
        setTimeout(() => {
          that.show = true;
          that.$nextTick(() => {
            gsap.to(that.$refs.inner, {
              opacity: 1,
              duration: 1,
            });
          });
        }, 100);
      }
    });
  }
}
</script>

<style lang="scss" scoped>
.pageItemBody {
  width: 100%;
  position: relative;
  height: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  flex-wrap: nowrap;
  flex-direction: column;
  z-index: 1;
  align-content: flex-start;
  border-radius: 5px;
}

.pageItemBodyInner {
  width: calc(100% - 0px);
  position: relative;
  height: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  flex-wrap: nowrap;
  flex-direction: column;
  z-index: 1;
  align-content: flex-start;
  opacity: 0;
  /* 添加毛玻璃效果 */
  backdrop-filter: blur(3px);
  -webkit-backdrop-filter: blur(3px);
}

.bg {
  height: 0%; /* 初始高度为 100% */
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  background: linear-gradient(to bottom, rgba(#2b89c8, 0) 0%, rgba(#2b89c8, 1) 100%); /* 从下到上渐变 */
}

.bgImg {
  position: absolute;
  top: 0;
  left: 0;
  z-index: 0;
  width: 100%;
  height: calc(100% - 0px);
}

.itemMain {
  position: relative;
  width: 100%;
  height: calc(100% - 50px);
  margin-top: 10px;
  z-index: 10;
}

</style>

