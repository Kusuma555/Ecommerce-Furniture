import axios from "axios";
import { BASE_API_URL, getAuthHeader } from "./con-utils";

export const uploadImage = ({ authToken, ownerId, files }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .post(`http://localhost:8004/api/upload/${ownerId}`, files, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const getImages = ({ authToken, ownerId }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .get(BASE_API_URL + `upload/${ownerId}`, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const deleteImages = ({ authToken, ownerId }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .delete(BASE_API_URL + `upload/${ownerId}`, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const deleteImage = ({ authToken, filename }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .delete(BASE_API_URL + `upload/filename/${filename}`, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};
