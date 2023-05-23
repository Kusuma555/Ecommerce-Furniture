import axios from "axios";
import { BASE_API_URL, getAuthHeader } from "./con-utils";

export const getUserDetails = ({ authToken, userId }, callback) => {
  const { headers } = getAuthHeader(authToken);
  axios
    .get(BASE_API_URL + `user/id/${userId}`, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};

export const updateUserDetails = (
  { authToken, userId, userDetails },
  callback
) => {
  const { headers } = getAuthHeader(authToken);
  //   console.log(details);
  axios
    .put(BASE_API_URL + `user/${userId}`, userDetails, headers)
    .then((res) => callback?.success(res))
    .catch((err) => callback?.error(err));
};
