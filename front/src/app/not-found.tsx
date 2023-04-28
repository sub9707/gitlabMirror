import Link from "next/link";
import React, { useState } from "react";

function _404() {
  return (
    <div
      style={{
        display: "flex",
        flexDirection: "column",
        alignItems: "center",
        marginTop: "5rem",
      }}
    >
      <h1 style={{ fontSize: "5rem", marginBottom: "2rem" }}>404</h1>
      <p style={{ fontSize: "1.5rem", marginBottom: "2rem" }}>
        페이지를 찾을 수 없습니다.
      </p>
      <Link href="/" style={{ fontSize: "1.5rem", marginTop: "2rem" }}>
        홈으로 돌아가기
      </Link>
    </div>
  );
}

export default _404;
