import Link from "next/link";
import styles from "./page.module.scss";

export default function Page({ params }: { params: { slug: string } }) {
  return (
    <>
      <div className={styles.pageContainer}>
        <div className={styles.profileWrapper}>
          <div className={styles.profileBox}>
            <div className={styles.upperMenu}>
              <div className={styles.menuItems}>
                <Link href="/" className={styles.visitLink}>
                  레포 농장 방문 &gt;&gt;
                </Link>
              </div>
              <div className={styles.menuItems}>
                <p>300</p>
              </div>
            </div>
            <div className={styles.downMenu}>
              <div className={styles.leftProfile}>
                <div className={styles.chracter}></div>
              </div>
              <div className={styles.description}>
                <div>
                  <p className={styles.userId}>Becoding84</p>
                </div>
                <div>
                  <p className={styles.descTxt}>레포지터리 수</p>
                  <p className={styles.descTxt}>보유 레포몬 수</p>
                  <p className={styles.descTxt}>경험치 총합</p>
                  <p className={styles.descTxt}>총 커밋 수</p>
                </div>
              </div>
              <div className={styles.ranking}></div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
}
