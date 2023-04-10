import { useEffect, useState } from 'react';
import { mypageApis } from '../../apis/mypageApis';
import { tokenState } from '../../states/loginState';
import { useRecoilValue, useRecoilState } from 'recoil';
import style from './MyBookmark.module.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faAnglesDown } from '@fortawesome/free-solid-svg-icons';
import {pageState} from '../../states/practiceFilterState';
import BookScript from './BookScript';
import axios from 'axios';

export default function MyPost() {
  const [scripts, setScripts] = useState([]);
  const BASE_URL = 'https://j8b301.p.ssafy.io';
  const token = useRecoilValue(tokenState);
  const [page, setPage] = useRecoilState(pageState);

  useEffect(() => {
    const page = 0;
    axios
      .get(BASE_URL + mypageApis.MY_BOOKMARK_GET_API(page), {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        console.log(res.data.value.content);
        setScripts(res.data.value.content);
      });
  }, []);

  const moreHandler = () => {
    
    setTimeout(() => {
      setPage((size) => size + 1);
      
    },'100');
    loadingNext(); 
       
  }

  const loadingNext = async () => {

    axios
      .get(BASE_URL + mypageApis.MY_BOOKMARK_GET_API(page), {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
      
      let newData = res.data.value.content;
      let nowData = [...scripts,...newData];
      
      setScripts(nowData);
    })
  };

  return (
    <div className={style.outline}>
      <div className={style.header}>북마크</div>
      <div className={style.scripts}>
        {scripts.map((script) => (
          <BookScript key={script.id} {...script} />
        ))}
      </div>
      <div className={style.more} onClick={moreHandler}>
            <FontAwesomeIcon icon={faAnglesDown} />
      </div>
    </div>
  );
}
