"use client";

import React, { useState, useEffect } from "react";
import { StarIcon, ShareIcon } from "@heroicons/react/24/outline";
import DetailRepomon from "@/components/Detail/DetailRepomon";
import ProgressBar from "@/components/Detail/ProgressBar";
import styles from "./page.module.scss";
import {
  PresentationChartLineIcon,
  ClipboardDocumentListIcon,
  ChartPieIcon,
} from "@heroicons/react/24/outline";
import DetailAnalysis from "@/components/Detail/DetailAnalysis";
import DetailBattle from "@/components/Detail/DetailBattle";
import {
  BattleRecordType,
  ConventionInfoType,
  RepoDetailBattleType,
  RepoDetailResearchType,
  RepoDetailType,
} from "@/types/repoDetail";
import {
  axiosRequestBattleRanking,
  axiosRequestBattleRecord,
  axiosRequestRepoDetail,
  axiosRequestRepoDetailBattleInfo,
  axiosRequestRepoDetailConvention,
} from "@/api/repoDetail";
import { axiosRequestRepoDetailResearch } from "@/api/repoDetail";
import { pretreatDate } from "@/app/utils/PretreatDate";
import DatePickerModal from "@/components/Detail/DatePickerModal";
import Modal from "react-modal";
import RenameModal from "@/components/Detail/RenameModal";
import Loading from "@/app/loading";
import DetailConvention from "@/components/Detail/DetailConvention";

