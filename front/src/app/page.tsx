import styles from "./page.module.scss";

export default function Home() {
  return (
    <div className={styles.container}>
      <div className={styles.banner}>
        <div className={styles.text}>
          <p>RepoMon</p>
        </div>
      </div>
    </div>
  );
}
