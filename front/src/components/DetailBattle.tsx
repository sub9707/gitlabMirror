"use client";

import React, { useState } from "react";
import Image from "next/image";
import { ChevronDoubleRightIcon } from "@heroicons/react/24/outline";
import styles from "./DetailBattle.module.scss";
import heartIcon from "../../public/static/icons/heart_icon.png";
import attackIcon from "../../public/static/icons/attack_icon.png";
import avoidIcon from "../../public/static/icons/avoid_icon.png";
import criticalIcon from "../../public/static/icons/critical_icon.png";
import hitIcon from "../../public/static/icons/hit_icon.png";
import shieldIcon from "../../public/static/icons/shield_icon.png";
import {
  BattleRecordType,
  RepoDetailBattleType,
  StatType,
} from "@/types/repoDetail";
import sword from "../../public/static/icons/sword_icon.png";
import tmp1 from "../../public/tmp_record_repomon_1.png";
import tmp2 from "../../public/tmp_record_repomon_2.png";
import { axiosRequestUpStat } from "@/api/repoDetail";

const DetailBattle = ({
  battleInfo,
  rank,
  battleRecords,
  myRepomonNickname,
  repoId,
  setStatUpdated,
}: {
  battleInfo: RepoDetailBattleType;
  rank: number;
  battleRecords: BattleRecordType[];
  myRepomonNickname: string;
  repoId: string;
  setStatUpdated: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  const [restStatPoint, setRestStatPoint] = useState<number>(
    battleInfo.statPoint
  );
  const defaultPointStats: StatType = {
    atkPoint: 0,
    criticalPoint: 0,
    defPoint: 0,
    dodgePoint: 0,
    hitPoint: 0,
  };
  const [pointStats, setPointStats] = useState<StatType>(defaultPointStats);
  const [statChanged, setStatChanged] = useState<boolean>(false);

  const onClickStatPlus = (e: React.MouseEvent<HTMLButtonElement>) => {
    setStatChanged(true);
    setRestStatPoint(restStatPoint - 1);

    const target = e.target as HTMLElement;

    setPointStats({
      ...pointStats,
      [target.id]: pointStats[target.id] + 1,
    });
  };

  const onClickReset = () => {
    setStatChanged(false);
    setRestStatPoint(battleInfo.statPoint);
    setPointStats(defaultPointStats);
  };

  const onClickStatApply = async () => {
    try {
      const res = await axiosRequestUpStat(parseInt(repoId, 10), pointStats);
      console.log("스탯 변경 응답: ", res);
      setStatUpdated((prev) => !prev);
    } catch (err) {
      console.error("스탯 변경 에러", err);
    }
  };

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
            <span>{battleInfo.rating}</span>
            <span>{rank}위</span>
            <span>{battleInfo.winCnt}</span>
            <span>{battleInfo.loseCnt}</span>
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
          <p className={styles["left-title"]}>
            능력치
            <span>
              스탯 포인트:
              <span style={{ color: "blue" }}> {restStatPoint}</span>
            </span>
          </p>
          <div className={styles["stat-line"]}>
            <p style={{ marginTop: "2rem" }}>
              <Image
                src={heartIcon}
                alt="heart"
                className={styles.icon}
              ></Image>
              생명력: <span style={{ color: "#E54F4F" }}>{battleInfo.hp}</span>
            </p>
            <p>
              <Image
                src={attackIcon}
                alt="attack"
                className={styles.icon}
              ></Image>
              공격력: <span>{battleInfo.atk}</span>{" "}
              <span style={{ color: "#FFB800" }}>
                (+ {pointStats.atkPoint * battleInfo.increaseAtk})
              </span>
              {restStatPoint > 0 && (
                <button id="atkPoint" onClick={onClickStatPlus}>
                  +
                </button>
              )}
            </p>
            <p>
              <Image
                src={shieldIcon}
                alt="shieldPoint"
                className={styles.icon}
              ></Image>
              방어율: <span>{battleInfo.def}</span>
              <span style={{ color: "#76E250" }}>
                (+ {pointStats.defPoint * battleInfo.increaseDef})
              </span>
              %
              {restStatPoint > 0 && (
                <button id="defPoint" onClick={onClickStatPlus}>
                  +
                </button>
              )}
            </p>
            <p>
              <Image
                src={criticalIcon}
                alt="critical"
                className={styles.icon}
              ></Image>
              치명타율: <span>{battleInfo.critical}</span>
              <span style={{ color: "#C846F5" }}>
                (+ {pointStats.criticalPoint * battleInfo.increaseCritical})
              </span>
              %
              {restStatPoint > 0 && (
                <button id="criticalPoint" onClick={onClickStatPlus}>
                  +
                </button>
              )}
            </p>
            <p>
              <Image
                src={avoidIcon}
                alt="avoid"
                className={styles.icon}
              ></Image>
              회피율: <span>{battleInfo.dodge}</span>
              <span style={{ color: "#83BCFF" }}>
                (+ {pointStats.dodgePoint * battleInfo.increaseDodge})
              </span>
              %
              {restStatPoint > 0 && (
                <button id="dodgePoint" onClick={onClickStatPlus}>
                  +
                </button>
              )}
            </p>
            <p>
              <Image src={hitIcon} alt="hit" className={styles.icon}></Image>
              명중율: <span>{battleInfo.hit}</span>
              <span style={{ color: "#6FD9E8" }}>
                (+ {pointStats.hitPoint * battleInfo.increaseHit})
              </span>
              %
              {restStatPoint > 0 && (
                <button id="hitPoint" onClick={onClickStatPlus}>
                  +
                </button>
              )}
            </p>
            <div style={{ minHeight: "80px", margin: "2.5rem 0 0 0" }}>
              {statChanged && (
                <div className={styles["btn-div"]}>
                  <button onClick={onClickReset}>취소</button>
                  <button onClick={onClickStatApply}>적용</button>
                </div>
              )}
            </div>
          </div>
        </div>
        <div className={styles.right}>
          <p style={{ margin: "6rem 0 2rem 0", fontSize: "1.75rem" }}>
            최근 전적
          </p>
          <div>
            {!battleRecords && <p>배틀 기록이 없습니다.</p>}
            {battleRecords &&
              battleRecords.slice(0, 5).map((record, index) => (
                <div key={index} className={styles["record-item"]}>
                  <div id="left">
                    <Image src={tmp1} alt="tmp1"></Image>
                    <div
                      className={`${styles["record-refo-info"]} ${styles.attack}`}
                    >
                      <p>공격 레포몬</p>
                      <p
                        className={styles["repomon-nickname"]}
                        style={
                          myRepomonNickname ===
                          record.attackRepo.repomonNickname
                            ? {
                                color: "rgb(54, 150, 96)",
                              }
                            : undefined
                        }
                      >
                        {record.attackRepo.repomonNickname}
                      </p>
                    </div>
                    <div
                      className={styles["result-div"]}
                      style={{ margin: "0 0 0 0.5rem" }}
                    >
                      <p className={record.isWin ? styles.win : styles.lose}>
                        {record.isWin ? "승리" : "패배"}
                      </p>
                      <p className={record.isWin ? styles.win : styles.lose}>
                        {record.attackPoint}
                      </p>
                    </div>
                  </div>
                  <Image
                    src={sword}
                    alt="무시무시한 칼"
                    className={styles.sword}
                  ></Image>
                  <div id="right">
                    <div
                      className={styles["result-div"]}
                      style={{ margin: "0 0.5rem 0 0" }}
                    >
                      <p className={!record.isWin ? styles.win : styles.lose}>
                        {!record.isWin ? "승리" : "패배"}
                      </p>
                      <p className={!record.isWin ? styles.win : styles.lose}>
                        {record.defensePoint}
                      </p>
                    </div>
                    <div
                      className={`${styles["record-refo-info"]} ${styles.defense}`}
                    >
                      <p>방어 레포몬</p>
                      <p
                        className={styles["repomon-nickname"]}
                        style={
                          myRepomonNickname ===
                          record.defenseRepo.repomonNickname
                            ? {
                                color: "rgb(54, 150, 96)",
                              }
                            : undefined
                        }
                      >
                        <p>{record.defenseRepo.repomonNickname}</p>
                      </p>
                    </div>
                    <Image src={tmp2} alt="tmp2"></Image>
                  </div>
                </div>
              ))}
          </div>
        </div>
      </div>
    </div>
  );
};

export default DetailBattle;
