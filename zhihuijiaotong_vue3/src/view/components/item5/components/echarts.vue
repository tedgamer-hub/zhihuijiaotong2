<template>
  <div class="echarts1" ref="echarts">
  </div>
</template>
<script>
import * as echarts from "echarts"
import dot1 from './assets/icon_dot1.png'
import dot2 from './assets/icon_dot2.png'
import {FontChart} from "@/utils/utils.js";

export default {
  name: 'echarts1',
  components: {},
  props: {
    list: {
      type: Array,
      default() {
        return []
      }
    },
    name: {
      type: String,
      default() {
        return ''
      }
    }
  },
  data() {
    return {}
  },
  watch: {
    list() {
      this.drawEcharts()
    }
  },
  mounted() {
    var that = this;
    that.drawEcharts();
  },
  methods: {
    drawEcharts() {
      // 基于准备好的dom，初始化echarts实例
      let myChart = echarts.init(this.$refs.echarts)
      window.addEventListener('resize', () => {
        myChart.resize()
      })
      var option = {
        grid: {
          top: '20px',
          bottom: '20px',
          left: '20px',
          right: '20px',
          containLabel: true
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          },
          backgroundColor: 'transparent',  // 完全透明
          borderColor: 'transparent',      // 完全透明
          borderWidth: 0,                  // 无边框
          padding: 0,                      // 移除内边距
          textStyle: {
            color: '#ffffff'
          },
          extraCssText: 'box-shadow: none;',  // 确保没有阴影
          formatter: function (params) {
            // const item = currentData[params[0].dataIndex];
            return `<div style="
                backdrop-filter: blur(10px);
                -webkit-backdrop-filter: blur(10px);
                background: linear-gradient(
                    135deg,
                    rgba(9,168,193,0.2) 0%,
                    rgba(9,168,193,0.15) 100%
                );
                border-radius: 4px;
                padding: 10px;
                border: 1px solid rgba(50, 150, 250, 0.3);
                box-shadow: none;
            ">
                <div style="
                    font-weight: bold;
                    margin-bottom: 5px;
                    color: rgba(255,255,255,1);
                ">${params[0].name}</div>
                <div style="color: rgba(255,255,255,0.9); margin: 4px 0;">${params[0].seriesName}:<span style="color: #00E5FF">${params[0].data}</span></div>
            </div>

            `;

          }
        },
        legend: {
          top: 0,
          right: 0,
          show: false,
          itemWidth: 10,
          itemHeight: 10,
          textStyle: {
            color: 'rgba(157, 185, 233, 1)',
            fontSize: FontChart(14)
          },
        },
        xAxis: [
          {
            data: this.list.map(obj => obj.name),
            axisLabel: {
              margin: 10,
              color: 'rgba(202, 215, 245, 1)',
              textStyle: {
                fontSize: FontChart(14)
              },
            },
            axisLine: {
              lineStyle: {
                color: 'rgba(134, 146, 185, 0.36)',
                width: 1
              }
            },
            splitLine: {
              show: false,
              lineStyle: {
                color: 'rgba(39, 76, 129, 0.26)',
                width: 1,
              }
            },
            axisTick: {
              show: false
            },
          }],
        yAxis: [
          {
            axisLabel: {
              color: 'rgba(202, 215, 245, 1)',
              textStyle: {
                fontSize: FontChart(14)
              },
            },
            axisLine: {
              lineStyle: {
                color: 'rgba(134, 146, 185, 1)',
              }
            },
            axisTick: {
              show: false
            },
            splitLine: {
              show: true,
              lineStyle: {
                color: 'rgba(39, 76, 129, 0.26)',
                width: 1,
              }
            }
          }],
        series: [
          {
            name: this.name,
            type: 'line',
            data: this.list.map(obj => obj.value),
            smooth: false,
            symbol: 'image://' + dot1, //数值点设定为实心点
            symbolSize: 11, // 折线的点的大小
            itemStyle: {
              normal: {
                color: 'rgba(0, 174, 255, 1)', //点的颜色
                lineStyle: {
                  color: 'rgba(0, 174, 255, 1)', //线的颜色
                  width: 1, // 折线图线条粗细设置
                },
              },
            },
            areaStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [{
                  offset: 0,
                  color: 'rgba(0, 174, 255, 0.5)'
                },
                  {
                    offset: 1,
                    color: 'rgba(0, 240, 203, 0)'
                  }
                ], false),
              }
            },
          },
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
