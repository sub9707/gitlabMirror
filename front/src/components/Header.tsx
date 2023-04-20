import Link from "next/link";
import styles from "./Header.module.scss";
import Logo from "../../public/temp.png";
import Image from "next/image";

export default function Header() {
  return (
    <header className={styles.navContainer}>
      <div className={styles.headerWrap}>
        <div className={styles.navMenu}>
          <Link href="/" className={styles.linkTag}>
            Ranking
          </Link>
          <Link href="/" className={styles.linkTag}>
            about
          </Link>
          <Link href="/" className={styles.linkTag}>
            posts
          </Link>
        </div>
        <div className={styles.logoImg}>
          <Link href={"/"}>
            <Image
              src={Logo}
              alt="logo"
              placeholder="blur"
              className={styles.logoImgDetail}
            />
          </Link>
        </div>
        <div className={styles.navIcons}>
          <Link href="/user/temp_user">샘플 유저 페이지</Link>
        </div>
      </div>
      <div className={styles.divider} />
    </header>
  );
}
