import React, { useState, useEffect } from 'react';
import style from './Comment.module.scss';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEllipsisVertical } from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';
import { communityApis } from '../../apis/communityApis';
import { tokenState, user } from '../../states/loginState';
import { useRecoilValue } from 'recoil';
import moment from 'moment';

const Comment = ({ id, nickname, createdDate, content, email }) => {
  const [open, setOpen] = useState(false);
  const BASE_URL = 'https://j8b301.p.ssafy.io';
  const token = useRecoilValue(tokenState);
  const userinfo = useRecoilValue(user);

  const clickDropDown = () => {
    setOpen(!open);
  };

  const deleteCommentHandler = () => {
    clickDropDown();
    axios.delete(BASE_URL + communityApis.COMMENT_DELETE_API(id), {
      headers: {
        Authorization: token,
      },
    });
  };

  return (
    <div className={style.comment}>
      <div className={style.info}>
        <div className={style.profile}>
          <img src="user.png" alt="user" width="32" />
          {userinfo.mymail === email && <div onClick={deleteCommentHandler}>삭제</div>}
        </div>
        <div className={style.items}>
          <div className={style.nickname}>{nickname}</div>
          <div className={style.content}>{content}</div>
          <div className={style.date}>{moment.utc(createdDate).utcOffset('+0900').format('YYYY-MM-DD HH:mm:ss')}</div>
        </div>
      </div>
    </div>
  );
};

export default Comment;
