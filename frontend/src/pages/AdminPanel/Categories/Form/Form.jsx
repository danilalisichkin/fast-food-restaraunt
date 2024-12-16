import React, { useEffect, useState } from "react";
import InputText from "../../../../components/Inputs/InputText/InputText";
import styles from "./index.module.css";

function Form({ data, onSubmit }) {
  const [newData, setNewData] = useState(data);

  useEffect(() => {
    setNewData(data);
  }, [data]);

  const handleNameChange = (value) => {
    setNewData((prev) => ({ ...prev, name: value }));
    console.log(data);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(newData);
  };

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <InputText
        label="Category name"
        name="name"
        value={newData.name}
        type="text"
        required
        maxLength={40}
        onChange={handleNameChange}
        className={styles.input}
      />
      <button type="submit" className={styles.submitButton}>
        Confirm
      </button>
    </form>
  );
}

export default Form;
