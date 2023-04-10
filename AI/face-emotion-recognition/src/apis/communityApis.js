export const communityApis = {
  /* 게시글 CRUD API */
  BOARD_POST_API: '/app/board',
  BOARD_GET_API: (boardId) => {
    return `/app/board/detail?boardId=${boardId}`;
  },
  BOARD_PUT_API: '/app/board',
  BOARD_DELETE_API: (boardId) => {
    return `/app/board?boardId=${boardId}`;
  },

  /* 게시글 목록 API */
  BOARD_LIST_GET_API: (page, sort, keyword) => {
    return `/app/board/all?page=${page}&sort=${sort}&keyword=${keyword}`;
  },

  /* 인기 게시글 목록 API */
  BOARD_TOP_LIST_GET_API: '/app/board/top',

  /* 게시글 좋아요 API */
  BOARD_LIKE_GET_API: (scriptId) => {
    return `/app/boardlike/check?scriptId=${scriptId}`;
  },
  BOARD_LIKE_POST_API: (boardId) => {
    return `/app/boardlike?boardId=${boardId}`;
  },
  BOARD_LIKE_DELETE_API: (boardId) => {
    return `/app/boardlike?boardId=${boardId}`;
  },

  /* 댓글 API */
  COMMENT_PUT_API: '/app/comment',
  COMMENT_POST_API: '/app/comment',
  COMMENT_DELETE_API: (commentId) => {
    return `/app/comment?commentId=${commentId}`;
  },
  COMMENT_LIST_GET_API: (commentId, page) => {
    return `/app/comment/all?commentId=${commentId}&page=${page}`;
  },
};
