<template>
  <div class="echarts1" ref="echarts">
  </div>
</template>
<script>
import * as echarts from "echarts"

export default {
  name: 'echarts1',
  components: {},
  props: {
    list: {
      type: Array,
      default() {
        return []
      }
    }
  },
  mounted() {
    this.drawEcharts();
  },
  watch: {
    list() {
      this.drawEcharts();
    }
  },
  methods: {
    drawEcharts() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(this.$refs.echarts)
      window.addEventListener("resize", this.drawEcharts);
      // 计算总和，以便于后面计算百分比
      let sum = this.list.reduce((a, b) => a + b.value, 0);
      var option = {
        grid: {
          top: '30px',
          bottom: '0px',
          left: '0px',
          right: '10px',
          containLabel: true
        },

        tooltip: {
          trigger: 'axis',
        },
        legend: {
          top: 0,
          show: false,
          right: 'center',
          itemWidth: 10,
          itemHeight: 10,
          textStyle: {
            color: 'rgba(157, 185, 233, 1)',
            fontSize: '14'
          },
        },
        xAxis: [
          {
            data: this.list.map(obj => obj.name),
            axisLabel: {
              color: 'rgba(156, 187, 223, 1)',
              textStyle: {
                fontSize: 12,
              },
            },
            axisLine: {
              lineStyle: {
                color: 'rgba(50, 144, 178, 0.56)',
                width: 1
              }
            },
            splitLine: {
              show: false,
              lineStyle: {
                color: 'rgba(50, 144, 178, 0.56)',
                width: 1,
              }
            },
            axisTick: {
              show: false
            },
          }],

        yAxis: [
          {
            show: true,
            type: 'value',
            axisLabel: {
              color: 'rgba(156, 187, 223, 1)',
              textStyle: {
                fontSize: 13
              },
            },
            axisLine: {
              lineStyle: {
                color: 'rgba(49, 119, 214, 1)',
              }
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: 'rgba(50, 144, 178, 0.56)',
                width: 1,
                type: 'dashed'
              }
            }
          }],
        series: [
          {
            name: '法人地区统计',
            type: 'bar',
            data: this.list.map(obj => obj.value),
            barWidth: '15',
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: 'rgba(35, 118, 255, 1)'
                }, {
                  offset: 1,
                  color: 'rgba(1, 28, 53, 1)'
                }], false),
                borderColor: 'rgba(56, 140, 255, 1)', // 边线颜色
                borderWidth: 1, // 边线宽度
              },
            },
            label: {
              show: true,
              position: 'top',
              rich: {
                value: {
                  color: 'rgba(255, 255, 255, 1)',
                  fontSize: 14,
                  align: 'center'
                }
              },
              formatter: function (params) {
                return '{value|' + params.value + '}';
              },
            },
          }
        ]
      }
      myChart.clear()
      myChart.resize()
      myChart.setOption(option)
    },
  }
}
</script>

<style lang="scss" scoped>
.echarts1 {
  position: relative;
  width: 100%;
  height: 100%;
}
</style>
