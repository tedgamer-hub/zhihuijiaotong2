import {createRouter, createWebHashHistory} from 'vue-router'

const routerHistory = createWebHashHistory();
const router = createRouter({
    history: routerHistory,
    routes: [
        {
            path: '/',
            redirect: '/splash'
        },
        {
            path: '/splash',
            title: '开屏页',
            component: () => import('../view/splash.vue'),
            meta: { requiresAuth: false }
        },
        {
            path: '/login',
            title: '登录',
            component: () => import('../view/auth/login.vue'),
            meta: { requiresAuth: false }
        },
        {
            path: '/register',
            title: '注册',
            component: () => import('../view/auth/register.vue'),
            meta: { requiresAuth: false }
        },
        {
            path: '/home',
            title: '交通事故检测系统',
            component: () => import('../view/home.vue'),
            meta: { requiresAuth: true }
        }
    ]
})

// 路由守卫
router.beforeEach((to, from, next) => {
    // 检查路由是否需要认证
    if (to.meta.requiresAuth) {
        // 检查是否已登录
        const userId = localStorage.getItem('userId');
        if (userId) {
            // 已登录，放行
            next();
        } else {
            // 未登录，重定向到登录页
            next('/login');
        }
    } else {
        // 不需要认证的路由，直接放行
        next();
    }
})

export default router