"use client";

import React, { useState } from "react";
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

function Page() {
  const [tabIndex, setTabIndex] = useState(1);

  const onClickTabBtn = (e: React.MouseEvent) => {
    const target = e.target as HTMLElement;

    console.log(e.target);

    setTabIndex(parseInt(target.id, 10));
  };

  return (
    <div className={styles.pageContainer}>
      <div className={styles.info}>
        <div className={styles["repo-mon-card-div"]}>
          <div className={styles["repo-mon-card"]}>
            <DetailRepomon />
            <div className={styles["repo-mon-info"]}>
              <p>칙칙폭폭스</p>
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
                <span className={styles.exp}>204</span>
              </p>
              <ProgressBar />
            </div>
          </div>
        </div>
        <div className={styles["default-info-div"]}>
          <div className={styles.first}>
            <span className={styles.title}>Funteer</span>
            <div className={styles["icon-div"]}>
              <span className={styles.star}>
                <StarIcon
                  width="1.25rem"
                  style={{ display: "inline", marginRight: "0.3rem" }}
                />
                12
              </span>
              <span className={styles.share}>
                <ShareIcon
                  width="1.25rem"
                  style={{ display: "inline", marginRight: "0.45rem" }}
                />
                15
              </span>
            </div>
          </div>
          <p className={styles.date}>23.01.02 ~ 23.02.17</p>
          <div className={styles["lan-div"]}>
            <span>TypeScript</span>
            <span>Java</span>
            <span>SCSS</span>
            <span>HTML</span>
            <span>TypeScript</span>
            <span>Java</span>
            <span>SCSS</span>
            <span>HTML</span>
            <span>TypeScript</span>
            <span>Java</span>
            <span>SCSS</span>
            <span>HTML</span>
          </div>
          <div className={styles["tag-div"]}>
            <span>Kakao Login</span>
            <span>KG Inicis</span>
            <span>WebRTC</span>
            <span>Socket IO</span>
            <span>Kakao Login</span>
            <span>KG Inicis</span>
            <span>WebRTC</span>
            <span>Socket IO</span>
            <span>Kakao Login</span>
            <span>KG Inicis</span>
            <span>WebRTC</span>
            <span>Socket IO</span>
          </div>
          <p className={styles.des}>
            기부형 크라우드 펀딩을 통한 봉사활동 중개 플랫폼입니다.
          </p>
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
