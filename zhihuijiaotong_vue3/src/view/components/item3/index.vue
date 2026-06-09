<template>
  <div class="itemBodys">
    <echarts1 :list="randomData"></echarts1>
  </div>
</template>

<script>
import echarts1 from './components/echarts.vue'

export default {
  name: "title",
  props: {
    chartData: {
      type: Array,
      default: () => []
    }
  },
  components: {echarts1},
  data() {
    return {
      vehicleTypes: [
        '小型车', '中型车', '大型车',
        '公交车', '出租车', '货车'
      ],
      randomData: []
    }
  },
  mounted() {
    this.generateRandomData()
  },
  methods: {
    generateRandomData() {
      this.randomData = this.vehicleTypes.map(type => ({
        name: type,
        value: Math.floor(Math.random() * (1000 - 100 + 1)) + 100
      }))
    }
  }
}
</script>

<style lang="scss" scoped>
.itemBodys {
  position: relative;
  width: calc(100% - 30px);
  height: calc(100% - 20px);
  margin: 0 auto;
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: nowrap;
  flex-direction: row;
  align-content: flex-start;
}
</style>
