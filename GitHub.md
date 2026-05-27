# Git & GitHub 学习笔记

> 📺 **参考课程**：
> - **技术爬爬虾**《小白玩转Github/Git，全功能精讲》— [B站课堂](https://www.bilibili.com/cheese/play/ep1104982/)（35课时付费课程）
> - 免费视频合集：B站搜索"技术爬爬虾" Git/GitHub 相关视频
> - 文字版笔记：[Git和GitHub是啥？咋用？一文吃透核心概念+AI实操](https://blog.csdn.net/2601_96073073/article/details/161258079)
> - 资源汇总：https://github.com/tech-shrimp/me
>
> **核心理念（得意忘言）**：掌握了核心概念，具体命令不需要死记硬背。AI 时代，用自然语言指挥 AI 完成 Git 操作效率更高——但前提是你要理解 Git 的分区模型、分支、合并等核心概念。

releases（在 GitHub 的项目链接后添加 /releases 可以选择版本下载，例如：https://github.com/chidiwilliams/buzz/releases）

## 一句话先懂全局

**Git** 是用来管理代码“历史版本”的工具，  
**GitHub** 是用来把 Git 仓库放到网上，方便备份、分享、协作的平台。

你可以把它理解成：

- **Git**：你电脑里的“版本记录本”
- **GitHub**：放在网上的“共享仓库中心”

---

### Git 仓库是什么

如果一个文件夹被 Git 管理了，它就变成了一个 **Git 仓库**。

仓库里会多一个隐藏文件夹：

- `.git`

这个文件夹里存着 Git 的所有管理信息。  
**删掉它，这个文件夹就不再是 Git 仓库了。**

---

### Commit 是什么

**Commit = 提交**  
它是 Git 保存版本的基本单位。

每次 commit，Git 都会保存：

- 当时所有文件的状态
- 当前项目的一个快照

所以 commit 越多，历史就越完整。  
你可以理解成：

> 每次 commit，就是给项目拍一张“快照照片”。

---

### 本地仓库和远端仓库

Git 仓库分两种：

#### 1）本地仓库
在你自己电脑上的仓库。

#### 2）远端仓库
在服务器上的仓库，比如 GitHub 上的仓库。

远端仓库的作用：

- 备份代码
- 分享代码
- 多人协作

---

### GitHub 是什么

GitHub 是一个提供 **远端 Git 仓库托管** 的网站。

它的作用是：

- 存储仓库
- 分享代码
- 团队协作
- 开源项目贡献

GitHub 上有两类仓库：

- **公开仓库**：别人都能看到
- **私有仓库**：只有授权的人能看到

很多开源项目都托管在 GitHub 上，比如：

- Linux
- Python 相关项目
- Nginx 等

---

### 为什么要让 AI 帮忙绑定 Git 和 GitHub

现在很多 AI 工具已经能直接帮你：

- 初始化仓库
- 创建分支
- commit
- push 到 GitHub
- 合并分支

所以文档强调：

> AI 时代，你不一定要死记硬背命令，理解概念更重要。

---

## 3. Git 最基础的几个概念

### 3.1 `git init`：初始化仓库

意思是：

> 把一个普通文件夹变成 Git 仓库。

在 VS Code 里通常是：

- 打开文件夹
- 点击 Initialize Repository

初始化后会生成 `.git` 文件夹。

---

### 3.2 `.gitignore`：忽略某些文件

`.gitignore` 是一个文件，用来告诉 Git：

> 哪些文件不要管，不要提交。

常见要忽略的文件：

- `.env`：存密钥、配置的文件
- `node_modules/`：前端/Node 项目依赖目录

为什么要忽略？

- 这些文件不适合提交到仓库
- 有些文件很大
- 有些文件是自动生成的
- 有些文件含密钥，不能上传到 GitHub

---

### 3.3 Commit：提交

commit 的作用是：

- 记录一个版本
- 保存当前项目状态
- 形成历史链条

建议：

> **每完成一个小功能，就做一次 commit。**

这样以后出问题更容易回退。

---

### 3.4 Commit message：提交说明

每次提交最好写一句说明，比如：

- 新增水果列表
- 修复登录问题
- 添加搜索功能

作用是帮助你以后回看历史时，知道这次改了什么。

---

### 3.5 git status：查看当前状态

这是最常用的 Git 命令之一，告诉你：
- 哪些文件被修改了
- 哪些文件在暂存区
- 哪些文件没被跟踪

```bash
git status

# 简短模式
git status -s
```

### 3.6 git log：查看提交历史

```bash
# 查看完整历史
git log

# 紧凑模式
git log --oneline

# 图形化分支历史
git log --oneline --graph --all

# 查看最近 N 条
git log -5
```

### 3.7 git diff：查看差异

```bash
# 工作区 vs 暂存区
git diff

# 暂存区 vs 最新 commit
git diff --staged

# 工作区 vs 最新 commit
git diff HEAD

# 比较两个分支
git diff main..feature
```

---

## 4. 如何撤销错误操作：三种“后悔药”

文档里重点讲了三种回退方式，非常重要。

---

### 4.1 Discard changes：放弃未提交修改

适用场景：

- 代码还没 commit
- 你想直接丢掉当前改动

它的效果是：

> 把文件恢复到上一次提交时的状态。

适合：

- 改乱了
- 试错失败
- 还没提交前想撤回

---

### 4.2 Reset：强制回退

作用：

> 把仓库直接回退到某个历史 commit。

特点：

- 很强力
- 会改变历史
- 可能丢代码

适合：

- 只有自己在用的分支
- 改动还没推到远端
- 你明确知道自己在做什么

**注意：多人协作分支上慎用或禁用。**

如果已经推到远端，再 reset，往往还要 **强制推送**，风险很高。

---

### 4.3 Revert：生成反向提交

作用：

> 不删除历史，而是新增一个“反向提交”，把某次 commit 抵消掉。

特点：

- 更安全
- 保留历史
- 适合多人协作分支

**多人协作时，优先用 revert。**

---

## 5. Branch 分支：为什么要有分支

### 分支是什么

分支就是一条独立的开发线。

默认仓库通常有一个：

- `main`
- 或者旧名字 `master`

它是主干分支。

---

### 分支的意义

你可以在不同分支上做不同的事：

- main：稳定版本
- feature：开发新功能
- bugfix：修 bug

好处是：

- 不同功能互不干扰
- 多人同时开发不会打架
- 功能做完再合并回主干

---

### 分支是怎么实现的

Git 的分支底层不是复制一份完整代码，而是通过 **指针** 来实现的。

所以创建分支非常轻量。

---

### Merge：合并

当分支开发完成后，把它合回主干，就叫：

- `merge`

意思就是：

> 把一个分支的改动合并进另一个分支。

---

### 删除分支

一般功能合并后，开发分支就可以删掉。

但注意：

- **不能删除当前正在使用的分支**
- 所以要先切到别的分支，再删除

---

### 从历史提交创建分支

你也可以基于某个历史 commit 新建分支。  
这比直接在 detached 状态下乱改更安全。

---

## 6. HEAD 是什么

HEAD 可以理解为：

> 当前仓库“正站在哪个提交上”。

它表示你现在看到的是：

- 哪个分支
- 哪个 commit

如果 HEAD 指向分支，说明你正常在分支上工作。  
如果 HEAD 指向某个历史提交，但不属于任何分支，就叫：

- **detached HEAD**
- 中文常叫：**分离头指针**

### 分离头指针的风险

这个状态适合 **查看历史**，  
但不适合随便改代码，因为容易丢失改动。

---

## 7. Worktree 是什么

Worktree 可以理解成：

> 为同一个仓库再开一个独立工作文件夹。

它的特点：

- 主文件夹和工作树可以并行开发
- 各自互不干扰
- 适合同时处理多个任务

比如：

- 主分支做正式开发
- worktree 里做另一个功能

做好后再合并回来。

---

## 8. Git 的几个“区域 / 分区”

### 8.1 Working Directory 工作区

就是你电脑上正在编辑的本地文件夹。

你打开代码、修改文件，改动首先发生在这里。

---

### 8.2 Staging Area 暂存区

这是 commit 前的“待提交区”。

作用：

- 先把你想提交的改动放进来
- 再统一 commit

常见命令对应：

- `git add`：把文件放入暂存区
- `git commit`：把暂存区内容提交到本地仓库

#### 在 VS Code 里
VS Code 很多时候把这个步骤简化了：

- 直接点 commit
- 实际上有时相当于顺便做了 add

但你也可以手动 stage 某些文件，只提交部分内容。

---

### 8.3 Local Repository 本地仓库

commit 之后，文件变化进入本地仓库。

这时历史就保存下来了。

---

### 8.4 Remote Repository 远端仓库

也就是 GitHub 这类仓库。

作用：

- 远程备份
- 团队共享
- 协作开发

---

## 9. 本地和远端怎么同步

### 9.1 Clone：把远端仓库克隆到本地

`git clone` 的意思是：

> 把 GitHub 上的仓库完整拷贝到本地。

克隆后通常会同时得到：

- 本地代码目录
- 本地仓库
- 与远端的关联

这是最常见的开始方式之一。

---

### 9.2 Push：把本地提交推送到远端

`git push` 的意思是：

> 把本地仓库的提交上传到 GitHub。

适合：

- 你改好了代码
- 你想备份到网上
- 你想让别人看到

---

### 9.3 Pull / Fetch + Merge：把远端改动拉到本地

当远端有新改动时，要同步到本地。

常见方式：

- `git pull`
- 或 `git fetch + git merge`

可以理解成：

- 先把远端更新拿下来
- 再合并到本地

---

### 9.4 git remote：管理远端仓库

```bash
# 查看已关联的远端仓库
git remote -v

# 添加远端仓库
git remote add origin <url>

# 修改远端地址
git remote set-url origin <new-url>

# 删除远端关联
git remote remove origin
```

### 9.5 git tag：版本标签

```bash
# 创建轻量标签
git tag v1.0.0

# 创建附注标签（含说明）
git tag -a v1.0.0 -m "第一个正式版本"

# 推送标签到远端
git push origin v1.0.0

# 推送所有标签
git push origin --tags

# 列出所有标签
git tag -l
```

### 9.6 Git 基本配置

```bash
# 设置用户名和邮箱（必须）
git config --global user.name "你的名字"
git config --global user.email "你的邮箱"

# 设置默认分支名
git config --global init.defaultBranch main

# 查看当前配置
git config --list

# 配置文件位置
# 全局：~/.gitconfig
# 项目：.git/config
```

---

## 10. GitHub 页面里常见的东西

### Repository / 仓库主页

一个仓库页面通常会看到：

- 代码区
- README
- Releases
- Issues
- Fork
- Star
- Pull Request

---

### README

README 是项目说明文件。  
GitHub 会自动把它显示在仓库首页。

通常写：

- 项目用途
- 如何安装
- 如何运行
- 功能介绍

---

### Releases

这里记录项目发布版本。

通常包含：

- 版本号
- 更新内容
- 打包好的可下载文件

---

### Star

可以理解成：

- 点赞
- 收藏

表示你对这个项目感兴趣。

---

### Fork

Fork 的意思是：

> 复刻一份项目到你自己的名下。

这样你就可以：

- 自己修改
- 自己实验
- 以后发 Pull Request 回原项目

---

### Issues

Issues 是项目讨论区，用来：

- 提 bug
- 提建议
- 讨论新功能
- 跟作者沟通

状态通常有：

- open：未解决
- closed：已解决

---

### Pull Request（PR）

PR 的意思是：

> 把你分支上的改动，发起一个“合并请求”，请求合并到主项目里。

它是开源协作最核心的流程之一。

#### PR 的流程
1. Fork 项目
2. 在自己仓库里 clone 到本地
3. 建分支
4. 修改代码并 commit
5. push 到你自己的 GitHub 仓库
6. 创建 PR
7. 维护者 code review
8. 通过后合并

---

### Code Review

就是代码审核。

维护者会看：

- 代码是否正确
- 逻辑是否清晰
- 有没有 bug
- 是否符合项目规范

---

### GitHub 快捷键

文档里提到几个常用快捷键：

- `/`：快速搜索
- `t`：搜索文件
- `l`：跳转到行号
- `?`：打开快捷键帮助表
- `.`：打开网页版 VS Code

---

### Codespace

GitHub 还提供云端开发环境，可以直接在浏览器里写代码、调试代码。

---

## 11. 开源协作的标准流程

---

### 场景一：你不是项目成员

你想给开源项目贡献代码，一般流程是：

1. Fork 原项目
2. clone 你自己名下的仓库
3. 创建分支
4. 修改代码
5. commit
6. push 到你自己的仓库
7. 发起 Pull Request
8. 原项目管理员审核并合并

#### 为什么一定要先建分支
因为这样更安全，也方便后续同步上游代码，减少冲突。

---

### 场景二：你是协作者

如果管理员把你加入为 collaborator，你就不一定要 fork 了，可以直接对项目仓库操作。

流程是：

1. 直接拉分支
2. 修改代码
3. commit
4. push 到远端
5. 提交 PR 或直接协作合并

---

## 12. 冲突是什么

当两个分支修改了 **同一个文件的同一行**，Git 不知道该保留谁，就会发生：

- merge conflict
- 合并冲突

这时需要人工决定：

- 保留 A 的改动
- 保留 B 的改动
- 两个都保留
- 重新整理成新的内容

### 新手建议
遇到冲突不要慌：

- 先看冲突点
- 再决定保留什么
- 必要时让 AI 帮你处理

---

## 13. 其他进阶概念

---

### Cherry-pick

意思是：

> 从某个分支里，挑选某几个 commit 单独拿到当前分支。

适合：

- 只想要某几个提交
- 不想把整个分支都合过来

你可以把它理解为：

> “我只摘我想要的樱桃”。

---

### Stash

意思是：

> 临时把没写完的修改藏起来。

适合场景：

- 代码写了一半
- 你要先切到别的分支处理紧急任务

它和暂存区不是一回事。

#### 区别
- **stash**：临时藏起来，之后还能拿回来
- **stage**：准备提交

---

### Rebase

Rebase 是更进阶的操作，中文常叫：

- 变基

它的作用可以简单理解为：

> 把一个分支“挪”到另一个分支的基础上，让历史更干净。

#### 好处
- 历史线更直
- 不会多出很多 merge 记录

#### 风险
如果分支已经多人共享，rebase 后通常需要 **强制推送**，这很危险。

---

### Force Push 强制推送

强制推送会直接覆盖远端历史。

#### 适合
- 个人分支
- 没有其他人在协作的分支

#### 不适合
- 多人协作分支

因为可能把别人的提交覆盖掉。

---

## 14. 新手最该记住的核心原则

### 原则 1：先理解概念，再记命令
现在很多操作可以交给 AI，但你必须知道：

- 你在做什么
- 会影响哪个分支
- 会不会丢代码

---

### 原则 2：小步提交
每完成一个小功能就 commit 一次。  
这样最安全，也最好回退。

---

### 原则 3：多人协作优先用安全操作
- 撤销已公开的提交：优先 `revert`
- 谨慎使用 `reset`
- 多人分支别乱 `force push`

---

### 原则 4：开发前先建分支
尤其是：

- 新功能
- 试验性修改
- 开源协作

---

### 原则 5：提交前想清楚
尤其注意：

- `.env` 不要提交
- `node_modules` 不要提交
- 密钥不要上传

---

## 15. 最后给你一个超简单记忆版

### Git
- 管历史版本
- 记录每次提交
- 本地操作为主

### GitHub
- 放远端仓库
- 做备份和协作
- 开源项目常用

### 常见动作
- `init`：初始化仓库
- `commit`：提交一个版本
- `branch`：开一条新开发线
- `merge`：把分支合并回去
- `clone`：把远端仓库拷到本地
- `push`：把本地上传到远端
- `pull`：把远端拉到本地
- `stash`：临时存起来
- `rebase`：变基，整理历史
- `cherry-pick`：挑几个提交拿过来
- `fork`：复刻别人的仓库到自己名下
- `PR`：请求把你的改动合并进去

---

## 16. 一页速记总结

### Git 的本质
- 管理文件历史版本
- 用 commit 保存快照
- 用分支并行开发
- 用 merge / rebase / revert 处理历史

### GitHub 的本质
- 存远端仓库
- 做代码分享与协作
- 支持开源贡献

### 最重要的三句话
1. **Git 是版本控制工具。**
2. **GitHub 是 Git 仓库托管平台。**
3. **多人协作先建分支，慎用 reset 和强制推送。**

---

## 17. 推荐新手的学习顺序

如果你是完全新手，建议按这个顺序学：

1. 先懂 Git 和 GitHub 的区别
2. 学会 `init / commit / branch / merge`
3. 学会 `clone / push / pull`
4. 学会 `.gitignore`
5. 学会 `discard / reset / revert`
6. 学会 PR、Fork、Issues
7. 最后再碰 `stash / rebase / cherry-pick`

---

## 18. 结语

这篇内容的核心不是让你死记命令，  
而是让你真正理解：

- Git 是怎么记录版本的
- 分支为什么重要
- GitHub 为什么适合协作
- 多人开发怎样避免丢代码

只要你掌握了这些核心概念，  
以后不管是自己开发、团队协作，还是参与开源项目，都会轻松很多。

---

## 19. 命令速查表

| 操作 | 命令 |
|------|------|
| **初始化仓库** | `git init` |
| **查看状态** | `git status` |
| **查看历史** | `git log --oneline` |
| **查看差异** | `git diff` |
| **暂存文件** | `git add <file>` |
| **提交** | `git commit -m "message"` |
| **创建分支** | `git branch <name>` |
| **切换分支** | `git checkout <name>` 或 `git switch <name>` |
| **创建并切换** | `git checkout -b <name>` |
| **合并分支** | `git merge <branch>` |
| **克隆仓库** | `git clone <url>` |
| **推送到远端** | `git push origin <branch>` |
| **从远端拉取** | `git pull origin <branch>` |
| **查看远端** | `git remote -v` |
| **创建标签** | `git tag v1.0.0` |
| **暂存修改** | `git stash` |
| **恢复暂存** | `git stash pop` |
| **撤销未提交修改** | `git checkout -- <file>` |
| **回退到某 commit** | `git reset --hard <commit>` |
| **反向提交** | `git revert <commit>` |
| **挑取提交** | `git cherry-pick <commit>` |
| **变基** | `git rebase <branch>` |
| **配置用户名** | `git config --global user.name "name"` |
| **配置邮箱** | `git config --global user.email "email"` |

---

## 20. Git LFS（大文件存储）

Git 不适合管理大文件（视频、模型、数据集等）。**Git LFS（Large File Storage）** 解决了这个问题——大文件本体存在 LFS 服务器上，仓库里只存指针。

```bash
# 安装 Git LFS
git lfs install

# 追踪特定文件类型
git lfs track "*.psd"
git lfs track "*.zip"
git lfs track "*.mp4"

# 追踪后 .gitattributes 会被更新，提交即可
git add .gitattributes
git commit -m "配置 Git LFS"

# 之后正常使用即可
git add large-file.psd
git commit -m "添加大文件"
git push
```

**适用场景**：游戏资源、AI 模型文件、视频/音频素材、大型数据集。

---

## 21. GitHub Actions（CI/CD 自动化）

GitHub Actions 是 GitHub 内置的 CI/CD 引擎，可以在代码 push、PR、定时等事件触发自动任务。

**核心概念**：
- **Workflow**：自动化流程，定义在 `.github/workflows/*.yml`
- **Job**：Workflow 中的一组任务，默认并行
- **Step**：Job 中的每个执行步骤
- **Action**：可复用的步骤单元（社区市场有大量现成的）

```yaml
# .github/workflows/ci.yml
name: CI

on:
  push:
    branches: [main]
  pull_request:
    branches: [main]

jobs:
  build-and-test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4

      - name: Setup Node.js
        uses: actions/setup-node@v4
        with:
          node-version: '20'

      - name: Install dependencies
        run: npm ci

      - name: Run tests
        run: npm test

      - name: Build
        run: npm run build

      - name: Build Docker image
        run: docker build -t myapp .

      - name: Push to Docker Hub
        run: |
          echo "${{ secrets.DOCKER_TOKEN }}" | docker login -u "${{ secrets.DOCKER_USERNAME }}" --password-stdin
          docker tag myapp username/myapp:latest
          docker push username/myapp:latest
```

**常见用途**：
- 自动运行测试
- 自动构建 & 发布
- 自动部署到服务器
- 定时任务（cron 触发）
- Docker 镜像构建 & 推送

---

## 22. GitHub Pages（免费静态网站）

GitHub Pages 可以将仓库直接发布为静态网站，完全免费，支持自定义域名。

```bash
# 方式一：个人/组织站点
# 仓库名必须为：<username>.github.io
# 直接推送到 main 分支，访问 https://<username>.github.io

# 方式二：项目站点
# 任意仓库 → Settings → Pages → 选择分支和目录 → Save
# 访问 https://<username>.github.io/<repo-name>

# 支持的静态站点生成器（自动构建）
# Jekyll（默认）、Hugo、Hexo、VuePress 等
```

**适用场景**：个人博客、项目文档、技术作品集、产品 Landing Page。

---

## 23. GitHub Desktop（图形化客户端）

对于不习惯命令行的初学者，GitHub Desktop 提供了可视化操作界面：

- 克隆仓库、创建分支
- 查看 diff（变更对比）
- 提交和推送
- 分支合并
- 解决冲突（图形化冲突编辑器）
- 管理 Pull Request

> 技术爬爬虾课程中推荐新手从 GitHub Desktop 入门——先用 GUI 理解 Git 工作流，再逐步过渡到命令行。

下载：https://desktop.github.com

---

## 24. GitHub 高级功能速览

### Webhook

当仓库发生特定事件时，GitHub 向你的服务器发送 HTTP POST 通知：

```bash
# 常见触发事件：push、PR、issue、release
# 用途：自动部署、通知机器人、触发 CI
```

### REST API

GitHub 提供完整的 REST API，可以程序化管理仓库：

```bash
# 获取仓库信息
curl https://api.github.com/repos/owner/repo

# 创建 Issue
gh issue create --title "Bug report" --body "描述..."

# 查看 PR 列表
gh pr list
```

### GitHub CLI（`gh` 命令）

```bash
# 安装后登录
gh auth login

# 创建仓库
gh repo create my-project --public

# 创建 PR
gh pr create --title "修复登录bug" --body "修复了..."

# 查看 Issues
gh issue list
```

### 安全功能

| 功能 | 说明 |
|------|------|
| **Dependabot** | 自动检测依赖漏洞并提 PR 更新 |
| **Code Scanning** | 代码安全扫描（基于 CodeQL） |
| **Secret Scanning** | 检测是否误提交了密钥/Token |
| **Security Policy** | `SECURITY.md` 文件，告知如何报告漏洞 |

---

## 25. AI 时代学 Git 的正确姿势（技术爬爬虾方法论）

核心理念：**得意忘言** —— 掌握核心概念，命令交给 AI。

1. **理解概念比背命令重要 100 倍**：AI 可以帮你执行具体命令，但只有你理解"为什么这样做"才能正确指挥 AI
2. **用自然语言和 AI 协作**：
   > "帮我把当前改动提交到 feature 分支，然后推送到 GitHub，commit message 写'添加搜索功能'"
3. **让 AI 处理复杂操作**：解决冲突、rebase 整理历史、cherry-pick 等
4. **AI 是安全带，不是自动驾驶**：每次 AI 操作前先看 diff，确认无误再执行
5. **Claude Code / Copilot 等工具底层全是 Git**：理解 Git 才能更好地利用这些 AI 编程工具

---

## 26. 课程内容对照（技术爬爬虾 vs 本文）

| 技术爬爬虾课程模块 | 本文对应章节 |
|--------------------|------------|
| Git/GitHub 基础概念 | 第 1~8 节 |
| GitHub 网站操作 | 第 10 节 |
| Git 四分区模型 | 第 8 节 |
| GitHub Desktop | 第 23 节 |
| 分支合并与冲突解决 | 第 5、12 节 |
| IDE 中使用 Git | —（另见 IDE 相关笔记） |
| Git 命令行 | 第 19 节 命令速查表 |
| GitHub Actions | 第 21 节 |
| GitHub Pages | 第 22 节 |
| Git LFS | 第 20 节 |
| Webhook / REST API | 第 24 节 |
| 安全功能 | 第 24 节 |
| AI 辅助 Git 操作 | 第 25 节 |