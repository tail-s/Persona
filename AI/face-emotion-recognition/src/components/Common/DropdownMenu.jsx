import { useRecoilState } from 'recoil';
import { dropdownMenuState } from '../../states/loginState';
import style from './DropdownMenu.module.scss';

const DropdownMenu = (props) => {
  const [dropdownMenu, setDropdownMenu] = useRecoilState(dropdownMenuState);

  const handler = (item) => {
    setDropdownMenu('');
    props.onItemClick(item);
  };

  return (
    <div>
      <button className={style.btn} onClick={() => setDropdownMenu(dropdownMenu === '' ? 'active' : '')}>
        {dropdownMenu === '' ? '▼' : '▲'}
      </button>
      {dropdownMenu === 'active' ? (
        <ul className={style.show}>
          {props.items.map((item) => (
            <ul key={item} onClick={() => handler(item)}>
              {item}
              {item === '북마크' &&  <hr className={style.hr}/> }
            </ul>
          ))}
        </ul>
      ) : (
        <></>
      )}
    </div>
  );
};

export default DropdownMenu;
