import { useCallback, useState } from "react";
import LoginTemplate from "./components/LoginTemplate";
import SignUpTemplate from "./components/SignUpTemplate";

function App() {
  const [loginOpen, setLoginOpen] = useState(false);
  const [singupOpen, setSignupOpen] = useState(false);

  const onClickLoginButton = useCallback(() => {
    setLoginOpen(!loginOpen);
  }, [loginOpen]);

  const onClickSignupButton = useCallback(() => {
    setSignupOpen(!singupOpen);
  }, [singupOpen]);

  return (
    <div>
      <button onClick={onClickLoginButton}>로그인</button>
      <button onClick={onClickSignupButton}>회원 가입</button>
      <LoginTemplate open={loginOpen} close={onClickLoginButton} />
      <SignUpTemplate open={singupOpen} onChangeOpen={onClickSignupButton} />
    </div>
  );
}

export default App;
