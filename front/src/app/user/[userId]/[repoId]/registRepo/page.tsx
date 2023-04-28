"use client";
import { useState, useEffect, useRef } from "react";
import { NextPage } from "next";
import styles from "./page.module.scss";
import Image from "next/image";
import { PageProps, Todo } from "@/types/repoRegist";
import "animate.css";
import GitTable from "@/components/GitTable";
import Button_OK from "@/components/Button_OK";
import Button_NO from "@/components/Button_NO";

const Page: NextPage<PageProps> = ({ params }) => {
  const [numArr, setNumArr] = useState([0, 0, 0, 0, 0]);
  const dice = useRef<HTMLImageElement>(null);
  const diceShadow = useRef<HTMLDivElement>(null);

  function generateRandomNumArr() {
    let sum = 0;
    const newArr = [];

    // 첫번째 요소는 1~10사이의 랜덤값으로 설정
    newArr.push(Math.floor(Math.random() * 10) + 1);

    // 나머지 요소는 1~10사이의 랜덤값으로 설정되며, 총합이 30이 되도록 함
    for (let i = 1; i < 5; i++) {
      const maxNum = 30 - sum - (5 - i);
      const randomNum = Math.floor(Math.random() * Math.min(maxNum, 10)) + 1;
      newArr.push(randomNum);
      sum += randomNum;
    }

    setNumArr(newArr);
  }

  // 주사위 클릭 시 실행
  const diceClick = () => {
    const audio = new Audio("/static/sound/Dice.mp3");
    const randomNumArr = numArr.map(() => Math.floor(Math.random() * 10) + 1);
    setNumArr(randomNumArr);
    generateRandomNumArr();

    if (dice.current && diceShadow.current) {
      dice.current.style.transform = `translate(-50%, -250%) rotate(720deg)`;
      dice.current.style.filter = `blur(0.1em)`;
      diceShadow.current.style.transform = "translate(-50%, -50%) scaleX(0.7)";

      setTimeout(() => {
        if (dice.current && diceShadow.current) {
          dice.current.style.transform =
            "translate(-50%, -50%) rotate(-1440deg)";
          dice.current.style.filter = `blur(0em)`;
          diceShadow.current.style.transform =
            "translate(-50%, -50%) scaleX(1)";
        }
        audio.play();
      }, 200);
    }
  };

  const attackRef = useRef<any>(null);
  const avoidanceRef = useRef<any>(null);
  const enduranceRef = useRef<any>(null);
  const criticalRef = useRef<any>(null);
  const hitRef = useRef<any>(null);

  useEffect(() => {
    // numArr의 값이 바뀔 때마다 호출
    const [attack, avoidance, endurance, critical, hit] = numArr;
    // 최대치를 달성하면 해당 숫자의 색깔을 주황색으로 변경
    if (
      !attackRef.current ||
      !avoidanceRef.current ||
      !enduranceRef.current ||
      !criticalRef.current ||
      !hitRef.current
    )
      return;

    const setStatStyle = (
      stat: number,
      statRef: React.MutableRefObject<any | null>
    ) => {
      const isMax = stat === 10;
      statRef.current!.style.color = isMax ? "red" : "white";
      statRef.current!.style.fontSize = isMax ? "1.2em" : "1em";
      if (isMax) {
        statRef.current!.classList.add("animate__animated", "animate__shakeX");
        setTimeout(() => {
          statRef.current!.classList.remove(
            "animate__animated",
            "animate__shakeX"
          );
        }, 80);
      }
    };

    setStatStyle(attack, attackRef);
    setStatStyle(avoidance, avoidanceRef);
    setStatStyle(endurance, enduranceRef);
    setStatStyle(critical, criticalRef);
    setStatStyle(hit, hitRef);
  }, [numArr]);

  return (
    <div className={styles.pageContainer}>
      <div className={styles.selectBox}>
        <div className={styles.monChar}></div>
        <div className={styles.monChar}></div>
        <div className={styles.monChar}></div>
      </div>
      <div className={styles.settingBox}>
        <div className={styles.conventionBox}>
          <GitTable />
        </div>
        <div className={styles.diceContainer}>
          <p className={styles.diceTitle}>초기 능력치 설정</p>
          <div className={styles.diceBox} style={{ marginBottom: "15%" }}>
            <Image
              src="/static/images/dice.png"
              alt="dice"
              width={100}
              height={100}
              className={styles[`dice`]}
              onClick={diceClick}
              ref={dice}
            />
            <div className={styles.diceShadow} ref={diceShadow} />
          </div>
          <div className={styles.statusBox}>
            <div className="flex flex-col" style={{ width: "80%" }}>
              <div className="overflow-x-auto sm:-mx-6 lg:-mx-8">
                <div className="inline-block min-w-full py-2 sm:px-6 lg:px-8">
                  <div className="rounded-lg overflow-hidden">
                    <table
                      className="min-w-full border  text-center dark:border-neutral-500  "
                      id={styles.tableText}
                    >
                      <tbody
                        style={{ textAlign: "center" }}
                        id={styles.tableBody}
                      >
                        <tr
                          className="border-white dark:border-neutral-500"
                          style={{ borderWidth: "5px", height: "4em" }}
                        >
                          <td
                            className=" border-white whitespace-nowrap border-r px-6 py-4  dark:border-neutral-500"
                            style={{ width: "50%", borderWidth: "5px" }}
                          >
                            공격력
                          </td>
                          <td
                            id={styles.ranNumber}
                            className="whitespace-nowrap border-r px-6 py-4 dark:border-neutral-500"
                            ref={attackRef}
                          >
                            {numArr[0]}
                          </td>
                        </tr>
                        <tr
                          className="border-white dark:border-neutral-500"
                          style={{ borderWidth: "5px", height: "4em" }}
                        >
                          <td
                            className="border-white whitespace-nowrap border-r px-6 py-4  dark:border-neutral-500"
                            style={{ width: "50%", borderWidth: "5px" }}
                          >
                            회피율
                          </td>
                          <td
                            className="whitespace-nowrap border-r px-6 py-4 dark:border-neutral-500"
                            ref={avoidanceRef}
                          >
                            {numArr[1]}%
                          </td>
                        </tr>
                        <tr
                          className="border-white dark:border-neutral-500"
                          style={{ borderWidth: "5px", height: "4em" }}
                        >
                          <td
                            className=" border-white whitespace-nowrap border-r px-6 py-4  dark:border-neutral-500"
                            style={{ width: "50%", borderWidth: "5px" }}
                          >
                            방어율
                          </td>
                          <td
                            className="whitespace-nowrap border-r px-6 py-4 dark:border-neutral-500"
                            ref={enduranceRef}
                          >
                            {numArr[2]}%
                          </td>
                        </tr>
                        <tr
                          className="border-white dark:border-neutral-500"
                          style={{ borderWidth: "5px", height: "4em" }}
                        >
                          <td
                            className="border-white whitespace-nowrap border-r px-6 py-4  dark:border-neutral-500"
                            style={{ width: "50%", borderWidth: "5px" }}
                          >
                            치명타율
                          </td>
                          <td
                            className="whitespace-nowrap border-r px-6 py-4 dark:border-neutral-500"
                            ref={criticalRef}
                          >
                            {numArr[3]}%
                          </td>
                        </tr>
                        <tr
                          className="border-white dark:border-neutral-500"
                          style={{ borderWidth: "5px", height: "4em" }}
                        >
                          <td
                            className="border-white whitespace-nowrap border-r px-6 py-4  dark:border-neutral-500"
                            style={{ width: "50%", borderWidth: "5px" }}
                          >
                            명중률
                          </td>
                          <td
                            className="whitespace-nowrap border-r px-6 py-4 dark:border-neutral-500"
                            ref={hitRef}
                          >
                            {numArr[4]}%
                          </td>
                        </tr>
                      </tbody>
                    </table>
                    <div
                      style={{
                        fontSize: "0.8em",
                        color: "grey",
                        textAlign: "center",
                        marginTop: "5%",
                      }}
                    >
                      <p>(공격력 최소 1 ~ 최대 10)</p>
                      <p>
                        (기타 확률 최소 1% ~ 최대 10%, 모든 확률 총합은 30 이하)
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div style={{ display: "flex", marginBlock: "3%" }}>
        <Button_OK msg={"결정하기"} />
        <Button_NO msg={"취소하기"} />
      </div>
    </div>
  );
};

export default Page;
