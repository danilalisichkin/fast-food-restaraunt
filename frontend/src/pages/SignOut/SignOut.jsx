import React, { useContext, useEffect, useState } from "react";
import { useNavigate } from 'react-router-dom';
import styles from "./index.module.css";
import Modal from "../../components/Modal/Modal";
import { AuthContext } from "../../AuthContext";

const MESSAGES = {
  SUCCESS: "You have successfully signed out!"
}

function SignOut() {
  const navigate = useNavigate();

  const { logout } = useContext(AuthContext);

  const [isModalWithFormOpen, setModalWithFormOpen] = useState(false);
  const openModalWithForm = () => setModalWithFormOpen(true);
  const closeModalWithForm = () => setModalWithFormOpen(false);

  const [isModalWithSuccessOpen, setModalWithSuccessOpen] = useState(false);
  const openModalWithSuccess = () => setModalWithSuccessOpen(true);

  useEffect(() => {
    openModalWithForm();
  }, []);

  const handleSignOutSubmit = (e) => {
    e.preventDefault();
    logout();
    closeModalWithForm();
    openModalWithSuccess();
  };

  const handleSignOutCancel = (e) => {
    e.preventDefault();
    navigate("/catalog");
  }

  return (
    <div className={styles.main__mainContainer}>
      <Modal isOpen={isModalWithFormOpen} onClose={null}>
        <p className={styles.greetingCaption}>Sign out</p>
        <p className={styles.text}>Are you sure?</p>
        <div className={styles.buttonContainer}>
          <button type="submit" onClick={handleSignOutSubmit}>YES</button>
          <button type="submit" onClick={handleSignOutCancel}>NO</button>
        </div>
      </Modal>
      <Modal isOpen={isModalWithSuccessOpen}>
        <p className={styles.success__caption}>{MESSAGES.SUCCESS}</p>
      </Modal>
    </div>
  );
}

export default SignOut;
