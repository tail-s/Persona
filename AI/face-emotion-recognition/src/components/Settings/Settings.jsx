import { useDashboardContext } from '../Dashboard';
import { useSettingsContext } from './SettingsContext';
import './Settings.css';
import { Button, Toggle } from '../AnimatedComponents';
import { SettingsIcon } from '../Icons';
import { useReactMediaRecorder } from 'react-media-recorder';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faRecordVinyl } from '@fortawesome/free-solid-svg-icons';
import { faStop } from '@fortawesome/free-solid-svg-icons';
import { useState } from 'react';
const Settings = (props) => {
  const { webcamOn, setWebcamOn, setWebcamOff, setSettingsVisible } = useSettingsContext();
  const { setRecordedExpressionsVisible, setMountedVideoComponent } = useDashboardContext();
  const { stopRecording } = useReactMediaRecorder({ video: true });

  function setvideo() {
    return stopRecording;
  }
  return (
    <div className={"flexBox flex flex-row justify-between my-7 mx-4 px-7 py-4 bg-bg-2 rounded-lg shadow-2xl border-2 border-fg-1"}>
      <div className="flex-row">
        {webcamOn ? (
          <Button className="endrecord" variant="contained" onClick={props.endrecord} color="error">
            <FontAwesomeIcon icon={faStop} style={{ width: '30px', height: '30px', color: '#f6092c' }} />
          </Button>
        ) : (
          <Button initialState={webcamOn} onClick={setWebcamOn}>
            <FontAwesomeIcon icon={faRecordVinyl} style={{ width: '30px', height: '30px', color: '#f6092c' }} />
          </Button>
        )}
      </div>
      <Button onClick={() => setSettingsVisible(true)} rotateAnimation={true}>
        <SettingsIcon />
      </Button>
 

    </div>
  );
};

export default Settings;
