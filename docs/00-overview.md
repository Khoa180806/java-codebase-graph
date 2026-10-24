# 00 — Overview

## Mục tiêu
1. Xây công cụ phân tích tĩnh (static analysis) cho source Java: parse → dựng đồ thị quan hệ → truy vấn.
2. Expose kết quả qua **MCP Server** (stdio) để AI coding agent (Claude Desktop, Cursor, Antigravity) gọi được.
3. Khoa **tự viết và hiểu 100%** phần lõi. Có test chứng minh đúng.

## Phi mục tiêu (KHÔNG làm trong v1.0)
- Không thay thế language server / Sourcegraph / IDE indexer.
- Không hỗ trợ ngôn ngữ khác Java.
- Không nhận file `.zip` (rủi ro zip-slip, không thêm giá trị học tập). Chỉ nhận **đường dẫn thư mục**.
- Không có database, không persistence, không backend HTTP (xem `future/`).
- UI chỉ là **Graph Viewer tĩnh** (React) đọc file JSON do CLI xuất ra — Phase 7, **tuỳ chọn có điều kiện** (ADR-007, `docs/09`).
- Không phân tích đầy đủ generics phức tạp, lambda, reflection, annotation processing, Spring DI.

## Định vị khi phỏng vấn
> "Đây là dự án học tập về static analysis và MCP. Em dựng call graph bằng JavaParser + SymbolSolver,
> resolve được X% method call trên fixture / repo thật, các call không resolve được thì bỏ qua và thống kê."

Luôn kèm **số liệu thật**: số file parse, thời gian index, tỷ lệ resolve.

## Định nghĩa "xong" cho v1.0
- [ ] Index được thư mục Java project, xuất `ResolutionStats`.
- [ ] 3 MCP tool chạy được trong Claude Desktop hoặc Cursor/Antigravity (có ảnh/video chứng minh).
- [ ] Bộ test chạy trên fixture project, `mvn test` xanh.
- [ ] README tiếng Anh + sơ đồ kiến trúc + video demo 2 phút.
- [ ] Git tag `v1.0.0`.
- [ ] Khoa giải thích được toàn bộ luồng từ `main()` đến kết quả tool mà không nhìn code.
