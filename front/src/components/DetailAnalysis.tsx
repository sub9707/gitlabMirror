import React from "react";
import styles from "./DetailAnalysis.module.scss";
import ExpAnalysis from "./ExpAnalysis";
import GrowthChart from "./GrowthChart";

function DetailAnalysis() {
  return (
    <div>
      <p className={styles["tab-title"]}>레포지토리 분석</p>
      <p className={styles["tab-des"]}>
        레포몬 성장의 근거와 일기를 볼 수 있어요.
      </p>
      <div style={{ margin: "4rem 0 0 0" }}>
        <div className={styles.att}>
          <span>경험치</span>
          <span>레포지토리 랭킹</span>
        </div>
        <div className={styles.att} style={{ color: "rgba(90, 167, 255, 1)" }}>
          <span>20,462</span>
          <span>14위</span>
        </div>
      </div>
      <div className={styles["chart-div"]}>
        <ExpAnalysis />
        <span>성장 요소</span>
      </div>
      <div className={styles["chart-div"]}>
        <GrowthChart />
        <span>성장 일기</span>
      </div>
    </div>
  );
}

export default DetailAnalysis;
