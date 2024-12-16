import React from "react";
import styles from "./index.module.css";
import { Link, useLocation } from "react-router-dom";

const navLinks = [
  { path: "/admin-panel/product-panel", label: "Product Panel" },
  { path: "/admin-panel/category-panel", label: "Category Panel" },
  { path: "/admin-panel/order-panel", label: "Order Panel" },
  { path: "/admin-panel/user-panel", label: "User Panel" }
];

function NaviBar() {
  const location = useLocation();

  return (
    <div className={styles.naviBar}>
      {navLinks.map((link, index) => {
        const isActive = location.pathname === link.path;
        return (
          <Link
            key={index}
            className={`${styles.naviBar__button} ${
              location.pathname === link.path ? styles.active : ""
            }`}
            to={link.path}
            onClick={(e) => {
              if (isActive) {
                e.preventDefault();
              }
            }}
          >
            {link.label}
          </Link>
        );
      })}
    </div>
  );
}

export default NaviBar;
