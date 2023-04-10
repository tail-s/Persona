const BASE_URL = 'https://j8b301.p.ssafy.io/app';

export const videoApis = {
    
    VIDEO_GET_API: (page) => {
      return BASE_URL + `/video?page=${page}`;
    },
};