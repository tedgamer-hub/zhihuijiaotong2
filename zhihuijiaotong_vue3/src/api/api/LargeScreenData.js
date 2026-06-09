import request from '../request'
import qs from 'qs'

// get请求实例
export function getData(data) {
    return request({
        url: '/api/wateringTruckCount?' + qs.stringify(data),
        method: 'get',
        data
    })
}

// post请求实例

export function postData(data) {
    return request({
        url: '/api/intelligentService',
        method: 'post',
        data
    })
}
