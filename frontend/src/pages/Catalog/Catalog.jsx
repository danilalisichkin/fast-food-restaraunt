import React, { useEffect, useState } from "react";
import styles from "./index.module.css";
import axios from "axios";
import CatalogProduct from "./CatalogProduct/ProductInCatalog";
import PaginationBar from "../../components/PaginationBar/PaginationBar";
import FilterBar from "./FilterBar/FilterBar";

const FILTER_FIELDS = [
  {
    id: 1,
    value: "PRICE",
    label: "price",
    upperBound: "expensive",
    lowerBound: "cheap",
  },
  {
    id: 2,
    value: "NAME",
    label: "name",
    upperBound: "z",
    lowerBound: "a",
  },
  {
    id: 3,
    value: "WEIGHT",
    label: "weight",
    upperBound: "heavy",
    lowerBound: "light",
  },
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

function Catalog() {
  const [selectedSortOrder, setSelectedSortOrder] = useState("ASC");
  const [selectedFilterField, setSelectedFilterField] = useState(
    FILTER_FIELDS[0]
  );
  const [productPageRequest, setProductPageRequest] = useState({
    offset: 0,
    limit: 6,
    sortBy: FILTER_FIELDS[0].value,
    sortOrder: selectedSortOrder,
  });
  const [responsePage, setResponsePage] = useState({
    page: 1,
    totalPages: 1,
    totalElements: 1,
  });

  const [products, setProducts] = useState([]);
  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState(DEFAULT_CATEGORY);

  useEffect(() => {
    getProductPage();
  }, [productPageRequest]);

  useEffect(() => {
    getCategoryPage();
  }, []);

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
    console.log("YES");
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

  const getProductPage = async () => {
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

  const getCategoryPage = async () => {
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
    <div className={styles.main__mainContainer}>
      <div className={styles.mainContainer__catalog}>
        <p className={styles.catalog__caption}>MENU</p>
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
        <div className={styles.catalog__items}>
          {products.map((item) => (
            <CatalogProduct key={item.id} product={item} />
          ))}
        </div>
        <PaginationBar
          page={responsePage.page + 1}
          pageSize={productPageRequest.limit}
          totalPages={responsePage.totalPages}
          totalElements={responsePage.totalElements}
          onButtonClick={handlePageSelect}
        />
      </div>
    </div>
  );
}

export default Catalog;
