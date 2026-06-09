<template>
  <div class="itemBodys">
    <echarts name="24小时交通趋势" :list="list"></echarts>
  </div>
</template>

<script>
import echarts from "./components/echarts.vue";
import dayjs from 'dayjs';

export default {
  data() {
    return {
      list: []
    }
  },
  components: { echarts },
  mounted() {
    this.getData()
  },
  methods: {
    getData() {
      // 定义24小时时间段
      const timeRanges = [];
      for (let i = 0; i < 24; i++) {
        timeRanges.push(`${i.toString().padStart(2, '0')}:00`);
      }

      // 生成交通流量数据
      this.list = timeRanges.map(time => {
        let value;
        const hour = parseInt(time.split(':')[0]);

        // 模拟真实交通趋势：早晚高峰，午间小高峰
        if (hour >= 7 && hour <= 9) {
          // 早高峰 (7:00-9:00)
          value = Math.floor(Math.random() * (500 - 350 + 1)) + 350;
        } else if (hour >= 17 && hour <= 19) {
          // 晚高峰 (17:00-19:00)
          value = Math.floor(Math.random() * (550 - 400 + 1)) + 400;
        } else if (hour >= 11 && hour <= 13) {
          // 午间小高峰 (11:00-13:00)
          value = Math.floor(Math.random() * (350 - 250 + 1)) + 250;
        } else if (hour >= 0 && hour <= 5) {
          // 深夜时段 (0:00-5:00)
          value = Math.floor(Math.random() * (150 - 50 + 1)) + 50;
        } else {
          // 其他时段
          value = Math.floor(Math.random() * (300 - 150 + 1)) + 150;
        }

        return {
          name: time,
          value: value
        };
      });
    },
  },
}
</script>

<style lang="scss" scoped>
.itemBodys {
  width: 100%;
  position: relative;
  height: calc(100% - 0px);
}

.echartsBody {
  position: relative;
  width: 100%;
  height: calc(100% - 0px);
  display: flex;
  justify-content: flex-start;
  align-items: center;
  flex-wrap: nowrap;
  flex-direction: row;
  align-content: flex-start;
}
</style>
