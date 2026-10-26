# STATUS — Trạng thái hiện tại

**Hôm nay đang ở:** Phase 0 · Task P0-T4
**Checkpoint:** CP1 07/11 · CP2 13/11 · CP3 21/11 (tag v1.0.0)
**Chế độ agent:** LEARN MODE

Quy ước: `[ ]` chưa làm · `[~]` đang làm · `[x]` xong (kèm ngày `dd/mm`) · `[A]` xong ở ASSIST mode (cần ôn lại)

## Phase 0 — Setup (24–25/10)
- [x] P0-T1 🧰 Maven skeleton (24/10)
- [x] P0-T2 🧰 Dependencies + ADR-001 (25/10)
- [x] P0-T3 🧰 Logging stderr (26/10)
- [ ] P0-T4 🧰 CI (optional)
- [ ] P0-T5 🧠 5 bài khởi động

## Phase 1 — Parsing (26–31/10)
- [ ] P1-T1 🧠 Legacy audit
- [ ] P1-T2 🧠 ProjectScanner
- [ ] P1-T3 🧠 Parse file → CU
- [ ] P1-T4 🧠 Parser DTOs
- [ ] P1-T5 🧠 AstExtractor
- [ ] P1-T6 🧠 CLI parse-only

## Phase 2 — Graph model (01–03/11)
- [ ] P2-T1 🧠 Fixture project
- [ ] P2-T2 🧠 Design note
- [ ] P2-T3 🧠 Model classes
- [ ] P2-T4 🧠 CodeGraph + Builder
- [ ] P2-T5 🧠 Type-name normalization
- [ ] P2-T6 🧰 SolverFactory
- [ ] P2-T7 🧠 Pass 1

## Phase 3 — Symbol resolution (04–09/11) · CP1 07/11
- [ ] P3-T1 🧠 Spike solver
- [ ] P3-T2 🧠 ResolutionStats
- [ ] P3-T3 🧠 CallResolver
- [ ] P3-T4 🧠 CALLS edges
- [ ] P3-T5 🧠 OVERRIDES
- [ ] P3-T6 🧠 Fixture test tổng hợp
- [ ] P3-T7 🧠 Thử repo thật
- [ ] P3-T8 🧠 Checkpoint CP1

## Phase 4 — Query engine (10–13/11) · CP2 13/11
- [ ] P4-T1 🧠 MethodLookup
- [ ] P4-T2 🧠 StructureQuery
- [ ] P4-T3 🧠 CallerQuery
- [ ] P4-T4 🧠 ImpactAnalyzer
- [ ] P4-T5 🧠 Unit tests hand-built
- [ ] P4-T6 🧠 Complexity notes
- [ ] P4-T7 🧠 Checkpoint CP2

## Phase 5 — MCP (14–18/11)
- [ ] P5-T1 🧰 Hello-world MCP
- [ ] P5-T2 🧠 JSON mapping
- [ ] P5-T3 🧠 get_class_structure
- [ ] P5-T4 🧠 find_callers
- [ ] P5-T5 🧠 impact_analysis
- [ ] P5-T6 🧠 Main
- [ ] P5-T7 🧠 Smoke tests
- [ ] P5-T8 🧰 Jar packaging
- [ ] P5-T9 🧠 Test với client thật

## Phase 6 — Release (19–21/11) · CP3 21/11
- [ ] P6-T1 🧠 Đo số liệu
- [ ] P6-T2 🧠 README EN
- [ ] P6-T3 🧰 Mermaid diagram
- [ ] P6-T4 🧠 Video demo
- [ ] P6-T5 🧠 Dọn dẹp
- [ ] P6-T6 🧠 Diễn tập phỏng vấn
- [ ] P6-T7 🧰 Release v1.0.0
- [ ] P6-T8 🧠 Bullet CV

## Phase 7 — Graph Viewer (TUỲ CHỌN, có cổng vào — xem roadmap)
- [ ] Cổng vào đạt? (CP2 đúng hạn + P5 xong trước 17/11): ____
- [ ] P7-T1 🧠 Thiết kế JSON schema export
- [ ] P7-T2 🧠 GraphJsonExporter + CLI flag
- [ ] P7-T3 🧰 Scaffold viewer (Vite + React + TS)
- [ ] P7-T4 🧠 Load file + search + ClassDetail
- [ ] P7-T5 🧠 Graph view (caller / impact)
- [ ] P7-T6 🧠 Stats panel + demo

## Nhật ký quyết định / vấn đề
| Ngày | Nội dung |
|---|---|
| | |
