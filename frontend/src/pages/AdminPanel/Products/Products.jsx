import axios from "axios";
import React, { useEffect, useState, useCallback } from "react";
import styles from "./index.module.css";
import FilterBar from "./FilterBar/FilterBar";
import PaginationBar from "../../../components/PaginationBar/PaginationBar";
import Form from "./Form/Form";
import Modal from "../../../components/Modal/Modal";
import addItemIcon from "./icons/add-item.svg";
import Product from "./Product/Product";

const FILTER_FIELDS = [
  {
    id: 1,
    value: "ID",
    label: "id",
    upperBound: "9",
    lowerBound: "0",
  },
  {
    id: 2,
    value: "PRICE",
    label: "price",
    upperBound: "expensive",
    lowerBound: "cheap",
  },
  {
    id: 3,
    value: "NAME",
    label: "name",
    upperBound: "z",
    lowerBound: "a",
  },
  {
    id: 4,
    value: "WEIGHT",
    label: "weight",
    upperBound: "heavy",
    lowerBound: "light",
  }
];

const DEFAULT_CATEGORY = {
  id: 0,
  value: 0,
  label: "any",
};

const CATEGORY_PAGE_REQUEST = {
  offset: 0,
  limit: 40,
  sortBy: "NAME",
  sortOrder: "ASC",
};

const EMPTY_PRODUCT = {
  id: 0,
  name: "",
  price: "",
  weight: "",
  imageUrl: "",
  description: "",
  categoryId: "",
};

