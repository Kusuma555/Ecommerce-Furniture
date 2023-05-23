import axios from "axios";
import { BASE_API_URL, getAuthHeader } from "./con-utils";

export const saveAddress = ({ userId, address }, callback) => {
  axios
    .post(BASE_API_URL + `user/address/${userId}`, address)
    .then((res) => {
      //   console.log(res);
      callback?.success(res);
    })
    .catch((error) => {
      //   console.log(error);
      callback?.error(error);
    });
};

export const getAddress = ({ authToken, userId }, callback) => {
  const { headers } = getAuthHeader(authToken);
  axios
    .get(BASE_API_URL + `user/address/${userId}`, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const updateAddress = (
  { authToken, userId, addressId, address },
  callback
) => {
  const { headers } = getAuthHeader(authToken);
  axios
    .put(BASE_API_URL + `user/address/${userId}/${addressId}`, address, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};
