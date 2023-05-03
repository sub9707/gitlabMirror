import React from "react";
import { gitTipData } from "./dashboard/gitData";

function Loading() {
  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        flexDirection: "column",
        width: "100%",
        height: "100vh",
      }}
    >
      <iframe src="https://embed.lottiefiles.com/animation/102785" />
      <p style={{ fontSize: "1.2em", marginTop: "1em", fontWeight: "700" }}>
        RepoMon~
      </p>
    </div>
  );
}

export default Loading;
