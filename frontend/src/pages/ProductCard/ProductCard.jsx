import React, { useState, useEffect } from "react";
import styles from "./index.module.css";
import axios from "axios";
import { useNavigate, useParams } from "react-router-dom";
import Modal from "../../components/Modal/Modal";

const MESSAGES = {
  NOT_FOUND: "Product not found.",
  ADD_TO_CART_SUCCESS: "Product added to cart.",
  ADD_TO_CART_FAILURE: "Product already in cart.",
};

function ProductCard() {
  const navigate = useNavigate();

  const [product, setProduct] = useState(null);
  const [loading, setLoading] = useState(true);
  const { id } = useParams();

  const [isModalWithMessageOpen, setModalWithMessageOpen] = useState(false);
  const [isModalWithProductOpen, setModalWithProductOpen] = useState(false);

  const [message, setMessage] = useState("");

  const openModalWithMessage = () => setModalWithMessageOpen(true);
  const closeModalWithMessage = () => setModalWithMessageOpen(false);
  const openModalWithProduct = () => setModalWithProductOpen(true);
  const closeModalWithProduct = () => setModalWithProductOpen(false);

  const accessToken = localStorage.getItem("accessToken");
  const userId = localStorage.getItem("user_id");

  const getProduct = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/products/${id}`
      );
      console.info("Fetched data", response.data);
      setProduct(response.data);
      openModalWithProduct();
    } catch (error) {
      console.error(
        "Request error",
        error.response ? error.response.data : error.message
      );
      setMessage(MESSAGES.NOT_FOUND);
      openModalWithMessage();
    } finally {
      setLoading(false);
    }
  };

  const putItemToCart = async (productId, quantity) => {
    try {
      const response = await axios.post(
        `http://localhost:8080/api/v1/carts/${userId}/items`,
        {
          productId: productId,
          quantity: quantity,
        },
        {
          headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      }
      );
      console.info("Fetched data", response.data);
      closeModalWithProduct();
      setMessage(MESSAGES.ADD_TO_CART_SUCCESS);
      openModalWithMessage();
    } catch (error) {
      console.error(
        "Request error",
        error.response ? error.response.data : error.message
      );
      closeModalWithProduct();
      setMessage(MESSAGES.ADD_TO_CART_FAILURE);
      openModalWithMessage();
    }
  };

  useEffect(() => {
    getProduct();
  }, [id]);

  function handleClickAddToCartButton() {
    putItemToCart(product.id, 1);
  }

  function handleCloseModalWithProduct() {
    navigate("/catalog");
  }

  function handleCloseModalWithMessage() {
    closeModalWithMessage();
    openModalWithProduct();
  }

  if (loading) {
    return <div></div>;
  }

  return (
    <div className={styles.main__mainContainer}>
      <Modal isOpen={isModalWithMessageOpen} onClose={handleCloseModalWithMessage}>
        <div className={styles.message}>
          <p>{message}</p>
        </div>
      </Modal>
      {product && (
        <Modal isOpen={isModalWithProductOpen} onClose={handleCloseModalWithProduct}>
          <div className={styles.productInfo}>
            <div className={styles.productInfo__imageContainer}>
              <img
                src={`${process.env.REACT_APP_IMAGE_PATH}/${product.imageUrl}`}
                className={styles.imageContainer__image}
                alt="food"
              />
            </div>
            <p className={styles.productInfo__name}>{product.name}</p>
            <p className={styles.productInfo__description}>
              {product.description}
            </p>
            <p className={styles.productInfo__price}>${product.price}</p>
          </div>
          <button
            className={styles.addToCartButton}
            onClick={handleClickAddToCartButton}
          >
            Add to cart
          </button>
        </Modal>
      )}
    </div>
  );
}

export default ProductCard;
