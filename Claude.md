# Claude Code 使用指南

## 一句话先懂全局

**Claude Code** 是 Anthropic 推出的命令行 AI 编程助手，可以直接在终端里帮你读代码、写代码、调试、管理 Git 等。

你可以把它理解成：
- 一个 **懂你整个项目** 的 AI 程序员
- 可以直接操作文件、执行命令、搜索代码
- 不是聊天机器人，而是真正能干活的工具

---

## 一、安装与配置

### 安装

```bash
# npm 全局安装
npm install -g @anthropic-ai/claude-code

# 或用 npx 直接运行
npx @anthropic-ai/claude-code
```

### 配置 API（切换模型）

```bash
# 使用 DeepSeek API
$env:ANTHROPIC_BASE_URL="https://api.deepseek.com/anthropic"
$env:ANTHROPIC_AUTH_TOKEN="你的DeepSeek_API_Key"
$env:ANTHROPIC_MODEL="deepseek-v4-pro[1m]"
```

### 配置文件位置

- 全局配置：`~/.claude/settings.json`
- 项目配置：`<项目根目录>/.claude/settings.json`
- 本地配置：`<项目根目录>/.claude/settings.local.json`（不提交到 Git）
- Memory 文件：`~/.claude/projects/<项目路径>/memory/`

---

## 二、常用命令

| 命令 | 说明 |
|------|------|
| `/help` | 查看帮助 |
| `/clear` | 清空对话历史 |
| `/compact` | 压缩上下文（对话太长时用） |
| `/config` | 打开配置面板 |
| `/memory` | 查看/管理记忆 |
| `/review` | 审查当前分支的改动 |
| `/init` | 为项目生成 CLAUDE.md |
| `/doctor` | 诊断安装问题 |
| `esc` | 取消当前操作 |
| `Ctrl+C` | 退出 Claude Code |

### 在提示符中使用 `!` 前缀

在对话中输入 `! <命令>` 可以直接执行 shell 命令并将输出带入对话上下文。

---

## 三、核心概念

### 3.1 Memory（记忆系统）

Claude Code 有持久化记忆能力，存储在 `~/.claude/projects/` 下。

**四种记忆类型**：

| 类型 | 用途 | 示例 |
|------|------|------|
| **user** | 用户角色、偏好、知识背景 | "我是后端开发，熟悉 Java" |
| **feedback** | 用户给你的行为反馈 | "不要写多余的注释" |
| **project** | 项目背景、决策、计划 | "正在做认证系统迁移" |
| **reference** | 外部资源指针 | "Bug 跟踪在 Linear 项目 INGEST" |

**重要**：Memory 不需要手动管理，Claude 会自动读写。你可以用 `/memory` 查看。

### 3.2 Hooks（钩子）

Hooks 是在特定事件触发时自动执行的 shell 脚本，配置在 `settings.json` 中。

常见事件：
- `UserPromptSubmit`：用户提交提示词时
- `PostToolUse`：工具调用完成后
- `Notification`：任务完成通知
- `Stop`：Claude Code 停止时

示例用途：
- 每次提交前自动运行 lint
- 任务完成后桌面通知
- 自动格式化生成的代码

### 3.3 MCP Servers

MCP (Model Context Protocol) 让 Claude Code 可以连接外部工具和数据源。配置文件在 `~/.claude/mcp.json`。

常见 MCP 用途：
- 连接数据库（PostgreSQL、MySQL）
- 连接 GitHub/GitLab API
- 连接 Jira/Linear 等项目管理工具
- 连接文件系统、搜索引擎等

### 3.4 Skills

Skills 是可调用的专项能力模块，用 `/skill名` 触发。内置技能包括：

| Skill | 用途 |
|-------|------|
| `/review` | 审查 PR / 分支改动 |
| `/init` | 初始化项目 CLAUDE.md |
| `/run` | 启动并验证应用 |
| `/security-review` | 安全审查 |
| `/code-review` | 代码审查（可评论到 PR） |
| `/loop` | 定时重复执行任务 |

### 3.5 CLAUDE.md

这是项目的"说明书"，Claude Code 每次启动都会读取它。放在项目根目录的 `.claude/CLAUDE.md` 或直接 `CLAUDE.md`。

内容建议包括：
- 项目简介和技术栈
- 构建/运行命令
- 项目结构说明
- 代码风格和规范
- 注意事项和约定

---

## 四、使用技巧

### 4.1 让它先计划再动手

对于复杂任务，先让 Claude 探索代码并给出计划，确认后再实现。这比直接改代码更安全。

```
先阅读相关代码，制定一个实现计划，我确认后再写代码。
```

### 4.2 小步提交

每完成一个小任务就让 Claude commit 一次，这样出了问题容易回退。

### 4.3 善用 Agent 并行

当有多个独立任务时，可以让 Claude 用 Agent 并行处理，效率更高。

### 4.4 善用 /review

在提交前用 `/review` 让 Claude 审查改动，它可以发现：
- 逻辑错误
- 安全漏洞
- 遗漏的边界情况
- 代码风格问题

### 4.5 遇到问题时的排查顺序

1. `/doctor` — 诊断安装和配置问题
2. `/compact` — 如果对话变慢，压缩上下文
3. `/clear` — 重新开始新对话
4. 检查 API Key 是否有效
5. 检查网络连接

---

## 五、与其他工具对比

| 工具 | 定位 | 特点 |
|------|------|------|
| **Claude Code** | CLI 编程助手 | 终端原生、可直接操作文件、Git 集成 |
| **GitHub Copilot** | IDE 补全 | 代码补全为主、嵌入编辑器 |
| **Cursor** | AI IDE | 完整 IDE、聊天 + 编辑一体化 |
| **ChatGPT** | 通用聊天 | 不直接操作代码库 |

---

## 六、我的配置备忘

```bash
# DeepSeek API 配置
$env:ANTHROPIC_BASE_URL="https://api.deepseek.com/anthropic"
$env:ANTHROPIC_AUTH_TOKEN="你的DeepSeek_API_Key"
$env:ANTHROPIC_MODEL="deepseek-v4-pro[1m]"
```

- Obsidian 仓库路径：`F:\GitHub\note\qnq_Note`
- 主要工作目录：`C:\Users\QN544`
