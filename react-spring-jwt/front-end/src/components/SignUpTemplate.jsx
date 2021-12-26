import React, { useCallback, useState } from "react";
import { ModalBox } from "../assets/css/login";
import axios from "axios";
import {
  ModalBackGround,
  SignUpBody,
  SignUpFooter,
  SignUpHeader,
} from "../assets/css/signup";

const SignUpTemplate = ({ open, onChangeOpen }) => {
  const [form, setForm] = useState({
    username: "",
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

  // const onClickSignUp = useCallback(() => {
  //   axios
  //     .post("http://localhost:8080/api/signup", form, {
  //       headers: {
  //         xsrfCookieName: "XSRF-TOKEN",
  //         xsrfHeaderName: "X-XSRF-TOKEN",
  //         "Content-Type": "application/x-www-form-urlencoded",
  //       },
  //     })
  //     .then((Response) => {
  //       console.log(Response);
  //     });
  // }, [form]);

  const onClickSignUp = useCallback(() => {
    axios.post("http://localhost:8080/api/signup", form).then((Response) => {
      console.log(Response);
    });
  }, [form]);

  return open ? (
    <ModalBackGround className="signup-modal-box">
      <ModalBox>
        <SignUpHeader>
          회원 가입
          <button className="close" onClick={onChangeOpen}>
            {" "}
            &times;{" "}
          </button>
        </SignUpHeader>
        <SignUpBody>
          <div>
            <input
              type="username"
              id="username"
              name="username"
              placeholder="이름"
              onChange={onChange}
            />
          </div>
          <div>
            <input
              type="email"
              id="email"
              name="email"
              placeholder="Email"
              onChange={onChange}
            />
          </div>
          <div>
            <input
              type="password"
              id="password"
              name="password"
              placeholder="Password"
              onChange={onChange}
            />
          </div>
        </SignUpBody>
        <SignUpFooter>
          <button onClick={onClickSignUp}>회원가입</button>
        </SignUpFooter>
      </ModalBox>
    </ModalBackGround>
  ) : null;
};

export default SignUpTemplate;
