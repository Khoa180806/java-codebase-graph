# START HERE — Java Codebase Knowledge Graph & MCP Server

> Đây là cổng vào của toàn bộ tài liệu. AI agent: **đọc file này trước, rồi đọc `AGENTS.md`**.
> Khi dự án xong (Phase 6), tạo `README.md` bằng tiếng Anh ở root và giữ file này làm index nội bộ.

## Dự án là gì (1 đoạn)
Một công cụ Java đọc source code của một dự án Java, dùng **JavaParser** để dựng **đồ thị quan hệ**
(class kế thừa/implement, method gọi method, class dùng class), rồi mở ra cho AI coding agent
qua **MCP Server** với 3 tool: `get_class_structure`, `find_callers`, `impact_analysis`.
Có thêm một **Graph Viewer** (React) cho người dùng xem đồ thị trực quan — Phase 7, có điều kiện.
Đây là dự án học tập về *static analysis + graph + MCP*, KHÔNG phải sản phẩm thay thế language server.

## Bản đồ tài liệu
| Cần biết gì | Đọc file |
|---|---|
| Luật làm việc của AI agent (BẮT BUỘC) | `AGENTS.md` |
| Tổng quan, mục tiêu, phi mục tiêu | `docs/00-overview.md` |
| Yêu cầu MVP, thứ gì IN / OUT | `docs/01-requirements.md` |
| Kiến trúc & luồng dữ liệu | `docs/02-architecture.md` |
| Mô hình đồ thị: node, edge, ID | `docs/03-domain-model.md` |
| Stack, convention, cấu trúc package | `docs/04-tech-and-conventions.md` |
| Chiến lược test | `docs/05-testing-strategy.md` |
| Đặc tả 3 MCP tool | `docs/06-mcp-tools-spec.md` |
| Đặc tả fixture project + expected graph | `docs/07-fixture-spec.md` |
| Câu hỏi phỏng vấn theo phase | `docs/08-interview-prep.md` |
| Export JSON + Graph Viewer (FE) | `docs/09-graph-export-and-viewer.md`, `plan/phase-7-graph-viewer.md` |
| Các quyết định thiết kế (ADR) | `docs/decisions/` |
| Lộ trình & ranh giới cắt scope | `plan/00-roadmap.md` |
| Việc cần làm từng phase | `plan/phase-0..6-*.md` |
| Tính năng sau v1.0 | `future/` |
| Trạng thái hiện tại | `progress/STATUS.md` |
| Nhật ký học | `progress/learning-log/` |

## Quy trình mỗi phiên làm việc
1. Mở `progress/STATUS.md` → biết đang ở task nào.
2. Mở file phase tương ứng trong `plan/`.
3. Làm đúng 1 task → commit → cập nhật STATUS → ghi learning log (5 dòng).
