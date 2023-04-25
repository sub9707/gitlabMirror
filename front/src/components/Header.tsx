import Link from "next/link";
import styles from "./Header.module.scss";
import Logo from "../../public/logo.png";
import Image from "next/image";

export default function Header() {
  return (
    <header className={styles.component}>
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
      <Link href="/user/temp_user">샘플 유저 페이지</Link>
    </header>
  );
}
