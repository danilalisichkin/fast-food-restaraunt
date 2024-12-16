import React from "react";
import styles from "./index.module.css";
import InputText from "../../../components/Inputs/InputText/InputText";

function RightSideBar({
  totalQuantity,
  totalPrice,
  totalWeight,
  deliveryAddress,
  onDeliveryAddressChange,
  onOrderPlace,
}) {
  return (
    <div className={styles.rightSideBarContainer}>
      <div className={styles.rightSideBar}>
        <h3 className={styles.rightSideBar__caption}>Shopping cart</h3>
        <p className={styles.rightSideBar__totalQuantity}>
          {totalQuantity} items
        </p>
        <div>
          <p className={styles.total__caption}>Total:</p>
          <div className={styles.total__container}>
            <p className={styles.total__price}>${totalPrice.toFixed(2)}</p>
            <p className={styles.total__weight}>{totalWeight}g</p>
          </div>
        </div>
        <form>
        <InputText
          label="Delivery address"
          name="delivery_address"
          value={deliveryAddress}
          required="true"
          maxLength="40"
          onChange={(value) => onDeliveryAddressChange(value)}
        />
        <button
          type="submit"
          className={styles.rightSideBar__placeOrderButton}
          onClick={() => onOrderPlace()}
        >
          Place an order
        </button>
        </form>
      </div>
    </div>
  );
}

export default RightSideBar;
