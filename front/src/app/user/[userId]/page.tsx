import styles from "./page.module.scss";

export default function Page({ params }: { params: { slug: string } }) {
  return (
    <>
      <div className={styles.pageContainer}>
        <div className={styles.profileWrapper}></div>
      </div>
    </>
  );
}
