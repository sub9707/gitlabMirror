"use client";

import React from "react";

import ProgressBar from "components/ProgressBar";
import styles from "./page.module.scss";
import { StarIcon, ShareIcon } from "@heroicons/react/24/outline";
import ExpAnalysis from "components/ExpAnalysis";
import GrowthChart from "components/GrowthChart";
import DetailRepomon from "components/DetailRepomon";

function Page({ params }: { params: { repoId: string } }) {
  return (
    <div className={styles.pageContainer}>
      <div className={styles.info}>
        <div className={styles["repo-mon-div"]}>
          <DetailRepomon />
          <div className={styles.exp}>
            <p>칙칙폭폭스</p>
            <ProgressBar />
          </div>
        </div>
        <div className={styles["default-info-div"]}>
          <div className={styles.first}>
            <span className={styles.title}>Repo Name</span>
            <div className={styles["icon-div"]}>
              <span className={styles.star}>
                <StarIcon
                  width="1.25rem"
                  style={{ display: "inline", marginRight: "0.2rem" }}
                />
                12
              </span>
              <span className={styles.share}>
                <ShareIcon
                  width="1.25rem"
                  style={{ display: "inline", marginRight: "0.2rem" }}
                />
                15
              </span>
            </div>
          </div>
          <p className={styles.date}>23.02.20 ~ 23.04.07</p>
          <div className={styles["lan-div"]}>
            <span>TypeScript</span>
            <span>Java</span>
            <span>Python</span>
            <span>TypeScript</span>
            <span>Java</span>
            <span>Python</span>
            <span>TypeScript</span>
            <span>Java</span>
            <span>Python</span>
          </div>
          <div className={styles["tag-div"]}>
            <span>KakaoAPI</span>
            <span>GithubAPI</span>
            <span>NextJS</span>
            <span>ThreeJS</span>
            <span>SpringBoot</span>
            <span>KakaoAPI</span>
            <span>GithubAPI</span>
            <span>NextJS</span>
            <span>ThreeJS</span>
            <span>SpringBoot</span>
          </div>
          <p className={styles.des}>
            Repo Description Repo Description Repo Description Repo Description
            Repo Description Repo Description
          </p>
          <div className={styles["exp-analysis-div"]}>
            <span className={styles["exp-analysis-title"]}>Exp Analysis</span>
            <ExpAnalysis />
          </div>
        </div>
      </div>
      <div className={styles["extra-div"]}>
        <div className={styles["growth-chart-div"]}>
          <span className={styles["growth-title"]}>Growth</span>
          <GrowthChart />
        </div>
      </div>
    </div>
  );
}

export default Page;
