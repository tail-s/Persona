import React, { useEffect } from 'react';
import { useRecoilState, useRecoilValue } from 'recoil';

import {
  clickedEmotionState,
  clickedGenreState,
  clickedBtnState,
  optionState,
  keywordState,
  sortingState,
  pageState,
  scriptState,
} from '../../states/practiceFilterState';
import { tokenState } from '../../states/loginState';
import axios from 'axios';

import Header from '../../components/Common/Header';
// import Footer from '@/components/Footer';
import FilterBtn from '../../components/PracticePage/FilterBtn';
import Script from '../../components/PracticePage/Script';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import style from './Practice.module.scss';
import { faMagnifyingGlass, faAnglesDown } from '@fortawesome/free-solid-svg-icons';

const List = () => {
  const [clickedEmotion, setClickedEmotion] = useRecoilState(clickedEmotionState);
  const [clickedGenre, setClickedGenre] = useRecoilState(clickedGenreState);
  const [clickedBtn, setClickedBtn] = useRecoilState(clickedBtnState);
  const [clickedOption, setClickedOption] = useRecoilState(optionState);
  const [clickedKeyword, setClickedKeyword] = useRecoilState(keywordState);
  const [clickedSorting, setClickedSorting] = useRecoilState(sortingState);
  const [page, setPage] = useRecoilState(pageState);

  const token = useRecoilValue(tokenState);

  const [scripts, setScripts] = useRecoilState(scriptState);

  // const API_BASE_URL = 'http://j8b301.p.ssafy.io:8080/app';
  const API_BASE_URL = 'https://j8b301.p.ssafy.io/app';

  const optionHandler = (event) => {
    setClickedOption(event.target.value);
    console.log(event.target.value);
  };

  const keywordHandler = (event) => {
    setClickedKeyword(event.target.value);
  };

  const moreHandler = () => {
    setTimeout(() => {
      setPage((size) => size + 1);
    }, '100');
    loadingNext();
  };

  const loading = async () => {
    setPage(0);
    setScripts([]);

    await axios
      .post(
        `${API_BASE_URL}/script/all`,
        {
          option: clickedOption,
          keyword: clickedKeyword,
          emotions: clickedEmotion,
          genres: clickedGenre,
          page: page,
          sort: clickedSorting,
        },
        {
          headers: {
            Authorization: token,
          },
        },
      )
      .then((res) => {
        setScripts(res.data.value.content);
      });
  };

  const loadingNext = async () => {
    await axios
      .post(
        `${API_BASE_URL}/script/all`,
        {
          option: clickedOption,
          keyword: clickedKeyword,
          emotions: clickedEmotion,
          genres: clickedGenre,
          page: page,
          sort: clickedSorting,
        },
        {
          headers: {
            Authorization: token,
          },
        },
      )
      .then((res) => {
        let newData = res.data.value.content;
        let nowData = [...scripts, ...newData];

        setScripts(nowData);
      });
  };

  useEffect(() => {
    loading();
    // console.log(scripts);
  }, [clickedEmotion, clickedGenre, clickedSorting, clickedKeyword]);

  return (
    <>
      <Header />
      <div className={style.container}>
        <div className={style.intro}>
          <div className={style.title}>스크립트</div>
          <div className={style.content}>내가 원하는 스크립트를 고를 수 있어요.</div>
        </div>
        {/* <div className={style.banner}> */}
        {/* <img className={style.bookimg} src="./Practice_banner.png" alt="책" /> */}
        {/* <div className={style.text}> */}
        {/* <h1>스크립트</h1>
          <div className={style.subtitle}>내가 원하는 스크립트를 고를 수 있어요</div> */}
        {/* </div> */}
        {/* </div> */}
        <div className={style.filter}>
          <div className={style.search}>
            <select className={style.selectbox} name="findby" onChange={optionHandler}>
              <option value="title">제목</option>
              <option value="content">내용</option>
              <option value="author">작가</option>
              <option value="actor">배역</option>
            </select>

            <input
              className={style.searchText}
              type="text"
              id="input_search"
              placeholder="글 제목, 글 내용, 작성자 검색"
              maxLength={200}
              autoComplete="off"
              onChange={keywordHandler}
            />
            <button type="submit" className={style.searchbtn}>
              <FontAwesomeIcon icon={faMagnifyingGlass} style={{ color: '#5e5e5e' }} />
            </button>
          </div>
          {/* {clickedEmotion.length > 0 ? <p>클릭된 감정 : {clickedEmotion.join(', ')}</p> : <p>비어있음</p>}
          {clickedGenre.length > 0 ? <p>클릭된 장르 : {clickedGenre.join(', ')}</p> : <p>비어있음</p>}
          {clickedBtn.length > 0 ? <p>클릭된 버튼 : {clickedBtn.join(', ')}</p> : <p>비어있음</p>} */}
          <div className={style.filterButton}>
            {/* <FilterBtn sensor={selectHandler} id={1} label="전체" value="" /> */}
            <FilterBtn id={2} label="#슬픈" value="슬픔" onClick={loading} />
            <FilterBtn id={3} label="#당황한" value="놀람" />
            <FilterBtn id={4} label="#화난" value="화남" />
            <FilterBtn id={5} label="#기쁜" value="기쁨" />
            <FilterBtn id={6} label="#무서운" value="두려움" />
            <FilterBtn id={7} label="#혐오스러운" value="역겨움" />
            <FilterBtn id={8} label="#중립" value="중립" />
            <FilterBtn id={9} label="#영화" value="영화" />
            <FilterBtn id={10} label="#연극" value="연극" />
            <FilterBtn id={11} label="#뮤지컬" value="뮤지컬" />
            <FilterBtn id={12} label="#드라마" value="드라마" />
          </div>
        </div>
        <div className={style.script}>
          <div className={style.sorting}>
            <div
              className={style.text}
              onClick={() => {
                setClickedSorting('최신순');
              }}>
              최신순
            </div>{' '}
            |{' '}
            <div
              className={style.text}
              onClick={() => {
                setClickedSorting('인기순');
              }}>
              인기순
            </div>
            |
            <div
              className={style.text}
              onClick={() => {
                setClickedSorting('참여순');
              }}>
              참여순
            </div>{' '}
            |{' '}
            <div
              className={style.text}
              onClick={() => {
                setClickedSorting('조회순');
              }}>
              조회순
            </div>
          </div>
          <div className={style.scripts}>
            {scripts.map((info) => (
              <Script key={info.id} data={info} />
            ))}
          </div>
          <div className={style.more} onClick={moreHandler}>
            <FontAwesomeIcon icon={faAnglesDown} />
          </div>
        </div>
      </div>
    </>
  );
};

export default List;
