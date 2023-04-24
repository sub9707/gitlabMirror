"use client";

import React, { useState, useEffect, useRef } from "react";
import * as THREE from "three";
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls";
import ProgressBar from "components/ProgressBar";
import styles from "./page.module.scss";
import { StarIcon, ShareIcon } from "@heroicons/react/24/outline";
import RadarChart from "components/RadarChart";

function Page({ params }: { params: { repoId: string } }) {
  const canvas = useRef<HTMLDivElement>(null);
  const [scene, setScene] = useState<THREE.Scene | null>(null);

  useEffect(() => {
    const scene = new THREE.Scene();

    const loader1 = new GLTFLoader();

    loader1.load(
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

    const loader2 = new GLTFLoader();

    loader2.load(
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

    const light = new THREE.PointLight(0xffffff, 4.2, 100);
    light.position.set(20, 20, 40);
    scene.add(light);

    const light2 = new THREE.PointLight(0xffffff, 3, 100);
    light2.position.set(-20, 20, -40);
    scene.add(light2);

    const renderer = new THREE.WebGLRenderer();
    renderer.setClearColor(0xffffff, 1);

    let camera: THREE.PerspectiveCamera;
    let controls: OrbitControls;

    if (canvas.current) {
      camera = new THREE.PerspectiveCamera(
        80,
        canvas.current.clientWidth / canvas.current.clientHeight
      );

      camera.position.y = 0.5;
      camera.position.z = 1;

      renderer.setSize(canvas.current.clientWidth, canvas.current.clientHeight);

      canvas.current.appendChild(renderer.domElement);

      controls = new OrbitControls(camera, canvas.current);

      controls.rotateSpeed = 0.5;
      controls.zoomSpeed = 1.2;
      controls.panSpeed = 0.8;
      controls.minDistance = 5;
      controls.maxDistance = 500;
    }

    function animate() {
      requestAnimationFrame(animate);
      controls.update();
      renderer.render(scene, camera);
    }

    animate();
  }, []);

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
            <RadarChart />
          </div>
        </div>
      </div>
      <div>asd</div>
    </div>
  );
}

export default Page;
