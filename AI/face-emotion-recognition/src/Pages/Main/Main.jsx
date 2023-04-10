/* eslint-disable react/no-unescaped-entities */

import style from './Main.module.scss';
import AOS from 'aos';
import 'aos/dist/aos.css';
import Header from '../../components/Common/Header';
import axios from 'axios';

import { useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { tokenState, user, loginState } from '../../states/loginState';

export default function Footer() {
  const [token, setToken] = useRecoilState(tokenState);
  const [userinfo, setUserinfo] = useRecoilState(user);
  const [isLogin, setIsLogin] = useRecoilState(loginState);
  const API_BASE_URL = 'https://j8b301.p.ssafy.io/app';

  const save = (data) => {
    setUserinfo(data);
  };

  useEffect(() => {
    console.log('메인페이지로 토큰이 잘 넘어왔다능 : ' + token);
    axios
      .get(`${API_BASE_URL}/user`, {
        headers: {
          Authorization: token,
        },
      })
      .then((res) => {
        if (token.length !== 0) {
          console.log(res);
          const data = res.data.value;
          save({
            nickname: data.nickname,
            mymail: data.email,
            img: data.imageUrl,
          });
          setIsLogin(true);
          console.log(userinfo);
        }
      });

    AOS.init();
    document.querySelectorAll('div').forEach((img) => img.addEventListener('load', () => AOS.refresh()));
  }, []);

  return (
    <>
      <Header />
      <div className={style.home}>
        <h1>
          I'm Your Persona <br /> 너를 더 멋진 배우로 만들어줄게!
        </h1>
        <div>
          지금 그 자체로도 매력적인 당신 <br />
          자연스러운 표정과 선명한 발음으로 한층 더 멋지게!
        </div>
        <img src="Main_home.webp" alt="home" width="1200" height="500" />
      </div>
      <div className={style.introduce}>
        <h1>연기에 관심이 있는데 시작이 어려웠나요?</h1>
        <div>
          퇴근 후 취미생활, 오디션 독학 준비 등으로 연기 연습을 해보고 싶다면 <br />
          페르소나를 통해 지금 바로 연기를 시작해보세요! <br />
          온라인으로 시간과 공간에 제약을 받지 않아 자유롭게 연습할 수 있고,
          <br />
          AI 감정 분석 등 다양한 기능을 제공받을 수 있습니다.
        </div>
      </div>
      <div className={style.smart}>
        <div className={style.left}>
          <div className={style.title}>#SMART</div>
          <div className={style.subtitle}>내 연기를 분석해주는 AI 서비스</div>
          <div className={style.content}>
            내가 분석한대로 감정을 잘 표현했는지 궁금하지 않으신가요? <br />
            AI가 내 연기를 분석해서 기록을 제공합니다! <br />
            같은 대본을 다른 사람들은 어떻게 표현했는지도 볼 수 있어요.
          </div>
        </div>
        <div className={style.right}>
          <img src="Main_smart.webp" alt="smart" width="500" height="350" />
        </div>
      </div>
      <div className={style.easy}>
        <div className={style.left}>
          <img src="Main_easy.webp" alt="easy" width="500" height="350" />
        </div>
        <div className={style.right}>
          <div className={style.title}>#SO EASY</div>
          <div className={style.subtitle}>대본부터 연기 연습까지 하나로!</div>
          <div className={style.content}>
            대본 준비부터 대본 분석, 연습까지 따로 하셨나요? <br />
            페르소나는 대본부터 분석, 연습 프로그램까지 제공합니다. <br />
            이제 여러가지 준비할 것 없이 쉽게 연기를 연습해보세요.
          </div>
        </div>
      </div>
      <div className={style.simple}>
        <div className={style.left}>
          <div className={style.title}>#SIMPLE</div>
          <div className={style.subtitle}>모니터링은 선택이 아닌 필수</div>
          <div className={style.content}>
            실제 연기 학원에서도 진행하는 모니터링. <br />
            연기는 연습 뿐만 아니라 자신의 연기를 직접 모니터링 하며 <br />
            피드백하는 과정도 중요합니다! <br />
            연기 연습 후 영상과 대본 분석을 보관할 수 있고, <br />
            이를 통해 셀프 피드백이 가능합니다.
          </div>
        </div>
        <div className={style.right}>
          <img src="Main_simple.webp" alt="simple" width="500" height="400" />
        </div>
      </div>
      <div className={style.exciting}>
        <div className={style.left}>
          <img src="Main_exciting.webp" alt="exciting" width="500" height="350" />
        </div>
        <div className={style.right}>
          <div className={style.title}>#EXCITING</div>
          <div className={style.subtitle}>다른 사람들은 어떻게 연기했을까?</div>
          <div className={style.content}>
            연기라는 같은 목적을 가진 사람들과 소통할 수 있는 공간. <br />
            궁금한 점부터 피드백까지 다양한 이야기를 나눌 수 있습니다! <br />
            보관함에 있는 나의 연기를 다른 사람들과 공유할 수 있어 <br />
            쉽고 빠르게 피드백을 받을 수 있습니다. <br />
          </div>
        </div>
      </div>
    </>
  );
}
