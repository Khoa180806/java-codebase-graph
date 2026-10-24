# 02 — Architecture

## Luồng tổng thể
```
 CLI args (--project-path)
        │
        ▼
 ┌───────────────┐   List<Path>    ┌───────────────┐   List<ParsedType>   ┌───────────────┐
 │ ProjectScanner│ ──────────────► │ AstExtractor  │ ───────────────────► │ GraphBuilder  │
 └───────────────┘                 │ (JavaParser)  │                      │ (nodes+DECLARES│
        │ source roots             └───────────────┘                      │ EXTENDS,IMPL, │
        ▼                                  ▲                              │ USES_TYPE)    │
 ┌───────────────┐   TypeSolver            │                              └──────┬────────┘
 │ SolverFactory │ ────────────────────────┘                                     │ CodeGraph
 └───────────────┘                                                               ▼
                                                                         ┌───────────────┐
                                  CALLS + OVERRIDES edges, stats         │ CallResolver  │
                                  ◄─────────────────────────────────────  │ (SymbolSolver)│
                                                                         └──────┬────────┘
                                                                                ▼
                                                                  ┌─────────────────────────┐
                                                                  │ Query layer             │
                                                                  │ StructureQuery          │
                                                                  │ CallerQuery             │
                                                                  │ ImpactAnalyzer          │
                                                                  └──────────┬──────────────┘
                                                                             ▼
                                                                  ┌─────────────────────────┐
                                                                  │ MCP layer (stdio)       │
                                                                  │ 3 tool handlers → JSON  │
                                                                  └─────────────────────────┘
```

## Nhánh Viewer (Phase 7, tuỳ chọn)
`CodeGraph` + `ResolutionStats` → `GraphJsonExporter` → file `graph.json` → `viewer/` (đọc bằng file picker / kéo-thả). Không có server HTTP: viewer là trang tĩnh. Xem `docs/09-graph-export-and-viewer.md`.

## Nguyên tắc
1. **Tách tầng rõ:** `model` (thuần dữ liệu, không biết JavaParser) ← `parser/resolve` (biết JavaParser) ← `query` (chỉ biết `model`) ← `mcp` (chỉ biết `query`).
   Lý do: test `query` và `model` bằng đồ thị dựng tay, không cần parse file thật → test nhanh, dễ hiểu.
2. **Graph là immutable sau khi build xong** (build bằng `GraphBuilder`, đọc bằng `CodeGraph`). Tránh bug thay đổi graph khi đang truy vấn.
3. **Lỗi resolve là dữ liệu, không phải exception:** đi vào `ResolutionStats`.
4. **Không có global state / singleton ẩn.** Truyền dependency qua constructor (làm quen DI thủ công trước khi học Spring).

## Phân tầng build 2 pass
- **Pass 1 (cấu trúc):** parse mọi file → tạo TYPE/METHOD node, cạnh DECLARES, EXTENDS, IMPLEMENTS, USES_TYPE. Chưa cần symbol solver sâu.
- **Pass 2 (hành vi):** duyệt body từng method → với mỗi `MethodCallExpr`, dùng symbol solver tìm method đích → cạnh CALLS. Sau đó tính OVERRIDES.
Lý do 2 pass: để khi resolve call, **mọi node đích đã tồn tại** trong graph.

## Luồng MCP
Server khởi động → index xong → đăng ký 3 tool → chờ request qua stdin → trả JSON qua stdout (do SDK quản lý).
Khoa không tự ghi gì ra stdout.
