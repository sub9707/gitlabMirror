export type RepoDetailType = {
  forkCnt: number;
  languages: string[];
  repoEnd: string;
  repoExp: number;
  repoName: string;
  repoDescription: string;
  repoStart: string;
  repomonId: number;
  repomonName: string;
  starCnt: number;
  tags: string[];
};

export type RepoDetailResearchType = {
  growthFactor: {
    [factor: string]: number;
  };
  histories: {
    exp: number;
    type: string;
    workedAt: string;
  }[];
  rank: number;
  repoExp: number;
  totalGetExp: number;
};
