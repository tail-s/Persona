import React, { useState, useEffect } from 'react';
import { tokenState, user } from '../../states/loginState';
import { useRecoilState, useRecoilValue } from 'recoil';
import { communityApis } from '../../apis/communityApis';
import { postDetailModal, selectedPostState, isHeartState } from '../../states/communityState';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCommentDots, faEye, faHeart as regularHeart } from '@fortawesome/free-regular-svg-icons';
import { faXmark, faEllipsisVertical, faHeart as solidHeart } from '@fortawesome/free-solid-svg-icons';
import style from './PostDetailModal.module.scss';
import Comment from './Comment';
import axios from 'axios';
import moment from 'moment';

export default function Modal() {
  const [showModal, setShowModal] = useRecoilState(postDetailModal);
  const [selectedPost, setSelectedPost] = useRecoilState(selectedPostState);
  const [comments, setComments] = useState([]);
  const BASE_URL = 'https://j8b301.p.ssafy.io';
  const token = useRecoilValue(tokenState);
  const [isHeart, setIsHeart] = useRecoilState(isHeartState);
  const [open, setOpen] = useState(false);
  const boardId = selectedPost.id;
  const [myuser, setMyuser] = useRecoilState(user);
  const [video, setVideo] = useState([]);
  const [isVideo, setIsVideo] = useState(false);

  const shutModal = () => {
    setShowModal(false);
  };

  const clickDropDown = () => {
    setOpen(!open);
  };

  useEffect(() => {
    axios
      .get(BASE_URL + communityApis.BOARD_GET_API(boardId), {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        setVideo(res.data.value.videoUrl);
        if (res.data.value.videoUrl !== null) {
          setIsVideo(true);
        }
        // console.log(res.data.value.videoUrl);
      });
  }, []);

  useEffect(() => {
    const page = 0;
    axios
      .get(BASE_URL + communityApis.COMMENT_LIST_GET_API(boardId, page), {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        setComments(res.data.value.content);
      });
  }, [comments]);

  useEffect(() => {
    axios
      .get(BASE_URL + communityApis.BOARD_LIKE_GET_API(boardId), {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        setIsHeart(res.data.value);
      });
  });

  const heartClickHandler = () => {
    if (isHeart) {
      // 좋아요가 이미 되어있는 경우 취소
      axios
        .delete(BASE_URL + communityApis.BOARD_LIKE_DELETE_API(boardId), {
          headers: {
            Authorization: token,
          },
        })
        .then((res) => {
          setIsHeart(false);
        });
    } else {
      // 좋아요가 되어있지 않은 경우
      axios
        .post(BASE_URL + communityApis.BOARD_LIKE_POST_API(boardId), null, {
          headers: {
            Authorization: token,
          },
        })
        .then((res) => {
          setIsHeart(true);
        });
    }
  };

  const deletePostHandler = () => {
    axios.delete(BASE_URL + communityApis.BOARD_DELETE_API(boardId), {
      headers: {
        Authorization: token,
      },
    });
    shutModal();
  };

  const commentPostHandler = () => {
    const content = document.querySelector(`.${style.commentInput}`).value;
    const data = {
      boardId: boardId,
      content: content,
    };
    axios.post(BASE_URL + communityApis.COMMENT_POST_API, data, {
      headers: {
        Authorization: token,
      },
    });
    document.querySelector(`.${style.commentInput}`).value = '';
  };

  return (
    <>
      {showModal && (
        <div className={style.back}>
          <div className={style.container}>
            <div className={style.body}>
              <div className={style.header}>
                <button className={style.close} onClick={shutModal}>
                  <FontAwesomeIcon icon={faXmark} style={{ color: '#f5f5f5' }} />
                </button>
              </div>
              <div className={style.info}>
                <div className={style.profile}>
                  <img src="user.png" alt="user" width="50" />
                </div>
                <div className={style.userdate}>
                  <div className={style.nickname}>{selectedPost.nickName}</div>
                  <div className={style.date}>
                    {moment.utc(selectedPost.createdDate).utcOffset('+0900').format('YYYY-MM-DD HH:mm:ss')}
                  </div>
                </div>
                <div className={style.btn}>
                  <button className={style.likebtn} onClick={heartClickHandler}>
                    <FontAwesomeIcon icon={isHeart ? solidHeart : regularHeart} style={{ color: '#ce4040' }} />
                  </button>
                  <button className={style.menubtn} onClick={clickDropDown}>
                    <FontAwesomeIcon icon={faEllipsisVertical} style={{ color: '#5d5d5d' }} />
                    {open && (
                      <div className={style.dropdownOptions}>
                        <div className={style.modify} onClick={clickDropDown}>
                          수정
                        </div>
                        <div className={style.delete} onClick={deletePostHandler}>
                          삭제
                        </div>
                      </div>
                    )}
                  </button>
                </div>
              </div>
              <div className={style.title}>{selectedPost.title}</div>
              {isVideo && <video className={style.video} src={video} autoPlay controls></video>}
              <div className={style.content} dangerouslySetInnerHTML={{ __html: selectedPost.content }}></div>
              <div className={style.items}>
                <div className={style.like}>
                  <FontAwesomeIcon icon={regularHeart} style={{ color: '#ce4040' }} />
                  <div className={style.num}>{selectedPost.likeCnt}</div>
                </div>
                <div className={style.visited}>
                  <FontAwesomeIcon icon={faEye} style={{ color: '#5e5e5e' }} />
                  <div className={style.num}>{selectedPost.viewCnt}</div>
                </div>
                <div className={style.comment}>
                  <FontAwesomeIcon icon={faCommentDots} style={{ color: '#5e5e5e' }} />
                  <div className={style.num}>{selectedPost.commentCnt}</div>
                </div>
              </div>
            </div>
            <div className={style.bottom}>
              <div className={style.input}>
                <input className={style.commentInput} type="text" placeholder="댓글을 입력하세요." />
                <button className={style.push} onClick={commentPostHandler}>
                  보내기
                </button>
              </div>
              {comments.map((comment) => (
                <div className={style.commentItem} key={comment.id}>
                  <Comment key={comment.id} {...comment} />
                </div>
              ))}
            </div>
          </div>
        </div>
      )}
    </>
  );
}
