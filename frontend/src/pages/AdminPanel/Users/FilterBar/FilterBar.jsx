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
    actives,
    selectedActive,
    onSortChange,
    onOrderChange,
    onActiveChange,
  } = props;

  const filterOrderText =
    filterOrder === "ASC"
      ? `${filterField.lowerBound}-${filterField.upperBound}`
      : `${filterField.upperBound}-${filterField.lowerBound}`;

  const handleFilterFieldChange = (value) => {
    const newSelectedField = filterFields.find(
      (field) => field.value === value
    );
    if (newSelectedField) {
      onSortChange(newSelectedField);
    }
  };

  const handleActiveChange = (value) => {
    const newSelectedActive = actives.find(
      (active) => active.value === value
    );
    if (newSelectedActive) {
      onActiveChange(newSelectedActive);
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
          label="User status"
          options={actives}
          value={selectedActive.value}
          name="user status"
          onChange={handleActiveChange}
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
