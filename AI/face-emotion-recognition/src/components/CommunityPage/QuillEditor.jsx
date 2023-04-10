import { useState } from 'react';
import 'react-quill/dist/quill.snow.css';
import style from './PostWriteModal.module.scss';
import ReactQuill from 'react-quill';

const modules = {
  toolbar: [
    [{ header: '1' }, { header: '2' }, { font: [] }],
    [{ size: [] }],
    ['bold', 'italic', 'underline', 'strike', 'blockquote'],
    [{ list: 'ordered' }, { list: 'bullet' }, { indent: '-1' }, { indent: '+1' }],
    ['link', 'image', 'video'],
  ],
  clipboard: {
    // toggle to add extra line breaks when pasting HTML:
    matchVisual: false,
  },
};
/*

Quill editor formats
See https://quilljs.com/docs/formats/
*/
const formats = [
  'header',
  'font',
  'size',
  'bold',
  'italic',
  'underline',
  'strike',
  'blockquote',
  'list',
  'bullet',
  'indent',
  'link',
  'image',
  'video',
];
export default function Home(props) {
  const [text, setText] = useState('');

  const handleEditorChange = (value) => {
    setText(value);
  };

  return (
    <ReactQuill
      className={style.quill}
      modules={modules}
      formats={formats}
      theme="snow"
      onChange={props.handleEditorChange}
      placeholder="내용을 입력하세요."
    />
  );
}
