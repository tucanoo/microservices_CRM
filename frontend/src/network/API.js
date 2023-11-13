import axios from "axios";
import {AUTH_TOKEN_NAME} from "../app/config.js";

const token = localStorage.getItem(AUTH_TOKEN_NAME + "_token")?.substring(1, localStorage.getItem(AUTH_TOKEN_NAME + "_token").length - 1);

// give 5 minute time out
const axiosInstance = axios.create({
  // baseURL: API_URL,
  timeout: 300000,
  headers: {
    'Authorization': `Bearer ${token}`,
    'Content-Type': 'application/json',
    'accept': 'application/json'
  }
});


export function setDefaultHeader(key, value) {
  axiosInstance.defaults.headers.common[key] = value;
}

// Request interceptor for API calls
axiosInstance.interceptors.request.use(
  async (config) => {
    const token = localStorage.getItem(AUTH_TOKEN_NAME + "_token").substring(1, localStorage.getItem(AUTH_TOKEN_NAME + "_token").length - 1);

    config.headers = {
      Authorization: `Bearer ${token}`,
    };
    return config;
  },
  (error) => {
    Promise.reject(error);
  }
);

axiosInstance.interceptors.response.use(function (response) {
    return response;
  }, function (error) {

    // Prevent infinite loops
    if (!error.response || error.response.status === 500) {
      console.error("RESPONSE NOT AVAILABLE. POSSIBLE SERVER CONNECTION ISSUE ", error)
      return Promise.reject(error);
    }

    // if we get a 401 (UNAUTHORIED) then return to sign in
    if (error.response?.status === 401) {
      window.location.href = '/logout';
      return Promise.reject(error);
    }

    // specific error handling done elsewhere
    return Promise.reject(error);
  }
);

export default axiosInstance;

