import axios from 'axios';

import { useRecoilValue } from 'recoil';
import { tokenState } from '../states/loginState';

const BASE_URL = 'https://j8b301.p.ssafy.io/app';

const api = axios.create({
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    const token = useRecoilValue(tokenState);
    if (token) {
      config.headers['Authorization'] = `${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  },
);

api.interceptors.response.use(
  (response) => {
    return response;
  },
  (error) => {
    return Promise.reject(error);
  },
);

// headers에 토큰을 추가하는 대신 직접 토큰을 사용할 수 있습니다.
// 예시:
// api.get('/api/some_endpoint', {
// headers: {
//   Authorization: 'Bearer ' + token,
// },
// });

// export const fetchData = {
//   get: async (url, option) => await api.get(url, option),
//   post: async (url, body, option) => await api.post(url, body, option),
//   put: async (url, body, option) => await api.put(url, body, option),
//   delete: async (url, body, option) => await api.delete(url, body, option),
// };

export default api;