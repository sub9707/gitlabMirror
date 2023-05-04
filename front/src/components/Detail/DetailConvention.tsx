import React from "react";
import styles from "./DetailConvention.module.scss";
import { ConventionChart } from "./ConventionChart";

function DetailConvention() {
  return (
    <div className={styles.container}>
      <p className={styles["tab-title"]}>레포지토리 컨벤션 정보</p>
      <p className={styles["tab-des"]}>
        레포지토리의 컨벤션과 준수율을 확인할 수 있어요.
      </p>
      <div style={{ display: "flex", marginTop: "6rem" }}>
        <div className={styles.left}></div>
        <div className={styles.right}>
          <ConventionChart />
        </div>
      </div>
    </div>
  );
}

export default DetailConvention;
