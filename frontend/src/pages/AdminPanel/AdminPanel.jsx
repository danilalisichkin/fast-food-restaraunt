import React from "react";
import styles from "./index.module.css";
import { Route, Routes } from "react-router-dom";
import NaviBar from "./NaviBar/NaviBar";
import Users from "./Users/Users";
import Orders from "./Orders/Orders";
import Categories from "./Categories/Categories";
import Products from "./Products/Products";

function AdminPanel() {
  return (
    <div className={styles.main__mainContainer}>
      <NaviBar />
      <div className={styles.content}>
        <Routes>
          <Route path="user-panel" element={<Users />} />
          <Route path="order-panel" element={<Orders />} />
          <Route path="category-panel" element={<Categories />} />
          <Route path="product-panel" element={<Products />} />
        </Routes>
      </div>
    </div>
  );
}

export default AdminPanel;
