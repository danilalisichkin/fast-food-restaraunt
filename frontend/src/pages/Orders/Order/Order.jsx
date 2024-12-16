import React from "react";
import styles from "./index.module.css";
import OrderItem from "./OrderItem/OrderItem";
import PhoneIcon from "./icons/phone.svg";

function Order({ order }) {
  const {
    id,
    userPhone,
    deliveryAddress,
    status,
    createdAt,
    completedAt,
    items,
  } = order;

  return (
    <div className={styles.order}>
      <p className={styles.order__id}>Order №{id}</p>
      <div className={styles.contactContainer}>
        <img
          src={PhoneIcon}
          className={styles.contactContainer__icon}
          alt="icon"
        />
        <a href={`callto:${userPhone}`}>{userPhone}</a>
      </div>
      <p className={styles.order__creationTime}>Created at {createdAt}</p>
      <div className={styles.statusContainer}>
        <p className={getStyleForOrderStatus(status)}>{order.status}&nbsp;</p>
        {status === "DELIVERED" && completedAt && (
          <p className={styles.order__completeTime}>
            at {formatDate(completedAt)}
          </p>
        )}
      </div>
      <ul className={styles.order__items}>
        {items.map((item) => (
          <OrderItem key={item.product.id} item={item} />
        ))}
      </ul>
      <div className={styles.totalContainer}>
        <p className={styles.totalContainer__caption}>Total:</p>
        <p className={styles.totalContainer__weight}>
          {calculateTotalWeight(items)}g
        </p>
        <p className={styles.totalContainer__price}>
          ${calculateTotalPrice(items)}
        </p>
        <p className={styles.totalContainer__quantity}>
          {calculateTotalQuantity(items)} pc.
        </p>
      </div>
      <p className={styles.order__deliveryAddress}>
        Delivery address: {deliveryAddress}
      </p>
    </div>
  );
}

function calculateTotalQuantity(items) {
  let totalQuantity = 0;

  if (Array.isArray(items)) {
    items.forEach((item) => {
      if (item.quantity) {
        totalQuantity += item.quantity;
      }
    });
  }

  return totalQuantity;
}

function calculateTotalPrice(items) {
  let totalPrice = 0;

  if (Array.isArray(items)) {
    items.forEach((item) => {
      if (item.relevantPrice && item.quantity) {
        totalPrice += item.relevantPrice * item.quantity;
      }
    });
  }

  return totalPrice.toFixed(2);
}

function calculateTotalWeight(items) {
  let totalWeight = 0;

  if (Array.isArray(items)) {
    items.forEach((item) => {
      if (item.product && item.product.weight && item.quantity) {
        totalWeight += item.product.weight * item.quantity;
      }
    });
  }

  return totalWeight;
}

function formatDate(date) {
  const options = {
    year: "numeric",
    month: "short",
    day: "numeric",
    hour: "2-digit",
    minute: "2-digit",
  };
  return new Date(date).toLocaleDateString("en-US", options);
}

function getStyleForOrderStatus(status) {
  const styleMap = {
    NEW: styles["order__status--new"],
    PROCESSING: styles["order__status--processing"],
    SHIPPED: styles["order__status--shipped"],
    DELIVERED: styles["order__status--delivered"],
    CANCELLED: styles["order__status--cancelled"],
  };
  return styleMap[status];
}

export default Order;
