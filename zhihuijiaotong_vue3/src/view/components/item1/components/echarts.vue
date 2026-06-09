<template>
  <div class="echartsBody">
    <div class="echartsBody1" ref="echarts1"></div>
  </div>
</template>

<script>
import * as echarts from "echarts"

export default {
  name: 'echarts1',
  components: {},
  data() {
    return {}
  },
  props: {
    list: {
      type: Array,
      default() {
        return [];
      }
    },
    shadowColor: {
      type: String,
      default() {
        return 'rgba(254, 175, 87, 0.5)';
      }
    },
  },
  mounted() {
    var that = this;
    const viewElem = document.body;
    // 监听窗口变化,重绘echarts
    const resizeObserver = new ResizeObserver(() => {
      setTimeout(() => {
        that.drawEcharts();
      }, 300)
    });
    resizeObserver.observe(viewElem);
  },
  computed: {
    colorList: function () {
      var that = this;
      var colorList = []
      that.list.forEach((type) => {
            if (type.checked) {
              var color = new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
                offset: 0,
                color: type.color1
              }, {
                offset: 1,
                color: type.color2
              }])
              colorList.push(color)
            }
          }
      );
      return colorList
    }
  },
  methods: {
    drawEcharts() {
      var that = this
      let myChart = echarts.init(this.$refs.echarts1)
      var colors = this.colorList
      var data = []
      that.list.forEach((type) => {
        if (type.checked) {
          var datas = {
            ...type,
            itemStyle: {
              normal: {
                borderColor: new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
                  offset: 0,
                  color: type.color1
                }, {
                  offset: 1,
                  color: type.color2
                }]),
                shadowColor: that.shadowColor
              }
            }
          }
          data.push(datas)
        }
      });
      var option = {
        color: colors,
        tooltip: {
          trigger: 'item',
          confine: true, // 关键配置，限制在图表区域内
          formatter: '{a} {b} : {c} ({d}%)'
        },
        series: [
          {
            type: 'pie',
            roseType: 'area',
            center: ['50%', '50%'],
            radius: ['0%', '80%'],
            labelLine: {
              normal: {
                length: 20
              }
            },
            label: {
              show: false,
              position: 'inside',
              formatter: '{d}%',
              color: '#fff',
              fontSize: 16
            },
            itemStyle: {
              normal: {
                borderColor: '#3eebc4',
                borderWidth: 0
              }
            },
            data: data
          },
          // 圆点背景
          {
            type: 'pie',
            name: '饼状背景',
            z: 2,
            radius: ['0%', '10%'],
            center: ['50%', '50%'],
            startAngle: 110,
            hoverAnimation: false,
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
                  offset: 0,
                  color: '#f3f7ff'
                }, {
                  offset: 1,
                  color: '#f3f7ff'
                }])
              },
            },
            tooltip: {
              show: false,
            },
            label: {
              show: false
            },
            data: [50]
          },
          // 圆点外围渐变光圈
          {
            type: 'pie',
            name: '饼状背景',
            z: 2,
            radius: ['0%', '25%'],
            center: ['50%', '50%'],
            startAngle: 110,
            hoverAnimation: false,
            itemStyle: {
              normal: {
                color: new echarts.graphic.RadialGradient(.5, .5, 0.5, [{
                  offset: 0,
                  color: 'rgba(233, 239, 255, 0.1)'
                },
                  {
                    offset: 0.5,
                    color: 'rgba(233, 239, 255, 0.1)'
                  }, {
                    offset: 0.9,
                    color: 'rgba(233, 239, 255, 0.42)'
                  },
                  {
                    offset: 1,
                    color: 'rgba(233, 239, 255, 0.6)'
                  }
                ], false),
              }
            },
            tooltip: {
              show: false,
            },
            label: {
              show: false
            },
            data: [50]
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
.echartsBody {
  position: relative;
  width: 50%;
  height: calc(100% - 0px);

  .echartsBody1 {
    position: relative;
    width: 100%;
    height: calc(100% - 0px);
  }

  .echartsBody2 {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: calc(100% - 0px);
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: nowrap;
    flex-direction: column;
    align-content: flex-start;
    pointer-events: none;

    .echartsTitle {
      font-size: 30px;
      font-family: DIN;
      font-weight: normal;
      color: #FFFFFF;
      text-shadow: 0 0 10px rgba(120, 168, 238, 1.00);
    }

    .echartsDesc {
      font-size: 14px;
      font-family: MicrosoftYaHei;
      font-weight: 400;
      color: #9DB9E9;
    }
  }
}
</style>
