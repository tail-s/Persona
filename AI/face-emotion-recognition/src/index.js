import React from 'react';
import * as ReactDOM from 'react-dom';
// import ReactDOM from 'react-dom';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
// import './index.css';
import App from './App';
// import { DashboardContextProvider } from './components/Dashboard';
// import { SettingsContextProvider } from './components/Settings';
// import reportWebVitals from './reportWebVitals';

// const Dashboard = React.lazy(() => import("./components/Dashboard/Dashboard"));
// const root = ReactDOM.createRoot(document.getElementById("root"));

ReactDOM.render(
  <BrowserRouter>
    <App></App>
  </BrowserRouter>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
// reportWebVitals(console.log);