/**
* @Author: 858834013@qq.com
* @Name: pageTop
* @Date: 2023年12月15日21:58:23
* @Desc:
*/
<template>
  <div class="headers">
    <div class="headerbg"></div>
    <sequence></sequence>
    <div class="headersBody">
      <div class="headerTitlel">
        <span v-if="username" class="username">欢迎, {{ username }}</span>
      </div>
      <div class="headerTitle">
        <span>{{ name }}</span>
      </div>
      <div class="headerTitler">
        <span class="dateTime">{{ formattedTimeWithWeekday }}</span>
        <button class="logout-btn" @click="handleLogout">退出登录</button>
      </div>
    </div>
  </div>
</template>

<script>
import sequence from "./sequence/index.vue";
import dayjs from 'dayjs';
import 'dayjs/locale/zh-cn'; // 导入中文简体语言包
import weekOfYear from 'dayjs/plugin/weekOfYear';
import weekday from 'dayjs/plugin/weekday';

dayjs.extend(weekOfYear);
dayjs.extend(weekday);
dayjs.locale('zh-cn'); // 设置语言为中文简体
export default {
  name: "pageTop",
  components: {sequence},
  data() {
    return {
      currentTime: new Date(),
      username: localStorage.getItem('username') || ''
    }
  },
  props: {
    name: {
      type: String,
      default() {
        return '';
      }
    },
    showTitleLeft: {
      type: Boolean,
      default() {
        return true;
      }
    },
  },
  computed: {
    formattedTimeWithWeekday() {
      return dayjs(this.currentTime)
          .format('YYYY-MM-DD HH:mm:ss dddd'); // 格式化日期和时间
    }
  },
  mounted() {
    setInterval(() => {
      this.currentTime = new Date();
    }, 1000); // 每秒更新时间
  },
  methods: {
    handleLogout() {
      // 清除localStorage中的用户信息
      localStorage.removeItem('userId');
      localStorage.removeItem('username');
      // 跳转到登录页
      this.$router.push('/login');
    }
  }
}
</script>

<style lang="scss" scoped>
.headers {
  width: 100%;
  height: 300px;
  margin-top: -20px;
  z-index: 10;
  display: flex;
  position: fixed;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: nowrap;
  flex-direction: row;
  align-content: flex-start;
}

.headerbg {
  background: #04223d;
  height: 110px;
  width: 100%;
  position: absolute;
  top: 0;
  z-index: -1;
}

.headersBody {
  position: relative;
  z-index: 10;
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  flex-wrap: nowrap;
  flex-direction: row;
  align-content: flex-start;

  .headerTitlel {
    width: 30%;
    display: flex;
    justify-content: flex-start;
    align-items: flex-start;
    flex-wrap: nowrap;
    height: 100%;
    flex-direction: row;
    align-content: flex-start;
    z-index: 100;

    span {
      font-size: 16px;
      font-family: PingFang SC-Bold, PingFang SC;
      font-weight: bold;
      color: #3FD1FF;
      margin-left: 20px;
      margin-top: 25px;
    }
  }

  .headerTitler {
    width: calc(30% - 20px);
    position: relative;
    height: 100%;
    display: flex;
    justify-content: flex-end;
    align-items: flex-start;
    flex-wrap: nowrap;
    flex-direction: row;
    align-content: flex-start;
    margin-right: 20px;
    z-index: 100;

    //span {
    //  font-size: 16px;
    //  font-family: PingFang SC, PingFang SC;
    //  font-weight: 500;
    //  background: linear-gradient(180deg, #AEECFF 0%, #68D2FF 100%);
    //  -webkit-background-clip: text;
    //  -webkit-text-fill-color: transparent;
    //}

    i {
      font-size: 16px;
      font-family: PingFang SC, PingFang SC;
      font-weight: 500;
      background: linear-gradient(180deg, #AEECFF 0%, #68D2FF 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
      margin-left: 20px;
    }

    .dateTime {
      z-index: 10;
      position: relative;
      //background: red;
      font-size: 16px;
      font-family: PingFang SC, PingFang SC;
      font-weight: 500;
      margin-top: 30px;
      margin-right: 20px;
    }

    .logout-btn {
      margin-top: 25px;
      padding: 8px 16px;
      background-color: #f56c6c;
      color: white;
      border: none;
      border-radius: 4px;
      cursor: pointer;
      font-size: 14px;
      transition: background-color 0.3s;
    }

    .logout-btn:hover {
      background-color: #f78989;
    }
  }

  .headerTitlel {
    width: 30%;
    display: flex;
    justify-content: flex-start;
    align-items: flex-start;
    flex-wrap: nowrap;
    height: 100%;
    flex-direction: row;
    align-content: flex-start;
    z-index: 100;

    span {
      font-size: 16px;
      font-family: PingFang SC-Bold, PingFang SC;
      font-weight: bold;
      color: #3FD1FF;
      margin-left: 20px;
      margin-top: 25px;
    }

    .username {
      font-size: 16px;
      font-family: PingFang SC-Bold, PingFang SC;
      font-weight: bold;
      color: #3FD1FF;
      margin-left: 20px;
      margin-top: 25px;
    }
  }

  .headerTitle {
    position: relative;
    z-index: 100;
    height: 50px;
    width: 50%;
    display: flex;
    margin-top: 30px;
    justify-content: center;
    align-items: center;
    flex-wrap: nowrap;
    flex-direction: row;
    align-content: flex-start;

    span {
      font-size: 38px;
      font-family: PangMenZhengDao;
      font-weight: 400;
      line-height: 45px;
      //text-shadow: 0px 4px 6px rgba(0,0,0,0.25);
      background: linear-gradient(180deg, #F2FCFF 0%, #47C8FF 100%);
      -webkit-background-clip: text;
      -webkit-text-fill-color: transparent;
    }
  }
}


</style>
