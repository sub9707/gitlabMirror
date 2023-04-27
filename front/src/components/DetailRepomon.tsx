"use client";

import React, { useState, useEffect, useRef } from "react";
import * as THREE from "three";
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls";
import styles from "./DetailRepomon.module.scss";

function DetailRepomon() {
  const canvas = useRef<HTMLDivElement>(null);
  const [classRepoMon, setClassRepoMon] = useState<string>("");
  const [isRender, setIsRender] = useState<boolean>(false);

  useEffect(() => {
    setClassRepoMon("repo-mon");

    if (!classRepoMon || !canvas.current) {
      return;
    }

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

    function animate() {
      requestAnimationFrame(animate);
      controls.update();
      renderer.render(scene, camera);
    }

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

    animate();

    return () => {
      renderer.dispose();
      controls.dispose();
    };
  }, [classRepoMon, canvas]);

  return <div ref={canvas} className={styles[classRepoMon]} />;
}

export default DetailRepomon;
