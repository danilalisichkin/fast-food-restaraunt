import React, { useState } from "react";
import styles from "./index.module.css";

function InputText({ label, value, name, required, type, step, maxLength, onChange, className }) {
  const [isFocused, setIsFocused] = useState(false);

  const handleValueChange = (event) => {
    onChange(event.target.value);
  };

  return (
    <div className={`${styles.inputContainer} ${className}`}>
      {(value === "" && !isFocused) && (
        <label htmlFor={name}>
          {required === "true" ? "*" : ""}
          {label}
        </label>
      )}
      <input
        type={type}
        step={step || ""}
        name={name}
        value={value}
        required={required === "true"}
        maxLength={maxLength}
        onChange={handleValueChange}
        onFocus={() => setIsFocused(true)}
        onBlur={() => setIsFocused(false)}
      />
    </div>
  );
}

export default InputText;