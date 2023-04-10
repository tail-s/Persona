import React, { useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { clickedEmotionState, clickedGenreState, clickedBtnState } from '../../states/practiceFilterState';

import style from './FilterBtn.module.scss'


const FilterBtn= ({ id, label, value }) => {
  const [clickedEmotion, setClickedEmotion] = useRecoilState(clickedEmotionState);
  const [clickedGenre, setClickedGenre] = useRecoilState(clickedGenreState);
  const [clickedBtnIds, setClickedBtnIds] = useRecoilState(clickedBtnState);

  const dynamic = clickedBtnIds.includes(id) ? style.selected : style.button;

  useEffect(() => {
    console.log("changed");
  }, [dynamic])


  const clickHandler = () => {

    if (id !== 1 && id < 9) {
      if (clickedEmotion.includes(value)) {
        setClickedEmotion(clickedEmotion.filter((target) => target !== value));
      } else {
        setClickedEmotion([...clickedEmotion, value]);
      }
    } else if (id > 8) {
      if (clickedGenre.includes(value)) {
        setClickedGenre(clickedGenre.filter((target) => target !== value));
      } else {
        setClickedGenre([...clickedGenre, value]);
      }
    }

    if (id !==1 & clickedBtnIds.includes(id)) {
      setClickedBtnIds(clickedBtnIds.filter((ids) => ids !== id));
    } else {
      setClickedBtnIds([...clickedBtnIds, id]);
    }
  };

  return <div className={dynamic} onClick={clickHandler}>{label}</div>;
};

export default FilterBtn;
