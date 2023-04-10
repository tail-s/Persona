
import { useLocation, useNavigate } from 'react-router-dom';
import { useRecoilState } from 'recoil';
import { tokenState } from '../states/loginState';

export default function Token() {
    const navigate = useNavigate();
    const location = useLocation();
    const param = new URLSearchParams(location.search).get('token');
    const [token, setToken] = useRecoilState(tokenState);

    setToken(`Bearer ${param}`);
    navigate('/');

    return <div>Now Loading...
        <div onClick={() => {
            navigate('/error');
        }}>에러페이지</div>
    </div>
};