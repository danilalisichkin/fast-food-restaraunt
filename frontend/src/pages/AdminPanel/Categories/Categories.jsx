import axios from "axios";
import React, { useEffect, useState, useCallback } from "react";
import styles from "./index.module.css";
import FilterBar from "./FilterBar/FilterBar";
import Category from "./Category/Category";
import PaginationBar from "../../../components/PaginationBar/PaginationBar";
import Form from "./Form/Form";
import Modal from "../../../components/Modal/Modal";
import addItemIcon from "./icons/add-item.svg";

const FILTER_FIELDS = [
  { id: 1, value: "ID", label: "id", upperBound: "0", lowerBound: "9" },
  { id: 2, value: "NAME", label: "name", upperBound: "z", lowerBound: "a" },
];

const EMPTY_CATEGORY = {
  id: 0,
  name: "",
};

function Categories() {
  const [selectedSortOrder, setSelectedSortOrder] = useState("ASC");
  const [selectedFilterField, setSelectedFilterField] = useState(
    FILTER_FIELDS[0]
  );
  const [categoryPageRequest, setCategoryPageRequest] = useState({
    offset: 0,
    limit: 5,
    sortBy: selectedFilterField.value,
    sortOrder: "ASC",
  });
  const [responsePage, setResponsePage] = useState({
    page: 1,
    totalPages: 1,
    totalElements: 1,
  });

  const [categories, setCategories] = useState([]);
  const [editableCategory, setEditableCategory] = useState(null);
  const [creatableCategory, setCreatableCategory] = useState(null);

  const [isModalOpen, setModalOpen] = useState(false);
  const openModal = () => setModalOpen(true);
  const closeModal = () => setModalOpen(false);

  const [isErrorModalOpen, setErrorModalOpen] = useState(false);
  const openErrorModal = () => setErrorModalOpen(true);
  const closeErrorModal = () => setErrorModalOpen(false);

  const [errorMessage, setErrorMessage] = useState({
    cause: "",
    message: "",
  });

  const fetchCategories = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/v1/categories",
        {
          params: categoryPageRequest,
        }
      );
      setCategories(response.data.content);
      setResponsePage({
        page: response.data.page,
        totalPages: response.data.totalPages,
        totalElements: response.data.totalElements,
      });
    } catch (error) {
      console.error("Failed to fetch categories", error);
    }
  };

  const accessToken = localStorage.getItem("accessToken");

  useEffect(() => {
    fetchCategories();
  }, [categoryPageRequest]);

  const handleDeleteCategory = async (id) => {
    try {
      await axios.delete(`http://localhost:8080/api/v1/categories/${id}`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      fetchCategories();
    } catch (error) {
      console.error("Failed to delete category", error);
    }
  };

  const handleEditCategory = (category) => {
    setEditableCategory(category);
    openModal();
  };

  const handleCreateCategory = (category) => {
    setCreatableCategory(category);
    openModal();
  };

  const handleSubmitEdit = async (updatedCategory) => {
    try {
      await axios.put(
        `http://localhost:8080/api/v1/categories/${updatedCategory.id}`,
        updatedCategory.name,
        {
          headers: {
            "Content-Type": "text/plain",
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      closeModal();
      setEditableCategory(null);
      fetchCategories();
    } catch (error) {
      console.error(
        "Edit Category Error:",
        error.response ? error.response : error.message
      );
      setErrorMessage(error.response.data);
      closeModal();
      openErrorModal();
    }
  };

  const handleSubmitCreate = async (newCategory) => {
    try {
      await axios.post(
        "http://localhost:8080/api/v1/categories",
        newCategory.name,
        {
          headers: {
            "Content-Type": "text/plain",
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      closeModal();
      fetchCategories();
    } catch (error) {
      console.error(
        "Create Category Error:",
        error.response ? error.response : error.message
      );
      setErrorMessage(error.response.data);
      closeModal();
      openErrorModal();
    }
  };

  const handleSortChange = (newSelectedSortField) => {
    setCategoryPageRequest((prevState) => ({
      ...prevState,
      sortBy: newSelectedSortField.value,
    }));
    setSelectedFilterField(newSelectedSortField);
  };

  const handleOrderChange = (newSelectedSortOrder) => {
    setCategoryPageRequest((prevState) => ({
      ...prevState,
      sortOrder: newSelectedSortOrder,
    }));
    setSelectedSortOrder(newSelectedSortOrder);
  };

  const handlePageSelect = (newPage) => {
    setCategoryPageRequest((prevState) => ({
      ...prevState,
      offset: newPage - 1,
    }));
  };

  return (
    <div className={styles.categoriesSection}>
      <Modal isOpen={isModalOpen} onClose={closeModal}>
        {editableCategory && (
          <>
            <p className={styles.modal__caption}>Edit category</p>
            <Form data={editableCategory} onSubmit={handleSubmitEdit} />
          </>
        )}
        {creatableCategory && (
          <>
            <p className={styles.modal__caption}>Add category</p>
            <Form data={creatableCategory} onSubmit={handleSubmitCreate} />
          </>
        )}
      </Modal>
      <Modal isOpen={isErrorModalOpen} onClose={closeErrorModal}>
        <p className={styles.modal__caption}>{errorMessage.cause}</p>
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
      <FilterBar
        filterFields={FILTER_FIELDS}
        filterField={selectedFilterField}
        filterOrder={selectedSortOrder}
        onSortChange={handleSortChange}
        onOrderChange={handleOrderChange}
      />
      <div className={styles.categories}>
        {categories.length > 0 ? (
          categories.map((category) => (
            <Category
              key={category.id}
              category={category}
              handleEdit={() => handleEditCategory(category)}
              handleDelete={() => handleDeleteCategory(category.id)}
            />
          ))
        ) : (
          <div>No categories available</div>
        )}
        <div
          className={styles.createContainer}
          onClick={() => handleCreateCategory(EMPTY_CATEGORY)}
        >
          <img src={addItemIcon} alt="icon" />
        </div>
      </div>
      <PaginationBar
        page={responsePage.page + 1}
        pageSize={categoryPageRequest.limit}
        totalPages={responsePage.totalPages}
        totalElements={responsePage.totalElements}
        onButtonClick={(newPage) => handlePageSelect(newPage)}
      />
    </div>
  );
}

export default Categories;
