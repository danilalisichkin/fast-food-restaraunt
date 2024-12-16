import React from "react";
import styles from "./index.module.css";
import arrowTop from "./icons/arrow-top.svg";
import arrowDown from "./icons/arrow-down.svg";
import InputSelect from "../../../../components/Inputs/InputSelect/InputSelect";

function FilterBar(props) {
  const {
    filterFields,
    filterField,
    filterOrder,
    categories,
    selectedCategory,
    onSortChange,
    onOrderChange,
    onCategoryChange,
  } = props;

  const filterOrderText =
    filterOrder === "ASC"
      ? `${filterField.lowerBound}-${filterField.upperBound}`
      : `${filterField.upperBound}-${filterField.lowerBound}`;

  const handleFilterFieldChange = (value) => {
    console.log(categories);
    const newSelectedField = filterFields.find(
      (field) => field.value === value
    );
    if (newSelectedField) {
      onSortChange(newSelectedField);
    }
  };

  const handleCategoryChange = (value) => {
    const numericValue = Number(value);

    const newSelectedCategory = categories.find(
      (category) => category.value === numericValue
    );

    if (newSelectedCategory) {
      onCategoryChange(newSelectedCategory);
    }
  };

  const toggleOrder = () => {
    const newOrder = filterOrder === "ASC" ? "DESC" : "ASC";
    onOrderChange(newOrder);
  };

  return (
    <form className={styles.filterBar}>
      <div className={styles.filterBar__filterContainer}>
        <InputSelect
          label="Filter by"
          options={filterFields}
          value={filterField.value}
          name="filter field"
          onChange={handleFilterFieldChange}
        />
      </div>
      <div className={styles.filterBar__filterContainer}>
        <InputSelect
          label="Category"
          options={categories}
          value={selectedCategory.value}
          name="filter category"
          onChange={handleCategoryChange}
        />
      </div>
      <div className={styles.filterBar__filterContainer}>
        <img
          src={filterOrder === "ASC" ? arrowTop : arrowDown}
          className={styles.filterOrder__icon}
          alt="icon"
          onClick={toggleOrder}
        />
        <p>{filterOrderText}</p>
      </div>
    </form>
  );
}

export default FilterBar;
