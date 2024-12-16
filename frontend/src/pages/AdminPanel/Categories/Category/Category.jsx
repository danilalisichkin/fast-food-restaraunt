import React from "react";
import styles from "./index.module.css";
import editIcon from "./icons/edit-record.svg";
import deleteIcon from "./icons/delete-record.svg";

function Category({ category, handleEdit, handleDelete }) {
  const { id, name } = category;

  return (
    <div className={styles.category}>
      <p className={styles.category__id}>{id}</p>
      <p className={styles.category__name}>{name}</p>
      <div className={styles.buttonContainer}>
        <img
          className={styles.buttonContainer__icon}
          src={editIcon}
          alt="Edit"
          onClick={handleEdit}
        />
        <img
          className={styles.buttonContainer__icon}
          src={deleteIcon}
          alt="Delete"
          onClick={handleDelete}
        />
      </div>
    </div>
  );
}

export default Category;
