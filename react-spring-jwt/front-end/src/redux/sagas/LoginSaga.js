import axios from 'axios';
import { LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS } from "../type"
import { all, call, fork, put, takeEvery } from 'redux-saga/effects';

const loginUserAPI = (loginData) => {
    console.log(loginData, "loginData")
    const config = {
        headers: {
            "Content-Type": "application/json"
        }
    }

    return axios.post("api/auth", loginData, config);
}


function* loginUser(loginaction) {
    try {
        const result = yield call(loginUserAPI, loginaction.payload)
        console.log(result)
        yield put({
            type: LOGIN_SUCCESS,
            payload: result.data
        })
    } catch (e) {
        yield put({
            type: LOGIN_FAILURE,
            payload: e.response
        })
    }
}

function* watchLoginUser() {
    yield takeEvery(LOGIN_REQUEST, loginUser);
}

export default function* authSaga() {
    yield all([
        fork(watchLoginUser),
    ])
}