import { LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS } from "../type"
const initialState = {
    token: localStorage.getItem('token'),
    username: "",
    email: "",
    password: "",
    userRole: "",
    errorMsg: "",
}

const LoginReducer = (state = initialState, action) => {
    switch (action.type) {
        case LOGIN_REQUEST:
            return {
                ...state,
            };
        case LOGIN_SUCCESS:
            localStorage.setItem("token", action.payload.token)
            return {
                ...state,

            };
        case LOGIN_FAILURE:
            return;
        default:
            return state;
    }
}