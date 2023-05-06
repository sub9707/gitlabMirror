import React from "react";
import styles from "./DetailConvention.module.scss";
import { ConventionChart } from "./ConventionChart";
import { RepoDetailConventionInfoType } from "@/types/repoDetail";

const tmp = {
  collectCnt: 64,
  // conventions: [],
  conventions: [
    {
      description: "새로운 기능 추가",
      prefix: "Feat",
    },
    {
      prefix: "Style",
      description:
        "코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우",
    },
    {
      prefix: "Chore",
      description:
        "위에 걸리지 않는 기타 변경사항(빌드 스크립트 수정, assets image, 패키지 매니저 등)",
    },
    {
      description: "새로운 기능 추가",
      prefix: "Feat",
    },
    {
      prefix: "Style",
      description: "코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우",
    },
    {
      prefix: "Chore",
      description:
        "위에 걸리지 않는 기타 변경사항(빌드 스크립트 수정, assets image, 패키지 매니저 등)",
    },
    {
      description: "새로운 기능 추가",
      prefix: "Feat",
    },
    {
      prefix: "Style",
      description: "코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우",
    },
    {
      prefix: "Chore",
      description:
        "위에 걸리지 않는 기타 변경사항(빌드 스크립트 수정, assets image, 패키지 매니저 등)",
    },
  ],
  repoId: 4,
  repoOwner: "becoding96",
  totalCnt: 93,
};

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
      {tmp.conventions.length > 0 && (
        <div style={{ display: "flex", marginTop: "4rem" }}>
          <div className={styles.left}>
            {tmp.conventions.map((con, index) => (
              <div key={index} className={styles["con-div"]}>
                <span className={styles.prefix}>{con.prefix}</span>
                <span className={styles.des}>{con.description}</span>
              </div>
            ))}
          </div>
          <div className={styles.right}>
            <ConventionChart total={tmp.totalCnt} obey={tmp.collectCnt} />
          </div>
        </div>
      )}
      {tmp.conventions.length === 0 && (
        <p className={styles.comment}>등록된 컨벤션이 없어요.</p>
      )}
    </div>
  );
}

export default DetailConvention;
