import React from "react";
import Image from "next/image";
import { ChevronDoubleRightIcon } from "@heroicons/react/24/outline";
import styles from "./DetailBattle.module.scss";
import heart from "../../public/heart_icon.png";
import attack from "../../public/attack_icon.png";
import avoid from "../../public/avoid_icon.png";
import critical from "../../public/critical_icon.png";
import hit from "../../public/hit_icon.png";
import shield_ from "../../public/shield_icon.png";

function DetailBattle() {
  return (
    <div>
      <p className={styles["tab-title"]}>레포몬 배틀 정보</p>
      <p className={styles["tab-des"]}>
        배틀에 관한 레포몬의 정보를 볼 수 있어요.
      </p>
      <div className={styles.line}>
        <div>
          <div className={styles.att}>
            <span>레이팅</span>
            <span>배틀 랭킹</span>
            <span>승</span>
            <span>패</span>
          </div>
          <div className={styles.att} style={{ color: "red" }}>
            <span>2,042</span>
            <span>16위</span>
            <span>33</span>
            <span>11</span>
          </div>
        </div>
        <button>
          배틀 매칭
          <span>
            <ChevronDoubleRightIcon />
          </span>
        </button>
      </div>
      {/* <p style={{ marginTop: "6rem", fontSize: "1.75rem" }}>능력치</p> */}
      {/* <p className={styles["stat-line"]}>
        <Image src={heart} alt="heart"></Image> <span>생명력</span>
        <span>
          <Image src={attack} alt="attack"></Image>공격력
        </span>
      </p> */}
    </div>
  );
}

export default DetailBattle;
