<template>
  <div class="login-container">
    <div class="login-form">
      <h2 class="title">交通事故检测系统</h2>
      <div class="form-item">
        <label for="username">用户名</label>
        <input type="text" id="username" v-model="loginForm.uname" placeholder="请输入用户名" />
      </div>
      <div class="form-item">
        <label for="password">密码</label>
        <input type="password" id="password" v-model="loginForm.passwor" placeholder="请输入密码" />
      </div>
      <div class="form-actions">
        <button @click="handleLogin" class="btn login-btn">登录</button>
        <button @click="$router.push('/register')" class="btn register-btn">注册</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue';
import { useRouter } from 'vue-router';
import { login } from '../../api/api/user';

const router = useRouter();
const loginForm = reactive({
  uname: '',
  passwor: ''
});

const handleLogin = async () => {
  try {
    if (!loginForm.uname || !loginForm.passwor) {
      alert('用户名和密码不能为空');
      return;
    }
    
    const response = await login(loginForm);
    
    if (response.code === 200) {
      // 登录成功，保存用户信息
      localStorage.setItem('userId', response.data.id);
      localStorage.setItem('username', loginForm.uname);
      alert('登录成功');
      router.push('/home');
    } else {
      alert(response.message || '登录失败');
    }
  } catch (error) {
    console.error('登录请求失败:', error);
    alert('登录失败，请稍后重试');
  }
};
</script>

<style scoped>
.login-container {
  width: 100vw;
  height: 100vh;
  background: #02142d;
  display: flex;
  justify-content: center;
  align-items: center;
  background-size: 100% 100%;
}

.login-form {
  width: 400px;
  padding: 40px;
  background: #093354;
  border: 1px solid #175787;
  border-radius: 8px;
  box-shadow: 0 0 20px rgba(5, 135, 255, 0.3);
}

.title {
  text-align: center;
  margin-bottom: 30px;
  color: #3FD1FF;
  font-size: 24px;
  font-weight: bold;
  background: linear-gradient(180deg, #F2FCFF 0%, #47C8FF 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
}

.form-item {
  margin-bottom: 20px;
}

.form-item label {
  display: block;
  margin-bottom: 8px;
  color: #3FD1FF;
  font-size: 14px;
  font-weight: bold;
}

.form-item input {
  width: 100%;
  padding: 12px;
  background: #02142d;
  border: 1px solid #175787;
  border-radius: 4px;
  font-size: 16px;
  box-sizing: border-box;
  color: white;
  transition: all 0.3s;
}

.form-item input:focus {
  outline: none;
  border-color: #3FD1FF;
  box-shadow: 0 0 10px rgba(5, 135, 255, 0.5);
}

.form-actions {
  display: flex;
  justify-content: space-between;
  margin-top: 30px;
}

.btn {
  width: 48%;
  padding: 12px;
  border: 1px solid #175787;
  border-radius: 4px;
  font-size: 16px;
  cursor: pointer;
  transition: all 0.3s;
  font-weight: bold;
}

.login-btn {
  background-color: #093354;
  color: #3FD1FF;
}

.login-btn:hover {
  background-color: #175787;
  box-shadow: 0 0 10px rgba(5, 135, 255, 0.5);
}

.register-btn {
  background-color: #093354;
  color: #3FD1FF;
}

.register-btn:hover {
  background-color: #175787;
  box-shadow: 0 0 10px rgba(5, 135, 255, 0.5);
}
</style>