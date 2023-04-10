import {useDashboardContext} from "../Dashboard";
import {
  Angry,
  Disgusted,
  Fearful,
  Happy,
  Neutral,
  Sad,
  Surprised
} from "../Icons";

/**
 * 
 * @returns Returns the respective emoji for the currentExpression
 */
const EmojiWidget = () => {

  const {emoji} = useDashboardContext();

  switch (emoji) {
    case "neutral":
      return <Neutral />
    case "happy":
      return <Happy />
    case "sad":
      return <Sad />
    case "angry":
      return <Angry />
    case "fear":
      return <Fearful />
    case "disgust":
      return <Disgusted />
    case "surprise":
      return <Surprised />
    default:
      return <></>
  };
};

export default EmojiWidget;