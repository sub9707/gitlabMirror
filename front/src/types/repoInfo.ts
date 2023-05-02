export type UserInfoType = {
  avatarUrl: string;
  nickname: string;
  representRepo: string;
  totalExp: number;
  userId: number;
  username: string;
};

export type RepoListType = {
  repoListItems: RepoListItemType[];
  totalElements: number;
  totalPages: number;
};

export type RepoListItemType = {
  isActive: boolean;
  isPrivate: boolean;
  repoExp: number;
  repoId: number;
  repomonId: number;
  repoName: string;
  repomonName: string;
  repoDescription: string;
  repoRating: number;
};
