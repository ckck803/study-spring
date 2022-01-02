import { combineReducers } from "redux";
import LoginReducer from "./LoginReducer";
import SignupReducer from "./SignupReducer";

const createRootReducer = combineReducers({
  LoginReducer,
  SignupReducer,
});

export default createRootReducer;
