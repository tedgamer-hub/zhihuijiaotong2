<template>
  <div class="mapMonitoring">
    <div class="monitoring" :style="monitoringStyle">
      <i class="icon" :class="icon"></i>
    </div>
    <div class="monitoringBase" :style="monitoringBaseStyle">
    </div>
  </div>
</template>
<script>
import pop from '@/components/pop/pop.vue'
import iconbg from './assets/bg1/iconbg.png'
import dizuoAnimate from './assets/bg1/dizuoAnimate.png'

export default {
  name: "index",
  components: {
    pop,
  },
  data() {
    return {
      iconbg: iconbg,
      dizuoAnimate: dizuoAnimate
    }
  },
  props: {
    icon: {
      type: String,
      default() {
        return '';
      }
    },
    status: {
      type: Number,
      default: 0
    }
  },
  computed: {
    monitoringStyle() {
      return {
        backgroundImage: `url(${this.iconbg})`
      };
    },
    monitoringBaseStyle() {
      return {
        backgroundImage: `url(${this.dizuoAnimate})`
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
    //background: url("./assets/iconbg.png") no-repeat;
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
      background: -webkit-linear-gradient(#ffffff, red);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      display: inline-block;
    }
  }

  .monitoringBase {
    width: 80PX;
    height: 32PX;
    background: var(--dizuoAnimate) no-repeat;
    background-size: 100% 100%;
    margin-top: -20PX;
  }
}
</style>
