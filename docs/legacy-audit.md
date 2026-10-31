# Legacy Audit — Quyết định Clean Slate (Viết mới 100%)

- **Tác giả:** Khoa
- **Ngày:** 31/10/2026
- **Trạng thái:** Đã chốt (Accepted)

---

## 1. Tuyên bố dứt điểm (Clean Slate)
Không tái sử dụng, không giữ lại bất kỳ package/class nào từ đồ án cũ. Toàn bộ codebase cũ được loại bỏ hoàn toàn khỏi phạm vi của dự án này.

## 2. Lý do kỹ thuật (Tại sao không dùng lại?)
- **Mất kiểm soát chất lượng mã nguồn:** Code cũ trước đây có sự can thiệp của AI sinh tự động, tiềm ẩn nhiều nợ kỹ thuật (technical debt) và không kiểm soát được độ phức tạp.
- **Không phù hợp kiến trúc mục tiêu:** Kiến trúc cũ không đáp ứng được mô hình Phân tích tĩnh (Static Analysis) chuyên sâu và giao thức MCP Server qua stdio chạy trên nền Java 21.
- **Mục tiêu học tập & Phỏng vấn:** Chỉ giải thích và bảo vệ những gì mình tự tay viết và thấu hiểu 100% bản chất.

## 3. Quyết định đối với Frontend cũ
- Bỏ hoàn toàn frontend cũ.
- Nếu dự án bước vào [Phase 7 (Tùy chọn)](../plan/phase-7-graph-viewer.md), sẽ xây dựng mới một Graph Viewer tĩnh bằng React + Vite đọc file `graph.json` xuất từ CLI theo đúng tinh thần của [ADR-007](decisions/ADR-007-static-viewer.md).

## 4. Cam kết cho dự án mới
- 100% viết mới từ đầu (from scratch) dựa trên tài liệu đặc tả yêu cầu [docs/01-requirements.md](01-requirements.md).
- Tuân thủ chặt chẽ ranh giới package và các quy chuẩn code sạch được định nghĩa trong [docs/04-tech-and-conventions.md](04-tech-and-conventions.md).
