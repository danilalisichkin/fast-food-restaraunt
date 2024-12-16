import React from "react";
import styles from "./index.module.css";
import DiagonalCrossIcon from "./icons/cross-diagonal.svg";

function Modal({ isOpen, children, onClose }) {
  if (!isOpen) return null;

  return (
    <div className={styles.modal__overlay}>
      {onClose && (<button className={styles.closeButton} onClick={onClose}>
        <img
          src={DiagonalCrossIcon}
          className={styles.closeButton__image}
          alt="icon"
        />
      </button>)}
      <div
        className={styles.modal__content}
        onClick={(e) => e.stopPropagation()}
      >
        {children}
      </div>
    </div>
  );
}

export default Modal;
