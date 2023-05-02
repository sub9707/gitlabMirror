"use client";

import React, { useEffect, useState } from "react";
import { ArrowPathIcon } from "@heroicons/react/24/solid";
import Link from "next/link";
import styles from "./page.module.scss";
import Pagination from "@/components/Pagination";
import RepositoryCard from "@/components/RepositoryCard";
import { useRouter } from "next/navigation";
import DropDown from "@/components/DropDown";
import { getTotalRepoList, getUserDetail } from "@/api/userRepo";
import { RepoListType, UserInfoType } from "@/types/repoInfo";

const Page = ({ params }: { params: { userId: string } }) => {
  const router = useRouter();

  // 레포지터리 유저 정보 GET
  const [userInfo, setUserInfo] = useState<UserInfoType>();
  const [repoInfo, setRepoInfo] = useState<RepoListType>();

  function getUserInfo(userId: string) {
    return getUserDetail(Number(userId));
  }
  useEffect(() => {
    const data = getUserInfo(params.userId)
      .then((response) => {
        const res = response.data;
        console.log(res);
        setUserInfo(res);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  // 레포지터리 리스트 GET
  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await getTotalRepoList(Number(params.userId), 1, 6);
        const data = response.data;
        console.log(data);
        setRepoInfo(data);
      } catch (error) {
        console.error(error);
      }
    };

    fetchData();
  }, []);

  return (
    <>
      <div className={styles.pageContainer}>
        <div className={styles.bannerBack}>
          <iframe
            src="https://embed.lottiefiles.com/animation/97144"
            style={{
              width: "30%",
              height: "100%",
              display: "flex",
            }}
          ></iframe>
        </div>
        <div className={styles.bodyContainer}>
          <div className={styles.sideProfile}>
            <div>
              <div
                className={styles.chracter}
                style={{
                  backgroundImage: `url('${userInfo?.avatarUrl}')`,
                }}
              />
              <p className={styles.boxTitle}>
                {userInfo?.userId}({userInfo?.nickname})
              </p>
              <p className={styles.boxContent}>
                총 경험치 : {userInfo?.totalExp}(14위)
              </p>
            </div>
            <div>
              <p className={styles.boxTitle}>대표 레포몬</p>
              <p
                className={styles.boxTitle}
                style={{ fontWeight: "800", marginBottom: "1em" }}
              >
                도슴고치
              </p>
              <div className={styles.charaterDiv} />
              <p className={styles.boxContent}>경험치 : 18502 (14위)</p>
              <p className={styles.boxContent}>배틀 레이팅 : 1850 (12위)</p>
            </div>
            <div>
              <Link href="/" className={styles.visitLink}>
                레포 농장 방문 &gt;&gt;
              </Link>
            </div>
          </div>
          <div className={styles.bodyList}>
            <div className={styles.listTitle}>
              <div
                style={{ display: "flex", width: "50%", alignItems: "center" }}
              >
                <p>레포지터리 목록</p>
                <ArrowPathIcon
                  width="2rem"
                  style={{ marginLeft: "2%" }}
                  className={styles.arrow}
                  onClick={() => router.push("/user/123/abc/registRepo")}
                />
              </div>
              <div className={styles.filterBox}>
                <DropDown />
              </div>
            </div>
            <div className={styles.listCards}>
              <div className="grid grid-cols-2 gap-4">
                {Array(repoInfo?.totalElements)
                  .fill(null)
                  .map((items, i) =>
                    repoInfo?.repoListItems &&
                    i < repoInfo.repoListItems.length ? (
                      <RepositoryCard
                        key={i}
                        title={repoInfo.repoListItems.at(i)?.repoName}
                        desc={repoInfo.repoListItems.at(i)?.repoDescription}
                        exp={repoInfo.repoListItems.at(i)?.repoExp}
                        rating={repoInfo.repoListItems.at(i)?.repoRating}
                        isActive={repoInfo.repoListItems.at(i)?.isActive}
                      />
                    ) : null
                  )}
              </div>

              <div className={styles.paginations}>
                <Pagination totalPage={1} totalElement={3} />
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Page;
