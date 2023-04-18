import Link from "next/link";
import styles from "./Header.module.scss";
import Logo from "../../public/temp.png";
import Image from "next/image";

export default function Header() {
  return (
    <header className={styles.navContainer}>
      <div className={styles.headerWrap}>
        <nav className={styles.navMenu}>
          <Link href="/" className={styles.linkTag}>
            home
          </Link>
          <Link href="/" className={styles.linkTag}>
            about
          </Link>
          <Link href="/" className={styles.linkTag}>
            posts
          </Link>
        </nav>
        <Image
          src={Logo}
          alt="logo"
          placeholder="blur"
          className={styles.logoImg}
        />
        <nav className={styles.navIcons}>
          <Link href="/">아이콘1</Link>
          <Link href="/">아이콘2</Link>
        </nav>
      </div>
      <div className={styles.divider} />
    </header>
  );
}
