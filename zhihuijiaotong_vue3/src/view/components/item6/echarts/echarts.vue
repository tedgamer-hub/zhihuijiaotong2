<template>
  <div class="echartsBody">
    <div class="echartsBody1" ref="echarts1"></div>
    <div class="echartsBg">
      <div class="echartsBgInner"></div>
    </div>
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
    title: {
      type: String,
      default() {
        return '各班组效率';
      }
    },
    total: {
      type: String,
      default() {
        return 0;
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
      var data2 = []
      that.list.forEach((type) => {
        if (type.checked) {
          var datas = {
            ...type,
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
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
      that.list.forEach((type) => {
        if (type.checked) {
          var datas = {
            ...type,
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 1, 1, [{
                  offset: 0,
                  color: type.color1.replace('1)', '0.5)')
                }, {
                  offset: 1,
                  color: type.color2.replace('1)', '0.5)')
                }]),
              }
            }
          }
          data2.push(datas)
        }
      });
      var option = {
        // color: colors,
        tooltip: {
          trigger: 'item',
          // formatter: '{a} {b} : {c} ({d}%)'
        },
        series: [
          {
            type: 'pie',
            center: ['50%', '50%'],
            radius: ['55%', '75%'],
            labelLine: {
              normal: {
                length: 20
              }
            },
            z: 1,
            label: {
              show: true,
              formatter: function (params) {
                return `{a|${params.name}}\n{b|${params.percent}%}`
              },
              rich: {
                a: {
                  fontSize: 14,
                  fontFamily: 'MiSans',
                  fontWeight: 400,
                  color: '#D4F2F1'
                },
                b: {
                  fontSize: 16,
                  fontFamily: 'MiSans',
                  fontWeight: 600,
                  color: '#FFFFFF'
                }
              },
            },
            itemStyle: {
              normal: {
                borderColor: '#142b47',
                borderWidth: 1
              }
            },
            data: data
          },
          {
            z: 0,
            type: 'pie',
            center: ['50%', '48.5%'],
            radius: ['55%', '75%'],
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
                borderColor: '#142b47',
                borderWidth: 1
              }
            },
            data: data2
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
  width: 100%;
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

  .echartsBg {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    flex-wrap: nowrap;
    flex-direction: row;
    align-content: flex-start;

    .echartsBgInner {
      width: 100%;
      height: 100%;
      background: url("./assets/echartsbg.png") center center no-repeat;
      background-size: contain;
      scale: 0.9;
      animation: rotate 3s linear infinite;
    }
  }
}

@keyframes rotate {
  0% {
    transform: rotateZ(0deg); /*从0度开始*/
  }
  100% {
    transform: rotateZ(360deg); /*360度结束*/
  }
}
</style>
