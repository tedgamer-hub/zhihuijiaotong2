<template>
  <div class="home">
    <top name="交通事故检测系统" class="wow fadeInDown" data-wow-delay="0.2s"></top>
<!--    高德地图-->
    <item8></item8>
    <div class="homeMain">
      <div class="homeMainLeft">
        <LeftPanel>
          <div class="homeMainLeftInner">
            <div class="item1">
              <item name="最近事故信息" icon="icon-liuliang" :duration="0.5" :delay="0">
                <item1></item1>
              </item>
            </div>
          </div>
        </LeftPanel>
      </div>
      <div class="homeMainCenter">
        <!--        数字卡片-->
        <div class="item0 wow fadeInUp">
          <item7></item7>
        </div>
      </div>

    </div>
    <!-- 版权信息 -->
    <div class="copyright">
      <p>作者: 王艺，龙俊安，杨伟豪 | 指导教师：张珠华，侯强，张靖昊（华为） | 版权：沈阳工学院，华为</p>
    </div>
  </div>
</template>

<script setup>
import { onMounted } from 'vue';
import top from "@/components/top/index.vue";
import item from "@/components/item/index.vue";
import item1 from "./components/item1/index.vue";
import item2 from "./components/item2/index.vue";
import item3 from "./components/item3/index.vue";

import item7 from "./components/item7/index.vue";
import item8 from "./components/item8/index.vue";
import WOW from "wow.js";
import LeftPanel from "@/components/Panel2/LeftPanel.vue";


onMounted(() => {
  var wow = new WOW({});
  wow.init();
});
</script>


<style lang="scss" scoped>
.home {
  width: 100%;
  position: fixed;
  height: 100%;
  display: flex;
  justify-content: flex-start;
  align-items: flex-start;
  flex-wrap: nowrap;
  flex-direction: column;
  background: #02142d;
  background-size: 100% 100%;
}

/* 版权信息样式 */
.copyright {
    position: absolute;
    top: 120px;
    right: 10px;
    text-align: right;
    font-size: 24px;
    color: yellow;
    z-index: 20;
    background: rgba(2, 20, 45, 0.8);
    padding: 5px 10px;
    border-radius: 4px;
    border: 1px solid rgba(63, 209, 255, 0.3);
}

.copyright p {
  margin: 0;
  opacity: 0.9;
}

.homeMain {
  position: relative;
  width: calc(100% - 40px);
  margin-top: 90px;
  margin-left: 20px;
  height: calc(100% - 90px);
  display: flex;
  z-index: 10;
  pointer-events: none;
  justify-content: space-between;
  align-items: center;
  flex-wrap: nowrap;
  flex-direction: row;

  .homeMainLeft {
      width: calc(30% - 0px);
      position: relative;
      display: flex;
      height: 100%;
      pointer-events: initial;
      justify-content: space-between;
      align-items: center;
      flex-wrap: nowrap;
      flex-direction: column;
      align-content: flex-start;
      z-index: 10;

      .homeMainLeftInner {
        width: 100%;
        position: relative;
        height: calc(100% - 20px);
        display: flex;
        pointer-events: initial;
        justify-content: space-between;
        align-items: center;
        flex-wrap: nowrap;
        flex-direction: column;
        align-content: flex-start;
        z-index: 10;
      }

      .item1 {
        width: calc(100% - 0px);
        height: 100%;
        position: relative;
      }
    }

  .homeMainCenter {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    flex-wrap: nowrap;
    flex-direction: column;
    align-content: flex-start;
    position: relative;
    width: calc(70% - 0px);
    height: calc(100% - 30px);
    z-index: 10;

    .item0 {
      width: 100%;
      position: relative;
      height: 100px;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-wrap: nowrap;
      flex-direction: row;
      align-content: flex-start;
      background: #093354;
      border: 1px solid #175787;
    }

    .item1 {
      width: 100%;
      height: calc(100% - 320px);
      margin-bottom: 10px;
      position: relative;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-wrap: nowrap;
      flex-direction: row;
      align-content: flex-start;

      img {
        width: 100%;
        height: 100%;
      }
    }

    .item2 {
      width: 100%;
      height: 200px;
      position: relative;
      display: flex;
      justify-content: center;
      align-items: center;
      flex-wrap: nowrap;
      flex-direction: row;
      align-content: flex-start;
    }
  }


}
</style>