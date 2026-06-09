const productionUrl = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/IntelliDetect'
const developmentUrl = ''
const ip = import.meta.env.PROD ? productionUrl : developmentUrl

export const devIp = ip
