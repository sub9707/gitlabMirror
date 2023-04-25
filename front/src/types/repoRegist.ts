// repo 등록 페이지 param
export type PageProps = {
  params: {
    userId: string;
    repoId: string;
  };
};

// 등록 페이지 깃 컨벤션 인터페이스
export interface Todo {
  id: number;
  title: string;
  description: string;
}
