export type RepomonRankContentType = {
  rating: number;
  repoExp: number;
  repoId: number;
  repoKey: string;
  repoName: string;
  repoOwner: string;
  repomon: string;
  repomonNickname: string;
  repomonTier: number;
  username: string;
};

export type RepomonRankInfoType = {
  content: RepomonRankContentType[];
  empty: boolean;
  first: boolean;
  last: boolean;
  number: number;
  numberOfElements: number;
  pageable: {
    page: number;
    size: number;
  };
  size: number;
  sort: {
    empty: boolean;
    sorted: boolean;
    unsorted: boolean;
  };
  totalElements: number;
  totalPages: number;
};
