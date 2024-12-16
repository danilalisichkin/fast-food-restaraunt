import React, { useEffect, useState } from "react";
import styles from "./index.module.css";
import axios from "axios";
import PaginationBar from "../../../components/PaginationBar/PaginationBar";
import Modal from "../../../components/Modal/Modal";
import Order from "./Order/Order";
import FilterBar from "./FilterBar/FilterBar";

const NO_ORDERS_MESSAGE =
  "You have no orders yet. It's time to see catalog and order something ;).";

const FILTER_FIELDS = [
  {
    id: 1,
    value: "ID",
    label: "identifier",
    upperBound: "0",
    lowerBound: "9",
  },
  {
    id: 2,
    value: "DELIVERY_ADDRESS",
    label: "delivery address",
    upperBound: "z",
    lowerBound: "a",
  },
  {
    id: 3,
    value: "CREATED_AT",
    label: "creation time",
    upperBound: "earlier",
    lowerBound: "before",
  },
  {
    id: 4,
    value: "COMPLETED_AT",
    label: "complete time",
    upperBound: "earlier",
    lowerBound: "before",
  },
  {
    id: 5,
    value: "STATUS",
    label: "status",
    upperBound: "z",
    lowerBound: "a",
  },
];

const ORDER_STATUSES = [
  {
    id: 0,
    value: "ANY",
    label: "any",
  },
  {
    id: 1,
    value: "NEW",
    label: "new",
  },
  {
    id: 2,
    value: "PROCESSING",
    label: "processing",
  },
  {
    id: 3,
    value: "SHIPPED",
    label: "shipped",
  },
  {
    id: 4,
    value: "DELIVERED",
    label: "delivered",
  },
  {
    id: 5,
    value: "CANCELLED",
    label: "canceled",
  },
];

const DEFAULT_STATUS = ORDER_STATUSES[0];

function Orders() {
  const [selectedSortOrder, setSelectedSortOrder] = useState("ASC");
  const [selectedFilterField, setSelectedFilterField] = useState(
    FILTER_FIELDS[0]
  );
  const [selectedOrderStatus, setSelectedOrderStatus] =
    useState(DEFAULT_STATUS);
  const [orderPageRequest, setOrderPageRequest] = useState({
    offset: 0,
    limit: 2,
    sortBy: FILTER_FIELDS[0].value,
    sortOrder: selectedSortOrder,
  });
  const [responsePage, setResponsePage] = useState({
    page: 1,
    totalPages: 1,
    totalElements: 1,
  });

  const [orders, setOrders] = useState([]);
  const [ordersFound, setOrdersFound] = useState(false);

  const [errorMessage, setErrorMessage] = useState({
    cause: "",
    message: "",
  });

  const [isModalWithErrorOpen, setModalWithErrorOpen] = useState(false);
  const openModalWithError = () => setModalWithErrorOpen(true);
  const closeModalWithError = () => setModalWithErrorOpen(false);

  const accessToken = localStorage.getItem("accessToken");
  
  useEffect(() => {
    getOrdersPage();
  }, [orderPageRequest]);

  const handleSortChange = (newSelectedSortField) => {
    setOrderPageRequest((prevState) => ({
      ...prevState,
      sortBy: newSelectedSortField.value,
    }));
    setSelectedFilterField(newSelectedSortField);
  };

  const handleOrderChange = (newSelectedSortOrder) => {
    setOrderPageRequest((prevState) => ({
      ...prevState,
      sortOrder: newSelectedSortOrder,
    }));
    setSelectedSortOrder(newSelectedSortOrder);
    console.info("Sort order:", newSelectedSortOrder);
  };

  const handleStatusChange = (newSelectedStatus) => {
    setOrderPageRequest((prevState) => {
      const newRequest = { ...prevState };
      if (newSelectedStatus.id === 0) {
        delete newRequest.status;
      } else {
        newRequest.status = newSelectedStatus.value;
      }
      
      return newRequest;
    });
    setSelectedOrderStatus(newSelectedStatus);
  };

  const handlePageSelect = (newPage) => {
    setOrderPageRequest((prevState) => ({
      ...prevState,
      offset: newPage - 1,
    }));
  };

  const handleOrderStatusChange = (id, newStatus) => {
    setOrderStatus(id, newStatus);
  };

  const getOrdersPage = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/v1/orders", {
        params: orderPageRequest,
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      console.info("Fetched orders data", response.data);
      setOrders(response.data.content);
      setOrdersFound(true);
      setResponsePage({
        page: response.data.page,
        totalPages: response.data.totalPages,
        totalElements: response.data.totalElements,
      });
    } catch (error) {
      console.error(
        "Get Orders Error:",
        error.response ? error.response : error.message
      );
    }
  };

  const setOrderStatus = async (id, newStatus) => {
    try {
      const response = await axios.put(
        `http://localhost:8080/api/v1/orders/${id}/status`,
        newStatus,
        { headers: { "Content-Type": "application/json", Authorization: `Bearer ${accessToken}` } }
      );
      if (selectedOrderStatus === DEFAULT_STATUS) {
        setOrders((prevOrders) =>
          prevOrders.map((order) =>
            order.id === id ? { ...order, status: newStatus } : order
          )
        );
      } else {
        getOrdersPage();
      }
    } catch (error) {
      console.error(
        "Set Order Status Error:",
        error.response ? error.response : error.message
      );
      setErrorMessage(error.response.data);
      openModalWithError();
    }
  };

  return (
    <>
      {!ordersFound || isModalWithErrorOpen ? (
        <Modal isOpen={isModalWithErrorOpen} onClose={closeModalWithError}>
          <p className={styles.error__caption}>{errorMessage.cause}</p>
          <p className={styles.error__message}>{errorMessage.message}</p>
          {errorMessage.messages &&
            Object.entries(errorMessage.messages).map(([key, value]) =>
              value.map((message, index) => (
                <p key={index} className={styles.error__message}>
                  {key}: {message}
                </p>
              ))
            )}
        </Modal>
      ) : (
        <div className={styles.mainContainer__ordersSection}>
          <FilterBar
            filterFields={FILTER_FIELDS}
            filterField={selectedFilterField}
            filterOrder={selectedSortOrder}
            statuses={ORDER_STATUSES}
            selectedStatus={selectedOrderStatus}
            onSortChange={handleSortChange}
            onOrderChange={handleOrderChange}
            onStatusChange={handleStatusChange}
          />
          <div className={styles.orders}>
            {orders.map((item) => (
              <Order
                key={item.id}
                order={item}
                onStatusChange={handleOrderStatusChange}
                statuses={ORDER_STATUSES}
              />
            ))}
          </div>
          <PaginationBar
            page={responsePage.page + 1}
            pageSize={orderPageRequest.limit}
            totalPages={responsePage.totalPages}
            totalElements={responsePage.totalElements}
            onButtonClick={handlePageSelect}
          />
        </div>
      )}
    </>
  );
}

export default Orders;
