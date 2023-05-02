import "@/styles/globals.scss";
import "@/styles/tailwind.css";

import Header from "../components/Header";
import Footer from "@/components/Footer";
import { Suspense } from "react";
import Loading from "./loading";
import Providers from "@/redux/provider";

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
    <Providers>
      <html lang="ko">
        <body style={{ backgroundColor: "78acde" }}>
          <Header />
          <Suspense fallback={<Loading />}>
            <div style={{ marginTop: "90px" }}>{children}</div>
          </Suspense>
          <Footer />
        </body>
      </html>
    </Providers>
  );
}
