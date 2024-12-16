import React from "react";
import styles from "./index.module.css";

function PaginationBar(props) {
  const { page, pageSize, totalPages, totalElements, onButtonClick } = props;

  return (
    <div className={styles.selectionBarContainer}>
      <div className={styles.pageSelectionBar}>
        {page > 1 && (
          <button
            className={styles.pageSelectionBar__button}
            onClick={() => onButtonClick(page - 1)}
          >
            &lt;
          </button>
        )}
        {ShowPageButtons({ ...props, onButtonClick })}
        {page < totalPages && (
          <button
            className={styles.pageSelectionBar__button}
            onClick={() => onButtonClick(page + 1)}
          >
            &gt;
          </button>
        )}
      </div>
      <p className={styles.totalCount}>
        {Math.min(pageSize * page, totalElements)} from {totalElements}
      </p>
    </div>
  );
}

function ShowPageButtons(props) {
  const { page, totalPages, onButtonClick } = props;
  const maxButtons = 5;

  let start = Math.max(1, page - Math.floor(maxButtons / 2));
  let end = Math.min(totalPages, start + maxButtons - 1);

  const buttons = [];
  for (let i = start; i <= end; i++) {
    buttons.push(
      <button
        key={i}
        className={
          i === page
            ? styles["pageSelectionBar__button--active"]
            : styles.pageSelectionBar__button
        }
        onClick={() => (i === page ? null : onButtonClick(i))}
      >
        {i}
      </button>
    );
  }

  return buttons;
}

export default PaginationBar;
