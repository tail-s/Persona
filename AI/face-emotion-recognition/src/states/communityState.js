import { atom } from 'recoil';

export const postWriteModal = atom({
  key: 'postWriteModal',
  default: false,
});

export const postDetailModal = atom({
  key: 'postDetailModal',
  default: false,
});

export const videoModal = atom({
  key: 'videoModal',
  default: false,
});

export const selectedPostState = atom({
  key: 'selectedPostState',
  default: undefined,
});

export const isHeartState = atom({
  key: 'isHeartState',
  default: false,
});

export const videosState = atom({
  key: 'videosState',
  default: [],
});

export const selectedVideo = atom({
  key: 'selectedVideo',
  default: undefined,
});
