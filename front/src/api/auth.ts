import { http } from "./axios";

export const axiosRequestLogout = async () => {
  const refreshToken = localStorage.getItem("refreshToken");

  const res = await http.get("auth/logout", {
    headers: {
      Authorization: refreshToken,
    },
  });

  return res;
};

export const axiosRequestRefresh = async () => {
  const accessToken = localStorage.getItem("accessToken");
  const refreshToken = localStorage.getItem("refreshToken");

  console.log(accessToken, refreshToken);
  console.log(typeof accessToken, typeof refreshToken);

  const res = await http.post(
    "auth/refresh",
    {},
    {
      headers: {
        accessToken,
        refreshToken,
      },
    }
  );

  return res;
};
