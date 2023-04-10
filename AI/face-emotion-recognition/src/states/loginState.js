import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const user = atom({
  key: 'user',
  default: null,
  effects_UNSTABLE: [persistAtom],
  // dangerouslyAllowMutability: true,
});

export const modal = atom({
  key: 'loginmodal',
  default: false,
});

export const dropdownMenuState = atom({
  key: 'dropdownMenuState',
  default: '',
});

export const tokenState = atom({
  key: 'tokenState',
  default: '',
  effects_UNSTABLE: [persistAtom],
});

export const loginState = atom({
  key: 'loginState',
  default: false,
  effects_UNSTABLE: [persistAtom],
});

export const mypageState = atom({
  key: 'mypageState',
  default: '1',
});
