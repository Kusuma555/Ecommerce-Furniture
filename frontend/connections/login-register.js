import axios from "axios";
import { saveAddress } from "./address";
import { BASE_API_URL } from "./con-utils";

export const login = ({ emailId, password }, callback) => {
  axios
    .post(BASE_API_URL + "auth", {
      emailId,
      password,
    })
    .then((res) => {
      callback.success(res);
    })
    .catch((err) => {
      callback.error(err);
    });
};

export const adminLogin = (credentials, callback) => {
  axios
    .post(BASE_API_URL + "auth/admin", credentials)
    .then((res) => {
      callback.success(res);
    })
    .catch((err) => {
      callback.error(err);
    });
};

export const register = async ({ userDetails, address }, callback) => {
  axios
    .post(BASE_API_URL + "user", userDetails)
    .then((res) => {
      const data = res.data.data;
      // console.log(data);

      saveAddress({ userId: data.userId, address }, callback);
    })
    .catch((err) => {
      callback.error(err);
    });
};

export const logout = ({ authToken }, callback) => {
  axios
    .delete(BASE_API_URL + `auth/${authToken}`)
    .then((res) => {
      callback?.success(res);
    })
    .catch((err) => {
      callback?.error(err);
    });
};
