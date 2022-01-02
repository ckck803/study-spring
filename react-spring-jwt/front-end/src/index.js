import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { composeWithDevTools } from 'redux-devtools-extension'; // 리덕스 개발자 도구
import { createStore, applyMiddleware } from 'redux';
import { Provider } from 'react-redux';
import LoginReducer from './redux/reducers/LoginReducer'
import createSagaMiddleware from 'redux-saga';
import authSaga from './redux/sagas/LoginSaga'

const sagaMiddleware = createSagaMiddleware();
const store = createStore(LoginReducer, composeWithDevTools(applyMiddleware(sagaMiddleware)))
// const store = createStore(LoginReducer);

sagaMiddleware.run(authSaga);

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('root')
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
