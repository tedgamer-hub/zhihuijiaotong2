<template>
  <div class="mapMonitoring">
    <div class="monitoring" :style="monitoringStyle">
      <i class="icon icon-yongdu3" :style="iconStyle"></i>
    </div>
    <div class="monitoringBase" :style="baseStyle">
    </div>
  </div>
</template>
<script>
import pop from '@/components/pop/pop.vue'
import heavyIconbg from './assets/heavy/iconbg.png'
import mediumIconbg from './assets/medium/iconbg.png'
import lightIconbg from './assets/light/iconbg.png'

import heavyBase from './assets/heavy/dizuoAnimate.png'
import mediumBase from './assets/medium/dizuoAnimate.png'
import lightBase from './assets/light/dizuoAnimate.png'

export default {
  name: "index",
  components: {
    pop,
  },
  data() {
    return {
      iconImages: {
        heavy: heavyIconbg,
        medium: mediumIconbg,
        light: lightIconbg
      },
      baseImages: {
        heavy: heavyBase,
        medium: mediumBase,
        light: lightBase
      },
      iconColors: {
        heavy: '#ff0000', // 红色
        medium: '#ffff00', // 黄色
        light: '#00ff00'   // 绿色
      }
    }
  },
  props: {
    icon: {
      type: String,
      default: () => {
        return '';
      }
    },
    status: {
      type: Number,
      default: 0
    },
    level: {
      type: String,
      default: 'heavy' // 默认为heavy级别
    }
  },
  computed: {
    monitoringStyle() {
      // 根据level选择对应的背景图片
      const bgImage = this.iconImages[this.level] || this.iconImages.heavy;
      return {
        backgroundImage: `url(${bgImage})`
      };
    },
    baseStyle() {
      // 根据level选择对应的底座图片
      const baseImage = this.baseImages[this.level] || this.baseImages.heavy;
      return {
        backgroundImage: `url(${baseImage})`
      };
    },
    iconStyle() {
      // 根据level选择对应的图标颜色
      const color = this.iconColors[this.level] || this.iconColors.heavy;
      return {
        background: `-webkit-linear-gradient(#ffffff, ${color})`,
        WebkitBackgroundClip: 'text',
        WebkitTextFillColor: 'transparent'
      };
    }
  },
  methods: {
  }
}
</script>

<style scoped lang="scss">
.mapMonitoring {
  width: 80PX;
  height: 100PX;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: nowrap;
  flex-direction: column;
  align-content: flex-start;

  .monitoring {
    width: 80PX;
    height: 87PX;
    background-size: 100% 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: nowrap;
    flex-direction: row;
    align-content: flex-start;

    i {
      font-size: 24PX;
      color: #fff;
      margin-top: -10px;
      display: inline-block;
    }
  }

  .monitoringBase {
    width: 80PX;
    height: 32PX;
    background-size: 100% 100%;
    margin-top: -20PX;
  }
}
</style>
