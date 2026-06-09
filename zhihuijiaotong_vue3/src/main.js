import { createApp } from 'vue';
import './style.css';
import './utils/flexible';
import App from './App.vue';
import router from './router';
import 'wow.js/css/libs/animate.css';
import './fonts/icomoon/style.css';
import { VueSeamlessScroll } from '@meruem117/vue-seamless-scroll';
import DisableDevtool from 'disable-devtool';
import ElementPlus from 'element-plus'; // 引入 Element Plus
import zhCn from 'element-plus/es/locale/lang/zh-cn'; // 引入中文语言包
import 'element-plus/dist/index.css'; // 引入 Element Plus 样式
import 'element-plus/theme-chalk/dark/css-vars.css'; // 引入 Element Plus 暗黑主题样式
import mitt from 'mitt';

const app = createApp(App);

// 创建事件总线
const bus = mitt();
app.config.globalProperties.$bus = bus;

// 检查主域是否为 wanjunshijie.com
if (window.location.hostname.includes('wanjunshijie.com')) {
    // 启用 disable-devtool
    DisableDevtool({
        disableMenu: true, // 禁用右键菜单
        disableCut: true, // 禁用剪切
        disablePaste: true, // 禁用粘贴
        disableF12: true, // 禁用 F12
        disableDevTools: true, // 禁用开发者工具
        ondevtoolopen: () => {
            window.location.href = 'https://www.wanjunshijie.com/project/3986.html';
        },
    });
}

app.use(router);
app.use(VueSeamlessScroll);
app.use(ElementPlus, {
    locale: zhCn, // 设置为中文
});
app.mount('#app');