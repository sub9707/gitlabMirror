"use client";
import { useState, useEffect } from "react";
import { NextPage } from "next";
import styles from "./page.module.scss";
import Image from "next/image";

type PageProps = {
  params: {
    userId: string;
    repoId: string;
  };
};

const Page: NextPage<PageProps> = ({ params }) => {
  const [selected, setSelected] = useState<number | null>(null);
  const [mounted, setMounted] = useState<boolean>(false);

  const handleSelect = (index: number) => {
    if (selected === index) {
      setSelected(null);
    } else {
      setSelected(index);
    }
  };

  const images = [
    "/static/images/monEgg1.png",
    "/static/images/monEgg2.png",
    "/static/images/monEgg3.png",
  ];

  useEffect(() => {
    setMounted(true);
  }, []);

  if (!mounted) return null;

  return (
    <div className={styles.pageContainer}>
      <div className={styles.pageBox}>
        <div className={styles.monSelect}>
          <p style={{ color: "aliceblue", fontSize: "3rem" }}>함뽑아바라!</p>
          <div style={{ display: "flex", justifyContent: "center" }}>
            {images.map((src, index) => (
              <div
                key={index}
                className={`${styles.monFrame} ${
                  selected === index ? styles.selected : ""
                }`}
                onClick={() => handleSelect(index)}
              >
                <Image
                  src={src}
                  alt={`mon${index + 1}`}
                  width={500}
                  height={400}
                  className={styles.monEgg}
                />
              </div>
            ))}
          </div>
        </div>
        <div style={{ display: "flex", justifyContent: "space-around" }}>
          <div className={styles.leftBox} />
          <div className={styles.rigthBox} />
        </div>
      </div>
    </div>
  );
};

export default Page;
