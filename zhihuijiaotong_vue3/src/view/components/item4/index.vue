<template>
  <div class="listCom">
    <vue-seamless-scroll
        class="scrollBody"
        :data="warningList"
        direction="top"
        :steep="0.3"
        roller
        :distance="20"
    >
      <div
          class="listItem"
          v-for="(item, index) in warningList"
          :key="index"
          :class="getWarningClass(item.level)"
      >
        <div class="line" :style="getLineStyle(item.level)"></div>
        <div class="rankNum" :style="getNumStyle(item.level)">
          {{ getWarningIcon(item.level) }}
        </div>
        <div class="content">
          <div class="content-row">
            <span class="unit">{{ item.location }}</span>
            <span class="status" :style="getStatusStyle(item.level)">
              {{ item.time }}
            </span>
          </div>
          <div class="content-row">
            <span class="desc">{{ item.type }}</span>
            <span class="time">{{ item.description }}</span>
          </div>
        </div>
      </div>
    </vue-seamless-scroll>
  </div>
</template>

<script>
export default {
  name: "WarningList",
  data() {
    return {
      warningColors: {
        high: '#ff4d4f',
        medium: '#faad14',
        low: '#1890ff',
        normal: '#52c41a'
      },
      warningList: [
        {
          level: 'high',
          location: '浦东大道近张杨路段',
          type: '交通事故',
          description: '早高峰拥堵，车辆缓慢行驶',
          time: '09:33'
        },
        {
          level: 'medium',
          location: '虹桥机场附近',
          type: '交通管制',
          description: '一辆公交车抛锚，占用一条车道',
          time: '09:33'
        },
        {
          level: 'medium',
          location: '人民路与解放大道交叉口',
          type: '交通管制',
          description: '一辆公交车抛锚，占用一条车道',
          time: '09:33'
        },
        {
          level: 'low',
          location: '中山北路隧道入口',
          type: '道路施工',
          description: '信号灯故障，交警正在指挥',
          time: '09:33'
        },
        {
          level: 'high',
          location: '延安高架路出口',
          type: '交通事故',
          description: '早高峰拥堵，车辆缓慢行驶',
          time: '09:33'
        },
        {
          level: 'medium',
          location: '世纪大道金融区',
          type: '交通管制',
          description: '一辆公交车抛锚，占用一条车道',
          time: '09:33'
        },
        {
          level: 'low',
          location: '南京西路商圈',
          type: '交通拥堵',
          description: '车流量大，行驶缓慢',
          time: '09:32'
        },
        {
          level: 'normal',
          location: '徐家汇地铁站附近',
          type: '交通正常',
          description: '车辆通行顺畅',
          time: '09:32'
        },
        {
          level: 'medium',
          location: '外滩观光区',
          type: '临时封路',
          description: '大型活动，临时交通管制',
          time: '09:31'
        },
        {
          level: 'high',
          location: '内环高架路段',
          type: '交通事故',
          description: '发生追尾事故，占用应急车道',
          time: '09:30'
        }
      ]
    };
  },
  methods: {
    getWarningClass(level) {
      switch (level) {
        case 'high':
          return 'gradient-bg-high';
        case 'medium':
          return 'gradient-bg-medium';
        case 'low':
          return 'gradient-bg-low';
        case 'normal':
          return 'gradient-bg-normal';
        default:
          return '';
      }
    },
    getNumStyle(level) {
      return {
        color: this.warningColors[level]
      }
    },
    getLineStyle(level) {
      return {
        backgroundColor: this.warningColors[level]
      }
    },
    getStatusStyle(level) {
      return {
        color: this.warningColors[level],
        borderColor: this.warningColors[level]
      }
    },
    getWarningIcon(level) {
      switch (level) {
        case 'high':
          return '!';
        case 'medium':
          return '⚠';
        case 'low':
          return '⚠';
        case 'normal':
          return '✓';
        default:
          return '';
      }
    },
    getWarningText(level) {
      switch (level) {
        case 'high':
          return '紧急';
        case 'medium':
          return '警告';
        case 'low':
          return '注意';
        case 'normal':
          return '正常';
        default:
          return '';
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.listCom {
  width: 100%;
  height: 100%;
  overflow: hidden;

  .scrollBody {
    height: 100%;

    .listItem {
      height: 60px; // 增加高度以适应两行
      display: flex;
      align-items: center;
      padding: 0 10px;
      box-sizing: border-box;
      margin-bottom: 10px;
      position: relative;
      width: calc(100% - 20px);
      margin-left: 10px;

      &.gradient-bg-high {
        background: linear-gradient(to right, rgba(255, 77, 79, 0.15), rgba(255, 77, 79, 0));
      }

      &.gradient-bg-medium {
        background: linear-gradient(to right, rgba(250, 173, 20, 0.15), rgba(250, 173, 20, 0));
      }

      &.gradient-bg-low {
        background: linear-gradient(to right, rgba(24, 144, 255, 0.15), rgba(24, 144, 255, 0));
      }

      &.gradient-bg-normal {
        background: linear-gradient(to right, rgba(82, 196, 26, 0.15), rgba(82, 196, 26, 0));
      }

      .line {
        position: absolute;
        left: 0;
        top: 50%;
        transform: translateY(-50%);
        width: 3px;
        height: 50px; // 增加高度
      }

      .rankNum {
        width: 24px;
        height: 24px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-weight: bold;
        font-size: 16px;
        position: relative;
        z-index: 1;
        margin-left: 10px;
        border-radius: 50%;
        background-color: rgba(0, 0, 0, 0.2);
        align-self: flex-start;
        margin-top: 18px; // 垂直居中调整
      }

      .content {
        flex: 1;
        display: flex;
        flex-direction: column; // 改为纵向排列
        justify-content: center;
        margin-left: 15px;
        position: relative;
        z-index: 1;

        .content-row {
          display: flex;
          align-items: center;
          justify-content: space-between;
          width: 100%;
          margin-bottom: 5px;
        }

        .unit {
          font-family: MicrosoftYaHei;
          font-weight: 400;
          font-size: 14px;
          color: #FFFFFF;
          flex: 1;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }

        .desc {
          font-family: MicrosoftYaHei;
          font-weight: 400;
          font-size: 14px;
          color: #83bff6;
          margin-right: 10px;
          width: 80px;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }

        .time {
          font-family: MicrosoftYaHei;
          font-weight: 400;
          font-size: 14px;
          color: rgba(255, 255, 255, 0.6);
          flex: 1;
          white-space: nowrap;
          overflow: hidden;
          text-overflow: ellipsis;
        }
      }

      .status {
        padding: 2px 8px;
        border-radius: 10px;
        font-size: 12px;
        font-weight: bold;
        border: 1px solid;
        background-color: rgba(0, 0, 0, 0.2);
        margin-left: 10px;
      }
    }
  }
}

// 为高级别警告添加闪烁动画
@keyframes blink {
  0%, 100% {
    opacity: 1;
  }
  50% {
    opacity: 0.6;
  }
}

.gradient-bg-high {
  animation: blink 1.5s infinite;
}
</style>
