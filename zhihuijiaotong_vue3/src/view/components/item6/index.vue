<template>
  <div class="echartsBodys">
    <div class="echartsTop">
      <echarts1 title="信号灯运行状态" :total="total" :list="list" ref="echarts"></echarts1>
    </div>
    <div class="list">
      <div class="listItem cur" @click="getChecked(index)" v-for="(item,index) in list" :key="index">
        <div class="listIteml">
          <div class="dot" :class="{disabled:!item.checked}"
               :style="'background: linear-gradient(0deg,'+item.color1+' 0%, '+item.color2+' 100%);'"></div>
          <span :class="{disabled:!item.checked}">{{ item.name }}</span>
        </div>
        <div class="num"
             v-if="item.checked">
          {{ item.percentage }}%
        </div>
      </div>
    </div>
  </div>
</template>

<script>

import echarts1 from "./echarts/echarts.vue";

export default {
  name: "title",
  data() {
    return {
      // 颜色格式要用rgba格式,以便于echarts根据颜色设置透明度为0.8的背景形成立体饼状图
      list: [
        {
          name: '正常',
          value: 0,
          checked: true,
          color1: 'rgba(101, 252, 125, 1)', // 绿色
          color2: 'rgba(101, 252, 125, 1)',
          percentage: 0
        },
        {
          name: '故障',
          value: 0,
          checked: true,
          color1: 'rgba(252, 108, 126, 1)', // 红色
          color2: 'rgba(252, 108, 126, 1)',
          percentage: 0
        },
        {
          name: '维护中',
          value: 0,
          checked: true,
          color1: 'rgba(254, 246, 71, 1)', // 黄色
          color2: 'rgba(254, 246, 71, 1)',
          percentage: 0
        }
      ]
    }
  },
  computed: {
    total: function () {
      var total = 0
      this.list.forEach((type) => {
        total += type.value
      });
      return total
    },
  },
  watch: {
    total: function (newTotal, oldTotal) {
      this.list.forEach((type) => {
        type.percentage = Math.floor((type.value / this.total) * 100);
      });
    }
  },
  components: {echarts1},
  mounted() {
    this.generateRandomData();
  },
  methods: {
    generateRandomData() {
      // 生成总数在500-1000之间的随机数
      const totalSignals = Math.floor(Math.random() * (1000 - 500 + 1)) + 500;

      // 正常信号灯占比较高(70-90%)
      const normalPercentage = Math.floor(Math.random() * (90 - 70 + 1)) + 70;
      const normalCount = Math.floor(totalSignals * normalPercentage / 100);

      // 故障信号灯占比较低(2-10%)
      const faultPercentage = Math.floor(Math.random() * (10 - 2 + 1)) + 2;
      const faultCount = Math.floor(totalSignals * faultPercentage / 100);

      // 维护中信号灯为剩余部分
      const maintenanceCount = totalSignals - normalCount - faultCount;

      // 更新数据
      this.list[0].value = normalCount;
      this.list[1].value = faultCount;
      this.list[2].value = maintenanceCount;

      // 计算百分比
      this.list.forEach((type) => {
        type.percentage = Math.floor((type.value / totalSignals) * 100);
      });

      // 重绘图表
      this.$nextTick(() => {
        if (this.$refs.echarts) {
          this.$refs.echarts.drawEcharts();
        }
      });
    },
    getChecked(index) {
      this.list[index].checked = !this.list[index].checked
      this.$refs.echarts.drawEcharts()
    },
  },
}
</script>

<style lang="scss" scoped>


.echartsBodys {
  width: 100%;
  position: relative;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: nowrap;
  flex-direction: column;
  align-content: flex-start;

  .list {
    position: relative;
    width: 100%;
    height: 50px;
    //background: url("./assets/jiaobiaobg.png") no-repeat;
    //background-size: 100% 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: wrap;
    flex-direction: row;
    align-content: flex-start;
    margin-top: 20px;

    .listItem {
      font-size: 14px;
      display: flex;
      height: 50%;
      width: calc(33.33% - 20px);
      margin: 0 auto;
      justify-content: flex-start;
      align-items: center;
      flex-wrap: nowrap;
      flex-direction: row;
      align-content: flex-start;

      .listIteml {
        display: flex;
        justify-content: flex-start;
        align-items: center;
        flex-wrap: nowrap;
        flex-direction: row;
        align-content: flex-start;
        font-size: 14px;
        font-family: MicrosoftYaHei;
        font-weight: 400;
        color: #FFFFFF;
        width: 340px;
      }

      span {
        font-size: 14px;
        font-family: MiSans;
        font-weight: 400;
        color: #D4F2F1;
      }

      .dot {
        width: 4px;
        height: 14px;
        background: #FEF647;
        border-radius: 2px;
        margin-right: 10px;
      }

      .dot.disabled {
        background: rgba(#999, 0.8) !important;
      }

      span.disabled {
        color: rgba(#999, 0.8) !important;
      }

      .num {
        font-size: 14px;
        font-family: MiSans;
        font-weight: 400;
        color: #FFFFFF;
      }
    }
  }

  .echartsTop {
    position: relative;
    width: 100%;
    height: calc(100% - 50px);
  }
}

</style>
