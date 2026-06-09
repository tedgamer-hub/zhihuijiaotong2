import axios from 'axios'
// import {Message} from 'element-ui'
import {devIp} from '@/api/ipConfig'
// 刷新token的一个开关，防止重复请求
let isRefreshing = true;

const service = axios.create({
    baseURL: devIp + '', // 测试IP
    timeout: 100000, // request timeout
})

// request interceptor
service.interceptors.request.use(config => {
    // Log all requests for debugging
    console.log('请求配置:', JSON.stringify(config, null, 2))
    console.log('请求URL:', config.baseURL + config.url)
    // 检查请求类型，如果是post或put请求，检查数据
    if (['post', 'put'].includes(config.method.toLowerCase()) && config.data) {
        console.log('请求数据:', JSON.stringify(config.data, null, 2))
        console.log('请求头:', JSON.stringify(config.headers, null, 2))
    }
    let token = localStorage.getItem('access_token')
    if (token) {
        config.headers['Authorization'] = `Bearer ${token}`
        config.headers['Blade-Auth'] = `bearer ${token}`
    }
    // 设置Content-Type为application/json
    config.headers['Content-Type'] = 'application/json'
    return config
}, error => {
    console.log('请求错误:', error)
    return Promise.reject(error)
})

// respone interceptor
service.interceptors.response.use(
    // response => response,
    response => {
        const res = response.data
        return res;
    },
    error => {
        // Check if error.response exists before accessing its properties
        if (error.response) {
            if (error.response.data.code == 401) {
                if (isRefreshing) {
                    isRefreshing = false;
                    return refreshToken().then((res) => {
                        const data = res.data;
                        if (data.access_token) {
                            localStorage.setItem('access_token', data.access_token)
                            // 已经刷新了token，将所有队列中的请求进行重试
                            window.location.reload();
                        } else {
                            return false;
                        }
                    }).catch(() => {
                        return false;
                    }).finally(() => {
                        isRefreshing = true
                    })
                }
            }
        }
        return Promise.reject(error)
    })

// 刷新token请求
function refreshToken() {
    const tenantId = import.meta.env.VITE_AUTH_TENANT_ID || '000000'
    const params = new URLSearchParams({
        tenantId,
        username: import.meta.env.VITE_AUTH_USERNAME || '',
        password: import.meta.env.VITE_AUTH_PASSWORD || '',
        grant_type: 'password',
        scope: 'all',
        type: 'account'
    })
    const headers = {'Tenant-Id': tenantId}
    const basicAuth = import.meta.env.VITE_AUTH_BASIC || ''
    if (basicAuth) {
        headers.Authorization = `Basic ${basicAuth}`
    }
    return axios.post(`${devIp}/api/blade-auth/oauth/token?${params.toString()}`, null, {headers});
}

export default service
