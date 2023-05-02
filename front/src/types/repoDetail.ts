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

export type GrowthFactorType = {
  [factor: string]: number;
};

export type HistoryType = {
  exp: number;
  type: string;
  workedAt: string;
};

export type RepoDetailResearchType = {
  growthFactor: GrowthFactorType;
  histories: HistoryType[];
  rank: number;
  repoExp: number;
  totalGetExp: number;
};

export type ExpAnaysisChartType = {
  labels: string[];
  datasets: {
    label: string;
    data: string[];
    backgroundColor: string;
    borderColor: string;
    pointBackgroundColor: string;
    borderWidth: number;
    color: string;
  }[];
};

export type DatasetType = {
  label: string;
  data: string[];
  borderColor: string;
  backgroundColor: string;
};

export type GrowthChartType = {
  labels: string[];
  datasets: DatasetType[];
};

type DetailRepomonType = {
  repomonId: number;
  repomonName: string;
  repomonSkillName: string;
  repomonSkillUrl: string;
  repomonTier: number;
  repomonUrl: string;
  selectRepomonList: {
    repomonId: number;
    repomonName: string;
    repomonUrl: string;
  }[];
};

export type RepoDetailBattleType = {
  atk: number;
  atkPoint: number;
  critical: number;
  criticalPoint: number;
  def: number;
  defPoint: number;
  dodge: number;
  dodgePoint: number;
  hit: number;
  hitPoint: number;
  hp: number;
  increaseAtk: number;
  increaseCritical: number;
  increaseDef: number;
  increaseDodge: number;
  increaseHit: number;
  loseCnt: number;
  rating: number;
  repoId: number;
  repoName: string;
  repoMon: DetailRepomonType;
  repomonNickname: string;
  repomonTier: number;
  repomonUrl: string;
  statPoint: number;
  winCnt: number;
};

export type BattleRecordType = {
  attackPoint: number;
  attackRepo: DetailRepomonType;
  defensePoint: number;
  defenseRepo: DetailRepomonType;
  isWin: boolean;
};
