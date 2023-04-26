`use-client`;
import { ArrowPathIcon } from "@heroicons/react/24/solid";
import Link from "next/link";
import styles from "./page.module.scss";
import Pagination from "@/components/Pagination";

export default function Page({ params }: { params: { userId: string } }) {
  return (
    <>
      <div className={styles.pageContainer}>
        <div className={styles.bannerBack} />
        <div className={styles.bodyContainer}>
          <div className={styles.sideProfile}>
            <div>
              <div
                className={styles.chracter}
                style={{
                  backgroundImage:
                    "url('https://avatars.githubusercontent.com/u/88614621?v=4')",
                }}
              />
              <p className={styles.boxTitle}>Becoding84</p>
              <p className={styles.boxContent}>총 경험치 : 146523 (14위)</p>
            </div>
            <div>
              <p className={styles.boxTitle}>대표 레포몬</p>
              <p
                className={styles.boxTitle}
                style={{ fontWeight: "800", marginBottom: "1em" }}
              >
                도슴고치
              </p>
              <div className={styles.charaterDiv} />
              <p className={styles.boxContent}>경험치 : 18502 (14위)</p>
              <p className={styles.boxContent}>배틀 레이팅 : 1850 (12위)</p>
            </div>
            <div>
              <Link href="/" className={styles.visitLink}>
                레포 농장 방문 &gt;&gt;
              </Link>
            </div>
          </div>
          <div className={styles.bodyList}></div>
        </div>
      </div>
    </>
  );
}
