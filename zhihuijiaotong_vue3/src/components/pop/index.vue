<template>
  <div class="popBody">
    <div class="popBut" @click="getShow">
      弹窗
    </div>
    <pop ref="pop" title="视频监控" :append-to-body="true" :z-index="55555">

    </pop>
  </div>
</template>

<script>

import pop from './pop.vue'

export default {
  data() {
    return {}
  },
  components: {pop},
  computed: {},
  methods: {
    getShow() {
      this.$refs.pop.getShow()
    }
  },
}
</script>

<style lang="scss" scoped>
.popBody {
  position: relative;
  width: 100%;
  height: 100%;
}
.popBut {
  display: inline-block; // 使其类似按钮
  padding: 10px 20px; // 内部填充，可以根据需要调整
  margin: 10px; // 外部间距，可根据需要调整
  font-size: 16px; // 文本大小，可以根据需要调整
  color: #fff; // 文本颜色，白色
  background-color: #007bff; // 背景颜色，这里使用的是Bootstrap的按钮蓝色
  border: none; // 无边框
  border-radius: 5px; // 圆角边框
  cursor: pointer; // 鼠标悬停时的指针变为点击状态
  transition: background-color 0.3s ease; // 背景色变化时的过渡效果

  &:hover {
    background-color: #0056b3; // 鼠标悬停时的背景色
  }

  &:active {
    background-color: #004085; // 鼠标点击时的背景色
  }

  &:focus {
    outline: none; // 点击时不显示轮廓
    box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25); // Bootstrap样式的焦点阴影
  }
}
</style>
