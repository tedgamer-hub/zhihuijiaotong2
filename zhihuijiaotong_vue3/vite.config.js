import {defineConfig} from 'vite';
import vue from '@vitejs/plugin-vue';


export default ({mode}) => {
    return defineConfig({
        plugins: [
            vue()],
        server: {
            port: 8230, // 新的端口号
            proxy: {
                '/api': {
                    target: 'http://localhost:8080',
                    changeOrigin: true,
                    rewrite: (path) => `/IntelliDetect${path}`
                }
            }
        },
        base: '/',
        resolve: {
            alias: {
                '@': '/src',
                // 其他别名
            },
        },
        css: {
            preprocessorOptions: {
                scss: {
                    api: 'modern', // 使用新版API
                    silenceDeprecations: ['legacy-js-api'], // 临时消除警告
                    sassOptions: {
                        outputStyle: 'compressed',
                    },
                },
            },
        }
    });
};
