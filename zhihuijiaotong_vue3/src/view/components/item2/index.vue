<template>
  <div class="itemBodys">
    <VueSeamlessScroll
        class="list"
        :data="sortList"
        direction="top"
        :steep="0.3"
        roller
        :distance="20"
    >
      <div class="listItem" v-for="(item,index) in sortList" :key="index">
        <div class="listItemTop">
          <div class="listItemTopl">
            <span>TOP {{ index + 1 }}</span><span>{{ item.name }}</span>
          </div>
          <div class="listItemTopr"><span>{{ item.num }}</span></div>
        </div>
        <div class="listItemBottom">
          <progressBar :index="index" :item="item"></progressBar>
        </div>
      </div>
    </VueSeamlessScroll>
  </div>
</template>

<script>
import progressBar from './components/progressBar.vue'

export default {
  name: "title",
  data() {
    return {
      roadNames: [
        "人民路", "解放大道", "中山路", "建设路", "和平路",
        "新华大道", "长江路", "黄河路", "东风大道", "西湖路",
        "南京路", "北京路", "上海路", "广州大道", "深圳路",
        "青年路", "文化路", "科技大道", "工业路", "商业街",
        "学院路", "医院路", "公园路", "体育路", "滨江大道",
        "江南大道", "江北路", "沿江路", "环城路", "机场路"
      ],
      localList: []
    }
  },
  components: {progressBar},
  props: {
    // 类型 0所有数据累加作为基数进行占比计算  1取数据中最大的数值作为基数进行计算
    type: {
      type: Number,
      default() {
        return 0;
      }
    },
    list: {
      type: Array,
      default() {
        // 不能在这里调用组件方法，改为返回空数组
        return [];
      }
    },
  },
  computed: {
    sortList: function () {
      // 使用本地列表或传入的列表
      const dataToSort = this.localList.length > 0 ? this.localList : this.list;
      return this.sortKey(dataToSort, 'num');
    },
    total: function () {
      var total = 0;
      const dataToUse = this.localList.length > 0 ? this.localList : this.list;

      if (this.type == 0) {
        total = this.getTotalByKey(dataToUse, 'num');
      } else {
        total = this.getMaxNumByKey(dataToUse, 'num');
      }
      return total;
    },
  },
  watch: {},
  created() {
    // 在created生命周期钩子中生成随机数据
    this.localList = this.generateRandomRoadData();
  },
  mounted() {
  },
  methods: {
    generateRandomRoadData() {
      const data = [];
      // 随机选择10-15条路段
      const roadCount = Math.floor(Math.random() * 6) + 10;

      // 随机选择不重复的路段名
      const selectedRoadIndices = new Set();
      while (selectedRoadIndices.size < roadCount) {
        const randomIndex = Math.floor(Math.random() * this.roadNames.length);
        selectedRoadIndices.add(randomIndex);
      }

      // 为每条路段生成拥堵指数
      selectedRoadIndices.forEach(index => {
        // 生成1-10之间的随机数，保留一位小数
        const congestionIndex = (Math.random() * 9 + 1).toFixed(1);
        data.push({
          name: this.roadNames[index],
          num: parseFloat(congestionIndex)
        });
      });

      return data;
    },
    sortKey(array, key) {
      // 添加检查确保array不为空
      if (!array || array.length === 0) {
        return [];
      }

      var list = array.slice().sort(function (a, b) {
        var x = a[key];
        var y = b[key];
        return ((x < y) ? -1 : (x > y) ? 1 : 0);
      });
      return list.reverse();
    },
    getMaxNumByKey(list, key) {
      // 添加检查确保list不为空
      if (!list || list.length === 0) {
        return 0;
      }

      var maxNum = list[0][key];
      list.forEach((type) => {
        if (type[key] > maxNum) {
          maxNum = type[key];
        }
      });
      return maxNum;
    },
    getTotalByKey(list, key) {
      var total = 0;
      if (list && list.length > 0) {
        list.forEach((type) => {
          total = total + type[key];
        });
      }
      return total;
    },
  },
}
</script>

<style lang="scss" scoped>
.itemBodys {
  position: relative;
  width: calc(100% - 30px);
  margin: 0 auto;
  height: calc(100% - 20px);
  overflow: hidden;

  .list {
    width: calc(100% - 0px);
    height: 100%;

    .listItem {
      font-size: 14px;
      display: flex;
      justify-content: flex-start;
      align-items: flex-start;
      flex-wrap: nowrap;
      flex-direction: column;
      align-content: flex-start;
      width: 100%;

      .listItemTop {
        display: flex;
        justify-content: space-between;
        align-items: center;
        flex-wrap: nowrap;
        width: 100%;
        flex-direction: row;
        height: 50px;
        align-content: flex-start;

        .listItemTopl {
          display: flex;
          justify-content: flex-start;
          align-items: center;
          flex-wrap: nowrap;
          flex-direction: row;
          align-content: flex-start;

          span {
            font-size: 14px;
            font-family: DIN-Bold;
            font-weight: 500;
            color: #FFFFFF;
            text-shadow: 0 0 10px #3873ad;
            margin-right: 20px;
          }
        }

        .listItemTopr {
          display: flex;
          justify-content: flex-end;
          align-items: center;
          flex-wrap: nowrap;
          flex-direction: row;
          align-content: flex-start;
          font-size: 14px;
          font-family: DIN-Bold;
          font-weight: 500;
          color: #FFFFFF;
          text-shadow: 0 0 10px #3873ad;
        }
      }

      .listItemBottom {
        width: 100%;
      }
    }
  }
}

</style>
