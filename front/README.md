Feat: 새로운 기능 추가
Fix: 버그 수정 또는 typo
Refactor: 리팩토링
Design: CSS 등 사용자 UI 디자인 변경
Comment: 필요한 주석 추가 및 변경
Style: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
Test: 테스트(테스트 코드 추가, 수정, 삭제, 비즈니스 로직에 변경이 없는 경우)
Chore: 위에 걸리지 않는 기타 변경사항(빌드 스크립트 수정, assets image, 패키지 매니저 등)
Docs: 문서를 수정한 경우
Rename: 파일 혹은 폴더명 수정하거나 옮기는 경우
Remove: 파일을 삭제하는 작업만 수행하는 경우
CleanUp: 사소한 수정

```
front
├─ .eslintrc.json
├─ .gitignore
├─ .gitkeep
├─ .vscode
│  └─ settings.json
├─ config
├─ data
│  └─ gitTips.json
├─ deploy-front.sh
├─ Dockerfile
├─ next.config.js
├─ package-lock.json
├─ package.json
├─ postcss.config.js
├─ public
│  ├─ cute_fox.glb
│  ├─ favicon.ico
│  ├─ git_cat.svg
│  ├─ logo-side.png
│  ├─ logo.png
│  ├─ manifest.json
│  ├─ next.svg
│  ├─ sidebar.html
│  ├─ sidebar.js
│  ├─ static
│  │  ├─ effects
│  │  │  ├─ explosion.gif
│  │  │  ├─ hit1.gif
│  │  │  ├─ hit2.gif
│  │  │  └─ jump.gif
│  │  ├─ icons
│  │  │  ├─ attack_icon.png
│  │  │  ├─ avoid_icon.png
│  │  │  ├─ bulb_icon.svg
│  │  │  ├─ critical_icon.png
│  │  │  ├─ fork.png
│  │  │  ├─ heart_icon.png
│  │  │  ├─ hit_icon.png
│  │  │  ├─ shield_icon.png
│  │  │  ├─ star.png
│  │  │  ├─ sword_icon.png
│  │  │  └─ versus.png
│  │  ├─ images
│  │  │  ├─ banner_02.png
│  │  │  ├─ banner_03.png
│  │  │  ├─ computer-mouse.png
│  │  │  ├─ default_repomon.png
│  │  │  ├─ dice.png
│  │  │  ├─ docs_extension_example.png
│  │  │  ├─ docs_extension_key.png
│  │  │  ├─ docs_repomon_create_01.png
│  │  │  ├─ docs_repo_active.png
│  │  │  ├─ docs_repo_reload.png
│  │  │  ├─ forestBack.jpg
│  │  │  ├─ github.png
│  │  │  ├─ paper.jpg
│  │  │  ├─ pokeball.png
│  │  │  ├─ reveal
│  │  │  │  └─ tri1.png
│  │  │  ├─ sky.jpg
│  │  │  └─ trophy.png
│  │  ├─ lotties
│  │  │  ├─ balloon.json
│  │  │  ├─ battle.json
│  │  │  ├─ card.json
│  │  │  ├─ ghost1.json
│  │  │  ├─ loadingEgg.json
│  │  │  ├─ not_found.json
│  │  │  ├─ not_found_commit.json
│  │  │  ├─ panpare.json
│  │  │  └─ ranking.json
│  │  ├─ models
│  │  │  ├─ Chick_1.glb
│  │  │  ├─ grass.glb
│  │  │  ├─ nature.glb
│  │  │  ├─ Particle.glb
│  │  │  ├─ Penguin.glb
│  │  │  ├─ tempLoader.glb
│  │  │  └─ Tuna_3.glb
│  │  ├─ models_png
│  │  │  ├─ Cheetah_1.png
│  │  │  ├─ Cheetah_2.png
│  │  │  ├─ Cheetah_3.png
│  │  │  ├─ Chick_1.png
│  │  │  ├─ Chick_2.png
│  │  │  ├─ Chick_3.png
│  │  │  ├─ Dingo_1.png
│  │  │  ├─ Dingo_2.png
│  │  │  ├─ Dingo_3.png
│  │  │  ├─ Dora_1.png
│  │  │  ├─ Dora_2.png
│  │  │  ├─ Dora_3.png
│  │  │  ├─ Egg.png
│  │  │  ├─ Ker_1.png
│  │  │  ├─ Ker_2.png
│  │  │  ├─ Ker_3.png
│  │  │  ├─ Mir_1.png
│  │  │  ├─ Mir_2.png
│  │  │  ├─ Mir_3.png
│  │  │  ├─ Pony_1.png
│  │  │  ├─ Pony_2.png
│  │  │  ├─ Pony_3.png
│  │  │  ├─ Tails_1.png
│  │  │  ├─ Tails_2.png
│  │  │  ├─ Tails_3.png
│  │  │  ├─ Tuna_1.png
│  │  │  ├─ Tuna_2.png
│  │  │  ├─ Tuna_3.png
│  │  │  ├─ Whale_1.png
│  │  │  ├─ Whale_2.png
│  │  │  └─ Whale_3.png
│  │  ├─ rank
│  │  │  ├─ 1.png
│  │  │  ├─ 2.png
│  │  │  └─ 3.png
│  │  ├─ services
│  │  │  ├─ service_card.png
│  │  │  ├─ service_fight.png
│  │  │  └─ service_grow.png
│  │  ├─ sound
│  │  │  ├─ battle.mp3
│  │  │  └─ Dice.mp3
│  │  └─ tier
│  │     ├─ bronze.svg
│  │     ├─ diamond.svg
│  │     ├─ gold.svg
│  │     ├─ platinum.svg
│  │     ├─ silver.svg
│  │     └─ tiers.png
│  ├─ steppe_grass.glb
│  └─ vercel.svg
├─ README.md
├─ src
│  ├─ api
│  │  ├─ auth.ts
│  │  ├─ axios.ts
│  │  ├─ modelLoader.ts
│  │  ├─ rank.ts
│  │  ├─ repoBattle.ts
│  │  ├─ repoDetail.ts
│  │  └─ userRepo.ts
│  ├─ app
│  │  ├─ dashboard
│  │  │  └─ gitData.ts
│  │  ├─ docs
│  │  │  ├─ page.module.scss
│  │  │  └─ page.tsx
│  │  ├─ favicon.ico
│  │  ├─ layout.module.scss
│  │  ├─ layout.tsx
│  │  ├─ loading.tsx
│  │  ├─ loading2.tsx
│  │  ├─ not-found.tsx
│  │  ├─ page.module.scss
│  │  ├─ page.tsx
│  │  ├─ rank
│  │  │  ├─ page.module.scss
│  │  │  └─ page.tsx
│  │  ├─ repo
│  │  │  └─ [repoId]
│  │  │     ├─ Nature.tsx
│  │  │     ├─ page.module.scss
│  │  │     ├─ page.tsx
│  │  │     ├─ registRepo
│  │  │     │  ├─ Model1.tsx
│  │  │     │  ├─ Model2.tsx
│  │  │     │  ├─ Model3.tsx
│  │  │     │  ├─ ModelSel.tsx
│  │  │     │  ├─ page.module.scss
│  │  │     │  ├─ page.tsx
│  │  │     │  └─ StatsChart.tsx
│  │  │     └─ repoBattle
│  │  │        ├─ Grass.tsx
│  │  │        ├─ ShadowCircle.tsx
│  │  │        └─ [oppoId]
│  │  │           ├─ page.module.scss
│  │  │           └─ page.tsx
│  │  ├─ store
│  │  │  └─ features
│  │  ├─ user
│  │  │  └─ [userId]
│  │  │     ├─ page.module.scss
│  │  │     └─ page.tsx
│  │  └─ utils
│  │     ├─ CustomAlert.ts
│  │     ├─ DateFormat.ts
│  │     ├─ PretreatDate.ts
│  │     └─ PretreatModelUrl.ts
│  ├─ components
│  │  ├─ Banner
│  │  │  ├─ banner.module.scss
│  │  │  ├─ Banner01.tsx
│  │  │  ├─ Banner02.tsx
│  │  │  └─ Banner03.tsx
│  │  ├─ Button_NO.tsx
│  │  ├─ Button_OK.tsx
│  │  ├─ Detail
│  │  │  ├─ ContributionChart.tsx
│  │  │  ├─ ConventionChart.module.scss
│  │  │  ├─ ConventionChart.tsx
│  │  │  ├─ ConventionEdit.module.scss
│  │  │  ├─ ConventionEdit.tsx
│  │  │  ├─ DatePickerModal.module.scss
│  │  │  ├─ DatePickerModal.tsx
│  │  │  ├─ DetailAnalysis.module.scss
│  │  │  ├─ DetailAnalysis.tsx
│  │  │  ├─ DetailBattle.module.scss
│  │  │  ├─ DetailBattle.tsx
│  │  │  ├─ DetailContribution.module.scss
│  │  │  ├─ DetailContribution.tsx
│  │  │  ├─ DetailConvention.module.scss
│  │  │  ├─ DetailConvention.tsx
│  │  │  ├─ DetailRepomon.tsx
│  │  │  ├─ ExpAnalysis.module.scss
│  │  │  ├─ ExpAnalysis.tsx
│  │  │  ├─ ExportModal.module.scss
│  │  │  ├─ ExportModal.tsx
│  │  │  ├─ GrowthChart.module.scss
│  │  │  ├─ GrowthChart.tsx
│  │  │  ├─ MatchModal.module.scss
│  │  │  ├─ MatchModal.tsx
│  │  │  ├─ ProgressBar.module.scss
│  │  │  ├─ ProgressBar.tsx
│  │  │  ├─ RenameModal.module.scss
│  │  │  └─ RenameModal.tsx
│  │  ├─ DropDown.tsx
│  │  ├─ Footer.module.scss
│  │  ├─ Footer.tsx
│  │  ├─ GitTable.tsx
│  │  ├─ Header.module.scss
│  │  ├─ Header.tsx
│  │  ├─ HpBar.tsx
│  │  ├─ Rank
│  │  │  ├─ BattleRank.module.scss
│  │  │  ├─ BattleRank.tsx
│  │  │  ├─ RepomonRank.module.scss
│  │  │  ├─ RepomonRank.tsx
│  │  │  ├─ UserRank.module.scss
│  │  │  └─ UserRank.tsx
│  │  ├─ RepositoryCard.module.scss
│  │  ├─ RepositoryCard.tsx
│  │  ├─ Skeletons
│  │  │  ├─ CardSkeleton.tsx
│  │  │  └─ LoadingSpinner.tsx
│  │  ├─ Spinner.tsx
│  │  ├─ threeLoader.tsx
│  │  ├─ UI
│  │  │  ├─ ArrowDown.tsx
│  │  │  ├─ Badge.tsx
│  │  │  ├─ BadgeChall.tsx
│  │  │  ├─ ErrorModel.tsx
│  │  │  ├─ InputField.tsx
│  │  │  ├─ Pagination.scss
│  │  │  ├─ Pagination.tsx
│  │  │  ├─ SkipArrow.tsx
│  │  │  ├─ SoundOff.tsx
│  │  │  └─ SoundOn.tsx
│  │  ├─ UserExportModal.module.scss
│  │  └─ UserExportModal.tsx
│  ├─ redux
│  │  ├─ features
│  │  │  └─ authSlice.ts
│  │  ├─ hooks.ts
│  │  ├─ provider.tsx
│  │  └─ store.ts
│  ├─ styles
│  │  ├─ colors.ts
│  │  ├─ globals.scss
│  │  ├─ mixin.scss
│  │  ├─ reset.css
│  │  ├─ speechBubble.scss
│  │  ├─ tailwind.css
│  │  └─ variables.scss
│  └─ types
│     ├─ rank.ts
│     ├─ repoBattle.ts
│     ├─ repoDetail.ts
│     ├─ repoInfo.ts
│     ├─ repomons.ts
│     └─ repoRegist.ts
├─ tailwind.config.js
└─ tsconfig.json

```