"use client";

import { Canvas, useFrame, useLoader } from "@react-three/fiber";
import React, { Suspense, useEffect, useRef, useState } from "react";
import {
  Stats,
  OrbitControls,
  Box,
  Sky,
  useFBX,
  useTexture,
} from "@react-three/drei";
import * as THREE from "three";
import { MTLLoader } from "three/examples/jsm/loaders/MTLLoader";

function Scene() {
  // const materials = useLoader(MTLLoader, "/static/models/M_Penguin.mat");
  // materials.preload();
  // const fbx = useFBX("/static/models/Penguin.fbx");
  const fbx = useFBX("/tecx.fbx");
  const texture_1 = useLoader(THREE.TextureLoader, "/Tex_Cat_Carrot.jpg");
  fbx.scale.set(0.006, 0.006, 0.006);

  return (
    <primitive object={fbx}>
      <mesh>
        <meshStandardMaterial map={texture_1} attach="material" />
      </mesh>
    </primitive>
  );
}

const Home = () => {
  const ref = useRef<any>(null);
  return (
    <div style={{ height: "80vh", width: "80vw" }}>
      <Canvas camera={{ position: [3, 3, 2] }}>
        <Suspense fallback={null}>
          <Scene />
          <Sky />
          <ambientLight />
          <OrbitControls />
        </Suspense>
      </Canvas>
    </div>
  );
};

export default Home;
