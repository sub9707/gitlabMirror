import React from "react";
import Image from "next/image";
import { ChevronDoubleRightIcon } from "@heroicons/react/24/outline";
import styles from "./DetailBattle.module.scss";
import heart from "../../public/static/icons/heart_icon.png";
import attack from "../../public/static/icons/attack_icon.png";
import avoid from "../../public/static/icons/avoid_icon.png";
import critical from "../../public/static/icons/critical_icon.png";
import hit from "../../public/static/icons/hit_icon.png";
import shield from "../../public/static/icons/shield_icon.png";

function DetailBattle() {
  return (
    <div>
      <p className={styles["tab-title"]}>레포몬 배틀 정보</p>
      <p className={styles["tab-des"]}>
        레포몬의 배틀에 관한 정보를 볼 수 있어요.
      </p>
      <div className={styles.top}>
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
      <div className={styles.bottom}>
        <div className={styles.left}>
          <p style={{ marginTop: "6rem", fontSize: "1.75rem" }}>능력치</p>
          <div className={styles["stat-line"]}>
            <p>
              <Image src={heart} alt="heart" className={styles.icon}></Image>
              생명력: <span style={{ color: "#E54F4F" }}>230</span>
            </p>
            <p>
              <Image src={attack} alt="attack" className={styles.icon}></Image>
              공격력: <span style={{ color: "#FFB800" }}>27</span>
            </p>
            <p>
              <Image src={shield} alt="shield" className={styles.icon}></Image>
              방어율: <span style={{ color: "#76E250" }}>15</span>%
            </p>
            <p>
              <Image
                src={critical}
                alt="critical"
                className={styles.icon}
              ></Image>
              치명타율: <span style={{ color: "#C846F5" }}>12</span>%
            </p>
            <p>
              <Image src={avoid} alt="avoid" className={styles.icon}></Image>
              회피율: <span style={{ color: "#83BCFF" }}>15</span>%
            </p>
            <p>
              <Image src={hit} alt="hit" className={styles.icon}></Image>
              명중율: <span style={{ color: "#6FD9E8" }}>17</span>%
            </p>
          </div>
        </div>
        <div className={styles.right}>
          <p style={{ marginTop: "6rem", fontSize: "1.75rem" }}>최근 전적</p>
          <div></div>
        </div>
      </div>
    </div>
  );
}

export default DetailBattle;
