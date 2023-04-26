"use client";

import Image from "next/image";
import React, { useEffect } from "react";
import styles from "./page.module.scss";
import grow from "../../public/service_grow.png";
import fight from "../../public/service_fight.png";
import card from "../../public/service_card.png";

const Home = () => {
  useEffect(() => {
    console.log(localStorage.getItem("accessToken"));
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.banner}>
        <div className={styles.left}>
          <h1 className={styles.title}>REPOMON</h1>
          <p className={styles.comment}>
            당신의 프로젝트에 함께할 레포몬은 무엇인가요?
          </p>
        </div>
        <div className={styles.right}></div>
      </div>
      <div className={styles.service}>
        <h2 className={styles["service-title"]}>
          <span>레포몬</span> 서비스 둘러보기
        </h2>
        <div className={styles["service-content"]}>
          <div className={styles["img-div"]} style={{ margin: "0 20px 0 0" }}>
            <Image alt="grow" src={grow} className={styles.fp}></Image>
            <span>레포지토리 분석</span>
            <p className={styles.des}>
              당신의 Github 레포지토리와 함께 성장하는 레포몬을 육성해보세요.
              <br />
              분석 차트와 함께 당신의 프로젝트를 한눈에 시각화해서 나타내줘요.
            </p>
          </div>
          <div>
            <div className={styles["img-div"]} style={{ margin: "0 0 20px 0" }}>
              <Image alt="grow" src={fight} className={styles.sp}></Image>
              <span>배틀 및 랭킹</span>
              <p className={styles.des}>
                당신의 프로젝트가 얼마나 강한지 궁금하지 않나요?
                <br />
                성장시킨 레포몬으로 다른 유저와 배틀해보세요!
              </p>
            </div>
            <div className={styles["img-div"]}>
              <Image alt="grow" src={card} className={styles.tp}></Image>
              <span>카드 추출</span>
              <p className={styles.des}>
                열심히 성장시킨 내 프로젝트를
                <br />
                README에서 실시간으로 자랑해보세요.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
