"use client";

import { requestMatchResult } from "@/api/repoBattle";
import Spinner from "@/components/Spinner";
import { BattleResultResponseDataType } from "@/types/repoBattle";
import React, { useEffect, useRef, useState } from "react";
import styles from "./page.module.scss";

const Page = () => {
  const [loadData, setLoadData] = useState<boolean>(false);
  const [matchData, setMatchData] = useState<BattleResultResponseDataType>();
  const opHp = matchData?.data.defenseRepo.hp;
  const myHp = matchData?.data.attackRepo.hp;

  function getMatchResult() {
    // oppo - my
    return requestMatchResult(1, 4);
  }
  useEffect(() => {
    getMatchResult()
      .then((response) => {
        const data = response.data;
        setMatchData(data);
        setLoadData(true);
        console.log(data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  return (
    <div className={styles.pageContainer}>
      <p className={styles.headerTitle}>
        <strong>{matchData?.data.attackRepo.repomonNickname}</strong>
        &nbsp;vs&nbsp;
        <strong>{matchData?.data.defenseRepo.repomonNickname}</strong> 레포몬
        배틀
      </p>
      <div className={styles.gameWrapper}>
        <div className={styles.gameBox}></div>
        <div className={styles.gameLogBox}>
          <div style={{ height: "200px", overflowY: "auto" }}>
            {loadData ? (
              <>
                <p>done...!</p>
                {matchData?.data.battleLog.map((resData, index, array) => {
                  return (
                    <React.Fragment key={resData.turn}>
                      {index === 0 && (
                        <>
                          <p>&lt;system&gt;{resData.attacker}의 선공</p>
                          <p>
                            &lt;system&gt;
                            {matchData?.data.attackRepo.repomonNickname}의 시작
                            체력: {myHp}
                          </p>
                          <p>
                            &lt;system&gt;
                            {matchData?.data.defenseRepo.repomonNickname}의 시작
                            체력: {opHp}
                          </p>
                          <br />
                        </>
                      )}

                      <p key={resData.turn}>{resData.turn}번째 턴!</p>
                      <p>
                        플레이어 {resData.attacker}의{" "}
                        {resData.attack_act === 1
                          ? "무지성"
                          : resData.attack_act === 2
                          ? "나름 치명적인"
                          : matchData.data.attackRepo.repomon
                              .repomonSkillName}{" "}
                        공격!
                      </p>
                      <p>
                        개쳐맞던 플레이어 {resData.defender}는{" "}
                        {resData.defense_act}를 취하고 {resData.damage}의
                        데미지를 입었다..!
                      </p>
                      <br />
                      {index === array.length - 1 && (
                        <>
                          {matchData.resultCode === "SUCCESS" ? (
                            <p>승리..!</p>
                          ) : (
                            <p>개같이 패배...!</p>
                          )}
                          <p>&lt;system&gt;배틀 종료...</p>
                        </>
                      )}
                    </React.Fragment>
                  );
                })}
              </>
            ) : (
              <Spinner />
            )}
          </div>
        </div>
      </div>
    </div>
  );
};

export default Page;
