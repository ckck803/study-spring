import { all, fork } from "redux-saga/effects";
import loginSaga from "./LoginSaga";
import signupSaga from "./SignupSaga";

export default function* rootSaga() {
  yield all([fork(loginSaga), fork(signupSaga)]);
}
