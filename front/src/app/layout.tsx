import "@/styles/globals.scss";
import "@/styles/tailwind.css";

import Header from "../components/Header";

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
      </body>
    </html>
  );
}
