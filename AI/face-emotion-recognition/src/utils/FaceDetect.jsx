import React, { useRef, useEffect,useState,useCallback,PureComponent  } from "react";
import { useDashboardContext } from "../components/Dashboard";
import { Settings, useSettingsContext } from "../components/Settings";
import Webcam from "react-webcam";
import { drawConnectors,drawLandmarks, drawRectangle } from "@mediapipe/drawing_utils/drawing_utils";
import { Camera } from "@mediapipe/camera_utils/camera_utils";
import { FaceDetection } from '@mediapipe/face_detection';
import {
  FaceMesh,
  FACEMESH_TESSELATION,
  FACEMESH_RIGHT_EYE,
  FACEMESH_RIGHT_EYEBROW,
  FACEMESH_LEFT_EYE,
  FACEMESH_LEFT_EYEBROW,
  FACEMESH_FACE_OVAL,
  FACEMESH_LIPS,
} from "@mediapipe/face_mesh/face_mesh";
import { useReactMediaRecorder  } from "react-media-recorder";
import RecordedExpressionsModal from "../components/Recording";
import WebcamTurnedOff from "../components/WebcamTurnedOff";
import "./FaceDetect.css";
import Button from '@mui/material/Button';
import axios from "axios"
import { AudioRecorder,useAudioRecorder } from 'react-audio-voice-recorder';
import AudioRecord from "./AudioRecord";
import ScriptText from "../components/Script/ScriptText";
import ReactDiffViewer,{ DiffMethod } from 'react-diff-viewer';
import { useLocation, useNavigate } from "react-router";
import AWS from 'aws-sdk';
import html2canvas from "html2canvas";
import {jsPDF} from "jspdf";
import { Modal } from "@mui/material";
import Typography from '@mui/material/Typography';
import Box from '@mui/material/Box';
import Input from '@mui/material/Input';
import { tokenState, user } from '../states/loginState';
import { useRecoilState, useRecoilValue } from 'recoil';
import { writeState } from '../states/practiceFilterState';
const FaceDetect = (props) => {
  const webcamRef = useRef(null);
  const chartRef = useRef(null);
  const API_BASE_URL = 'https://j8b301.p.ssafy.io/app';

  const { status, startRecording, stopRecording, mediaBlobUrl } =
  useReactMediaRecorder({ video: true });
  const style = {
    position: 'absolute',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: 400,
    bgcolor: 'background.paper',
    border: '2px solid #000',
    boxShadow: 24,
    p: 4,
  };
  const [open, setOpen] = useState(false);
  const handleOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const getwrite = useRecoilValue(writeState);
  const navigate = useNavigate();
  
  const {
    setCurrentExpression,
    setEmoji,
    setRecordedExpressions,
    setMountedVideoComponent,
    setRecordedvideo,
    recordedvideo,
    recordedExpressions,
    canvasRef

  } = useDashboardContext();
  const { pathname } = useLocation();
  const name = pathname.substring(11);
  const token = useRecoilValue(tokenState);
  const [stream, setStream] = useState();
  const [media, setMedia] = useState();
  const [onRec, setOnRec] = useState(true);
  const [source, setSource] = useState();
  const [analyser, setAnalyser] = useState();
  const [audioUrl, setAudioUrl] = useState();
  const [ongraph, setongraph] = useState(false);
  const valueRef = useRef('')
  const videoEl = useRef(null);
  const {
    webcamOn,
    webcamOff,
    overlayOn,
    setWebcamOff,
    setWebcamOn
  } = useSettingsContext();
  let faceDetectionArray = [];
  const [endcam,setendcam] = useState(false);
  const [bloburl,setbloburl]=useState(mediaBlobUrl);
  const [text,setText] =useState(props.text);
  const [recordtext,setRecordtext] =useState("");
  const [partnum,setpartnum] =useState(0);
  
  const ACCESS_KEY = "AKIA2A2FFZJ6BHNCU6PQ";
  const SECRET_ACCESS_KEY = "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";
  const REGION = "ap-northeast-2";
  const S3_BUCKET = "step-up-bucket";

  AWS.config.update({
    accessKeyId: ACCESS_KEY,
    secretAccessKey: SECRET_ACCESS_KEY
  });

  const myBucket = new AWS.S3({
    params: { Bucket: S3_BUCKET},
    region: REGION,
  });
  useEffect(() => {
    // if(webcamOff){
    //   setWebcamOff(false);
    // }

    if(!webcamOn){
      setendcam(true)
    }else{
      setendcam(false)
    }

    if(status==="recording"){

    }else if(status==="stopped"){
      console.log(11)
      setbloburl(mediaBlobUrl);
      callaudio();
    }

    if(webcamOff){
      function setstopRecording(){
        return stopRecording;
      }
      setstopRecording();
    }
    
    setRecordedvideo(() => {
      return mediaBlobUrl;
    })

    const faceMesh = new FaceMesh({
      locateFile: (file) => {
        console.log(`${file}`);
        return `https://cdn.jsdelivr.net/npm/@mediapipe/face_mesh@0.4.1633559619/${file}`;
      },
    });

    const faceDetection = new FaceDetection(
      {locateFile: (file) => {
        return `https://cdn.jsdelivr.net/npm/@mediapipe/face_detection@0.4/${file}`;
    }});

    faceMesh.setOptions({
      refineLandmarks: true,
      // selfie: true,
      minDetectionConfidence: 0.5,
      minTrackingConfidence: 0.5,
      maxNumFaces: 1,
      // maxNumFaces: 1,
      // minDetectionConfidence: 0.5,
      // minTrackingConfidence: 0.5,
    });
    faceMesh.onResults(onResults);
    
    faceDetection.setOptions({
      model: 'short',
      minDetectionConfidence: 0.5
    });
    if(webcamOn){
      startRecording()
      onRecAudio()
      setRecordedvideo(mediaBlobUrl)
    }else{
      // stopRecording()
    }
    if(ongraph){
      // startRecording()
    }

    faceDetection.onResults(faceDetectionOnResults);

    if (
      typeof webcamRef.current !== "undefined" &&
      webcamRef.current !== null
    ) {
      const camera = new Camera(webcamRef.current.video, {
        onFrame: async () => {
          await faceDetection.send({ image: webcamRef.current.video });
          await faceMesh.send({ image: webcamRef.current.video });
          
        },
        width: 1280,
        height: 720,
      });
      camera.start();
      


      
    }
  }, [mediaBlobUrl]);




    /**
   * 
   * @param {Object} info - Object with details of Age, Expressions, ...
   * @returns {Object} - Returns expression of the first face recorded.
   */
    const formatExpression = (info) => {
      if (
        info === undefined ||
        info === null ||
        info.predictions === undefined ||
        info.predictions=== null 
      ) {
        return null;
      }
      const expression = [];
      for (const [key, value] of Object.entries(info.predictions)) {
        expression.push({
          expression: key,
          percent: value*100
        });
      }
      return expression;
    };
  
    /**
     * 
     * @param {Array} formattedExpression - Formatted expression
     * @returns {String} - Returns the emoji name.
     */
    const getEmojiName = (formattedExpression) => {
      if (formattedExpression === null || formattedExpression === undefined || formattedExpression.length < 1) {
        return null;
      }
      let emojiName = null, maxPercent = Number.NEGATIVE_INFINITY;
      formattedExpression.forEach((expr) => {
        if (expr.percent > maxPercent) {
          emojiName = expr.expression;
          maxPercent = expr.percent;
        }
      });
      return emojiName;
    };
  
    /**
     * 
     * @param {Array} recordedExpressions - Expressions recorded upto this point of time.
     * @param {Object} currentExpression - Current expression from the video stream
     * @returns {Array} - Returns an array of recorded expressions
     */
    const recordExpression = (recordedExpressions, currentExpression) => {
      if (currentExpression === undefined || currentExpression === null) {
        return recordedExpressions;
      }
      // console.log(currentExpression)
      if (recordedExpressions.length < 1) {
        currentExpression.forEach((current) => {
          recordedExpressions.push({
            id: current.expression,
            data: [{
              x: 0,
              y: current.percent
            }]
          });
        });
        return recordedExpressions;
      }
      currentExpression.forEach((current, index) => {
        recordedExpressions[index].data.push({
          x: recordedExpressions[index].data.length+1,
          y: current.percent
        });
      });
      return recordedExpressions;
    };
  

  const onResults = async (results) => {

  const videoWidth = webcamRef.current.video.videoWidth;
  const videoHeight = webcamRef.current.video.videoHeight;
  canvasRef.current.width = videoWidth;
  canvasRef.current.height = videoHeight;
  const canvasElement = canvasRef.current;
  const canvasCtx = canvasElement.getContext("2d");
  canvasCtx.save();
  canvasCtx.clearRect(0, 0, videoWidth, videoHeight);
  canvasCtx.translate(videoWidth, 0);
  canvasCtx.scale(-1, 1);
  canvasCtx.drawImage(
    results.image,
    0,
    0,
    canvasElement.width,
    canvasElement.height
  );

  // Websocket
  var socket = new WebSocket('wss://j8b301.p.ssafy.io/api/socket')
  var imageSrc = webcamRef.current.getScreenshot()
  var apiCall = {
    event: "localhost:subscribe",
    data: { 
      image: imageSrc
    },
  };


  socket.onopen = () => socket.send(JSON.stringify(apiCall))

  socket.onmessage = function(event) {

    var pred_log = JSON.parse(event.data)
    const formattedExpression = formatExpression(pred_log);
    
    setEmoji((previousEmoji) => {
      if (formattedExpression === undefined || formattedExpression === null) {
        return previousEmoji;
      }
      const name = getEmojiName(formattedExpression);
      if (name === null) {
        return previousEmoji;
      }
      return name;
    });


    setCurrentExpression((previousExpression) => {
      if (formattedExpression === undefined || formattedExpression === null) {
        return previousExpression;
      }
      return formattedExpression;
    });
    setRecordedExpressions((recordedExpressions) => {
      if (formattedExpression === undefined || formattedExpression === null) {
        return recordedExpressions;
      }
      return recordExpression(recordedExpressions, formatExpression(pred_log));
    });
    setongraph(true);
    
  }


    if (results.multiFaceLandmarks) {
      for (const landmarks of results.multiFaceLandmarks) {
        drawConnectors(canvasCtx, landmarks, FACEMESH_TESSELATION, {
          color: "#C0C0C070",
          lineWidth: 1,
        });
        drawConnectors(canvasCtx, landmarks, FACEMESH_RIGHT_EYE, {
          color: "#E0E0E0",
          lineWidth: 1,
        });
        drawConnectors(canvasCtx, landmarks, FACEMESH_RIGHT_EYEBROW, {
          color: "#E0E0E0",
          lineWidth: 1,
        });
        drawConnectors(canvasCtx, landmarks, FACEMESH_LEFT_EYE, {
          color: "#E0E0E0",
          lineWidth: 1,
        });
        drawConnectors(canvasCtx, landmarks, FACEMESH_LEFT_EYEBROW, {
          color: "#E0E0E0",
          lineWidth: 1,
        });
        drawConnectors(canvasCtx, landmarks, FACEMESH_FACE_OVAL, {
          color: "#E0E0E0",
          lineWidth: 1,
        });
        drawConnectors(canvasCtx, landmarks, FACEMESH_LIPS, {
          color: "#E0E0E0",
          lineWidth: 1,
        });
      }

    
    for (const faceDetection of faceDetectionArray) {
      drawRectangle(canvasCtx, faceDetection.boundingBox, {color: 'blue', lineWidth: 4, fillColor: '#00000000'});
    };
  }
        
    canvasCtx.restore();
  };



  const faceDetectionOnResults = (results) => {
    if (results.detections) {
      faceDetectionArray = results.detections;
    }
  }
  const click= async ()=>{
    stopRecording()
    offRecAudio()
    setWebcamOff(true);
    
    let stream = webcamRef.current.video.srcObject;
    const tracks = stream.getTracks();
    
    tracks.forEach(track => track.stop());
    webcamRef.current.video.srcObject = null;
    // recorderControls.stopRecording()
    return stopRecording()
  }
  function callaudio(){
    if(status==="stopped"){

      submitAudioTofastAPI();

    }
  }

  const onRecAudio = () => {
    // 음원보를 담은 노드를 생성하거나 음원을 실행또는 디코딩 시키는 일을 한다
    const audioCtx = new (window.AudioContext || window.webkitAudioContext)();
    // 자바스크립트를 통해 음원의 진행상태에 직접접근에 사용된다.
    const analyser = audioCtx.createScriptProcessor(0, 1, 1);

    setAnalyser(analyser);

    function makeSound(stream) {
      // 내 컴퓨터의 마이크나 다른 소스를 통해 발생한 오디오 스트림의 정보를 보여준다.
      const source = audioCtx.createMediaStreamSource(stream);
      setSource(source);
      source.connect(analyser);
      analyser.connect(audioCtx.destination);
    }
    // 마이크 사용 권한 획득
    console.log(111)
    navigator.mediaDevices.getUserMedia({ audio: true }).then((stream) => {
      const mediaRecorder = new MediaRecorder(stream);
      mediaRecorder.start();
      setStream(stream);
      setMedia(mediaRecorder);
      makeSound(stream);

      analyser.onaudioprocess = function (e) {
        // 3분(180초) 지나면 자동으로 음성 저장 및 녹음 중지
        if (e.playbackTime > 180) {
          stream.getAudioTracks().forEach(function (track) {
            track.stop();
          });
          mediaRecorder.stop();
          // 메서드가 호출 된 노드 연결 해제
          analyser.disconnect();
          audioCtx.createMediaStreamSource(stream).disconnect();

          mediaRecorder.ondataavailable = function (e) {
            setAudioUrl(e.data);
            setOnRec(true);
          };
        } else {
          setOnRec(false);
        }
      };
    });
  };

  // 사용자가 음성 녹음을 중지했을 때
  const offRecAudio = () => {
    // dataavailable 이벤트로 Blob 데이터에 대한 응답을 받을 수 있음
    media.ondataavailable = function (e) {
      setAudioUrl(e.data);
      setOnRec(true);
    };

    // 모든 트랙에서 stop()을 호출해 오디오 스트림을 정지
    stream.getAudioTracks().forEach(function (track) {
      track.stop();
    });

    // 미디어 캡처 중지
    media.stop();
    // 메서드가 호출 된 노드 연결 해제
    analyser.disconnect();
    source.disconnect();
  };

  const submitAudioTofastAPI = useCallback(() => {
    // File 생성자를 사용해 파일로 변환

    console.log("분석 시작");
    const sound = new File([audioUrl], "sound2.mp3", {
      lastModified: new Date().getTime(),
      type: "audio/mp3",
    });

    console.log(audioUrl);
    console.log(sound);
    const formData = new FormData();
    formData.append("file", sound);

    console.log(sound);
    console.log(formData);

    try {
      console.log("axios 시작");
      axios
        .post("https://j8b301.p.ssafy.io/api/audio/", formData, {
          headers: {
            "Content-Type": "audio/mpeg",
          },
        })
        .then((response) => {
          console.log(response.data[1]);
          setRecordtext(response.data[0].message[0])
          
          // console.log(response.data.filename);
        });
    } catch (error) {
      console.log(error);
    }
  });


//  function emotionAnalyze(text){
//   // const config = { headers: {'Content-Type': 'application/json'} }

//   // axios.post("http://j8b301.p.ssafy.io:8000/api/ai/emotion", JSON.stringify({script:{text}}),config)
//   // // axios.post("http://127.0.0.1:8000/ai/emotion", JSON.stringify({script:{text}}),config)
//   //       .then((response) => {
//   //         console.log(response);
          
//   //         // console.log(response.data.filename);
//   //       });
//  }

 function save(){
  const url="https://step-up-bucket.s3.ap-northeast-2.amazonaws.com/";
  const userid="user"
  const gettext= recordtext;
  const vid=props.script;
  const title= valueRef.current.value
  console.log(title)
  axios.post(API_BASE_URL+"/participant",
  {
    "scriptId": name
  },{
    headers: {
      Authorization: token,
    },
  }
  ).then((res)=>{
    setpartnum(res.data.value)
    const num=res.data.value;
    axios.get(mediaBlobUrl, { responseType : "blob"})
  .then((response) => {
    //  console.log(response.data);
     let video = new File([response.data], num+vid+userid+"video.mp4", {
      lastModified: new Date().getTime(),
      type: "video/mp4",
    });
    uploadFile(video);
    html2canvas(document.querySelector(".chart")).then((canvas) => {
    // const imgData = canvas.toDataURL("image/jpeg");
    // var can = document.getElementById('canvas');

    html2canvas(document.getElementById('video')).then(can => {
      can.getContext('2d')
      // .drawImage(videoEl.current,0,0, videoEl.current.clientWidth, 
      // videoEl.current.clientHeight)
      let dataURI = can.toDataURL('image/jpeg',1.0);
      const blob= dataURItoBlob(dataURI)
      let thm = new File([blob], num+vid+userid+"thmimg.jpg", { type: "image/jpeg" })
      uploadFile(thm);
      canvas.toBlob((blob) => {
        let file = new File([blob], num+vid+userid+"img.jpg", { type: "image/jpeg" })
        uploadFile(file)
      }, 'image/jpeg');

      const data={
        analysis:text+"!?,"+gettext+"!?,"+getwrite,
        graphUrl:url+num+vid+userid+"img.jpg",
        participantId:res.data.value,
        thumbnailUrl:url+num+vid+userid+"thmimg.jpg",
        title: title,
        videoUrl:url+num+vid+userid+"video.mp4"
      }
      console.log(data)
      axios.post("https://j8b301.p.ssafy.io/app/video/save", data,{
        headers: {
          Authorization: token,
        },
      })
      .then((response) => {
        setWebcamOn(false);
        setWebcamOff(false);
        navigate(`/storage`);
      });

      });
    });

   
    
  });    
  })
 }

 const uploadFile = (file) => {
  // console.log(file)
  const params = {
    ACL: 'public-read',
    Body: file,
    Bucket: S3_BUCKET,
    Key: file.name
  };
  
  myBucket.putObject(params)
    .on('httpUploadProgress', (evt) => {

    })
    .send((err) => {
      if (err) console.log(err)
    })
  }
  function dataURItoBlob(dataURI) {
    // convert base64/URLEncoded data component to raw binary data held in a string
    var byteString;
    if (dataURI.split(',')[0].indexOf('base64') >= 0)
        byteString = atob(dataURI.split(',')[1]);
    else
        byteString = unescape(dataURI.split(',')[1]);
    // separate out the mime component
    var mimeString = dataURI.split(',')[0].split(':')[1].split(';')[0];
    // write the bytes of the string to a typed array
    var ia = new Uint8Array(byteString.length);
    for (var i = 0; i < byteString.length; i++) {
        ia[i] = byteString.charCodeAt(i);
    }
    return new Blob([ia], {type:mimeString});
  }  

  return (
    <div>
      {webcamOff ? (
        <div>
          <div className="wrap">
            <video
              style={{ width: '1110px', height: '50%', objectFit: 'cover', right: '30px' }}
              className="recordvideo"
              src={mediaBlobUrl}
              ref={videoEl}
              id="video"
              autoPlay
              controls
            />
            <RecordedExpressionsModal />
          </div>

   <div className="scriptComponent">
            {/* <ScriptText text={text}></ScriptText>
          <ScriptText text={recordtext}></ScriptText> */}
            <div>
              <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <h1 style={{ fontSize: '30px', color: 'white' }}>대본</h1>
                <h1 style={{ fontSize: '30px', color: 'white' }}>음성인식결과</h1>
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


          <div className="container">
            <h1 className="mytitle">분석내용</h1>
            
            <div className="mywrite">
              <div className="writewrap">
                {getwrite}
              </div>
            </div>
            
          </div>


          <div style={{ marginBottom: '50px', display: 'flex', justifyContent: 'center' }}>
            <Button
              variant="contained"
              onClick={() => {
                handleOpen()
                // save();
              }}
              color="error">
              저장하기
            </Button>
            <Modal
              open={open}
              onClose={handleClose}
              aria-labelledby="modal-modal-title"
              aria-describedby="modal-modal-description"
            >
              <Box sx={style}>
                <Typography id="modal-modal-title" variant="h6" component="h2">
                  제목을 입력해 주세요
                </Typography>
                <Input inputRef={valueRef} />
                <Button
                  variant="contained"
                  onClick={() => {
                    save();
                  }}
                  color="error">
                  확인
                </Button>
              </Box>
            </Modal>
          </div>
          {/* <canvas id="canvas" style={{
                width:650,
                // visibility:"hidden",
                height:480
          }}></canvas> */}
        </div>
        
      ) : (
        <>
          <Webcam className="onvideo" audio={true} mirrored={true} ref={webcamRef} />
          {overlayOn ? (
            <canvas className="overvideo" ref={canvasRef}></canvas>
          ) : (
            <canvas
              className="overvideo"
              style={{
                visibility:"hidden",
              }}
              ref={canvasRef}></canvas>
          )}
          <Settings endrecord={click}></Settings>
        </>
      )}
    </div>
  );
};


export default FaceDetect;
