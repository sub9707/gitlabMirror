"use client";

import { requestMatchResult } from "@/api/repoBattle";
import Spinner from "@/components/Spinner";
import { BattleResultResponseDataType } from "@/types/repoBattle";
import { Canvas, useFrame, useLoader } from "@react-three/fiber";
import React, { Suspense, useEffect, useRef, useState } from "react";
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader";
import styles from "./page.module.scss";
import * as THREE from "three";
import { Html, OrbitControls } from "@react-three/drei";
import Loader from "@/components/threeLoader";
import HpBar from "@/components/HpBar";

const Page = () => {
  const [loadData, setLoadData] = useState<boolean>(false);
  const [matchData, setMatchData] = useState<BattleResultResponseDataType>();
  const opHp = matchData?.data.defenseRepo.hp;
  const myHp = matchData?.data.attackRepo.hp;

  // 내 레포몬 출력
  const Model = () => {
    const gltf = useLoader(GLTFLoader, "/static/models/Penguin.glb");

    let mixer: THREE.AnimationMixer | undefined;

    if (gltf.animations.length) {
      mixer = new THREE.AnimationMixer(gltf.scene);
      mixer.timeScale = 0.4;
      if (doMyAttack) {
        const action = mixer.clipAction(gltf.animations[11]);
        action.play();
      } else {
        const action = mixer.clipAction(gltf.animations[8]);
        action.clampWhenFinished = true;
        action.play();
      }
    }

    useFrame((state, delta) => {
      mixer?.update(delta);
    });

    return (
      <primitive
        object={gltf.scene}
        scale={[9, 9, 9]}
        position={[-4, -6, 0]}
        rotation={[0, 15, 0]}
      />
    );
  };

  // 상대 레포몬 출력
  const SecondModel = () => {
    const gltf = useLoader(GLTFLoader, "/static/models/Chick_1.glb");

    let mixer: THREE.AnimationMixer | undefined;

    console.log("animations: ", gltf.animations);
    if (gltf.animations.length) {
      mixer = new THREE.AnimationMixer(gltf.scene);
      mixer.timeScale = 0.4;
      if (doAttack) {
        const action = mixer.clipAction(gltf.animations[11]);
        action.setLoop(THREE.LoopOnce, 0);
        action.play();
      } else {
        const action = mixer.clipAction(gltf.animations[8]);
        action.clampWhenFinished = true;
        action.play();
      }
    }

    useFrame((state, delta) => {
      mixer?.update(delta);
    });

    return (
      <primitive
        object={gltf.scene}
        scale={[3, 3, 3]}
        position={[5, 0, 0]}
        rotation={[0, 5, 0]}
      />
    );
  };

  const MyUI = () => {
    return (
      <Html style={{ position: "relative", zIndex: 0 }}>
        <div
          style={{
            position: "absolute",
            top: "70px",
            left: "10px",
            width: "550px",
            height: "150px",
            fontSize: "24px",
            color: "black",
            backgroundColor: "white",
            border: "10px double black",
            padding: "20px",
            fontFamily: "NeoDunggeunmo",
          }}
        >
          내 레포명: {matchData?.data.attackRepo.repomonNickname} <br />
          HP : {myHp}
          <HpBar
            HP={Number(myHp)}
            maxHP={Number(matchData?.data.attackRepo.hp)}
          />
        </div>
      </Html>
    );
  };
  const OpUI = () => {
    return (
      <Html style={{ position: "relative" }}>
        <div
          style={{
            position: "absolute",
            bottom: "70px",
            right: "10px",
            width: "550px",
            height: "150px",
            fontSize: "24px",
            color: "black",
            backgroundColor: "white",
            border: "10px double black",
            padding: "20px",
            fontFamily: "NeoDunggeunmo",
          }}
        >
          상대 레포명: {matchData?.data.defenseRepo.repomonNickname} <br />
          HP : {opHp}
          <HpBar
            HP={Number(opHp)}
            maxHP={Number(matchData?.data.defenseRepo.hp)}
          />
        </div>
      </Html>
    );
  };

  // 애니메이션 제어 테스트
  const [doMyAttack, setDoMyAttack] = useState<boolean>(false);
  const [doAttack, setDoAttack] = useState<boolean>(false);

  function getMatchResult() {
    // oppo - my
    return requestMatchResult(1, 4);
  }

  useEffect(() => {
    getMatchResult()
      .then((response) => {
        const data = response.data;
        setMatchData(data);
        setLoadData(true);
        console.log(data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  return (
    <>
      <div className={styles.pageContainer}>
        <p className={styles.headerTitle}>
          <strong>{matchData?.data.attackRepo.repomonNickname}</strong>
          &nbsp;vs&nbsp;
          <strong>{matchData?.data.defenseRepo.repomonNickname}</strong> 레포몬
          배틀
        </p>
        <div className={styles.gameWrapper}>
          <div className={styles.gameBox}>
            <Canvas
              style={{
                backgroundImage: `url('/static/images/forestBack.jpg')`,
                position: "relative",
              }}
            >
              <Suspense fallback={<Loader />}>
                <ambientLight intensity={0.1} />
                <directionalLight
                  color="white"
                  position={[0, 0, 5]}
                  intensity={0.5}
                />
                <Model />
                <SecondModel />
                <MyUI />
                <OpUI />
              </Suspense>
            </Canvas>
          </div>
          <div className={styles.gameLogBox}>
            <div style={{ height: "200px", overflowY: "auto" }}>
              {loadData ? (
                <>
                  <p>done...!</p>
                  {matchData?.data.battleLog.map((resData, index, array) => {
                    return (
                      <React.Fragment key={resData.turn}>
                        {index === 0 && (
                          <>
                            <p>&lt;system&gt;{resData.attacker}의 선공</p>
                            <p>
                              &lt;system&gt;
                              {matchData?.data.attackRepo.repomonNickname}의
                              시작 체력: {myHp}
                            </p>
                            <p>
                              &lt;system&gt;
                              {matchData?.data.defenseRepo.repomonNickname}의
                              시작 체력: {opHp}
                            </p>
                            <br />
                          </>
                        )}

                        <p key={resData.turn}>{resData.turn}번째 턴!</p>
                        <p>
                          플레이어 {resData.attacker}의{" "}
                          {resData.attack_act === 1
                            ? "무지성"
                            : resData.attack_act === 2
                            ? "나름 치명적인"
                            : matchData.data.attackRepo.repomon
                                .repomonSkillName}{" "}
                          공격!
                        </p>
                        <p>
                          개쳐맞던 플레이어 {resData.defender}는{" "}
                          {resData.defense_act}를 취하고 {resData.damage}의
                          데미지를 입었다..!
                        </p>
                        <br />
                        {index === array.length - 1 && (
                          <>
                            {matchData.resultCode === "SUCCESS" ? (
                              <p>승리..!</p>
                            ) : (
                              <p>개같이 패배...!</p>
                            )}
                            <p>&lt;system&gt;배틀 종료...</p>
                          </>
                        )}
                      </React.Fragment>
                    );
                  })}
                </>
              ) : (
                <Spinner />
              )}
            </div>
          </div>
        </div>
      </div>
    </>
  );
};

export default Page;
