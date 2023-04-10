import { useEffect, useState } from 'react';
import { mypageApis } from '../../apis/mypageApis';
import { tokenState } from '../../states/loginState';
import { useRecoilValue } from 'recoil';
import style from './MyPost.module.scss';
import Post from '../CommunityPage/Post';
import Comment from '../CommunityPage/Comment';
import axios from 'axios';

export default function MyPost() {
  const [posts, setPosts] = useState([]);
  const [comments, setComments] = useState([]);
  const [isPost, setIsPost] = useState(true);
  const BASE_URL = 'https://j8b301.p.ssafy.io';
  const token = useRecoilValue(tokenState);

  useEffect(() => {
    const page = 0;
    axios
      .get(BASE_URL + mypageApis.MY_BOARD_GET_API(page), {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        setPosts(res.data.value.content);
      });
  }, []);

  const clickPostMenuHandler = () => {
    const page = 0;
    axios
      .get(BASE_URL + mypageApis.MY_BOARD_GET_API(page), {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        setPosts(res.data.value.content);
        setIsPost(true);
      });
  };

  const clickCommentMenuHandler = () => {
    const page = 0;
    axios
      .get(BASE_URL + mypageApis.MY_COMMENT_GET_API(page), {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        setComments(res.data.value.content);
        setIsPost(false);
      });
  };

  return (
    <div className={style.outline}>
      <div className={style.header}>내가 쓴 글</div>
      <div className={style.menu}>
        <div className={style.postMenu} onClick={clickPostMenuHandler}>
          게시글
        </div>
        <div className={style.commentMenu} onClick={clickCommentMenuHandler}>
          댓글
        </div>
      </div>
      {isPost && (
        <div className={style.postContainer}>
          {posts.map((post) => (
            <div key={post.id}>
              <Post key={post.id} {...post} />
            </div>
          ))}
        </div>
      )}
      {!isPost && (
        <div className={style.commentContainer}>
          {comments.map((comment) => (
            <div className={style.commentItem} key={comment.id}>
              <Comment key={comment.id} {...comment} />
            </div>
          ))}
        </div>
      )}
    </div>
  );
}
