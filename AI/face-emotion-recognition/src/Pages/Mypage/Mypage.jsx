/* eslint-disable default-case */
import style from './Mypage.module.scss';
import BasicList from '../../components/Mypage/BasicList';
import MyInfo from '../../components/Mypage/MyInfo';
import { useState } from 'react';
import { useRecoilState } from 'recoil';
import {mypageState} from '../../states/loginState'
import MyBookmark from '../../components/Mypage/MyBookmark';
import MyPost from '../../components/Mypage/MyPost';
import Header from '../../components/Common/Header';

const Mypage = () => {
  const [data, setData] = useRecoilState(mypageState);

  function Component() {
    switch (data) {
      case '1':
        return <MyInfo />;
      case '2':
        return <MyPost />;
      case '3':
        return <MyBookmark />;
    }
  }

  return (
    <div className={style.wrapper}>
      <Header />
      <div className={style.flexBox}>
        <BasicList setData={setData} />
        <Component></Component>
      </div>
    </div>
  );
};

export default Mypage;
