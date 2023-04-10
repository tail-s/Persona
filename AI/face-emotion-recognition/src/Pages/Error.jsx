import React from 'react';
import style from './Error.module.scss';

export default function Error({ errormsg }) {
    return (
        <div className={style.container}>
            <div className={style.pic}>
                <img src="error.jpg" alt='error' width="500px" height="500px" />
                <div className={style.msg}>
                    <h1>{errormsg}</h1>
                </div>
            </div>     
        </div>
    );
}