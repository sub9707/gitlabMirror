import { http } from "./axios";

export const axiosRequestRepoDetail = async (repoId: number) => {
  const res = await http.get(`repo/${repoId}/info`);

  return res;
};

export const axiosRequestRepoDetailResearch = async (repoId: number) => {
  const res = await http.get(`repo/${repoId}/info/research`);

  return res;
};

export const axiosRequestRepoDetailBattleInfo = async (repoId: number) => {
  const res = await http.get(`repomon/${repoId}`);

  return res;
};

export const axiosRequestSetPeriod = async (
  repoId: number,
  startedAt: string,
  endAt: string
) => {
  const data = {
    startedAt,
    endAt,
  };

  const res = await http.put(`repo/${repoId}/info/period`, { data });

  return res;
};

export const axiosRequestCheckNicknameDuplicated = async (
  repomonNickname: string
) => {
  const data = {
    repomonNickname,
  };

  const res = await http.post(`repo/nickname`, { data });

  return res;
};

export const axiosRequestChangeNickname = async (
  repoId: number,
  repomonNickname: string
) => {
  const data = {
    repoId,
    repomonNickname,
  };

  const res = await http.put(`repomon/nickname`, { data });

  return res;
};
