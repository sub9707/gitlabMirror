import { http } from "./axios";

/**
 *  배틀 결과 요청
 * @method POST
 * @param opponentRepoId:상대 레포 아이디
 * @param repoId:내 레포 아이디
 */

export const requestMatchResult = async (oppoId: number, myId: number) => {
  const data = {
    opponentRepoId: oppoId,
    repoId: myId,
  };

  const res = await http.post(`/api/v1/repomon/${myId}/match`);

  return res;
};
