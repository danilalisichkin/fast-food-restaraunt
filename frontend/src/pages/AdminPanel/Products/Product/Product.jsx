import React from "react";
import styles from "./index.module.css";
import editIcon from "./icons/edit-record.svg";
import { useNavigate } from "react-router-dom";

function Product({ product, handleEdit }) {
  const navigate = useNavigate();
  const { id, name, price, weight, imageUrl, description, categoryId } =
    product;

  return (
    <div className={styles.product}>
      <a
        className={styles.product__imageContainer}
        href={"/catalog/product/" + id}
      >
        <img className={styles.product__image} src={`${process.env.REACT_APP_IMAGE_PATH}/${imageUrl}`} alt="food" />
      </a>
      <p className={styles.product__id}>{id}</p>
      <p className={styles.product__name}>{name}</p>
      <p className={styles.product__weight}>g{weight}</p>
      <p className={styles.product__description}>{description}</p>
      <p className={styles.product__price}>${price}</p>
      <p className={styles.product__categoryId}>category: {categoryId}</p>
      <div className={styles.buttonContainer}>
        <img
          className={styles.buttonContainer__icon}
          src={editIcon}
          alt="Edit"
          onClick={handleEdit}
        />
      </div>
    </div>
  );
}

export default Product;
