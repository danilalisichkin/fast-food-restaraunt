import React from "react";
import styles from "./index.module.css";
import InputSelect from "../../../components/Inputs/InputSelect/InputSelect";
import arrowTop from "./icons/arrow-top.svg";
import arrowDown from "./icons/arrow-down.svg";

function FilterBar(props) {
  const {
    filterFields,
    filterField,
    filterOrder,
    statuses,
    selectedStatus,
    onSortChange,
    onOrderChange,
    onStatusChange,
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

  const handleStatusChange = (value) => {
    const newSelectedStatus = statuses.find(
      (status) => status.value === value
    );
    if (newSelectedStatus) {
      onStatusChange(newSelectedStatus);
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
          label="Status"
          options={statuses}
          value={selectedStatus.value}
          name="order status"
          onChange={handleStatusChange}
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
