import axios, { AxiosInstance } from "axios";

export const http: AxiosInstance = axios.create({
  baseURL: "https://k8e105.p.ssafy.io/api/v1",
  // withCredentials: true,
});
