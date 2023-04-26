import styles from "./page.module.scss";

export default function Home() {
  return (
    <div className={styles.container} style={{ marginTop: "90px" }}>
      <div className={styles.banner}>
        <div className={styles.text}>
          <p>RepoMon</p>
        </div>
      </div>
    </div>
  );
}
