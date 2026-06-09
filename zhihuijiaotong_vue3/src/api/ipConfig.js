const productionUrl = import.meta.env.VITE_API_BASE_URL || ''
const developmentUrl = ''
const ip = import.meta.env.PROD ? productionUrl : developmentUrl

export const devIp = ip
