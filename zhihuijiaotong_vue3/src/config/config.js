// config/config.js
import data from '../../qiniu-upload-prefiex.js'

var developmentUrl = '' // 开发地址
var productionUrl = '' // 生产地址，使用相对路径
var links = process.env.NODE_ENV === 'production' ? productionUrl : developmentUrl

export const link = links
