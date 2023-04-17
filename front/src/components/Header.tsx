import Link from "next/link";
import styles from "./Header.module.scss";
import Logo from "../../public/temp.png";
import Image from "next/image";

export default function Header() {
  return (
    <header className={styles.headerWrap}>
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
      <Image src={Logo} alt="logo" placeholder="blur" />
      <nav className={styles.navIcons}>
        <Link href="/">home</Link>
        <Link href="/">about</Link>
      </nav>
    </header>
  );
}
