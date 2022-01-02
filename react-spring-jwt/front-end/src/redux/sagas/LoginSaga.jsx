import axios from "axios";
import { LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS } from "../type";
import { all, call, fork, put, takeEvery } from "redux-saga/effects";

const loginUserAPI = (loginData) => {
  return axios.post("api/login", loginData).then;
};

// export const loginRequest = (loginData) => {
//   const response = axios
//     .post("/api/login", loginData)
//     .then((response) => {
//       localStorage.setItem("token", response.headers.authorization);
//       console.log(localStorage.getItem("token"));
//       return response.data;
//     })
//     .catch((err) => {
//       console.log(err);
//     });
//   return { type: LOGIN_REQUEST, data: response };
// };

function* loginUser(action) {
  try {
    const result = yield call(loginUserAPI, action.data);
    yield put({
      type: LOGIN_SUCCESS,
    });
  } catch (e) {
    yield put({
      type: LOGIN_FAILURE,
    });
  }
}

function* watchLoginUser() {
  yield takeEvery(LOGIN_REQUEST, loginUser);
}

export default function* loginSaga() {
  yield all([fork(watchLoginUser)]);
}
