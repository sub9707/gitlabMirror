"use client";

import React, { useState, useEffect } from "react";
import { StarIcon, ShareIcon } from "@heroicons/react/24/outline";
import DetailRepomon from "@/components/DetailRepomon";
import ProgressBar from "@/components/ProgressBar";
import styles from "./page.module.scss";
import {
  MagnifyingGlassIcon,
  ClipboardDocumentListIcon,
  ChartPieIcon,
} from "@heroicons/react/24/outline";
import DetailAnalysis from "@/components/DetailAnalysis";
import DetailBattle from "@/components/DetailBattle";
import { RepoDetailResearchType, RepoDetailType } from "@/types/repoDetail";
import {
  axiosRequestRepoDetail,
  axiosRequestRepoDetailBattleInfo,
} from "@/api/repoDetail";
import { axiosRequestRepoDetailResearch } from "@/api/repoDetail";
import { pretreatDate } from "@/app/utils/PretreatDate";
import DatePickerModal from "@/components/DatePickerModal/DatePickerModal";
import Modal from "react-modal";
import RenameModal from "@/components/RenameModal/RenameModal";

Modal.setAppElement("#repo-detail");

function Page({ params }: { params: { repoId: string } }) {
  const [repoDetailInfo, setRepoDetailInfo] = useState<RepoDetailType>({
    forkCnt: 0,
    languages: [],
    repoEnd: "",
    repoExp: 0,
    repoName: "",
    repoDescription: "",
    repoStart: "",
    repomonId: 0,
    repomonName: "",
    starCnt: 0,
    tags: [""],
  });
  const [tabIndex, setTabIndex] = useState<number>(0);
  const [isUpdated, setIsUpdated] = useState<boolean>(false);
  const [repoDetailResearchInfo, setRepoDetailResearchInfo] =
    useState<RepoDetailResearchType>();

  /** =============================================== useEffect =============================================== */
  /** 레포 기본 정보 불러오기 + 레포몬 닉네임, 기간 업데이트 시 정보 재요청 */
  useEffect(() => {
    requestRepoDetail(parseInt(params.repoId, 10));
  }, [isUpdated]);

  /** 레포 정보 불러오기 */
  useEffect(() => {
    requestRepoDetailResearch(parseInt(params.repoId, 10));
    requestRepoDetailBattleInfo(parseInt(params.repoId, 10));
  }, []);

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
  const requestRepoDetail = async (repoId: number) => {
    try {
      const res = await axiosRequestRepoDetail(repoId);
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
    } catch (err) {
      console.error(err);
    }
  };

  /** 레포 디테일 배틀 정보 */
  const requestRepoDetailBattleInfo = async (repoId: number) => {
    try {
      const res = await axiosRequestRepoDetailBattleInfo(repoId);
      console.log("레포 디테일 배틀 정보: ", res);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div id="repo-detail" className={styles.pageContainer}>
      <div className={styles.info}>
        <div className={styles["repo-mon-card-div"]}>
          <div className={styles["repo-mon-card"]}>
            <DetailRepomon />
            <div className={styles["repo-mon-info"]}>
              <p>
                {repoDetailInfo.repomonName}
                <RenameModal
                  repoId={params.repoId}
                  setIsUpdated={setIsUpdated}
                />
              </p>
              <p>
                <span
                  style={{
                    fontFamily: "SUIT-Bold",
                    fontWeight: "bold",
                    fontStyle: "italic",
                    marginRight: "1rem",
                    color: "blue",
                  }}
                >
                  LV
                </span>
                <span className={styles.exp}>
                  {Math.floor(repoDetailInfo.repoExp / 100)}
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
            <span>{pretreatDate(repoDetailInfo.repoStart) + " ~"}</span>
            {repoDetailInfo.repoEnd ? (
              <span>{pretreatDate(repoDetailInfo.repoEnd)}</span>
            ) : (
              <span className={styles.end}>프로젝트 기간을 설정해주세요.</span>
            )}
            <DatePickerModal
              repoId={params.repoId}
              setIsUpdated={setIsUpdated}
            />
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
              <MagnifyingGlassIcon id="1" onClick={onClickTabBtn} />
              분석
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
        {tabIndex === 1 && <DetailAnalysis />}
        {tabIndex === 2 && <DetailBattle />}
      </div>
    </div>
  );
}

export default Page;
