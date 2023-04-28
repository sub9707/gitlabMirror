"use client";
import React, { useEffect, useRef, useState } from "react";
import styles from "./page.module.scss";

function page() {
  const logRef = useRef<HTMLDivElement>(null);
  const [logs, setLogs] = useState<string[]>([]);

  const addLog = (log: string) => {
    setLogs((prevLogs) => [...prevLogs, log]);
  };
  useEffect(() => {
    if (logRef.current) {
      logRef.current.scrollTop = logRef.current.scrollHeight;
    }
  }, [logs]);
  return (
    <div className={styles.pageContainer}>
      <p className={styles.headerTitle}>Becoding84 vs ddings 레포몬 배틀</p>
      <div className={styles.gameWrapper}>
        <div className={styles.gameBox}></div>
        <div className={styles.gameLogBox}>
          <button onClick={() => addLog(`Log ${logs.length + 1}`)}>
            Add Log
          </button>{" "}
          <div ref={logRef} style={{ height: "200px", overflowY: "auto" }}>
            {logs.map((log, index) => (
              <p key={index}>{log}</p>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}

export default page;
