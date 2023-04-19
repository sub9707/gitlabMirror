"use client";

import React, { useEffect, useRef } from "react";
import * as THREE from "three";
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls";
import styles from "./page.module.scss";

function Page({ params }: { params: { repoId: string } }) {
  const canvas = useRef<HTMLDivElement>(null);

  useEffect(() => {
    const scene = new THREE.Scene();
    const camera = new THREE.PerspectiveCamera(
      75,
      window.innerWidth / window.innerHeight,
      0.1,
      1000
    );

    const loader = new GLTFLoader();

    loader.load(
      "/cute_fox.glb",
      function (gltf) {
        scene.add(gltf.scene);
      },
      undefined,
      function (error) {
        console.error(error);
      }
    );

    const light = new THREE.PointLight(0xffffff, 5, 100);
    light.position.set(0, 0, 50);
    scene.add(light);

    const renderer = new THREE.WebGLRenderer();

    let controls: OrbitControls;

    if (canvas.current) {
      renderer.setSize(
        canvas.current.clientWidth,
        canvas.current.clientWidth / 2
      );

      canvas.current.appendChild(renderer.domElement);

      controls = new OrbitControls(camera, canvas.current);

      controls.rotateSpeed = 0.5; // 마우스로 카메라를 회전시킬 속도입니다. 기본값(Float)은 1입니다.
      controls.zoomSpeed = 1.2; // 마우스 휠로 카메라를 줌 시키는 속도 입니다. 기본값(Float)은 1입니다.
      controls.panSpeed = 0.8; // 패닝 속도 입니다. 기본값(Float)은 1입니다.
      controls.minDistance = 5; // 마우스 휠로 카메라 거리 조작시 최소 값. 기본값(Float)은 0 입니다.
      controls.maxDistance = 500; // 마우스 휠로 카메라 거리 조작시 최대 값. 기본값(Float)은 무제한 입니다.
    }

    // const geometry = new THREE.BoxGeometry(1, 1, 1);
    // const material = new THREE.MeshBasicMaterial({ color: 0x00ff00 });
    // const cube = new THREE.Mesh(geometry, material);
    // scene.add(cube);

    camera.position.z = 5;

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
          <div className={styles.exp}>asd</div>
        </div>
      </div>
    </div>
  );
}

export default Page;
