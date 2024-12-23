import React from "react";
import styles from "./index.module.css";

function OrderItem({ item }) {
  const { product, quantity, relevantPrice } = item;

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
      <p className={styles.item__productPrice}>${relevantPrice}</p>
      <p className={styles.item__quantity}>{quantity} pc.</p>
    </div>
  );
}

export default OrderItem;
