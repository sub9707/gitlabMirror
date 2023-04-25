import { ArrowPathIcon } from "@heroicons/react/24/solid";
import Link from "next/link";
import styles from "./page.module.scss";
import Pagination from "@/components/Pagination";

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
                <div
                  className={styles.chracter}
                  style={{
                    backgroundImage: "url()",
                  }}
                />
              </div>
              <div className={styles.description}>
                <div style={{ display: "flex" }}>
                  <p className={styles.userId}>Becoding84</p>
                  <ArrowPathIcon
                    width="2rem"
                    style={{ marginLeft: "2%", marginBottom: "5%" }}
                    className={styles.arrow}
                  />
                </div>
                <div>
                  <p className={styles.descTxt}>레포지터리 수</p>
                  <p className={styles.descTxt}>보유 레포몬 수</p>
                  <p className={styles.descTxt}>경험치 총합</p>
                  <p className={styles.descTxt}>총 커밋 수</p>
                </div>
              </div>
              <div className={styles.ranking}>
                <button type="button" className={styles.btn}>
                  대표 캐릭터 변경
                </button>
                <div className={styles.character}>캐릭터</div>
                <p className={styles.fonts}> 총 경험치 순위 0위</p>
                <p className={styles.fonts}> 선택 레포몬 경험치 순위 0위</p>
              </div>
            </div>
          </div>
        </div>
        <div className={styles.listWrapper}>
          <div className={styles.listHeader}>
            <p className={styles.repoTitle}>등록된 레포지터리 목록</p>
            <button type="button" className={styles.repoBtn}>
              레포지터리 등록
            </button>
          </div>
          <div className={styles.listBox}>
            <div style={{ marginBottom: "5%" }}>
              <div className="grid grid-cols-6 md:grid-cols-2 sm:grid-cols-1 gap-10">
                <div className={styles.cardContainer}>123</div>
                <div className={styles.cardContainer}>123</div>
                <div className={styles.cardContainer}>123</div>
                <div className={styles.cardContainer}>123</div>
                <div className={styles.cardContainer}>123</div>
                <div className={styles.cardContainer}>123</div>
              </div>
            </div>
            <Pagination />
          </div>
        </div>
      </div>
    </>
  );
}
