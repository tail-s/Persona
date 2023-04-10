import { useRecoilState } from 'recoil';
import style from './Video.module.scss';
import React, { useState } from 'react';

const Video = ({ id, createDate, title, imgUrl, onSelect }) => {
  const [selected, setSelected] = useState(false);

  const handleSelect = () => {
    onSelect({ id, createDate, title, imgUrl });
    setSelected(!selected);
  };

  return (
    <div className={`${style.video} ${selected ? style.selected : ''}`} onClick={handleSelect}>
      <div className={style.date}>{createDate}</div>
      <div className={style.title}>{title}</div>
      <div className={style.img}>{imgUrl}</div>
    </div>
  );
};

export default Video;
