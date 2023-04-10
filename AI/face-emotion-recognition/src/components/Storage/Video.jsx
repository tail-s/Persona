import { useRecoilState } from 'recoil';
import style from './Video.module.scss';
import React, { useState } from 'react';
import moment from 'moment';

  const Video = ({ id, createdDate, title, thumbnailUrl, onSelect }) => {
  

  return (
    <div className={style.video} onClick={() => onSelect(id)}>
      <div className={style.date}>{moment.utc(createdDate).utcOffset('+0900').format('YYYY-MM-DD HH:mm:ss')}</div>
      <div className={style.title}>{title}</div>
      <img className={style.img} src={thumbnailUrl}/>
    </div>
  )
};

export default Video;
