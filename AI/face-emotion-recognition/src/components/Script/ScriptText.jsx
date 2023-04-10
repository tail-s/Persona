import "./ScriptText.css";


const ScriptText = (props) => {

  return(
  <div className="script">
    <div className="scripttext">
      {props.text}
    </div>
  </div>

  );
};

export default ScriptText;