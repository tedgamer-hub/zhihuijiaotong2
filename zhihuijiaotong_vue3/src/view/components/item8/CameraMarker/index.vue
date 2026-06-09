<template>
  <div class="mapMonitoring">
    <div class="monitoring" :style="monitoringStyle">
      <i class="icon icon-shexiangtou"></i>
    </div>
    <div class="monitoringBase">

    </div>
  </div>
</template>
<script>
import iconbg from './assets/iconbg.png'
import iconbg_offline from './assets/iconbg_offline.png'

export default {
  name: "index",
  components: {
  },
  data() {
    return {
      iconbg: iconbg, // 将iconbg添加到data中
      iconbg_offline: iconbg_offline, // 将iconbg_offline添加到data中
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
      default: 0 // 假设默认状态为1
    }
  },
  computed: {
    monitoringStyle() {
      return {
        backgroundImage: `url(${this.status == 0 ? this.iconbg_offline : this.iconbg})`
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
      background: -webkit-linear-gradient(#ffffff, #5c84f5);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      display: inline-block;
    }
  }

  .monitoringBase {
    width: 80PX;
    height: 32PX;
    background: url("./assets/dizuoAnimate.png") no-repeat;
    background-size: 100% 100%;
    margin-top: -20PX;
  }
}
</style>
