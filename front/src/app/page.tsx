"use client";

import Image from "next/image";
import React, { useState, useEffect, useRef, Suspense } from "react";
import { useSearchParams } from "next/navigation";
import styles from "./page.module.scss";
import grow from "../../public/static/services/service_grow.png";
import fight from "../../public/static/services/service_fight.png";
import card from "../../public/static/services/service_card.png";
import { Canvas, useLoader, useFrame } from "@react-three/fiber";
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader";
import { OrbitControls } from "@react-three/drei";
import * as THREE from "three";

const Model = () => {
  const [isClicked, SetIsClicked] = useState<boolean>(false);
  const gltf = useLoader(GLTFLoader, "/static/models/Penguin.glb");

  let mixer: THREE.AnimationMixer | undefined;

  if (gltf.animations.length) {
    mixer = new THREE.AnimationMixer(gltf.scene);
    mixer.timeScale = 0.4;
    if (isClicked) {
      const randomIndex = Math.floor(Math.random() * 39);
      const action = mixer.clipAction(gltf.animations[randomIndex]);
      action.play();
      setTimeout(() => {
        SetIsClicked(false);
      }, 1000);
    } else {
      const action = mixer.clipAction(gltf.animations[8]);
      action.clampWhenFinished = true;
      action.play();
    }
  }
  console.log(gltf.animations);

  useFrame((state, delta) => {
    mixer?.update(delta);
  });

  useFrame((state, delta) => {
    gltf.scene.rotation.y += delta * 0.05; // 회전 속도를 조절할 수 있습니다.
  });
  return (
    <primitive
      object={gltf.scene}
      scale={[5, 5, 5]}
      position={[2, -2, 0]}
      rotation={[0, -0.8, 0]}
      onClick={() => SetIsClicked(!isClicked)}
    />
  );
};

const Home = () => {
  const [scrollY, setScrollY] = useState<number>(0);
  const fpRef = useRef<HTMLImageElement>(null);
  const spRef = useRef<HTMLImageElement>(null);
  const tpRef = useRef<HTMLImageElement>(null);
  const params = useSearchParams();

  useEffect(() => {
    localStorage.setItem("accessToken", params.get("access-token") as string);
    localStorage.setItem("refreshToken", params.get("refresh-token") as string);
  }, []);

  useEffect(() => {
    const handleScroll = () => {
      setScrollY(window.scrollY);
    };

    window.addEventListener("scroll", handleScroll);

    return () => {
      window.removeEventListener("scroll", handleScroll);
    };
  }, []);

  useEffect(() => {
    if (fpRef.current && spRef.current && tpRef.current) {
      if (scrollY < 550) {
        fpRef.current.style.opacity = "0";
        spRef.current.style.opacity = "0";
        tpRef.current.style.opacity = "0";
      } else {
        fpRef.current.style.opacity = "1";
        spRef.current.style.opacity = "1";
        tpRef.current.style.opacity = "1";
      }
    }
  }, [scrollY]);

  return (
    <div className={styles.container}>
      <div className={styles.banner}>
        <div className={styles.left}>
          <h1 className={styles.title}>REPOMON</h1>
          <p className={styles.comment}>
            당신의 프로젝트에 함께할 레포몬은 무엇인가요?
          </p>
        </div>
        <div className={styles.right}>
          <Canvas className={styles.rightCanvas}>
            <ambientLight intensity={0.1} />
            <ambientLight intensity={0.1} />
            <directionalLight
              color="white"
              position={[0, 0, 5]}
              intensity={0.5}
            />
            <directionalLight
              color="white"
              position={[-5, 0, -5]}
              intensity={0.5}
            />
            <Model />
            <OrbitControls enableZoom={false} target={[1, 0, -1]} />
          </Canvas>
        </div>
      </div>
      <div className={styles.service}>
        <h2 className={styles["service-title"]}>
          <span>레포몬</span> 서비스 둘러보기
        </h2>

        <div className={styles["service-content"]}>
          <div className={styles["img-div"]} style={{ margin: "0 20px 0 0" }}>
            <Image
              alt="grow"
              src={grow}
              className={styles.fp}
              ref={fpRef}
            ></Image>
            <span>레포지토리 분석</span>
            <p className={styles.des} style={{ padding: "4rem 1rem" }}>
              당신의 Github 레포지토리와 함께 성장하는 레포몬을 육성해보세요.
              <br />
              분석 차트와 함께 당신의 프로젝트를 한눈에 시각화해서 나타내줘요.
            </p>
          </div>
          <div>
            <div className={styles["img-div"]} style={{ margin: "0 0 20px 0" }}>
              <Image
                alt="grow"
                src={fight}
                className={styles.sp}
                ref={spRef}
              ></Image>
              <span>배틀 및 랭킹</span>
              <p className={styles.des}>
                당신의 프로젝트가 얼마나 강한지 궁금하지 않나요?
                <br />
                성장시킨 레포몬으로 다른 유저와 배틀해보세요!
              </p>
            </div>
            <div className={styles["img-div"]}>
              <Image
                alt="grow"
                src={card}
                className={styles.tp}
                ref={tpRef}
              ></Image>
              <span>카드 추출</span>
              <p className={styles.des}>
                열심히 성장시킨 내 프로젝트를
                <br />
                README에서 실시간으로 자랑해보세요.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Home;
