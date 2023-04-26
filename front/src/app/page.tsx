"use client";

import React, { useEffect } from "react";
import styles from "./page.module.scss";

const Home = () => {
  useEffect(() => {
    console.log(localStorage.getItem("accessToken"));
  }, []);

  return (
    <div className={styles.container} style={{ marginTop: "90px" }}>
      <div className={styles.banner}>
        <div className={styles.left}>
          <p className={styles.title}>REPOMON</p>
          <p className={styles.comment}>
            당신의 프로젝트에 함께할 레포몬은 무엇인가요?
          </p>
        </div>
      </div>
    </div>
  );
};

export default Home;
