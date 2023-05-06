import React from "react";
import styles from "./DetailConvention.module.scss";
import { ContributionChart } from "./ContributionChart";
import { RepoDetailContributionInfoType } from "@/types/repoDetail";

function DetailContribution({
  contributionInfo,
}: {
  contributionInfo: RepoDetailContributionInfoType;
}) {
  return (
    <div>
      <p className={styles["tab-title"]}>레포지토리 기여도 정보</p>
      <p className={styles["tab-des"]}>
        나와 팀원들의 기여도를 확인할 수 있어요.
      </p>
      <div style={{ display: "flex", marginTop: "4rem" }}>
        <div className={styles.left}>
          {/* <ContributionChart commiters={contributionInfo.committers} /> */}
        </div>
        <div className={styles.right}></div>
      </div>
    </div>
  );
}

export default DetailContribution;
