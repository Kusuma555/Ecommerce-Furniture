import axios from "axios";
import { BASE_API_URL, getAuthHeader } from "./con-utils";

export const addProduct = ({ authToken, product }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .post(BASE_API_URL + "product", product, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const getProduct = ({ productId }, callback) => {
  axios
    .get(BASE_API_URL + `product/${productId}`)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const getProducts = (callback) => {
  axios
    .get(BASE_API_URL + "product")
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const updateProduct = ({ authToken, product }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .put(BASE_API_URL + `product/${product.productId}`, product, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const deleteProduct = ({ authToken, productId }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .delete(BASE_API_URL + `product/${productId}`, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};
