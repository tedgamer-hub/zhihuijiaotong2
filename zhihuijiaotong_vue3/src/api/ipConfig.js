function normalizeBaseUrl(url) {
  return (url || '').replace(/\/+$/, '')
}

const productionUrl = normalizeBaseUrl(import.meta.env.VITE_API_BASE_URL)
const developmentUrl = ''
const ip = import.meta.env.PROD ? productionUrl : developmentUrl

export const devIp = ip
