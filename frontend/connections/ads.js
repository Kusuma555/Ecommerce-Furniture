import axios from "axios";
import { BASE_API_URL, getAuthHeader } from "./con-utils";

export const getAd = ({ authToken, aid }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .get(BASE_API_URL + `ad/${aid}`, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const getAdsByUser = ({ authToken, userId, page = 1 }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .get(BASE_API_URL + `ad/user/${userId}/${page}`, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const getAds = ({ authToken }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .get(BASE_API_URL + `ad`, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const postAd = ({ authToken, ad }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .post(BASE_API_URL + "ad", ad, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const updateAd = ({ authToken, ad, adId }, callback) => {
  const headers = getAuthHeader(authToken);
  axios
    .put(BASE_API_URL + `/${adId}`, ad, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};
