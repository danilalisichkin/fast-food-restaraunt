import React, { useEffect, useState } from "react";
import styles from "./index.module.css";
import axios from "axios";
import CartItem from "./CartItem/CartItem";
import RightSideBar from "./RightSideBar/RightSideBar";
import Modal from "../../components/Modal/Modal";

const MESSAGES = {
  EMPTY_CART: "You have no items in cart. Go to catalog and order something.",
  ORDER_SUCCESSFULLY_PLACED:
    "Your order is successfully placed. You can find it in 'Orders' section.",
  NEED_AUTHORIZE:
    "You need to log in to access your cart. Please sign in to see your items!",
};

function Cart() {
  const [cart, setCart] = useState({ items: [] });
  const [totalQuantity, setTotalQuantity] = useState(0);
  const [totalWeight, setTotalWeight] = useState(0);
  const [totalPrice, setTotalPrice] = useState(0);
  const [orderRequest, setOrderRequest] = useState({
    deliveryAddress: "",
  });
  const [cartFound, setCartFound] = useState(false);

  const [modalWithMessageOpen, setModalWithMessageOpen] = useState(false);

  const [message, setMessage] = useState("");

  const openModalWithMessage = () => setModalWithMessageOpen(true);

  const accessToken = localStorage.getItem("accessToken");
  const userId = localStorage.getItem("user_id");

  useEffect(() => {
    fetchCart();
  }, []);

  useEffect(() => {
    if (cart.items.length > 0) {
      updateCartTotals(cart.items);
    }
  }, [cart]);

  const fetchCart = async () => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/carts/${userId}`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      setCart(response.data);
      setCartFound(true);

      if (response.data.items.length === 0) {
        setMessage(MESSAGES.EMPTY_CART);
        openModalWithMessage();
      }
    } catch (error) {
      setCartFound(false);
      if (error.status === 404) {
        setMessage(MESSAGES.EMPTY_CART);
      } else {
        setMessage(MESSAGES.NEED_AUTHORIZE);
      }
      openModalWithMessage();
    }
  };

  const postOrder = async () => {
    try {
      await axios.post(
        "http://localhost:8080/api/v1/orders",
        {
          cartId: userId,
          deliveryAddress: orderRequest.deliveryAddress,
        },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      setMessage(MESSAGES.ORDER_SUCCESSFULLY_PLACED);
      openModalWithMessage();
    } catch (error) {
      console.error("Failed to place order", error);
    }
  };

  const removeItemFromCart = async (productId) => {
    try {
      await axios.delete(
        `http://localhost:8080/api/v1/carts/${userId}/items/${productId}`,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      await fetchCart();
    } catch (error) {
      console.error("Failed to remove item", error);
    }
  };

  const updateItemInCart = async (productId, quantity) => {
    try {
      await axios.put(
        `http://localhost:8080/api/v1/carts/${userId}/items/${productId}`,
        quantity,
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
            "Content-Type": "application/json",
          },
        }
      );
      await fetchCart();
    } catch (error) {
      console.error("Failed to update item", error);
    }
  };

  const calculateTotalQuantity = (items) =>
    items.reduce((total, item) => total + item.quantity, 0);
  const calculateTotalPrice = (items) =>
    items.reduce(
      (total, item) => total + item.quantity * item.product.price,
      0
    );
  const calculateTotalWeight = (items) =>
    items.reduce(
      (total, item) => total + item.quantity * item.product.weight,
      0
    );

  const updateCartTotals = (items) => {
    setTotalQuantity(calculateTotalQuantity(items));
    setTotalPrice(calculateTotalPrice(items));
    setTotalWeight(calculateTotalWeight(items));
  };

  const updateCart = (updatedItems) => {
    setCart({ ...cart, items: updatedItems });
    updateCartTotals(updatedItems);
  };

  const handleQuantityChange = (productId, newQuantity) => {
    const updatedItems = cart.items.map((item) =>
      item.product.id === productId
        ? { ...item, quantity: parseInt(newQuantity) }
        : item
    );
    updateItemInCart(productId, parseInt(newQuantity));
    updateCart(updatedItems);
  };

  const handleItemRemoving = (productId) => {
    removeItemFromCart(productId);
    const updatedItems = cart.items.filter(
      (item) => item.product.id !== productId
    );
    updateCart(updatedItems);

    if (totalQuantity === 0) {
      setMessage(MESSAGES.EMPTY_CART);
      openModalWithMessage();
    }
  };

  const handleDeliveryAddressChange = (newAddress) => {
    setOrderRequest((prev) => ({ ...prev, deliveryAddress: newAddress }));
  };

  const handleOrderPlacing = () => {
    postOrder();
  };

  return (
    <div className={styles.main__mainContainer}>
      {!cartFound || cart.items.length === 0 ? (
        <Modal isOpen={modalWithMessageOpen} onClose={null}>
          <div className={styles.message}>
            <p>{message}</p>
          </div>
        </Modal>
      ) : (
        <>
          <ul className={styles.items}>
            {cart.items.map((item) => (
              <CartItem
                key={item.product.id}
                item={item}
                onQuantityChange={(newQuantity) =>
                  handleQuantityChange(item.product.id, newQuantity)
                }
                onRemove={() => handleItemRemoving(item.product.id)}
              />
            ))}
          </ul>
          <RightSideBar
            totalQuantity={totalQuantity}
            totalPrice={totalPrice}
            totalWeight={totalWeight}
            deliveryAddress={orderRequest.deliveryAddress}
            onDeliveryAddressChange={handleDeliveryAddressChange}
            onOrderPlace={handleOrderPlacing}
          />
        </>
      )}
    </div>
  );
}

export default Cart;
