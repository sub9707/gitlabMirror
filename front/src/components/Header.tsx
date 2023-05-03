"use client";

import React, { useEffect, useRef, useState } from "react";
import Link from "next/link";
import styles from "./Header.module.scss";
import Logo from "../../public/logo.png";
import Image from "next/image";
import { useAppSelector } from "@/redux/hooks";
import { axiosRequestLogout } from "@/api/auth";
import { useRouter } from "next/navigation";
import gitCat from "../../public/git_cat.svg";

const Header = () => {
  const githubLoginUrl =
    "https://repomon.kr/api/v1/oauth2/authorization/github";
  const login = useAppSelector((state) => state.authReducer.login);
  const [userId, setUserId] = useState<number>();
  const [avatarUrl, setAvatarUrl] = useState<string>("");
  const [showMenu, setShowMenu] = useState<boolean>(false);
  const router = useRouter();
  const menuRef = useRef<HTMLDivElement>(null);

  /** ======================================== useEffect ======================================== */
  useEffect(() => {
    if (localStorage.getItem("accessToken")) {
      setUserId(parseInt(localStorage.getItem("userId") as string, 10));
      setAvatarUrl(localStorage.getItem("avatarUrl") as string);
      console.log(typeof localStorage.getItem("avatarUrl") as string);
    } else {
      setUserId(-1);
    }
  }, [login]);

  useEffect(() => {
    const handleClickOutside = (e: MouseEvent) => {
      if (menuRef.current && !menuRef.current.contains(e.target as Node)) {
        setShowMenu(false);
      }
    };

    document.addEventListener("mousedown", handleClickOutside);

    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [menuRef]);

  /** ======================================== event Handler ======================================== */
  const onClickAvatar = () => {
    setShowMenu((prev) => !prev);
  };

  const onClickRepoList = () => {
    setShowMenu(false);
    router.push(`user/${userId}`);
  };

  const onClickLogout = async () => {
    setShowMenu(false);
    try {
      const res = await axiosRequestLogout();
      console.log(res);
      localStorage.clear();
      setUserId(-1);
      router.push(".");
    } catch (err) {
      console.error(err);
    }
  };

  return (
    <header className={styles.component}>
      <div className={styles.left}>
        <div className={styles["logo-div"]}>
          <Link href="/">
            <Image
              src={Logo}
              alt="logo"
              placeholder="blur"
              className={styles.logo}
            />
          </Link>
        </div>
        <Link href="/" className={styles.item}>
          랭킹
        </Link>
        <Link href="/user/3/16" className={styles.item}>
          샘플 레포 상세
        </Link>
      </div>
      <div className={styles.right}>
        {userId === -1 && (
          <Link href={githubLoginUrl} className={styles.item}>
            로그인
          </Link>
        )}
        {userId && userId !== -1 && (
          <div className={styles["avatar-div"]}>
            {avatarUrl && (
              <Image
                alt="프로필 이미지"
                src={avatarUrl}
                width={55}
                height={55}
                className={styles.avatar}
                onClick={onClickAvatar}
              />
            )}
            {!avatarUrl && (
              <Image
                alt="기본 프로필 이미지"
                src={gitCat}
                width={55}
                height={55}
                className={styles.avatar}
                onClick={onClickAvatar}
              />
            )}
            {showMenu && (
              <div ref={menuRef} className={styles.menu}>
                <button
                  style={{ marginBottom: "1rem" }}
                  onClick={onClickRepoList}
                >
                  내 레포지토리
                </button>
                <button onClick={onClickLogout}>로그아웃</button>
              </div>
            )}
          </div>
        )}
      </div>
    </header>
  );
};

export default Header;
