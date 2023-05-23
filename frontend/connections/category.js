import axios from "axios";
import { BASE_API_URL } from "./con-utils";
import { getAuthHeader } from "./con-utils";

export const getCategory = ({ categoryId }, callback) => {
  axios
    .get(BASE_API_URL + `category/${categoryId}`)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const getCategories = ({ authToken }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .get(BASE_API_URL + "category", headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const addCategory = ({ category, authToken }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .post(BASE_API_URL + "category", { name: category }, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const updateCategory = (
  { category, authToken, categoryId },
  callback
) => {
  const headers = getAuthHeader(authToken);
  axios
    .put(BASE_API_URL + `category/${categoryId}`, { name: category }, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const deleteCategory = ({ authToken, categoryId }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .delete(BASE_API_URL + `category/${categoryId}`, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};
