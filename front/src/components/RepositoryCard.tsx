`use-client`;

import { setActiveRepo } from "@/api/userRepo";
import { useAppSelector } from "@/redux/hooks";
import Link from "next/link";
import { useRouter } from "next/navigation";
import React, { useEffect, useState } from "react";
import CardSkeleton from "./Skeletons/CardSkeleton";

type propType = {
  title: string | undefined;
  desc: string | undefined;
  exp: number | undefined;
  rating: number | undefined;
  isActive: boolean | undefined;
  userId: string | undefined;
  repoId: number | undefined;
  isSameUser: boolean | undefined;
  setIsSameUser: React.Dispatch<React.SetStateAction<boolean | undefined>>;
  isLoaded: boolean | undefined;
};

function RepositoryCard(props: propType) {
  const [isActive, setIsActive] = useState<boolean | undefined>(props.isActive);
  const [userOriginId, setUserOriginId] = useState<number>();
  const login = useAppSelector((state) => state.authReducer.login);
  const router = useRouter();

  function handleClick() {
    setIsActive(!isActive);
    if (props.repoId) setActiveRepo(props.repoId);
    console.log("변경");
  }

  function handleRouting() {
    if (props.isActive) {
      // 활성화 일 때,
      router.push(`/repo/${props.repoId}`);
    } else {
      // 아닐 때
      if (props.isSameUser) router.push(`/repo/${props.repoId}`);
    }
  }

  useEffect(() => {
    if (localStorage.getItem("accessToken")) {
      setUserOriginId(parseInt(localStorage.getItem("userId") as string, 10));
    } else {
      setUserOriginId(-1);
    }
  }, [login]);

  useEffect(() => {
    if (userOriginId == props.userId) {
      props.setIsSameUser(true);
    } else {
      props.setIsSameUser(false);
    }
  }, [props.isSameUser]);

  return !props.isLoaded ? (
    <CardSkeleton />
  ) : (
    <div
      className="h-64 p-6 bg-white border border-gray-200 rounded-lg shadow dark:bg-gray-800 dark:border-gray-700"
      style={{
        opacity: isActive ? "1" : "0.5",
      }}
    >
      <div
        style={{
          height: "10%",
          justifyContent: "flex-end",
          display: "flex",
          visibility: props.isSameUser ? "visible" : "hidden",
        }}
      >
        <span className="mr-3 text-sm font-medium text-gray-900 dark:text-gray-300">
          레포지터리 공개
        </span>
        <label className="relative inline-flex items-center cursor-pointer">
          <input
            type="checkbox"
            className="sr-only peer"
            checked={isActive ? true : false}
            onClick={() => handleClick()}
          />
          <div className="w-11 h-6 bg-gray-200 peer-focus:outline-none peer-focus:ring-4 peer-focus:ring-blue-300 dark:peer-focus:ring-blue-800 rounded-full peer dark:bg-gray-700 peer-checked:after:translate-x-full peer-checked:after:border-white after:content-[''] after:absolute after:top-[0px] after:left-[2px] after:bg-white after:border-gray-300 after:border after:rounded-full after:h-5 after:w-5 after:transition-all dark:border-gray-600 peer-checked:bg-blue-600"></div>
        </label>
      </div>
      <div
        style={{
          display: "flex",
          justifyContent: "space-around",
          alignItems: "center",
          height: "80%",
        }}
      >
        <div
          style={{
            width: "40%",
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            border: "1px solid black",
            height: "100%",
          }}
        >
          {props.isActive ? "캐릭터" : "달걀"}
        </div>
        <div style={{ width: "50%" }}>
          <p
            style={{
              fontSize: "2em",
              fontWeight: "600",
              overflow: "hidden",
              whiteSpace: "nowrap",
              textOverflow: "ellipsis",
              cursor: props.isSameUser
                ? "pointer"
                : props.isActive
                ? "pointer"
                : "auto",
            }}
            onClick={handleRouting}
          >
            {props.title}
          </p>
          <p style={{ fontSize: "1em", fontWeight: "500", marginBlock: "3%" }}>
            {props.desc === null ? "설명 없음" : props.desc}
          </p>
          <div style={{ display: props.isSameUser ? "block" : "none" }}>
            <p>경험치 : {props.exp}</p>
            <p>배틀 레이팅 : {props.rating} </p>
          </div>
        </div>
      </div>
    </div>
  );
}

export default RepositoryCard;
