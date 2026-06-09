import request from '../request'

// 用户登录
export function login(data) {
  return request({
    url: '/api/v1/users/login',
    method: 'post',
    data
  })
}

// 用户注册
export function register(data) {
  return request({
    url: '/api/v1/users/register',
    method: 'post',
    data
  })
}

// 根据用户名获取用户信息
export function getUserInfoByUsername(uname) {
  return request({
    url: `/api/v1/users/info/${uname}`,
    method: 'get'
  })
}