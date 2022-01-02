import axios from "axios";
import { LOGIN_FAILURE, LOGIN_REQUEST, LOGIN_SUCCESS } from "../type";
const initialState = {
  token: localStorage.getItem("token"),
};

export const loginRequest = (loginData) => ({
  type: LOGIN_REQUEST,
  data: loginData,
});

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

const LoginReducer = (state = initialState, action) => {
  switch (action.type) {
    case LOGIN_REQUEST:
      console.log("Log In Reqeust");
      return {
        ...state,
      };
    case LOGIN_SUCCESS:
      console.log("Log In Success");
      return {
        ...state,
        token: localStorage.getItem("token"),
      };
    case LOGIN_FAILURE:
      console.log("Log In Failure");
      return {
        ...state,
      };
    default:
      return state;
  }
};

export default LoginReducer;
