"use client";

import React, { useEffect } from "react";
import { ArrowPathIcon } from "@heroicons/react/24/solid";
import Link from "next/link";
import styles from "./page.module.scss";
import Pagination from "@/components/Pagination";
import RepositoryCard from "@/components/RepositoryCard";
import { useRouter } from "next/navigation";

const Page = ({ params }: { params: { userId: string } }) => {
  const router = useRouter();

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
                  backgroundImage:
                    "url('https://avatars.githubusercontent.com/u/88614621?v=4')",
                }}
              />
              <p className={styles.boxTitle}>Becoding84</p>
              <p className={styles.boxContent}>총 경험치 : 146523 (14위)</p>
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
              <div className={styles.filterBox}>필터</div>
            </div>
            <div className={styles.listCards}>
              <div className="grid grid-cols-2 gap-4 place-content-center">
                <RepositoryCard />
                <RepositoryCard />
                <RepositoryCard />
                <RepositoryCard />
                <RepositoryCard />
                <RepositoryCard />
              </div>
              <div className={styles.paginations}>
                <Pagination />
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Page;
