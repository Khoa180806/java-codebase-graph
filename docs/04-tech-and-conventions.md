# 04 — Tech Stack & Conventions

## Stack
| Thành phần | Lựa chọn | Ghi chú |
|---|---|---|
| Ngôn ngữ | Java 21 | Dùng record, switch expression, pattern matching `instanceof` |
| Build | Maven | Wrapper `mvnw` |
| Parser | JavaParser (`javaparser-core`) | |
| Symbol resolution | `javaparser-symbol-solver-core` | `CombinedTypeSolver` = `ReflectionTypeSolver` + `JavaParserTypeSolver` |
| MCP | Java MCP SDK (`io.modelcontextprotocol.sdk:mcp`) | Kiểm tra artifact & API thật trên GitHub/Maven Central, API có thể thay đổi giữa các bản |
| JSON | Jackson (thường đã đi kèm MCP SDK) | Tránh thêm lib JSON thứ hai |
| Log | SLF4J + backend nhẹ (vd `slf4j-simple` cấu hình ra stderr) | Không `System.out` |
| Test | JUnit 5 + AssertJ | Mockito chỉ khi thật cần |

> ⚠️ Phiên bản: agent phải tra Maven Central, chọn bản ổn định mới nhất tại thời điểm làm, **pin cứng** và ghi vào ADR-001.
> Graph tự dựng (KHÔNG dùng JGraphT) — xem ADR-002.

## Viewer (Phase 7, tuỳ chọn) — thư mục `viewer/`
React + TypeScript + Vite, thư viện vẽ đồ thị Cytoscape.js (hoặc D3 — chốt bằng ADR nhỏ), Tailwind tuỳ chọn. Không state manager nặng (useState/useReducer là đủ). Viewer **không** nằm trong Maven build.

## Package (placeholder `dev.khoa.codegraph`, đổi tuỳ ý ở Phase 0)
```
dev.khoa.codegraph
├── cli          Main (đọc args, nối các tầng)
├── ingestion    ProjectScanner, SourceRoots
├── parser       AstExtractor, ParsedType, ParsedMethod ...
├── model        TypeNode, MethodNode, EdgeType, CodeGraph, GraphBuilder
├── resolve      SolverFactory, CallResolver, OverrideResolver, ResolutionStats
├── query        StructureQuery, CallerQuery, ImpactAnalyzer, result records
└── mcp          McpServerBootstrap, tool handlers, JSON mapping
```
Quy tắc phụ thuộc: `model` không import `com.github.javaparser.*`. `query` chỉ import `model`. `mcp` chỉ import `query` (+ SDK).

## Convention
- Tên: class `PascalCase`, method `camelCase`, hằng `UPPER_SNAKE`.
- Test: `ClassNameTest`, method theo mẫu `should_<kết quả>_when_<điều kiện>`.
- Không `null` làm kết quả "không tìm thấy": dùng `Optional` hoặc result record có trạng thái.
- Collection trả ra ngoài là **immutable** (`List.copyOf`, `Collections.unmodifiableSet`...).
- Không bắt `Exception` chung chung nuốt lỗi; bắt đúng loại và ghi log có ngữ cảnh (đường dẫn file, method).
- Mỗi class public có Javadoc 1-2 dòng nêu *trách nhiệm*.
- Không comment lại điều code đã nói; comment để giải thích **vì sao**.

## Cấu trúc repo
Xem `START_HERE.md`. Thư mục `src/test/resources/fixtures/shop/` chứa fixture project (spec ở `07-fixture-spec.md`).
