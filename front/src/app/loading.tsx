import React from "react";

function Loading() {
  return (
    <div
      style={{
        display: "flex",
        justifyContent: "center",
        alignItems: "center",
        width: "100%",
        height: "100vh",
      }}
    >
      <iframe src="https://embed.lottiefiles.com/animation/102785" />
    </div>
  );
}

export default Loading;
