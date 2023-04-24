"use client";

import React, { useState, useEffect, useRef } from "react";
import * as THREE from "three";
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls";
import ProgressBar from "components/ProgressBar";
import styles from "./page.module.scss";
import { StarIcon, ShareIcon } from "@heroicons/react/24/outline";
import GrowthElementChart from "components/GrowthElementChart";
import GrowthChart from "components/GrowthChart";

function Page({ params }: { params: { repoId: string } }) {
  const canvas = useRef<HTMLDivElement>(null);
  const [scene, setScene] = useState<THREE.Scene | null>(null);
  const [repomonLoader, setRepomonLoader] = useState<GLTFLoader | null>(null);
  const [grassLoader, setGrassLoader] = useState<GLTFLoader | null>(null);
  const [light1, setLight1] = useState<THREE.PointLight | null>(null);
  const [light2, setLight2] = useState<THREE.PointLight | null>(null);
  const [renderer, setRenderer] = useState<THREE.WebGLRenderer | null>(null);
  const [camera, setCamera] = useState<THREE.PerspectiveCamera | null>(null);
  const [controls, setControls] = useState<OrbitControls | null>(null);

  useEffect(() => {
    if (!canvas.current) {
      return;
    }

    setScene(new THREE.Scene());
    setRepomonLoader(new GLTFLoader());
    setGrassLoader(new GLTFLoader());
    setLight1(new THREE.PointLight(0xffffff, 4.2, 100));
    setLight2(new THREE.PointLight(0xffffff, 3, 100));
    setRenderer(new THREE.WebGLRenderer());
    setCamera(
      new THREE.PerspectiveCamera(
        80,
        canvas.current.clientWidth / canvas.current.clientHeight
      )
    );
  }, [canvas]);

  useEffect(() => {
    if (canvas.current && camera) {
      setControls(new OrbitControls(camera, canvas.current));
    }
  }, [camera]);

  useEffect(() => {
    if (
      !(
        canvas.current &&
        scene &&
        repomonLoader &&
        grassLoader &&
        light1 &&
        light2 &&
        renderer &&
        camera &&
        controls
      )
    ) {
      return;
    }

    repomonLoader.load(
      "/cute_fox.glb",
      function (gltf) {
        gltf.scene.position.set(0, 0, 0);
        scene.add(gltf.scene);
      },
      undefined,
      function (error) {
        console.error(error);
      }
    );

    grassLoader.load(
      "/steppe_grass.glb",
      function (gltf) {
        gltf.scene.position.set(0, -2.35, 0);
        scene.add(gltf.scene);
      },
      undefined,
      function (error) {
        console.error(error);
      }
    );

    light1.position.set(20, 20, 40);
    scene.add(light1);

    light2.position.set(-20, 20, -40);
    scene.add(light2);

    renderer.setClearColor(0xffffff, 1);

    camera.position.y = 0.5;
    camera.position.z = 1;

    renderer.setSize(canvas.current.clientWidth, canvas.current.clientHeight);

    canvas.current.appendChild(renderer.domElement);

    controls.rotateSpeed = 0.5;
    controls.zoomSpeed = 1.2;
    controls.panSpeed = 0.8;
    controls.minDistance = 5;
    controls.maxDistance = 500;

    function animate() {
      requestAnimationFrame(animate);
      if (scene && renderer && camera && controls) {
        controls.update();
        renderer.render(scene, camera);
      }
    }

    animate();
  }, [
    canvas,
    scene,
    repomonLoader,
    grassLoader,
    light1,
    light2,
    renderer,
    camera,
    controls,
  ]);

  return (
    <div className={styles.pageContainer}>
      <div className={styles.info}>
        <div className={styles["repo-mon-div"]}>
          <div ref={canvas} className={styles["repo-mon"]}></div>
          <div className={styles.exp}>
            <p>폭스</p>
            <ProgressBar />
          </div>
        </div>
        <div className={styles["default-info-div"]}>
          <div className={styles.first}>
            <span className={styles.title}>Repo Name</span>
            <div className={styles["icon-div"]}>
              <span className={styles.star}>
                <StarIcon
                  width="1.25rem"
                  style={{ display: "inline", marginRight: "0.2rem" }}
                />
                12
              </span>
              <span className={styles.share}>
                <ShareIcon
                  width="1.25rem"
                  style={{ display: "inline", marginRight: "0.2rem" }}
                />
                15
              </span>
            </div>
          </div>
          <p className={styles.date}>23.02.20 ~ 23.04.07</p>
          <div className={styles["lan-div"]}>
            <span>TypeScript</span>
            <span>Java</span>
            <span>Python</span>
            <span>TypeScript</span>
            <span>Java</span>
            <span>Python</span>
            <span>TypeScript</span>
            <span>Java</span>
            <span>Python</span>
          </div>
          <div className={styles["tag-div"]}>
            <span>KakaoAPI</span>
            <span>GithubAPI</span>
            <span>NextJS</span>
            <span>ThreeJS</span>
            <span>SpringBoot</span>
            <span>KakaoAPI</span>
            <span>GithubAPI</span>
            <span>NextJS</span>
            <span>ThreeJS</span>
            <span>SpringBoot</span>
          </div>
          <p className={styles.des}>
            Repo Description Repo Description Repo Description Repo Description
            Repo Description Repo Description
          </p>
          <div className={styles["growth-element-chart-div"]}>
            <p className={styles["growth-element-title"]}>Growth Element</p>
            <GrowthElementChart />
          </div>
        </div>
      </div>
      <div className={styles["extra-div"]}>
        <div className={styles["growth-chart-div"]}>
          <GrowthChart />
        </div>
      </div>
    </div>
  );
}

export default Page;
