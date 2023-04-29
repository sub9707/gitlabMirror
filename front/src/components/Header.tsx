import Link from "next/link";
import styles from "./Header.module.scss";
import Logo from "../../public/logo.png";
import Image from "next/image";

const Header = () => {
  const githubLoginUrl =
    "https://k8e105.p.ssafy.io/api/v1/oauth2/authorization/github";

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
        <Link href="/user/123" className={styles.item}>
          샘플 유저
        </Link>
        <Link href="/user/1/1" className={styles.item}>
          샘플 레포 상세
        </Link>
      </div>
      <div className={styles.right}>
        <Link href={githubLoginUrl} className={styles.item}>
          로그인
        </Link>
      </div>
    </header>
  );
};

export default Header;
