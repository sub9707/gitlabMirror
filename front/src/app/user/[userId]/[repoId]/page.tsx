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

    const light = new THREE.PointLight(0xffffff, 4.5, 100);
    light.position.set(0, 0, 50);
    scene.add(light);

    const renderer = new THREE.WebGLRenderer();
    renderer.setClearColor(0xffffff, 1);

    let camera: THREE.PerspectiveCamera;
    let controls: OrbitControls;

    if (canvas.current) {
      camera = new THREE.PerspectiveCamera(
        75,
        canvas.current.clientWidth / canvas.current.clientHeight,
        0.1,
        1000
      );

      camera.position.z = 5;

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
            <div className="w-full bg-gray-200 rounded-full h-2.5 dark:bg-gray-700">
              <div
                className="bg-blue-600 h-2.5 rounded-full"
                style={{ width: "45%" }}
              ></div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Page;
