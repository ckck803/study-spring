import axios from "axios";
import { all, call, put, takeEvery, fork } from "redux-saga/effects";
import {} from "../reducers/LoginReducer";
import { SIGNUP_REQUEST, SIGNUP_SUCCESS } from "../type";

const SignupUserAPI = (signupData) => {
  return axios.post("/api/signup", signupData).then((response) => {
    return response.data;
  });
};

function* signupUser(action) {
  try {
    const result = yield call(SignupUserAPI, action.data);

    yield put({
      type: SIGNUP_SUCCESS,
      data: result,
    });
  } catch (e) {
    yield put({
      type: SIGNUP_REQUEST,
    });
  }
}

function* watchSignupUser() {
  yield takeEvery(SIGNUP_REQUEST, signupUser);
}

export default function* signupSaga() {
  yield all([fork(watchSignupUser)]);
}
