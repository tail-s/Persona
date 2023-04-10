
import EmojiWidget from "../EmojiWidget";
import WebcamTurnedOff from "../WebcamTurnedOff";
import { useSettingsContext } from "../Settings";
import style from "./VideoComponent.module.scss";
import FaceDetect from "../../utils/FaceDetect";
import FaceOverlay from "../../utils/FaceOverlay";
import { useReactMediaRecorder } from "react-media-recorder";

const VideoComponent = (props) => {
  const {
    webcamOn, overlayOn,webcamOff,
    emojiOn
  } = useSettingsContext();

  return(
    <>
      {/* <VideoStream></VideoStream> */}
      {webcamOn ?  <FaceDetect text={props.text} script={props.scriptid}/> : <WebcamTurnedOff />}
      {webcamOn && overlayOn && !webcamOff &&  <FaceOverlay/>}
      {webcamOn && emojiOn && !webcamOff && <span className="absolute top-8 right-8"><EmojiWidget /></span>}
    </>
  );
};

export default VideoComponent;