"use client";

import React, { useEffect, useState } from "react";
import { axiosRequestRepoRank } from "@/api/rank";
import { RepomonRankContentType, RepomonRankInfoType } from "@/types/rank";
import styles from "./RepomonRank.module.scss";
import Image from "next/image";
import Pagination from "../Pagination";
import top1Icon from "public/static/rank/1.png";
import top2Icon from "public/static/rank/2.png";
import top3Icon from "public/static/rank/3.png";

const tmpTop3: RepomonRankContentType[] = [
  {
    rating: 1000,
    repoExp: 1470,
    repoId: 4,
    repoKey: "R_kgDOJA3-MA",
    repoName: "funteer",
    repoOwner: "becoding96",
    repomon: "/static/models_png/12.png",
    repomonNickname: "케찹몬",
    repomonTier: 3,
    username: "becoding96",
  },
  {
    rating: 1000,
    repoExp: 1340,
    repoId: 4,
    repoKey: "R_kgDOJA3-MA",
    repoName: "IUJ",
    repoOwner: "becoding96",
    repomon: "/static/models_png/9.png",
    repomonNickname: "말몬",
    repomonTier: 3,
    username: "becoding96",
  },
  {
    rating: 1000,
    repoExp: 1260,
    repoId: 4,
    repoKey: "R_kgDOJA3-MA",
    repoName: "Almong",
    repoOwner: "becoding96",
    repomon: "/static/models_png/6.png",
    repomonNickname: "불닭몬",
    repomonTier: 3,
    username: "becoding96",
  },
];

const RepomonRank = ({
  searchInput,
  searchRequestSign,
}: {
  searchInput: string;
  searchRequestSign: boolean;
}) => {
  const [page, setPage] = useState<number>(0);
  const [repomonRankInfo, setRepomonRankInfo] = useState<RepomonRankInfoType>();

  useEffect(() => {
    requestRepoRank();
  }, [page, searchRequestSign]);

  const requestRepoRank = async () => {
    try {
      const res = await axiosRequestRepoRank(page, searchInput);
      console.log("레포몬 랭킹: ", res);
      setRepomonRankInfo(res.data);
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <div>
      <div className={styles["top-3-div"]}>
        <div className={styles["top-1"]}>
          <div className={styles.border} />
          <div className={styles["main-element"]} />
          <Image
            alt="top1icon"
            src={top1Icon}
            width={40}
            height={40}
            className={styles["rank-icon"]}
          ></Image>
          <div className={styles["img-div"]}>
            <Image
              alt="top1"
              src={tmpTop3[0].repomon}
              width={150}
              height={120}
            ></Image>
            <p className={styles["repomon-name"]}>
              {tmpTop3[0].repomonNickname}
            </p>
          </div>
          <div>
            <p className={styles["card-title"]}>유저</p>
            <p className={styles["card-content"]}>{tmpTop3[0].repoOwner}</p>
            <p className={styles["card-title"]}>레포지토리</p>
            <p className={styles["card-content"]}>{tmpTop3[0].repoName}</p>
            <p className={styles["card-title"]}>경험치</p>
            <p className={styles["card-content"]}>{tmpTop3[0].repoExp}</p>
          </div>
        </div>
        <div className={styles["top-2"]}>
          <div className={styles.border} />
          <div className={styles["main-element"]} />
          <Image
            alt="top2icon"
            src={top2Icon}
            width={40}
            height={40}
            className={styles["rank-icon"]}
          ></Image>
          <div className={styles["img-div"]}>
            <Image
              alt="top1"
              src={tmpTop3[1].repomon}
              width={150}
              height={120}
            ></Image>
            <p className={styles["repomon-name"]}>
              {tmpTop3[1].repomonNickname}
            </p>
          </div>
          <div>
            <p className={styles["card-title"]}>유저</p>
            <p className={styles["card-content"]}>{tmpTop3[1].repoOwner}</p>
            <p className={styles["card-title"]}>레포지토리</p>
            <p className={styles["card-content"]}>{tmpTop3[1].repoName}</p>
            <p className={styles["card-title"]}>경험치</p>
            <p className={styles["card-content"]}>{tmpTop3[1].repoExp}</p>
          </div>
        </div>
        <div className={styles["top-3"]}>
          <div className={styles.border} />
          <div className={styles["main-element"]} />
          <Image
            alt="top3icon"
            src={top3Icon}
            width={40}
            height={40}
            className={styles["rank-icon"]}
          ></Image>
          <div className={styles["img-div"]}>
            <Image
              alt="top1"
              src={tmpTop3[2].repomon}
              width={150}
              height={120}
            ></Image>
            <p className={styles["repomon-name"]}>
              {tmpTop3[2].repomonNickname}
            </p>
          </div>
          <div>
            <p className={styles["card-title"]}>유저</p>
            <p className={styles["card-content"]}>{tmpTop3[2].repoOwner}</p>
            <p className={styles["card-title"]}>레포지토리</p>
            <p className={styles["card-content"]}>{tmpTop3[2].repoName}</p>
            <p className={styles["card-title"]}>경험치</p>
            <p className={styles["card-content"]}>{tmpTop3[2].repoExp}</p>
          </div>
        </div>
      </div>
      <div className={styles.list}>
        <div className={styles["list-title"]}>
          <span>순위</span>
          <span>레포몬</span>
          <span>레포지토리</span>
          <span>유저</span>
          <span>경험치</span>
        </div>
        {repomonRankInfo?.content.map((item, index) => (
          <div key={index} className={styles["list-item"]}>
            <span>{index + 1}</span>
            <span>
              <Image
                alt="레포몬"
                src={item.repomon ? item.repomon : "/static/models_png/1.png"}
                width={45}
                height={45}
              ></Image>
              {item.repomonNickname}
            </span>
            <span>{item.repoName}</span>
            <span>{item.repoOwner}</span>
            <span>{item.repoExp}</span>
          </div>
        ))}
      </div>
      <div className={styles["pagination-div"]}>
        <Pagination
          totalPage={repomonRankInfo?.totalPages!}
          totalElement={repomonRankInfo?.totalElements!}
        />
      </div>
    </div>
  );
};

export default RepomonRank;
