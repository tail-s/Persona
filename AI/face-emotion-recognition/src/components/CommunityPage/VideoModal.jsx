import axios from 'axios';
import React, { useEffect, useState } from 'react';
import style from './VideoModal.module.scss';
import { useRecoilState, useRecoilValue } from 'recoil';
import { videoModal, selectedVideo } from '../../states/communityState';
import { videoApis } from '../../apis/videoApis';
import { tokenState } from '../../states/loginState';
import Video from './Video';

export default function Modal() {
  const [showModal, setShowModal] = useRecoilState(videoModal);
  const [videos, setVideos] = useState([]);
  const [selectVideo, setSelectVideo] = useRecoilState(selectedVideo);
  const token = useRecoilValue(tokenState);

  const shutModal = () => {
    setShowModal(false);
  };

  useEffect(() => {
    const page = 0;
    axios
      .get(videoApis.VIDEO_GET_API(page), {
        headers: { Authorization: token },
      })
      .then((res) => {
        setVideos(res.data.value.content);
      });
  }, []);

  const handleWrite = () => {
    setSelectVideo(selectVideo.id);
    shutModal();
  };

  return (
    <>
      {showModal && (
        <div className={style.back}>
          <div className={style.container}>
            <div className={style.header}>
              <div className={style.title}>내 영상 가져오기</div>
            </div>
            <div className={style.videos}>
              {videos.map((video) => (
                <Video key={video.id} {...video} onSelect={setSelectVideo} />
              ))}
            </div>
            <div className={style.bottom}>
              <button className={style.close} onClick={shutModal}>
                취소
              </button>
              <button className={style.write} onClick={handleWrite}>
                확인
              </button>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
