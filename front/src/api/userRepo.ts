import { http } from "./axios";

// 유저 정보 조회
// GET
export const getUserDetail = async (userId: number) => {
  const res = await http.get(`/user/${userId}`);

  return res;
};

// 개별 레포 디테일 조회
// GET
export const getRepoDetail = async (repoId: number) => {
  const res = await http.get(`/repo/${repoId}/info`);

  return res;
};

// 개별 레포 정보 갱신
// PUT
export const refreshDetail = async (repoId: number) => {
  const res = await http.put(`/repo/${repoId}/info`);

  return res;
};

// 레포지토리 활성화 설정
// PUT
export const setRepoActive = async (repoId: number) => {
  const res = await http.put(`/repo/${repoId}/info/active`);

  return res;
};

// 개별 레포 랭킹정보 조회
// GET
export const getRankingInfo = async (repoId: number) => {
  const res = await http.get(`/repo/${repoId}/info/battle`);
  return res;
};

// 개별 레포 기여도 조회
// GET
export const getRepoConts = async (repoId: number) => {
  const res = await http.get(`/repo/${repoId}/info/contribute`);
  return res;
};

// 개별 레포 컨벤션 정보 조회
// GET
export const getRepoConvenInfo = async (repoId: number) => {
  const res = await http.get(`/repo/${repoId}/info/convention`);
  return res;
};

// 전체 레포 리스트 조회
// GET
export const getTotalRepoList = async (
  userId: number,
  pageNum: number,
  sizeNum: number
) => {
  const data = {
    page: pageNum,
    size: sizeNum,
  };

  const res = await http.get(`/repo/${userId}`, { data });

  return res;
};
