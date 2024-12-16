import React, { useEffect, useState } from "react";
import styles from "./index.module.css";
import FilterBar from "./FilterBar/FilterBar";
import PaginationBar from "../../../components/PaginationBar/PaginationBar";
import User from "./User/User";
import axios from "axios";

const FILTER_FIELDS = [
  {
    id: 1,
    value: "PHONE",
    label: "phone",
    upperBound: "0",
    lowerBound: "9",
  },
  {
    id: 2,
    value: "FIRST_NAME",
    label: "first name",
    upperBound: "z",
    lowerBound: "a",
  },
  {
    id: 3,
    value: "LAST_NAME",
    label: "last name",
    upperBound: "z",
    lowerBound: "a",
  },
  {
    id: 4,
    value: "ACTIVE",
    label: "user active",
    upperBound: "active",
    lowerBound: "blocked",
  },
];

const USER_ACTIVES = [
  {
    id: 0,
    value: "ANY",
    label: "any",
  },
  {
    id: 1,
    value: "true",
    label: "active",
  },
  {
    id: 2,
    value: "false",
    label: "blocked",
  },
];

const DEFAULT_ACTIVE = USER_ACTIVES[0];

function Users() {
  const [selectedSortOrder, setSelectedSortOrder] = useState("ASC");
  const [selectedFilterField, setSelectedFilterField] = useState(
    FILTER_FIELDS[0]
  );
  const [selectedActive, setSelectedActive] = useState(DEFAULT_ACTIVE);
  const [userPageRequest, setUserPageRequest] = useState({
    offset: 0,
    limit: 4,
    sortBy: FILTER_FIELDS[0].value,
    sortOrder: selectedSortOrder,
  });
  const [responsePage, setResponsePage] = useState({
    page: 1,
    totalPages: 1,
    totalElements: 1,
  });

  const [users, setUsers] = useState([]);

  const accessToken = localStorage.getItem("accessToken");

  useEffect(() => {
    getUsersPage();
  }, [userPageRequest]);

  const handleSortChange = (newSelectedSortField) => {
    setUserPageRequest((prevState) => ({
      ...prevState,
      sortBy: newSelectedSortField.value,
    }));
    setSelectedFilterField(newSelectedSortField);
  };

  const handleOrderChange = (newSelectedSortOrder) => {
    setUserPageRequest((prevState) => ({
      ...prevState,
      sortOrder: newSelectedSortOrder,
    }));
    setSelectedSortOrder(newSelectedSortOrder);
    console.info("Sort order:", newSelectedSortOrder);
  };

  const handleActiveChange = (newSelectedActive) => {
    setUserPageRequest((prevState) => {
      const newRequest = { ...prevState };
      if (newSelectedActive.id === 0) {
        delete newRequest.active;
      } else {
        newRequest.active = newSelectedActive.value;
      }

      return newRequest;
    });
    setSelectedActive(newSelectedActive);
  };

  const handlePageSelect = (newPage) => {
    setUserPageRequest((prevState) => ({
      ...prevState,
      offset: newPage - 1,
    }));
  };

  const getUsersPage = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/v1/users", {
        params: userPageRequest,
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      console.info("Fetched users data", response.data);
      setUsers(response.data.content);
      setResponsePage({
        page: response.data.page,
        totalPages: response.data.totalPages,
        totalElements: response.data.totalElements,
      });
    } catch (error) {
      console.error("Failed to fetch orders", error);
    }
  };

  function handleUserStatusChange(phone) {
    if (selectedActive !== DEFAULT_ACTIVE) {
      setUsers((prevUsers) => {
        const updatedUsers = prevUsers.filter((user) => user.phone !== phone);

        setResponsePage((prevResponsePage) => ({
          ...prevResponsePage,
          totalElements: prevResponsePage.totalElements - 1,
          totalPages: Math.ceil(
            (prevResponsePage.totalElements - 1) / userPageRequest.limit
          ),
        }));

        return updatedUsers;
      });
    } else {
      setUsers((prevUsers) =>
        prevUsers.map((user) =>
          user.phone === phone ? { ...user, active: !user.active } : user
        )
      );
    }
  }

  const activateUser = (phone) => {
    try {
      axios.put(
        `http://localhost:8080/api/v1/users/${phone}/activate`,
        {},
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
    } catch (error) {
      console.error("Failed to activate user", error);
    }
  };
  
  const deactivateUser = (phone) => {
    try {
      axios.put(
        `http://localhost:8080/api/v1/users/${phone}/deactivate`,
        {},
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
    } catch (error) {
      console.error("Failed to deactivate user", error);
    }
  };
  
  return (
    <div className={styles.usersSection}>
      <FilterBar
        filterFields={FILTER_FIELDS}
        filterField={selectedFilterField}
        filterOrder={selectedSortOrder}
        actives={USER_ACTIVES}
        selectedActive={selectedActive}
        onSortChange={handleSortChange}
        onOrderChange={handleOrderChange}
        onActiveChange={handleActiveChange}
      />
      <div className={styles.users}>
        {users.map((item) => (
          <User
            key={item.id}
            user={item}
            onStatusChange={handleUserStatusChange}
            onActivate={activateUser}
            onDeactivate={deactivateUser}
          />
        ))}
      </div>
      <PaginationBar
        page={responsePage.page + 1}
        pageSize={userPageRequest.limit}
        totalPages={responsePage.totalPages}
        totalElements={responsePage.totalElements}
        onButtonClick={handlePageSelect}
      />
    </div>
  );
}

export default Users;
