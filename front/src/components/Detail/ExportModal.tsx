"use client";

import React, { useState, useEffect } from "react";
import Modal from "react-modal";
import { Cog8ToothIcon, XMarkIcon } from "@heroicons/react/24/solid";
import styles from "./ExportModal.module.scss";
import { ChevronDownIcon, ChevronUpIcon } from "@heroicons/react/24/outline";
import ReactMarkdown from "react-markdown";
import Loading2 from "@/app/loading2";
import Link from "next/link";
import { customAlert } from "@/app/utils/CustomAlert";
import { axiosRequestSetPersonalLans } from "@/api/repoDetail";
import { languageColor } from "@/styles/colors";

const customStyles = {
  content: {
    zIndex: "10000000",
    top: "50%",
    left: "50%",
    right: "auto",
    bottom: "auto",
    marginRight: "-50%",
    transform: "translate(-50%, -50%)",
    width: "600px",
    height: "470px",
  },
};

const ExportModal = ({
  repoId,
  userId,
  isTeam,
  lans,
}: {
  repoId: number;
  userId: number;
  isTeam: boolean;
  lans: string[];
}) => {
  const [modalIsOpen, setIsOpen] = React.useState(false);
  const [kind, setKind] = useState<string>("레포 카드");
  const [showKind, setShowKind] = useState<boolean>(false);
  const teamSource = `![RepomonRepoCard](https://repomon.kr/card/repo?repoId=${repoId})`;
  const personalSource = `![RepomonRepoPersonalCard](https://repomon.kr/card/repo_personal?repoId=${repoId}&userId=${userId}) `;
  const [selected, setSelected] = useState<string[]>([]);

  /** ============================== 함수, Event Handler ============================== */
  const openModal = () => {
    setIsOpen(true);
  };

  const afterOpenModal = () => {};

  const closeModal = () => {
    setIsOpen(false);
  };

  const onClickKindItem = (e: React.MouseEvent<HTMLLIElement>) => {
    const target = e.target as HTMLElement;
    setKind(target.id);
    setShowKind(false);
  };

  const onClickCard = () => {
    let copyUrl: string;

    if (kind === "레포 카드") {
      copyUrl = `[![RepomonRepoCard](https://repomon.kr/card/repo?repoId=${repoId})](https://repomon.kr)`;
    } else {
      copyUrl = `[![RepomonRepoPersonalCard](https://repomon.kr/card/repo_personal?repoId=${repoId}&userId=${userId})](https://repomon.kr)`;
    }

    window.navigator.clipboard.writeText(copyUrl).then(() => {
      alert("복사가 완료되었습니다.");
    });
  };

  const onClickLan = (lan: string) => {
    if (selected.includes(lan)) {
      const newSelected = selected.filter((item) => item !== lan);

      setSelected(newSelected);
      return;
    }

    if (selected.length == 6) {
      return;
    }

    setSelected([...selected, lan]);
  };

  const onClickApplyBtn = () => {
    requestSetPersonalLans();
  };

  const onClickClearBtn = () => {
    setSelected([]);
  };

  /** ============================== Axios ============================== */
  const requestSetPersonalLans = async () => {
    try {
      const res = await axiosRequestSetPersonalLans(repoId, selected);
      console.log("레포 개인 카드 언어 설정: ", res);
      if (res.data.resultCode === "SUCCESS") {
        setSelected([]);
      }
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <>
      <button onClick={openModal} className={styles.open}>
        추출하기
      </button>

      <Modal
        isOpen={modalIsOpen}
        onAfterOpen={afterOpenModal}
        onRequestClose={closeModal}
        style={customStyles}
        contentLabel=""
      >
        <p className={styles.title}>레포지토리 카드 추출</p>
        <div className={styles["kind-select"]}>
          <div className={styles["btn-div"]}>
            <button
              onClick={() => setShowKind(!showKind)}
              className={styles["cur-kind"]}
            >
              {kind}
              {!showKind && <ChevronDownIcon />}
              {showKind && <ChevronUpIcon />}
            </button>
            <button className={styles.copy}>카드 복사</button>
          </div>

          {showKind && (
            <ul className={styles.option}>
              <li id="레포 카드" onClick={onClickKindItem}>
                레포 카드
              </li>
              {isTeam && (
                <li id="개인 레포 카드" onClick={onClickKindItem}>
                  개인 레포 카드
                </li>
              )}
            </ul>
          )}
        </div>
        {kind === "레포 카드" && (
          <p className={styles.comment}>
            <p>
              레포 카드를 통해 레포지토리의 정보를 한 눈에 확인할 수 있습니다.
            </p>
            <p>README에 URL을 입력하면, 레포 카드를 생성할 수 있습니다.</p>
            <p>
              <span style={{ color: "red" }}>*</span> 레포지토리 갱신 후의
              정보가 반영됩니다.
            </p>
          </p>
        )}
        {kind === "개인 레포 카드" && (
          <p className={styles.comment}>
            <p>개인 레포 카드를 통해 나의 기여 정보를 확인할 수 있습니다.</p>
            <p>README에 URL을 입력하면, 개인 레포 카드를 생성할 수 있습니다.</p>
            <p>
              <span style={{ color: "red" }}>*</span> 레포지토리 갱신 후의
              정보가 반영됩니다.
            </p>
          </p>
        )}
        {kind === "개인 레포 카드" && (
          <>
            <p className={styles["set-lan-title"]}>언어 설정</p>
            <p className={styles["set-lan-com"]}>
              카드에 표시할 언어를 최대 6개까지 선택해주세요.
            </p>
            <div className={styles["set-lan-div"]}>
              {lans?.map((lan, index) => (
                <span
                  key={index}
                  onClick={() => onClickLan(lan)}
                  className={
                    selected.includes(lan) ? styles.selected : undefined
                  }
                >
                  {lan}
                </span>
              ))}
            </div>
            {selected.length >= 1 && (
              <div className={styles["btn-div"]}>
                <button className={styles.clear} onClick={onClickClearBtn}>
                  취소
                </button>
                <button className={styles.apply} onClick={onClickApplyBtn}>
                  적용
                </button>
              </div>
            )}
          </>
        )}

        <p className={styles.exam}>
          <span>*</span> {"예시)"}
        </p>
        {kind === "레포 개인 카드" && (
          <div
            style={
              kind === "레포 개인 카드"
                ? { display: "hidden" }
                : { display: "block" }
            }
            className={styles["card-div"]}
            onClick={onClickCard}
          >
            <ReactMarkdown>{personalSource}</ReactMarkdown>
          </div>
        )}
        {kind === "레포 카드" && (
          <div
            style={
              kind === "레포 카드"
                ? { display: "hidden" }
                : { display: "block" }
            }
            className={styles["card-div"]}
            onClick={onClickCard}
          >
            <ReactMarkdown>{teamSource}</ReactMarkdown>
          </div>
        )}
        <p className={styles.docs}>
          추가적인 정보는 <Link href="/docs/#RepoTeamCard">Docs</Link>를
          참고해주세요.
        </p>
      </Modal>
    </>
  );
};

export default ExportModal;
