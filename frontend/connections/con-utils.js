export const BASE_API_URL = "http://localhost:8080/api/";

export const getAuthHeader = (token) => {
  return {
    headers: {
      Authorization: "Bearer " + token,
    },
  };
};
