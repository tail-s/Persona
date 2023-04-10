/* eslint-disable react-hooks/rules-of-hooks */
import { useEffect, useState } from 'react';
import { useRecoilState } from 'recoil';
import { communityApis } from '../../apis/communityApis';
import { postWriteModal, postDetailModal, popostsState } from '../../states/communityState';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCrown, faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import Header from '../../components/Common/Header';
import WriteModal from '../../components/CommunityPage/PostWriteModal';
import DetailModal from '../../components/CommunityPage/PostDetailModal';
import Post from '../../components/CommunityPage/Post';
// import Footer from "../../components/Common/Footer";
import style from './Community.module.scss';
import axios from 'axios';

export default function List() {
  const [searchQuery, setSearchQuery] = useState('');
  const [showWriteModal, setShowWriteModal] = useRecoilState(postWriteModal);
  const [showDetailModal, setShowDetailModal] = useRecoilState(postDetailModal);
  const [posts, setPosts] = useState([]);
  const [poposts, setPoposts] = useState([]);
  const [page, setPage] = useState(0);
  const [sort, setSort] = useState('최신순');
  const [keyword, setKeyword] = useState('');
  const [openDropDown, setOpenDropDown] = useState(false);

  const clickDropDown = () => {
    setOpenDropDown(!openDropDown);
  };

  const BASE_URL = 'https://j8b301.p.ssafy.io';
  useEffect(() => {
    axios.get(BASE_URL + communityApis.BOARD_LIST_GET_API(page, sort, keyword)).then((res) => {
      setPosts(res.data.value.content);
    });
  }, [posts]);

  useEffect(() => {
    axios.get(BASE_URL + communityApis.BOARD_TOP_LIST_GET_API).then((res) => {
      setPoposts(res.data.value);
    });
  }, [poposts]);

  useEffect(() => {
    axios.get(BASE_URL + communityApis.BOARD_LIST_GET_API(page, sort, keyword)).then((res) => {
      console.log(res.data.value);
      setPosts(res.data.value.content);
    });
  }, [sort]);

  const searchPostHandler = (event) => {
    event.preventDefault();
    setKeyword(`${searchQuery}`);
    axios.get(BASE_URL + communityApis.BOARD_LIST_GET_API(page, sort, keyword)).then((res) => {
      console.log(res.data.value.content);
      setPosts(res.data.value.content);
    });
    console.log(`Searching for: ${searchQuery}`);
  };

  const startWriteHandler = () => {
    setShowWriteModal(true);
  };

  return (
    <div className={style.wrapper}>
      <Header />
      {showWriteModal && <WriteModal />}
      {showDetailModal && <DetailModal />}
      <div className={style.intro}>
        <div className={style.title}>커뮤니티</div>
        <div className={style.content}>궁금한 점부터 피드백까지 다양한 이야기를 나눌 수 있습니다.</div>
      </div>
      <div className={style.container}>
        <div className={style.left}>
          <form className={style.searchForm} autoComplete="off">
            <div className={style.searchInput}>
              <input
                className={style.searchText}
                type="text"
                id="input_search"
                placeholder="글 제목, 글 내용, 작성자 검색"
                maxLength={200}
                value={searchQuery}
                onChange={(event) => setSearchQuery(event.target.value)}
              />
              <button type="submit" className={style.search} onClick={searchPostHandler}>
                <FontAwesomeIcon icon={faMagnifyingGlass} style={{ color: '#5e5e5e' }} />
              </button>
            </div>
          </form>
          <div className={style.writePost}>
            <button className={style.writeBtn} onClick={startWriteHandler}>
              새로운 글을 남겨보세요.
            </button>
          </div>
          <div className={style.posts}>
            <div className={style.sort}>
              <div className={style.dropdown}>
                <div className={style.dropdownHeader} onClick={clickDropDown}>
                  {sort}
                </div>
                {openDropDown && (
                  <div className={style.dropdownOptions}>
                    <div
                      onClick={() => {
                        setSort('최신순');
                        clickDropDown();
                      }}>
                      최신순
                    </div>
                    <div
                      onClick={() => {
                        setSort('좋아요순');
                        clickDropDown();
                      }}>
                      좋아요순
                    </div>
                    <div
                      onClick={() => {
                        setSort('조회순');
                        clickDropDown();
                      }}>
                      조회순
                    </div>
                    <div
                      onClick={() => {
                        setSort('댓글수순');
                        clickDropDown();
                      }}>
                      댓글수순
                    </div>
                  </div>
                )}
              </div>
            </div>
            {posts.map((post) => (
              <div key={post.id}>
                <Post key={post.id} {...post} />
              </div>
            ))}
          </div>
        </div>
        <div className={style.right}>
          <div className={style.popular}>
            <div className={style.more}>
              <FontAwesomeIcon icon={faCrown} style={{ color: '#ecc022' }} />
              <div className={style.title}>인기글 TOP3</div>
            </div>
            <div className={style.posts}>
              {poposts.map((popost) => (
                <div key={popost.id}>
                  <Post key={popost.id} {...popost} />
                </div>
              ))}
            </div>
          </div>
        </div>
      </div>
      {/* <Footer /> */}
    </div>
  );
}
