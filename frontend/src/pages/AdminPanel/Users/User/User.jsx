import { React, useState } from "react";
import styles from "./index.module.css";
import PhoneIcon from "./icons/phone.svg";
import MailIcon from "./icons/mail.svg";
import axios from "axios";

function User({ user, onStatusChange, onActivate, onDeactivate }) {
  const { phone, email, firstName, lastName, active } = user;

  function handleClickOnStatus(e) {
    e.preventDefault();
    if (active) {
      onDeactivate(phone);
    } else {
      onActivate(phone);
    }
    onStatusChange(phone);
  }

  return (
    <div className={styles.user}>
      <div className={styles.contactContainer}>
        <img
          src={MailIcon}
          className={styles.contactContainer__icon}
          alt="icon"
        />
        <a href={`mailto:${email}`}>{email}</a>
      </div>
      <div className={styles.contactContainer}>
        <img
          src={PhoneIcon}
          className={styles.contactContainer__icon}
          alt="icon"
        />
        <a href={`callto:${phone}`}>{phone}</a>
      </div>
      <p className={styles.user__firstName}>{firstName}</p>
      <p className={styles.user__lastName}>{lastName}</p>
      <button
        className={`${styles.activateButton} ${
          active ? styles.active : styles.blocked
        }`}
        onClick={handleClickOnStatus}
      >
        {active ? "active" : "blocked"}
      </button>
    </div>
  );
}

export default User;