function Page({ params }: { params: { userId: string; repoId: string } }) {
  const [loginUserId, setLoginUserId] = useState<string>();
  const [repoDetailInfo, setRepoDetailInfo] = useState<RepoDetailType>();
  const [tabIndex, setTabIndex] = useState<number>(0);
  const [isUpdated, setIsUpdated] = useState<boolean>(false);
  const [repoDetailResearchInfo, setRepoDetailResearchInfo] =
    useState<RepoDetailResearchType>();
  const [repoDetailBattleInfo, setRepoDetailBattleInfo] =
    useState<RepoDetailBattleType>();
  const [battleRank, setBattleRank] = useState<number>();
  const [battleRecordInfo, setBattleRecordInfo] =
    useState<BattleRecordType[]>();
  const [showPage, setShowPage] = useState<boolean>(false);
  const [statUpdated, setStatUpdated] = useState<boolean>(false);
  const [repoDetailConventionInfo, setRepoDetailConventionInfo] =
    useState<ConventionInfoType>();

  /** =============================================== useEffect =============================================== */
  useEffect(() => {
    const localUserId = sessionStorage.getItem("userId");
    if (localUserId) {
      setLoginUserId(localUserId);
    }
  }, []);

  /** 레포 기본 정보 불러오기 + 레포몬 닉네임, 기간 업데이트 시 정보 재요청 */
  useEffect(() => {
    requestRepoDetail(
      parseInt(params.repoId, 10),
      parseInt(loginUserId as string, 10)
    );
  }, [loginUserId, isUpdated]);

  /** 배틀 정보 불러오기 + 스탯 변경 시 재요청 */
  useEffect(() => {
    requestRepoDetailBattleInfo(parseInt(params.repoId, 10));
  }, [statUpdated]);

  /** 레포 정보 불러오기 */
  useEffect(() => {
    requestRepoDetailResearch(parseInt(params.repoId, 10));
    requestBattleRanking(parseInt(params.repoId, 10));
    requestBattleRecord(parseInt(params.repoId, 10));
    requestRepoDetailConvention(parseInt(params.repoId, 10));
  }, []);

  useEffect(() => {
    if (
      repoDetailInfo &&
      repoDetailResearchInfo &&
      repoDetailBattleInfo &&
      battleRank &&
      battleRecordInfo &&
      repoDetailConventionInfo
    ) {
      setShowPage(true);
    }
  }, [
    repoDetailInfo,
    repoDetailResearchInfo,
    repoDetailBattleInfo,
    battleRank,
    battleRecordInfo,
    repoDetailConventionInfo,
  ]);

  useEffect(() => {
    if (showPage) {
      Modal.setAppElement("#repo-detail");
    }
  }, [showPage]);

  /** 탭 인덱스 정보 */
  useEffect(() => {
    if (document.referrer !== window.location.href) {
      setTabIndex(1);
    } else {
      setTabIndex(parseInt(sessionStorage.getItem("tabIndex") as string, 10));
    }
  }, []);

  /** =============================================== Event Hadler =============================================== */
  const onClickTabBtn = (e: React.MouseEvent) => {
    const target = e.target as HTMLElement;

    console.log(e.target);

    setTabIndex(parseInt(target.id, 10));
    sessionStorage.setItem("tabIndex", target.id);
  };

  /** =============================================== Axios =============================================== */
  /** 레포 디테일 기본 정보 */
  const requestRepoDetail = async (repoId: number, userId: number) => {
    let res;
    try {
      if (userId) {
        res = await axiosRequestRepoDetail(repoId, userId);
      } else {
        res = await axiosRequestRepoDetail(repoId);
      }
      console.log("레포 디테일 기본 정보: ", res);
      setRepoDetailInfo(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  /** 레포 디테일 분석 정보 */
  const requestRepoDetailResearch = async (repoId: number) => {
    try {
      const res = await axiosRequestRepoDetailResearch(repoId);
      console.log("레포 디테일 분석 정보: ", res);
      setRepoDetailResearchInfo(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  /** 레포 디테일 배틀 정보 */
  const requestRepoDetailBattleInfo = async (repoId: number) => {
    try {
      const res = await axiosRequestRepoDetailBattleInfo(repoId);
      console.log("레포 디테일 배틀 정보: ", res);
      setRepoDetailBattleInfo(res.data.data);
    } catch (err) {
      console.error(err);
    }
  };

  /** 레포 디테일 배틀 랭킹 정보 */
  const requestBattleRanking = async (repoId: number) => {
    try {
      const res = await axiosRequestBattleRanking(repoId);
      console.log("레포 디테일 배틀 랭킹 정보: ", res.data.rank);
      setBattleRank(res.data.rank);
    } catch (err) {
      console.error(err);
    }
  };

  /** 레포 디테일 배틀 전적 정보 */
  const requestBattleRecord = async (repoId: number) => {
    try {
      const res = await axiosRequestBattleRecord(repoId);
      console.log("레포 디테일 배틀 전적 정보: ", res.data.data.battleLogList);
      setBattleRecordInfo(res.data.data.battleLogList);
    } catch (err: any) {
      console.error(err);
      if (err.response.data.status === 404) {
        setBattleRecordInfo([]);
      }
    }
  };

  /** 레포 디테일 컨벤션 정보 */
  const requestRepoDetailConvention = async (repoId: number) => {
    try {
      const res = await axiosRequestRepoDetailConvention(repoId);
      console.log("레포 디테일 컨벤션 정보: ", res);
      setRepoDetailConventionInfo(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <>
      {!showPage && <Loading />}
      {repoDetailInfo && showPage && (
        <div id="repo-detail" className={styles.pageContainer}>
          <div className={styles.info}>
            <div className={styles["repo-mon-card-div"]}>
              <div className={styles["repo-mon-card"]}>
                <DetailRepomon />
                <div className={styles["repo-mon-info"]}>
                  <p>
                    {repoDetailInfo.repomonName}
                    {repoDetailInfo.myRepo && (
                      <RenameModal
                        repoId={params.repoId}
                        setIsUpdated={setIsUpdated}
                      />
                    )}
                  </p>
                  <p>
                    <span
                      style={{
                        fontFamily: "SUIT-Bold",
                        fontWeight: "bold",
                        fontStyle: "italic",
                        marginRight: "1rem",
                        color: "rgb(124, 131, 255)",
                      }}
                    >
                      LV
                    </span>
                    <span className={styles.exp}>
                      {Math.floor(repoDetailInfo.repoExp / 100) + 1}
                    </span>
                  </p>
                  <ProgressBar restExp={repoDetailInfo.repoExp % 100} />
                </div>
              </div>
            </div>
            <div className={styles["default-info-div"]}>
              <div className={styles.first}>
                <span className={styles.title}>{repoDetailInfo.repoName}</span>
                <div className={styles["icon-div"]}>
                  <span className={styles.star}>
                    <StarIcon
                      width="1.25rem"
                      style={{ display: "inline", marginRight: "0.3rem" }}
                    />
                    {repoDetailInfo.starCnt}
                  </span>
                  <span className={styles.share}>
                    <ShareIcon
                      width="1.25rem"
                      style={{ display: "inline", marginRight: "0.45rem" }}
                    />
                    {repoDetailInfo.forkCnt}
                  </span>
                </div>
              </div>
              <p className={styles.date}>
                <span>{pretreatDate(repoDetailInfo.repoStart)}</span>
                <span style={{ margin: "0 0.5rem" }}>~</span>
                {repoDetailInfo.repoEnd && (
                  <span> {pretreatDate(repoDetailInfo.repoEnd)}</span>
                )}
                {!repoDetailInfo.repoEnd && repoDetailInfo.myRepo && (
                  <span className={styles.end}>
                    프로젝트 기간을 설정해주세요.
                  </span>
                )}
                {repoDetailInfo.myRepo && (
                  <DatePickerModal
                    repoId={params.repoId}
                    setIsUpdated={setIsUpdated}
                  />
                )}
              </p>
              <div className={styles["lan-div"]}>
                {repoDetailInfo.languages &&
                  repoDetailInfo.languages.map((lan, index) => (
                    <span key={index}>{lan}</span>
                  ))}
              </div>
              <div className={styles["tag-div"]}>
                {repoDetailInfo.tags &&
                  repoDetailInfo.tags.map((tag, index) => (
                    <span key={index}>{tag}</span>
                  ))}
              </div>
              <p className={styles.des}>{repoDetailInfo.repoDescription}</p>
              <div className={styles.tab}>
                <button
                  id="1"
                  onClick={onClickTabBtn}
                  className={tabIndex === 1 ? styles.selected : ""}
                >
                  <PresentationChartLineIcon id="1" onClick={onClickTabBtn} />
                  성장
                </button>
                <button
                  id="2"
                  onClick={onClickTabBtn}
                  className={tabIndex === 2 ? styles.selected : ""}
                >
                  <p
                    id="2"
                    style={{
                      textAlign: "center",
                      fontFamily: "SUIT-Bold",
                      fontStyle: "italic",
                      fontSize: "40px",
                      padding: "0 0.8rem 0.5rem 0",
                    }}
                    onClick={onClickTabBtn}
                  >
                    VS
                  </p>
                  배틀
                </button>
                <button
                  id="3"
                  onClick={onClickTabBtn}
                  className={tabIndex === 3 ? styles.selected : ""}
                >
                  <ClipboardDocumentListIcon id="3" onClick={onClickTabBtn} />
                  컨벤션
                </button>
                <button
                  id="4"
                  onClick={onClickTabBtn}
                  className={tabIndex === 4 ? styles.selected : ""}
                >
                  <ChartPieIcon id="4" onClick={onClickTabBtn} />
                  기여도
                </button>
              </div>
            </div>
          </div>
          <div className={styles["tab-content"]}>
            {tabIndex === 1 && (
              <DetailAnalysis researchInfo={repoDetailResearchInfo} />
            )}
            {tabIndex === 2 && (
              <DetailBattle
                battleInfo={repoDetailBattleInfo!}
                rank={battleRank!}
                battleRecords={battleRecordInfo!}
                myRepomonNickname={repoDetailInfo.repomonName}
                repoId={params.repoId}
                setStatUpdated={setStatUpdated}
                myRepo={repoDetailInfo.myRepo}
              />
            )}
            {tabIndex === 3 && <DetailConvention />}
          </div>
        </div>
      )}
    </>
  );
}

export default Page;