function Products() {
  const [selectedSortOrder, setSelectedSortOrder] = useState("ASC");
  const [selectedFilterField, setSelectedFilterField] = useState(
    FILTER_FIELDS[0]
  );
  const [productPageRequest, setProductPageRequest] = useState({
    offset: 0,
    limit: 3,
    sortBy: selectedFilterField.value,
    sortOrder: selectedSortOrder,
  });
  const [responsePage, setResponsePage] = useState({
    page: 1,
    totalPages: 1,
    totalElements: 1,
  });

  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(DEFAULT_CATEGORY);

  const [products, setProducts] = useState([]);
  const [editableProduct, setEditableProduct] = useState(null);
  const [creatableProduct, setCreatableProduct] = useState(null);

  const [isModalOpen, setModalOpen] = useState(false);
  const openModal = () => setModalOpen(true);
  const closeModal = () => setModalOpen(false);

  const [isErrorModalOpen, setErrorModalOpen] = useState(false);
  const openErrorModal = () => setErrorModalOpen(true);
  const closeErrorModal = () => {
    setErrorModalOpen(false);
    openModal();
  };

  const [errorMessage, setErrorMessage] = useState({
    cause: "",
    message: "",
  });

  const accessToken = localStorage.getItem("accessToken");

  useEffect(() => {
    getProducts();
  }, [productPageRequest]);

  useEffect(() => {
    getCategories();
  }, []);

  const handleEditProduct = (product) => {
    setEditableProduct(product);
    openModal();
  };

  const handleCreateProduct = (product) => {
    setCreatableProduct(product);
    openModal();
  };

  const handleSubmitEdit = async (editedProduct) => {
    try {
      await axios.put(
        `http://localhost:8080/api/v1/products/${editedProduct.id}`,
        {
          name: editedProduct.name,
          price: editedProduct.price,
          weight: editedProduct.weight,
          imageUrl: editedProduct.imageUrl,
          description: editedProduct.description,
          categoryId: editedProduct.categoryId,
        },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      closeModal();
      getProducts();
    } catch (error) {
      console.error(
        "Edit Product Error:",
        error.response ? error.response : error.message
      );
      setErrorMessage(error.response.data);
      closeModal();
      openErrorModal();
    }
  };

  const handleSubmitCreate = async (newProduct) => {
    try {
      await axios.post(
        "http://localhost:8080/api/v1/products",
        {
          name: newProduct.name,
          price: newProduct.price,
          weight: newProduct.weight,
          imageUrl: newProduct.imageUrl,
          description: newProduct.description,
          categoryId: newProduct.categoryId,
        },
        {
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      closeModal();
      getProducts();
    } catch (error) {
      console.error(
        "Create Product Error:",
        error.response ? error.response : error.message
      );
      setErrorMessage(error.response.data);
      closeModal();
      openErrorModal();
    }
  };

  const handleSortChange = (newSelectedSortField) => {
    setProductPageRequest((prevState) => ({
      ...prevState,
      sortBy: newSelectedSortField.value,
    }));
    setSelectedFilterField(newSelectedSortField);
  };

  const handleOrderChange = (newSelectedSortOrder) => {
    setProductPageRequest((prevState) => ({
      ...prevState,
      sortOrder: newSelectedSortOrder,
    }));
    setSelectedSortOrder(newSelectedSortOrder);
  };

  const handleSelectedCategoryChange = (newSelectedCategory) => {
    setProductPageRequest((prevState) => {
      const newRequest = { ...prevState };
      if (newSelectedCategory.id === 0) {
        delete newRequest.categoryId;
      } else {
        newRequest.categoryId = newSelectedCategory.value;
      }
      return newRequest;
    });
    setSelectedCategory(newSelectedCategory);
  };

  const handlePageSelect = (newPage) => {
    setProductPageRequest((prevState) => ({
      ...prevState,
      offset: newPage - 1,
    }));
  };

  const getProducts = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/v1/products",
        {
          params: productPageRequest,
        }
      );
      setProducts(response.data.content);
      setResponsePage({
        page: response.data.page,
        totalPages: response.data.totalPages,
        totalElements: response.data.totalElements,
      });
    } catch (error) {
      console.error(
        "Request error",
        error.response ? error.response.data : error.message
      );
    }
  };

  const getCategories = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/v1/categories",
        {
          params: CATEGORY_PAGE_REQUEST,
        }
      );
      const transformedList = response.data.content.map((item, index) => ({
        id: index + 1,
        value: item.id,
        label: item.name,
      }));
      setCategories([DEFAULT_CATEGORY, ...transformedList]);
      setSelectedCategory(DEFAULT_CATEGORY);
    } catch (error) {
      console.error(
        "Request error",
        error.response ? error.response.data : error.message
      );
    }
  };

  return (
    <div className={styles.productsSection}>
      <Modal isOpen={isModalOpen} onClose={closeModal}>
        {editableProduct && (
          <>
            <p className={styles.modal__caption}>Edit category</p>
            <Form data={editableProduct} onSubmit={handleSubmitEdit} />
          </>
        )}
        {creatableProduct && (
          <>
            <p className={styles.modal__caption}>Add category</p>
            <Form data={creatableProduct} onSubmit={handleSubmitCreate} />
          </>
        )}
      </Modal>
      <Modal isOpen={isErrorModalOpen} onClose={closeErrorModal}>
        <p className={styles.modal__caption}>{errorMessage.cause}</p>
        <p className={styles.error__message}>{errorMessage.message}</p>
        {errorMessage.messages &&
          Object.entries(errorMessage.messages).map(([key, value]) =>
            value.map((message, index) => (
              <p key={key} className={styles.error__message}>
                {key}: {message}
              </p>
            ))
          )}
      </Modal>
      <FilterBar
        filterFields={FILTER_FIELDS}
        filterField={selectedFilterField}
        filterOrder={selectedSortOrder}
        categories={categories}
        selectedCategory={selectedCategory}
        onSortChange={handleSortChange}
        onOrderChange={handleOrderChange}
        onCategoryChange={handleSelectedCategoryChange}
      />
      <div className={styles.products}>
        {products.length > 0 ? (
          products.map((product) => (
            <Product
              key={product.id}
              product={product}
              handleEdit={() => handleEditProduct(product)}
            />
          ))
        ) : (
          <div>No categories available</div>
        )}
        <div
          className={styles.createContainer}
          onClick={() => handleCreateProduct(EMPTY_PRODUCT)}
        >
          <img src={addItemIcon} alt="icon" />
        </div>
      </div>
      <PaginationBar
        page={responsePage.page + 1}
        pageSize={productPageRequest.limit}
        totalPages={responsePage.totalPages}
        totalElements={responsePage.totalElements}
        onButtonClick={(newPage) => handlePageSelect(newPage)}
      />
    </div>
  );
}

export default Products;
