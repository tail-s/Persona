import axios from 'axios';
import React from 'react';
import style from './PostWriteModal.module.scss';
import { useRecoilState, useRecoilValue } from 'recoil';
import { communityApis } from '../../apis/communityApis';
import { postWriteModal, videoModal, selectedVideo } from '../../states/communityState';
import QuillEditor from './QuillEditor';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCirclePlay } from '@fortawesome/free-regular-svg-icons';
import VideoModal from './VideoModal';
import { tokenState } from '../../states/loginState';

export default function Modal() {
  const [showModal, setShowModal] = useRecoilState(postWriteModal);
  const [showVideoModal, setShowVideoModal] = useRecoilState(videoModal);
  const token = useRecoilValue(tokenState);
  const selectVideo = useRecoilValue(selectedVideo);

  const closeModal = () => {
    setShowModal(false);
  };

  const openVideoModal = (props) => {
    setShowVideoModal(true);
  };

  const BASE_URL = 'https://j8b301.p.ssafy.io';
  const writePost = () => {
    const title = document.querySelector(`.${style.input}`).value; // 제목
    const content = document.querySelector(`.ql-editor`).innerHTML; // 내용
    const data = {
      title: title,
      content: content,
      videoId: selectVideo,
    };
    axios
      .post(BASE_URL + communityApis.BOARD_POST_API, data, {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        console.log(res);
      })
      .then(() => {
        window.location.replace('community');
      });

    closeModal();
  };

  return (
    <>
      {showModal && (
        <div className={style.back}>
          <div className={style.container}>
            <div className={style.header}>
              <div className={style.title}>글쓰기</div>
            </div>
            <input className={style.input} type="text" placeholder="제목을 입력하세요." />
            <QuillEditor />
            <div className={style.bottom}>
              <div className={style.video} onClick={openVideoModal}>
                <FontAwesomeIcon icon={faCirclePlay} style={{ color: '#5c5c5c' }} />
                <div className={style.pull}>내 영상 가져오기</div>
              </div>
              <button className={style.close} onClick={closeModal}>
                취소
              </button>
              <button className={style.write} onClick={writePost}>
                게시
              </button>
            </div>
          </div>
        </div>
      )}
      {showVideoModal && <VideoModal closeModal={() => setShowVideoModal(false)} />}
    </>
  );
}
