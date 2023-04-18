"use client";

import React, { useEffect, useRef } from "react";
import * as THREE from "three";
import { GLTFLoader } from "three/addons/loaders/GLTFLoader.js";
import styles from "./page.module.scss";

function Page({ params }: { params: { repoId: string } }) {
  return (
    <div className={styles.pageContainer}>
      <div className={styles.info}>
        <div className={styles["repo-mon-div"]}>
          <div className={styles["repo-mon"]}></div>
          <div className={styles.exp}>asd</div>
        </div>
      </div>
    </div>
  );
}

export default Page;
