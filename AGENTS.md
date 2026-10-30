# Codex instructions

When working in the co-located workspace, first read and follow the
[workspace instructions](../AGENTS.md) if that file exists.
Then read and follow [CLAUDE.md](CLAUDE.md) in this directory before working
in this scope. It is the shared source of truth for repository instructions
used by both Claude Code and Codex; keep shared rules there instead of
duplicating them here.

Treat repository behavior and restrictions addressed to "Claude" or "Claude
Code" as applying to Codex too. Claude CLI commands, slash commands, plugins,
and MCP setup are tool-specific: use only capabilities actually available in
this Codex session. If a required capability is unavailable, report the
limitation and use a documented local fallback where available.
