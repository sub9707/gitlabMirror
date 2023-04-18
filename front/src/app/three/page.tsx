"use client";

import React, { useRef } from "react";
import { useEffect } from "react";
import * as THREE from "three";
import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls";

function Page() {
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
      "/cute_fox.fbx",
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
    renderer.setSize(window.innerWidth, window.innerHeight);

    let controls: OrbitControls;

    if (canvas.current) {
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

  return <div ref={canvas}></div>;
}

export default Page;
