<template>
  <div class="register-container">
    <div class="register-form">
      <h2 class="title">交通事故检测系统 - 注册</h2>
      <div class="form-item">
        <label for="username">用户名</label>
        <input type="text" id="username" v-model="registerForm.uname" placeholder="请输入用户名" />
      </div>
      <div class="form-item">
        <label for="password">密码</label>
        <input type="password" id="password" v-model="registerForm.passwor" placeholder="请输入密码" />
      </div>
      <div class="form-item">
        <label for="phone">手机号</label>
        <input type="tel" id="phone" v-model="registerForm.phone_number" placeholder="请输入手机号" />
      </div>
      <div class="form-item">
        <label for="email">邮箱</label>
        <input type="email" id="email" v-model="registerForm.email" placeholder="请输入邮箱" />
      </div>
      <div class="form-actions">
        <button @click="handleRegister" class="btn register-btn">注册</button>
        <button @click="$router.push('/login')" class="btn login-btn">返回登录</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import { register } from '../../api/api/user';

const router = useRouter();
const registerForm = reactive({
  uname: '',
  passwor: '',
  phone_number: '',
  email: ''
});

const handleRegister = async () => {
  try {
    // 表单验证
    if (!registerForm.uname || !registerForm.passwor || !registerForm.phone_number || !registerForm.email) {
      alert('所有字段不能为空');
      return;
    }
    
    // 简单的邮箱格式验证
    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(registerForm.email)) {
      alert('请输入有效的邮箱地址');
      return;
    }
    
    // 简单的手机号格式验证（11位数字）
    const phoneRegex = /^1[3-9]\d{9}$/;
    if (!phoneRegex.test(registerForm.phone_number)) {
      alert('请输入有效的手机号');
      return;
    }
    
    const response = await register(registerForm);
    
    if (response.code === 200) {
      alert('注册成功');
      router.push('/login');
    } else {
      alert(response.message || '注册失败');
    }
  } catch (error) {
    console.error('注册请求失败:', error);
    alert('注册失败，请稍后重试');
  }
};
</script>

<style scoped>
.register-container {
  width: 100vw;
  height: 100vh;
  background: #02142d;
  display: flex;
  justify-content: center;
  align-items: center;
  background-size: 100% 100%;
}

.register-form {
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

.register-btn {
  background-color: #093354;
  color: #3FD1FF;
}

.register-btn:hover {
  background-color: #175787;
  box-shadow: 0 0 10px rgba(5, 135, 255, 0.5);
}

.login-btn {
  background-color: #093354;
  color: #3FD1FF;
}

.login-btn:hover {
  background-color: #175787;
  box-shadow: 0 0 10px rgba(5, 135, 255, 0.5);
}
</style>