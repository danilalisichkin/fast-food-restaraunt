import React from "react";
import styles from "./index.module.css";

function InputNumeric({ label, value, name, min, max, onChange, className }) {
  const handleValueChange = (event) => {
    onChange(event.target.value);
  };

  return (
    <div className={`${styles.inputContainer} ${className}`}>
      {label && <label className={styles.label}>{label}</label>}
      <input
        type="number"
        name={name}
        value={value}
        min={min}
        max={max}
        onChange={handleValueChange}
        onKeyDown={(e) => e.preventDefault()}
      />
    </div>
  );
}

export default InputNumeric;
