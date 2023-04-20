import React from "react";
import styles from "./ProgressBar.module.scss";

function ProgressBar() {
  return (
    <div className={styles.component}>
      <div className={styles.width} style={{ width: "55%" }}>
        <div className={styles.inner}></div>
      </div>
    </div>
  );
}

export default ProgressBar;
