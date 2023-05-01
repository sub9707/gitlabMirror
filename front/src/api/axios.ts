import axios, { AxiosInstance } from "axios";

export const http: AxiosInstance = axios.create({
  baseURL: "https://repomon.kr/api/v1",
  withCredentials: true,
});
