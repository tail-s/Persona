import React from 'react';
import { Dashboard } from './components/Dashboard';
import { RecoilRoot } from 'recoil';
// import ReactDOM from 'react-dom';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import { DashboardContextProvider } from './components/Dashboard';
import { SettingsContextProvider } from './components/Settings';
import Main from './Pages/Main/Main';
import Community from './Pages/Community/Community';
import Practice from './Pages/Practice/Practice';
import PracticeDetail from './Pages/Practice/PracticeDetail';
import Storage from './Pages/Storage/Storage';
import Token from './Pages/Token';
import Mypage from './Pages/Mypage/Mypage';
import Savepage from './Pages/Save/Savepage';
import Error from './Pages/Error';
function App() {
  return (
    <div className="App">
      <header className="App-header">
        <RecoilRoot>
          <DashboardContextProvider>
            <SettingsContextProvider>
              <Routes>
                <Route path="/" element={<Main></Main>} />
                {/* <Route path='/Bookmark' element={<Bookmark/>} /> */}
                <Route path="/Community" element={<Community />} />
                <Route path='/Mypage' element={<Mypage/>} />
                <Route path="/Practice" element={<Practice />} />
                <Route path="/Practice/Detail" element={<PracticeDetail />} />
                <Route path="/Storage" element={<Storage />} />
                <Route path="/oauth2/token" element={<Token />} />
                <Route
                  path="/dashboard/:scriptid"
                  element={
                    <React.Suspense fallback={<>Loading Fallback ...</>}>
                      <Dashboard />
                    </React.Suspense>
                  }
                />
                <Route path="/savepage/:videoid" element={<Savepage />} />
                <Route path="/error" element={<Error />} />
              </Routes>
            </SettingsContextProvider>
          </DashboardContextProvider>
        </RecoilRoot>
      </header>
    </div>
  );
}

export default App;
