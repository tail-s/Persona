import React from 'react';
import style from './BookScript.module.scss';
import { useNavigate } from 'react-router-dom';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faHeart, faEye, faUsers } from '@fortawesome/free-solid-svg-icons';
import { faHeart as empty } from '@fortawesome/free-regular-svg-icons';
import { useRecoilState } from 'recoil';
import { detailState } from '../../states/practiceFilterState';
import moment from 'moment';

export default function Script({ id, actor, author, createdDate, emotion, genre, title, viewCnt, participantCnt }) {
  const [detail, setDetail] = useRecoilState(detailState);
  const navigate = useNavigate();

  const move = () => {
    setDetail(id);
    navigate('/practice/detail');
  };

  const bookmark = <FontAwesomeIcon icon={faHeart} />;

  return (
    <div className={style.container} onClick={move}>
      <div className={style.wrap}>
        <div className={style.dateandbookmark}>
        <div className={style.date}>작성일 | {moment.utc(createdDate).utcOffset('+0900').format('YYYY-MM-DD')}</div>
          <div className={style.bookmark}>{bookmark}</div>
        </div>

        <div className={style.scriptContent}>
          
          <h2 className={style.title}>{title}</h2>
          <h3 className={style.actor}>{actor}</h3>
          <div className={style.category}>
            <div className={style.round}>#{emotion}</div>
            <div className={style.round}>#{genre}</div>
          </div>
          
          <div className={style.line}></div>
          <div className={style.subinfo}>
            <div className={style.author}>{author}</div>
            <div className={style.cntinfo}>
                <div className={style.section}>
                    <FontAwesomeIcon icon={faEye} />
                </div>
                <div className={style.section} style={{marginRight:"8px"}}>
                    {viewCnt}
                </div>
                <div className={style.section}>
                    <FontAwesomeIcon icon={faUsers} />
                </div>
                <div className={style.section}>
                    {participantCnt}
                </div>
             </div>
          </div>
        </div>

      </div>      
    </div>
  );
}
