import { useRecoilState,useRecoilValue} from 'recoil';

import style from './Savepage.module.scss';
import Button from '@mui/material/Button';
import ReactDiffViewer,{ DiffMethod } from 'react-diff-viewer';
import { useEffect, useState } from 'react';
import Header from "../../components/Common/Header";
import axios from 'axios';
import { tokenState, user } from '../../states/loginState';
import { useLocation, useNavigate } from 'react-router-dom';
export default function Savepage(props) {
  const [videourl,setvideourl]=useState("");
  const [imgurl,setimgurl]=useState("");
  const [text,setText] =useState("1");
  const [recordtext,setRecordtext] =useState("2");
  const token = useRecoilValue(tokenState);
  const { pathname } = useLocation();
  const name = pathname.substring(10);
  const [myrecord, setMyrecord] = useState("");
  const navigate = useNavigate();
  useEffect(() => {
    const data={
      videoId:name
    }
    axios
      .get(`https://j8b301.p.ssafy.io/app/video/`+name,{
        headers: {
          Authorization: token,
        },
    })
      .then((response) => {
        console.log(response)
        setimgurl(response.data.value.graphUrl)
        setvideourl(response.data.value.videoUrl)
        const text1= response.data.value.analysis
        const textall =text1.split("!?,")
        console.log(textall)
        setText(textall[0])
        setRecordtext(textall[1])
        setMyrecord(textall[2]);
    });
  

  }, [])
  

  const handleClick = (event) => {
    const x = event.clientX;
    const video = document.querySelector('video');
    const hole = 1280 - 170;
    const k = x - 170;
    const res = (k * video.duration) / hole;

    if (k >= 0 && k < 1280) {
      video.currentTime = res;
    }
  };

  const goList = () => {
    navigate('/storage');
  }

  const deleteVideo = () => {
    axios
      .delete(`https://j8b301.p.ssafy.io/app/video/?videoId=${name}`,{
        headers: {
          Authorization: token,
        },
    }).then((res) => {
      navigate('/storage');
    })

  }

  

  return(
    <>
    <Header/>
      <div className={style.savepage}>    
        <video 
          className="recordvideo" 
          src={videourl} 
          autoPlay
          controls
          style={{ width: '800px', height: '50%', objectFit: 'cover', right: '30px' }}
        />
        <img src={imgurl} onClick={(e) => {
          handleClick(e);
        }}/>
        <div className={style.scriptComponent}>
        <div>
              <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <h1 style={{ fontSize: '30px', color: 'black' }}>대본</h1>
                <h1 style={{ fontSize: '30px', color: 'black' }}>음성인식결과</h1>
              </div>
              <hr />
              <hr />

              <ReactDiffViewer
                styles={{
                  marker: {
                    display: 'none',
                  },
                  lineNumber: {
                    display: 'none',
                  },
                }}
                oldValue={text}
                newValue={recordtext}
                splitView={true}
                compareMethod={DiffMethod.WORDS}
              />
            </div>

        </div>
        <div className={style.container}>
            <h1 className={style.mytitle}>분석내용</h1>
            
            <div className={style.mywrite}>
              <div className={style.writewrap}>
                {myrecord}
              </div>
            </div>
            
          </div>
      </div>
      <div className={style.route}>
          <div className={style.button} onClick={goList}>목록</div>
          <div className={style.button2} onClick={deleteVideo}>삭제</div>
      </div>
      </>
  );
};

