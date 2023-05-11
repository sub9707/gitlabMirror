"use client";

import React, { useEffect, useState } from "react";
import styles from "./page.module.scss";
import Lottie from "react-lottie-player";
import cardLottie from "public/static/lotties/card.json";
import { ChevronDownIcon, ChevronUpIcon } from "@heroicons/react/24/outline";
import ReactMarkdown from "react-markdown";

const Page = ({
  params,
}: {
  params: { repoId: string; userId: string; isTeam: string };
}) => {
  const [kind, setKind] = useState<string>("레포 카드");
  const [showKind, setShowKind] = useState<boolean>(false);
  const [markdownSource, setMarkdownSource] = useState<string>(
    `![RepomonRepoCard](https://repomon.kr/card/repo?repoId=${params.repoId}`
  );

  const onClickKindItem = (e: React.MouseEvent<HTMLLIElement>) => {
    const target = e.target as HTMLElement;
    setKind(target.id);
    setShowKind(false);
  };

  useEffect(() => {
    if (JSON.parse(params.isTeam)) {
      setMarkdownSource(markdownSource + `&userId=${params.userId})`);
    } else {
      setMarkdownSource(markdownSource + `)`);
    }
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.banner}>
        <div className={styles["banner-inner"]}>
          <div className={styles["banner-text"]}>
            <p className={styles["banner-title"]}>레포지토리 카드 추출</p>
            <p className={styles["banner-comment"]}>
              Github README에 활용 가능한 레포지토리 분석 카드를 추출할 수
              있어요.
            </p>
          </div>
          <div className={styles["banner-lottie"]}>
            <Lottie
              loop={false}
              animationData={cardLottie}
              play
              style={{ width: "350px" }}
            />
          </div>
        </div>
      </div>
      <div className={styles.content}>
        <div className={styles.kind}>
          <p className={styles["kind-title"]}>카드 유형</p>
          <div className={styles["kind-select"]}>
            <button
              onClick={() => setShowKind(!showKind)}
              className={styles["cur-kind"]}
            >
              {kind}
              {!showKind && <ChevronDownIcon />}
              {showKind && <ChevronUpIcon />}
            </button>
            {showKind && (
              <ul className={styles.option}>
                <li id="레포 카드" onClick={onClickKindItem}>
                  레포 카드
                </li>
                {JSON.parse(params.isTeam) && (
                  <li id="개인 레포 카드" onClick={onClickKindItem}>
                    개인 레포 카드
                  </li>
                )}
              </ul>
            )}
          </div>
        </div>
        {kind === "레포 카드" && (
          <p className={styles.comment}>
            프로젝트 분석 결과를 README에 활용할 수 있습니다.
          </p>
        )}
        {kind === "개인 레포 카드" && (
          <p className={styles.comment}>프로젝트에 대한 분석 결과를 </p>
        )}
        {/* <div style={{ height: "500px" }}></div> */}
        <ReactMarkdown>{markdownSource}</ReactMarkdown>
      </div>
    </div>
  );
};

export default Page;
