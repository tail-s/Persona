// import { useState } from 'react';
import style from './LoginModal.module.scss';
import { useRecoilState } from 'recoil';
import { modal } from '../../states/loginState';

const API_BASE_URL = 'https://j8b301.p.ssafy.io/app';
// const LOCAL_API_BASE_URL = 'http://localhost:8080/app';
const WEB_REDIRECT_URI = 'https://j8b301.p.ssafy.io/oauth2/token';
// const WEB_REDIRECT_URI = 'http://localhost:3000/oauth2/token';

export default function Modal() {
  const [showModal, setShowModal] = useRecoilState(modal);
  // const [loading, setLoading] = useState(false);

  const shutModal = () => {
    setShowModal(false);
  };

  return (
    <>
      {showModal && (
        <div className={style.back} onClick={shutModal}>
          <div className={style.container}>
            <h2 className={style.banner}>간편하게 SNS 로그인</h2>
            <div className={style.loginSelect}>
              <a href={`${API_BASE_URL}/oauth2/authorization/google?redirect_uri=${WEB_REDIRECT_URI}`}>
                {/* <a href={`http://${API_BASE_URL}/app/oauth2/authorization/google`}> */}
                <img src="Modal_google.png" alt="구글로그인버튼" className={style.google} width="300" />
              </a>
              <a href={`${API_BASE_URL}/oauth2/authorization/naver?redirect_uri=${WEB_REDIRECT_URI}`}>
                <img src="Modal_naver.png" alt="네이버로그인버튼" className={style.naver} width="300" />
              </a>
              <a href={`${API_BASE_URL}/oauth2/authorization/kakao?redirect_uri=${WEB_REDIRECT_URI}`}>
                <img src="Modal_kakao.png" alt="카카오로그인버튼" className={style.kakao} width="300" />
              </a>
            </div>
          </div>
        </div>
      )}
    </>
  );
}
