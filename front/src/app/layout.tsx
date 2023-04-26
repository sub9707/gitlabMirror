import "@/styles/globals.scss";
import "@/styles/globals.css";

import Header from "../components/Header";
import { Suspense } from "react";
import Loading from "./dashboard/loading";
import Footer from "@/components/Footer";

export const metadata = {
  title: "Repomon",
  description: "깃으로 키우는 Repomon 프로젝트",
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="ko">
      <body style={{ backgroundColor: "78acde" }}>
        <Header />
        <div style={{ marginTop: "90px" }}>{children}</div>
        <Footer />
      </body>
    </html>
  );
}
