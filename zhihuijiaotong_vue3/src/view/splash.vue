<template>
  <div class="splash-container">
    <div class="splash-content">
      <h1 class="splash-title">基于端-边-云-AI的智能交通事故检测系统</h1>
      <div class="splash-credit">
        作者: 王艺，龙俊安，杨伟豪 | 指导教师：张珠华，侯强，张靖昊（华为） | 版权：沈阳工学院，华为
      </div>
      <div class="splash-text">
        <p class="text-item">针对城市交通事故发现不及时、信息分散、处置依赖人工经验等问题，本项目建设了一套智能交通事故检测与处置系统。</p>
        <p class="text-item">系统可自动识别交通事故，快速上报并生成事故分析和处置建议，支撑指挥人员及时决策和联动处置。</p>
        <p class="text-item">通过系统化、智能化手段，有效提升交通事故的发现效率、研判能力和应急响应水平。</p>
      </div>
      <img src="@/assets/14f41aeb035ca7133f4a1a5508f58b32.png" alt="智慧交通系统" class="splash-logo" />
      <div class="splash-button">
        <button class="login-button" @click="goToLogin">进入系统</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const goToLogin = () => {
  router.push('/login');
};
</script>

<style scoped>
.splash-container {
  width: 100vw;
  height: 100vh;
  background: #02142d;
  display: flex;
  justify-content: center;
  align-items: center;
  background-size: 100% 100%;
}

.splash-content {
  text-align: center;
  width: 80%;
  max-width: 800px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.splash-title {
  color: #3FD1FF;
  font-size: 28px;
  font-weight: bold;
  margin-bottom: 15px;
  text-align: center;
  text-shadow: 0 0 10px rgba(5, 135, 255, 0.7);
}

.splash-credit {
  color: #3FD1FF;
  font-size: 16px;
  margin-bottom: 30px;
  text-align: center;
  opacity: 0.8;
}

.splash-logo {
  width: 450px; /* 300px * 1.5 */
  height: auto;
  margin-top: 30px;
  margin-bottom: 30px;
  border-radius: 8px;
  box-shadow: 0 0 30px rgba(5, 135, 255, 0.5);
}

.splash-text {
  color: #3FD1FF;
  font-size: 18px;
  line-height: 1.8;
  text-align: left;
  max-width: 700px;
  margin: 0 auto;
}

.text-item {
  margin-bottom: 20px;
  font-size: 16px;
  text-indent: 2em;
}

/* 按钮样式 */
.splash-button {
  margin-top: 30px;
}

.login-button {
  padding: 12px 40px;
  font-size: 18px;
  color: #fff;
  background: linear-gradient(135deg, #0587ff 0%, #3fd1ff 100%);
  border: none;
  border-radius: 30px;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 0 20px rgba(5, 135, 255, 0.5);
}

.login-button:hover {
  transform: translateY(-2px);
  box-shadow: 0 0 30px rgba(5, 135, 255, 0.8);
}

.login-button:active {
  transform: translateY(0);
}

/* 响应式设计 */
@media (max-width: 768px) {
  .splash-logo {
    width: 300px; /* 200px * 1.5 */
  }
  
  .splash-text {
    font-size: 14px;
  }
  
  .text-item {
    font-size: 14px;
  }
  
  .login-button {
    padding: 10px 30px;
    font-size: 16px;
  }
}
</style>