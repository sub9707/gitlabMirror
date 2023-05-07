import React from "react";
import styles from "./DetailConvention.module.scss";
import { ConventionChart } from "./ConventionChart";
import { RepoDetailConventionInfoType } from "@/types/repoDetail";
import Lottie from "react-lottie-player";
import notFound from "public/static/lotties/not_found.json";

function DetailConvention({
  conventionInfo,
}: {
  conventionInfo: RepoDetailConventionInfoType;
}) {
  return (
    <div className={styles.container}>
      <p className={styles["tab-title"]}>레포지토리 컨벤션 정보</p>
      <p className={styles["tab-des"]}>
        레포지토리의 컨벤션과 준수율을 확인할 수 있어요.
      </p>
      {conventionInfo.conventions && conventionInfo.conventions.length > 0 && (
        <div style={{ display: "flex", marginTop: "4rem" }}>
          <div className={styles.left}>
            {conventionInfo.conventions.map((con, index) => (
              <div key={index} className={styles["con-div"]}>
                <span className={styles.prefix}>{con.prefix}</span>
                <span className={styles.des}>{con.description}</span>
              </div>
            ))}
          </div>
          <div className={styles.right}>
            <ConventionChart
              total={conventionInfo.totalCnt}
              obey={conventionInfo.collectCnt}
            />
          </div>
        </div>
      )}
      {(!conventionInfo.conventions ||
        conventionInfo.conventions?.length === 0) && (
        <div className={styles["no-convention"]}>
          <Lottie
            loop={true}
            animationData={notFound}
            play
            style={{ width: "300px" }}
          />
          <p className={styles.comment}>등록된 컨벤션이 없어요.</p>
        </div>
      )}
    </div>
  );
}

export default DetailConvention;
