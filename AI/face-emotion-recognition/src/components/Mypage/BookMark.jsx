import style from "./MyInfo.module.scss";
import { useRecoilState } from "recoil";
import { user } from "../../states/loginState"; 
import BookScript from './BookScript';
import axios from "axios";
import { useEffect } from "react";

const BookMark = () => {
    const [userInfo, setUserInto] = useRecoilState(user);
    useEffect(() => {
        axios.get("https://j8b301.p.ssafy.io/app/bookmark/my?page=1",
        {headers:{
            Authorization: "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNjgwNTg2MTczLCJleHAiOjE2ODMxNzgxNzN9.uYsCZx7bY16iGRp9w7uKp--MN1_hvHkiSOwlYbBqen2IVQP9yDHfAJZzc7sTe0xpBNimQP61JiPYt7FDiF9VIg"
        }}
        )
        .then((res)=>{
            console.log(res)
        })
    })
    
    
    return(
        <div className={style.box}>
            <h1>내정보</h1>
            <hr/>
            <div className={style.userInfo}>
                <div className={style.flexBox}>
                  <div>
                      {/* <BookScript></BookScript> */}
                    </div>
                    <div className={style.userText}>
                    </div>
                </div>
            </div>
        </div>
    );
} 
