<template>
  <div class="info">
    <div class="infoItem" v-for="(item, index) in list" :key="index">
      <div class="infoIteml">
        <i :class="getIconClass(index)" :style="getIconStyle(item.color2, item.color)" :ref="'icon-' + index"></i>
        <bg :img-path="getThemeColor(index)"></bg>
        <lizi :img-path="getThemeColor(index)"></lizi>
      </div>
      <div class="infoItemr">
        <div class="num">
          <p :style="{ color: item.color }">{{ item.num }}</p>
          <span class="unit">{{ item.unit }}</span>
        </div>
        <div class="title">{{ item.title }}</div>
        <div class="subTitle">{{ item.subTitle }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import bg from './bg/index.vue'
import lizi from './bg/lizi.vue'
import {gsap} from 'gsap';
import request from '../../../api/request';

export default {
  name: "index",
  components: {
    bg,
    lizi
  },
  data() {
    return {
      list: [{ 
        title: '总事故数',
        subTitle: '累计交通事故',
        num: 0,
        unit: '起',
        color: 'rgba(255, 181, 109, 1)',
        color2: '#ffe9d5'
      }, {
        title: '严重事故',
        subTitle: '重大交通事故',
        num: 0,
        unit: '起',
        color: 'rgba(255, 87, 34, 1)',
        color2: '#ffd7cc'
      }, {
        title: '今日新增',
        subTitle: '今日交通事故',
        num: 0,
        unit: '起',
        color: 'rgba(255, 193, 7, 1)',
        color2: '#fff3e0'
      }]
    }
  },
  mounted() {
    this.animateIcons();
    this.fetchAccidentStats();
    // 每30秒更新一次数据
    setInterval(() => {
      this.fetchAccidentStats();
    }, 30000);
  },
  methods: {
    fetchAccidentStats() {
      request({
        url: '/api/v1/accidents/stats',
        method: 'get'
      })
        .then(response => {
          const { totalAccidents, severeAccidents, todayAccidents } = response.data;
          this.list[0].num = totalAccidents;
          this.list[1].num = severeAccidents;
          this.list[2].num = todayAccidents;
        })
        .catch(error => {
          console.error('Failed to fetch accident stats:', error);
        });
    },
    getThemeColor(index) {
      const themes = ['hong', 'hong', 'huang', 'huang', 'lan', 'lv'];
      return themes[index] || 'lan';
    },
    getIconClass(index) {
      const icons = [
        'icon-shigu',
        'icon-shigu',
        'icon-shigu',
        'icon-yongdu',
        'icon-chesu',
        'icon-zhishu'
      ];
      return icons[index] || 'icon-traffic';
    },
    getIconStyle(color, color2) {
      return {
        background: `linear-gradient(to bottom, ${color}, ${color2})`,
        '-webkit-background-clip': 'text',
        '-webkit-text-fill-color': 'transparent'
      };
    },
    animateIcons() {
      this.list.forEach((item, index) => {
        const icon = this.$refs['icon-' + index];
        if (icon) {
          gsap.to(icon, {
            y: -10,
            repeat: -1,
            yoyo: true,
            ease: 'power1.inOut',
            duration: 1
          });
        }
      });
    },

  }
}
</script>

<style lang="scss" scoped>
.info {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  flex-direction: row;
  align-content: flex-start;
  position: relative;

  .infoItem {
    width: calc(33.33% - 10px);
    height: calc(100% - 0px);
    //background: url("./assets/biankuangAnimate.png") no-repeat;
    //background-size: 100% 100%;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    flex-wrap: nowrap;
    flex-direction: row;
    align-content: flex-start;

    .infoIteml {
      width: 89px;
      height: 80px;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-wrap: nowrap;
      flex-direction: row;
      align-content: flex-start;
      position: relative;

      /* 字体图标样式 */
      i {
        position: absolute;
        z-index: 10;
        font-size: 30px; /* 根据需要调整字体图标大小 */
        /* 渐变效果通过JS绑定 */
      }
    }

    .unit {
      margin-top: 5px;
    }

    .infoItemr {
      display: flex;
      justify-content: flex-start;
      margin-left: 0px;
      align-items: flex-start;
      flex-wrap: nowrap;
      flex-direction: column;
      align-content: flex-start;

      .num {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        flex-wrap: nowrap;
        flex-direction: row;
        align-content: flex-start;

        p {
          font-size: 24px;
          font-family: DIN, DIN;
          font-weight: bold;
          color: #31FFFD;
        }

        span {
          font-size: 14px;
          font-family: PingFang SC, PingFang SC;
          font-weight: 500;
          color: #5b7083;
          margin-left: 5px;
        }
      }

      .title {
        font-size: 14px;
        font-family: PingFang SC, PingFang SC;
        font-weight: 500;
        color: #FFFFFF;
      }
    }
  }
}
</style>
