<template>
  <div class="titleBodyCom">
    <div class="itemTitle">
      <div class="iconMain">
          <i class="icon" :class="icon"></i>
      </div>
      <div class="itemTitleMain">
        <sequence/>
      <span class="text" v-for="(char, index) in titleArray" :style="{ '--delay': index * 0.1 + 's' }">{{
          char
        }}</span>
      </div>
    </div>
  </div>
</template>

<script>
import sequence from './sequence/index.vue'
export default {
  name: "title",
  data() {
    return {}
  },
  components: {sequence},
  computed: {
    titleArray() {
      return this.name.split('');
    }
  },
  props: {
    name: {
      type: String,
      default() {
        return '';
      }
    },
    icon: {
      type: String,
      default() {
        return '';
      }
    },
    type: {
      type: String,
      default() {
        return '';
      }
    },
  }
}
</script>

<style lang="scss" scoped>

.titleBodyCom {
  width: calc(100% - 0px);
  position: relative;
  height: 45px;
  //max-width: 507px;
  display: flex;
  //background: url("./assets/titlebg.png") no-repeat;
  //background-size: 100% 100%;
  justify-content: flex-start;
  align-items: center;
  flex-wrap: nowrap;
  flex-direction: row;
  z-index: 1;
  align-content: flex-start;

  .itemTitle {
    height: 45px;
    position: relative;
    width: 100%;
    display: flex;
    justify-content: flex-start;
    align-items: center;
    flex-wrap: nowrap;
    flex-direction: row;
    align-content: flex-start;

    .iconMain {
      display: flex;
      justify-content: center;
      align-items: center;
      flex-wrap: nowrap;
      flex-direction: row;
      align-content: flex-start;
      background: url("./assets/iconbg.png") no-repeat;
      background-size: 100% 100%;
      width: 45px;
      height: 45px;

      i{
        font-size: 24px;
        color: transparent;
        background-image: linear-gradient(180deg, #62c4f8, #13afe1);
        background-clip: text;
        -webkit-background-clip: text;
        -webkit-text-fill-color: transparent;
      }
    }

    .itemTitleMain {
      width: calc(100% - 55px);
      margin-left: 10px;
      height: 100%;
      position: relative;
      //background: url("./assets/titlebg.png") no-repeat;
      //background-size: 100% 100%;
      display: flex;
      justify-content: flex-start;
      align-items: center;
      flex-wrap: nowrap;
      flex-direction: row;
      align-content: flex-start;
      padding-left: 20px;
      box-sizing: border-box;
    }

    .text {
      opacity: 0;
      transform: translateY(1em);
      animation: appear 0.5s forwards;
      animation-delay: var(--delay);
      font-family: PangMenZhengDao;
      font-weight: 400;
      font-size: 24px;
      color: #FBFFFF;
    }

  }

  @keyframes appear {
    to {
      opacity: 1;
      transform: translateY(0);
    }
  }

}
</style>