import React from "react";
import styles from "./index.module.css";
import InputNumeric from "../../../components/Inputs/InputNumeric/InputNumeric";
import DiagonalCrossIcon from "./icons/cross-diagonal.svg";

function CartItem({ item, onQuantityChange, onRemove }) {
  const { product, quantity } = item;

  return (
    <div className={styles.item}>
      <a
        href={"catalog/product/" + product.id}
        className={styles.item__imageContainer}
      >
        <img
          src={`${process.env.REACT_APP_IMAGE_PATH}/${product.imageUrl}`}
          className={styles.imageContainer__image}
          alt="food"
        />
      </a>
      <p className={styles.item__productName}>{product.name}</p>
      <p className={styles.item__productWeight}>{product.weight}g</p>
      <p className={styles.item__productPrice}>${product.price}</p>
      <InputNumeric
        value={quantity}
        name={`item_${product.id}_quantity`}
        min={1}
        max={20}
        onChange={(value) => onQuantityChange(value)}
      />
      <button className={styles.item__removeButton} onClick={onRemove}>
        <img
          src={DiagonalCrossIcon}
          className={styles.removeButton__image}
          alt="icon"
        />
      </button>
    </div>
  );
}

export default CartItem;
