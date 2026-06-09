export default {
    prefix: process.env.QINIU_PREFIX || '',
    prefixhost: process.env.QINIU_PREFIX_HOST || '',
    accessKey: process.env.QINIU_ACCESS_KEY || '',
    secretKey: process.env.QINIU_SECRET_KEY || '',
    space: process.env.QINIU_BUCKET || '',
}
