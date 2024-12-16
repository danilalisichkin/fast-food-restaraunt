import React, { useEffect, useState } from "react";
import styles from "./index.module.css";
import axios from "axios";
import FilterBar from "./FilterBar/FilterBar";
import PaginationBar from "../../components/PaginationBar/PaginationBar";
import Order from "./Order/Order";
import Modal from "../../components/Modal/Modal";

const MESSAGES = {
  NO_ORDERS: "You have no orders yet. It's time to see catalog and order something ;).",
  NEED_AUTHORIZE: "You need to log in to access your orders. Please sign in to see your orders!"
}

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
  }
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

  const [message, setMessage] = useState("");
  const [isModalOpen, setModalOpen] = useState(false);
  const openModal = () => setModalOpen(true);
  const [accessToken, setAccessToken] = useState(null);
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    const storedAccessToken = localStorage.getItem("accessToken");
    const storedUserId = localStorage.getItem("user_id");

    setAccessToken(storedAccessToken);
    setUserId(storedUserId);

    if (!storedAccessToken || !storedUserId) {
      setMessage(MESSAGES.NEED_AUTHORIZE);
      openModal();
    }
  }, []);

  useEffect(() => {
    if (accessToken && userId) {
      getOrdersPage();
    }
  }, [orderPageRequest, accessToken, userId]);

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

  const getOrdersPage = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/v1/orders", {
        params: orderPageRequest,
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      setOrders(response.data.content);
      setOrdersFound(true);
      setResponsePage({
        page: response.data.page,
        totalPages: response.data.totalPages,
        totalElements: response.data.totalElements
      });
      if (response.data.content.length === 0) {
        setMessage(MESSAGES.NO_ORDERS)
        openModal();
      }
    } catch (error) {
      console.error("Failed to fetch orders", error);
      setMessage(MESSAGES.NEED_AUTHORIZE);
      openModal();
    }
  };

  return (
    <div className={styles.main__mainContainer}>
      {!ordersFound ? (
        <Modal isOpen={isModalOpen} onClose={null}>
          <div className={styles.message}>
            <p>{message}</p>
          </div>
        </Modal>
      ) : (
        <div className={styles.mainContainer__ordersSection}>
          <p className={styles.ordersSection__caption}>ORDERS</p>
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
              <Order key={item.id} order={item} />
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
    </div>
  );
}

export default Orders;
