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
  };

  const handlePriceChange = (value) => {
    const floatValue = parseFloat(value);

    setNewData((prev) => ({ ...prev, price: floatValue }));
  };

  const handleWeightChange = (value) => {
    const numericValue = Number(value);

    setNewData((prev) => ({ ...prev, weight: numericValue }));
  };

  const handleImageUrlChange = (value) => {
    setNewData((prev) => ({ ...prev, imageUrl: value }));
  };
  const handleDescriptionChange = (value) => {
    setNewData((prev) => ({ ...prev, description: value }));
  };
  const handleCategoryIdChange = (value) => {
    const numericValue = Number(value);

    setNewData((prev) => ({ ...prev, categoryId: numericValue }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    onSubmit(newData);
  };

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <InputText
        label="Product name"
        name="name"
        value={newData.name}
        type="text"
        required
        maxLength={40}
        onChange={handleNameChange}
        className={styles.input}
      />
      <InputText
        label="Price"
        name="name"
        value={newData.price}
        type="number"
        step="0.01"
        required
        maxLength={40}
        onChange={handlePriceChange}
        className={styles.input}
      />
      <InputText
        label="Weight"
        name="weight"
        value={newData.weight}
        type="number"
        required
        maxLength={40}
        onChange={handleWeightChange}
        className={styles.input}
      />
      <InputText
        label="Image url"
        name="image"
        value={newData.imageUrl}
        type="text"
        required
        maxLength={40}
        onChange={handleImageUrlChange}
        className={styles.input}
      />
      <InputText
        label="Description"
        name="description"
        value={newData.description}
        type="text"
        required
        maxLength={40}
        onChange={handleDescriptionChange}
        className={styles.input}
      />
      <InputText
        label="Category id"
        name="category"
        value={newData.categoryId}
        type="number"
        required
        maxLength={40}
        onChange={handleCategoryIdChange}
        className={styles.input}
      />
      <button type="submit" className={styles.submitButton}>
        Confirm
      </button>
    </form>
  );
}

export default Form;
