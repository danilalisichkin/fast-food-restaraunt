import React from "react";
import styles from "./index.module.css";

function InputSelect({ label, options, value, name, onChange, className }) {
  return (
    <div className={`${styles.inputContainer} ${className}`}>
      {label && <label>{label}</label>}
      <select
        name={name}
        className={styles.filterFieldContainer__sortFieldSelect}
        onChange={(event) => onChange(event.target.value)}
        value={value}
      >
        {options.map((option) => (
          <option key={option.id} value={option.value}>
            {option.label.toLowerCase()}
          </option>
        ))}
      </select>
    </div>
  );
}

export default InputSelect;
