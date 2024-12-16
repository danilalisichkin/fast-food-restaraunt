import React, { useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import styles from './index.module.css';
import Modal from "../../components/Modal/Modal";
import InputText from "../../components/Inputs/InputText/InputText";
import axios from "axios";

const MESSAGES = {
  SUCCESS: "You have successfully registered!",
  BUY_SOMETHING: "Now log in and then buy something ;)"
}

const DEFAULT_SIGN_UP_DATA = {
  phone: "",
  email: "",
  password: "",
  firstName: "",
  lastName: ""
};

function SignUp() {
  const navigate = useNavigate();

  const [signUpData, setSignUpData] = useState(DEFAULT_SIGN_UP_DATA);

  const [errorMessage, setErrorMessage] = useState({
    cause: "",
    message: "",
  });

  const [modalWithFormOpen, setModalWithFormOpen] = useState(false);
  const openModalWithForm = () => setModalWithFormOpen(true);
  const closeModalWithForm = () => setModalWithFormOpen(false);

  const [modalWithErrorOpen, setModalWithErrorOpen] = useState(false);
  const openModalWithError = () => setModalWithErrorOpen(true);
  const closeModalWithError = () => setModalWithErrorOpen(false);

  const [modalWithSuccessOpen, setModalWithSuccessOpen] = useState(false);
  const openModalWithSuccess = () => setModalWithSuccessOpen(true);
  const closeModalWithSuccess = () => setModalWithSuccessOpen(false);

  useEffect(() => {
    openModalWithForm();
  }, []);

  const handlePhoneChange = (value) => {
    setSignUpData((prevData) => ({
      ...prevData,
      phone: value,
    }));
  };

  const handleEmailChange = (value) => {
    setSignUpData((prevData) => ({
      ...prevData,
      email: value,
    }));
  };

  const handleFirstNameChange = (value) => {
    setSignUpData((prevData) => ({
      ...prevData,
      firstName: value,
    }));
  };

  const handleLastNameChange = (value) => {
    setSignUpData((prevData) => ({
      ...prevData,
      lastName: value,
    }));
  };

  const handlePasswordChange = (value) => {
    setSignUpData((prevData) => ({
      ...prevData,
      password: value,
    }));
  };

  const handleSignUpSubmit = async (e) => {
    e.preventDefault();
    try {
      await axios.post(
        "http://localhost:8080/api/v1/auth/sign-up",
        signUpData
      );
      closeModalWithForm();
      openModalWithSuccess();
      setTimeout(() => {
        closeModalWithSuccess();
        navigate("/sign-in");
      }, 4000);
    } catch (error) {
      console.error(
        "Sign Up Error:",
        error.response ? error.response : error.message
      );
      setErrorMessage(error.response.data);
      closeModalWithForm();
      openModalWithError();
    }
  };

  const handleErrorModalClose = () => {
    closeModalWithError();
    setSignUpData(DEFAULT_SIGN_UP_DATA);
    openModalWithForm();
  };

  return (
    <div className={styles.main__mainContainer}>
      <Modal isOpen={modalWithFormOpen} onClose={null}>
        <p className={styles.greetingCaption}>Sign up</p>
        <form className={styles.signUpForm} onSubmit={handleSignUpSubmit}>
          <InputText
            label="Phone"
            name="phone"
            value={signUpData.phone}
            type="text"
            required="true"
            maxLength={40}
            onChange={handlePhoneChange}
            className={styles.input}
          />
          <InputText
            label="Email"
            name="email"
            value={signUpData.email}
            type="text"
            required="true"
            maxLength={40}
            onChange={handleEmailChange}
            className={styles.input}
          />
          <InputText
            label="Password"
            name="password"
            value={signUpData.password}
            type="password"
            required="true"
            maxLength={20}
            onChange={handlePasswordChange}
            className={styles.input}
          />
          <InputText
            label="Fist name"
            name="firstname"
            value={signUpData.firstName}
            type="text"
            required="true"
            maxLength={40}
            onChange={handleFirstNameChange}
            className={styles.input}
          />
          <InputText
            label="Last name"
            name="lastname"
            value={signUpData.lastName}
            type="text"
            required="true"
            maxLength={40}
            onChange={handleLastNameChange}
            className={styles.input}
          />
          <button type="submit">Sign up</button>
        </form>
      </Modal>
      <Modal isOpen={modalWithErrorOpen} onClose={handleErrorModalClose}>
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
      <Modal isOpen={modalWithSuccessOpen}>
        <p className={styles.success__caption}>{MESSAGES.SUCCESS}</p>
        <p className={styles.success__message}>{MESSAGES.BUY_SOMETHING}</p>
      </Modal>
    </div>
  );
}

export default SignUp;
