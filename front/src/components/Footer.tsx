import React from "react";
import styles from "./Footer.module.scss";

function Footer() {
  return (
    <div className={styles.footerContainer}>
      <p className={styles.footerLogo}>RepoMon</p>
      <p className={styles.footerText}>Copyright ©로켓단 All right Reserved.</p>
    </div>
  );
}

export default Footer;
