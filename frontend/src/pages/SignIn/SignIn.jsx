import React, { useContext, useEffect, useState } from "react";
import styles from "./index.module.css";
import Modal from "../../components/Modal/Modal";
import InputText from "../../components/Inputs/InputText/InputText";
import axios from "axios";
import { AuthContext } from "../../AuthContext";

const MESSAGES = {
  SUCCESS: "You have successfully logged in!",
  BUY_SOMETHING: "Now go to catalog and then buy something ;)",
};

const DEFAULT_SIGN_IN_DATA = {
  identifier: "",
  password: "",
};

function SignIn() {
  const { login, logout } = useContext(AuthContext);

  const [signInData, setSignInData] = useState(DEFAULT_SIGN_IN_DATA);

  const [errorMessage, setErrorMessage] = useState({
    cause: "",
    message: "",
  });

  const [isModalWithFormOpen, setModalWithFormOpen] = useState(false);
  const openModalWithForm = () => setModalWithFormOpen(true);
  const closeModalWithForm = () => setModalWithFormOpen(false);

  const [isModalWithErrorOpen, setModalWithErrorOpen] = useState(false);
  const openModalWithError = () => setModalWithErrorOpen(true);
  const closeModalWithError = () => setModalWithErrorOpen(false);

  const [isModalWithSuccessOpen, setModalWithSuccessOpen] = useState(false);
  const openModalWithSuccess = () => setModalWithSuccessOpen(true);

  useEffect(() => {
    openModalWithForm();
  }, []);

  const handleIdentifierChange = (value) => {
    setSignInData((prevData) => ({
      ...prevData,
      identifier: value,
    }));
  };

  const handlePasswordChange = (value) => {
    setSignInData((prevData) => ({
      ...prevData,
      password: value,
    }));
  };

  const handleSignInSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await axios.post(
        "http://localhost:8080/api/v1/auth/sign-in",
        signInData
      );
      login(response.data.accessToken);
      localStorage.setItem('refreshToken', response.data.refreshToken);
      startTokenRefresh(Number(response.data.expiresIn) - 1);
      closeModalWithForm();
      openModalWithSuccess();
    } catch (error) {
      console.error("Sign In Error:", error.response || error.message);

      setErrorMessage(error.response?.data || "Ошибка входа");
      closeModalWithForm();
      openModalWithError();
    }
  };

  const refreshToken = async () => {
    const token = localStorage.getItem("refreshToken");

    if (token) {
      try {
        const response = await axios.post(
          "http://localhost:8080/api/v1/auth/refresh-token",
          JSON.stringify({ token }),
          { headers: { "Content-Type": "application/json" } }
        );
        localStorage.setItem('accessToken', response.data.accessToken);
        localStorage.setItem('refreshToken', response.data.refreshToken);
      } catch (error) {
        console.error("Refresh token failed", error);
        logout();
      }
    }
  };

  const [refreshIntervalId, setRefreshIntervalId] = useState(null);

  const startTokenRefresh = (interval) => {
    if (refreshIntervalId) {
      clearInterval(refreshIntervalId);
    }
  
    const id = setInterval(() => {
      refreshToken();
    }, interval * 60 * 1000);
    
    setRefreshIntervalId(id);
  };
  
  useEffect(() => {
    return () => {
      if (refreshIntervalId) {
        clearInterval(refreshIntervalId);
      }
    };
  }, [refreshIntervalId]);

  const handleErrorModalClose = () => {
    closeModalWithError();
    setSignInData(DEFAULT_SIGN_IN_DATA);
    openModalWithForm();
  };

  return (
    <div className={styles.main__mainContainer}>
      <Modal isOpen={isModalWithFormOpen} onClose={null}>
        <p className={styles.greetingCaption}>Sign in</p>
        <div className={styles.redirectContainer}>
          <p className={styles["redirectContainer__text--general"]}>
            Are you new?
          </p>
          <a className={styles.redirectContainer__redirect} href="/sign-up">
            Sign up
          </a>
        </div>
        <form className={styles.signInForm} onSubmit={handleSignInSubmit}>
          <InputText
            label="Phone number or email"
            name="identifier"
            value={signInData.identifier}
            type="text"
            required="true"
            maxLength={40}
            onChange={handleIdentifierChange}
            className={styles.input}
          />
          <InputText
            label="Password"
            name="password"
            value={signInData.password}
            type="password"
            required="true"
            maxLength={20}
            onChange={handlePasswordChange}
            className={styles.input}
          />
          <button type="submit">Sign in</button>
        </form>
      </Modal>
      <Modal isOpen={isModalWithErrorOpen} onClose={handleErrorModalClose}>
        <p className={styles.error__caption}>{errorMessage.cause}</p>
        <p className={styles.error__message}>{errorMessage.message}</p>
        {errorMessage.messages &&
          Object.entries(errorMessage.messages).map(([key, value]) =>
            value.map((message, index) => (
              <p key={index} className={styles.error__message}>
                {key}: {message}
              </p>
            ))
          )}
      </Modal>
      <Modal isOpen={isModalWithSuccessOpen}>
        <p className={styles.success__caption}>{MESSAGES.SUCCESS}</p>
        <p className={styles.success__message}>{MESSAGES.BUY_SOMETHING}</p>
      </Modal>
    </div>
  );
}

export default SignIn;
