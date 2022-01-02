import axios from "axios";
import { useDispatch } from "react-redux";
import { loginRequest } from "../redux/reducers/LoginReducer";
import React, { useState, useCallback } from "react";
import {
  LoginBody,
  LoginFooter,
  LoginHeader,
  ModalBackGround,
  ModalBox,
} from "../assets/css/login";

const LoginTemplate = ({ open, close }) => {
  const [form, setForm] = useState({
    email: "",
    password: "",
  });

  const onChange = useCallback(
    (e) => {
      setForm({
        ...form,
        [e.target.name]: e.target.value,
      });
    },
    [form]
  );

  const dispatch = useDispatch();
  const onClick = useCallback((e) => {
    dispatch(loginRequest(form));
  });
  // const onClick = useCallback(
  //   (e) => {
  //     axios
  //       .post("/api/login", form)
  //       .then((response) => {
  //         localStorage.setItem("token", response.headers.authorization);
  //         console.log(localStorage.getItem("token"));
  //       })
  //       .catch((err) => {
  //         console.log(err);
  //       });
  //   },
  //   [form]
  // );

  return open ? (
    <ModalBackGround>
      <ModalBox>
        <LoginHeader>
          로그인
          <button className="close" onClick={close}>
            {" "}
            &times;{" "}
          </button>
        </LoginHeader>
        <LoginBody className="login-body">
          <div>
            <input
              id="email"
              name="email"
              type="email"
              placeholder="Email"
              onChange={onChange}
            />
          </div>
          <div>
            <input
              id="password"
              name="password"
              type="password"
              placeholder="Password"
              onChange={onChange}
            />
          </div>
        </LoginBody>
        <LoginFooter>
          <button onClick={onClick}>로그인</button>
        </LoginFooter>
      </ModalBox>
    </ModalBackGround>
  ) : null;
};

export default LoginTemplate;
