import styled, { keyframes } from "styled-components";

export const ModalFade = keyframes`{
    from {
        opacity: 0;
        margin-top: -50px;
    }
    to {
        opacity: 1;
        margin-top: 0;
    }
}
`;

export const ModalBackGround = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  animation: modal-bg-show 0.3s;
`;

export const ModalBox = styled.div`
  width: 90%;
  max-width: 400px;
  margin: 0 auto;
  border-radius: 0.3rem;
  background-color: #fff;
  animation: ModalFade 0.3s;
  overflow: hidden;
`;

export const SignUpHeader = styled.div`
  position: relative;
  padding: 16px 64px 16px 16px;
  background-color: #f1f1f1;
  font-weight: 700;

  & > button {
    position: absolute;
    top: 15px;
    right: 15px;
    width: 30px;
    font-size: 21px;
    font-weight: 700;
    text-align: center;
    color: #999;
    border: 0;
  }
`;

export const SignUpBody = styled.div`
  padding: 10px;

  & > div {
    padding: 5px;
    display: flex;
    align-items: center;
    justify-content: center;
  }

  & > div > input {
    padding-top: 5px;
    padding-bottom: 5px;
    padding-left: 15px;
    padding-right: 15px;
    width: 290px;
    height: 30px;
    font-size: 14px;
    border: 1px solid rgb(222, 226, 230);
  }
`;

export const SignUpFooter = styled.div`
  border-top: 1px solid;
  padding: 7px 16px;
  text-align: right;
  & > button {
    padding: 6px 12px;
    color: #fff;
    background-color: #6c757d;
    border-radius: 5px;
    font-size: 13px;
  }
`;
