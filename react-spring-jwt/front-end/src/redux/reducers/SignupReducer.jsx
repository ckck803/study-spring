import { SIGNUP_FAILURE, SIGNUP_REQUEST, SIGNUP_SUCCESS } from "../type";

const initialState = "";

export const signupRequest = (form) => ({
  type: SIGNUP_REQUEST,
  data: form,
});

const SignupReducer = (state = initialState, action) => {
  switch (action.type) {
    case SIGNUP_REQUEST:
      return {
        ...state,
      };
    case SIGNUP_SUCCESS:
      return {
        ...state,
      };
    case SIGNUP_FAILURE:
      return {
        ...state,
      };
    default:
      return state;
  }
};

export default SignupReducer;
