export const mypageApis = {
  /* 내가 쓴글 API */
  MY_BOARD_GET_API: (page) => {
    return `/app/board/my?page=${page}`;
  },
  MY_COMMENT_GET_API: (page) => {
    return `/app/comment/my?page=${page}`;
  },
  MY_BOOKMARK_GET_API: (page) => {
    return `/app/bookmark/my?page=${page}`;
  },
};
