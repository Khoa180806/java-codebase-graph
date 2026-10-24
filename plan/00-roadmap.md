# 00 — Roadmap & Cut Lines

Cửa sổ thời gian: **24/10/2026 → 21/11/2026** (4 tuần). Dự án 2 bắt đầu **22/11** — không dời.

| Phase | File | Ngày dự kiến | Sản phẩm chính |
|---|---|---|---|
| 0 | `phase-0-setup.md` | 24/10 – 25/10 | Repo, Maven, logging, bài khởi động Java |
| 1 | `phase-1-parsing.md` | 26/10 – 31/10 | Scanner + AstExtractor + test |
| 2 | `phase-2-graph-model.md` | 01/11 – 03/11 | Fixture, model, CodeGraph, pass 1 (EXTENDS/IMPLEMENTS/DECLARES/USES_TYPE) |
| 3 | `phase-3-symbol-resolution.md` | 04/11 – 09/11 | CALLS, OVERRIDES, ResolutionStats |
| 4 | `phase-4-query-engine.md` | 10/11 – 13/11 | StructureQuery, CallerQuery, ImpactAnalyzer |
| 5 | `phase-5-mcp-server.md` | 14/11 – 18/11 | MCP server 3 tool, chạy được trong client thật |
| 6 | `phase-6-polish-release.md` | 19/11 – 21/11 | Số liệu, README EN, video, tag v1.0.0 |
| 7 | `phase-7-graph-viewer.md` | **Tuỳ chọn** — chỉ khi đạt cổng vào | Export JSON + Graph Viewer (React) |

## Cut lines (quy tắc cứng — tránh trễ làm sập Dự án 2)
| Mốc | Điều kiện phải đạt | Nếu chưa đạt thì CẮT |
|---|---|---|
| **CP1 — 07/11** | P3 có CALLS đúng trên fixture (11 cạnh) | Bỏ nested class, varargs, generics nâng cao. Chấp nhận resolve rate thấp hơn. |
| **CP2 — 13/11** | P4 xong; `impact_analysis` chạy đúng trên fixture | Impact chỉ dùng CALLS + USES_TYPE, bỏ subtype. |
| **CP3 — 21/11** | Tag `v1.0.0` | Bất cứ thứ gì chưa xong → `future/`. Đóng gói phần đã có (graph + ≥2 tool chạy được). KHÔNG kéo sang tuần 22/11. |

## Về Phase 7 (FE) — đọc kỹ
Phase 7 **không nằm trong đường găng v1.0**. Cổng vào: CP2 đạt đúng hạn (13/11) **và** P5 xong trước 17/11. Đạt → làm P7-T1/T2 (export JSON, ~½ ngày) và viewer tối giản trong thời gian chen vào Phase 6, tối đa 2 ngày. Không đạt → viewer thành bản `v1.1` làm **sau** Dự án 2 (lúc đó đã quen React) — không được lấn sang tuần 22/11.
Bản chất: viewer là để demo trực quan và kể chuyện "có sản phẩm cho người dùng", không phải điều kiện của v1.0.

## Nhịp làm việc mỗi ngày
- 1 task chính (xem `progress/STATUS.md`) + commit nhỏ.
- Cuối ngày: learning log 5 dòng — "hôm nay em hiểu ra… / chưa hiểu… / câu hỏi cho ngày mai".
- Cuối mỗi phase: agent quiz 3-5 câu ở `docs/08-interview-prep.md`.

## Nhãn task
🧠 CORE = Khoa tự viết · 🧰 BOILERPLATE = agent được viết (có giải thích) · Ước lượng: S (<1h) · M (1-3h) · L (3-6h)
