import "@/styles/globals.scss";
import "@/styles/globals.css";

import Header from "../components/Header";
import { Suspense } from "react";
import Loading from "./dashboard/loading";

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
      <body>
        <Header />
        {/* <Suspense fallback={<Loading />}>{children}</Suspense> */}
        {children}
      </body>
    </html>
  );
}
