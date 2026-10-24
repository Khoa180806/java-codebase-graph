# Phase 5 — MCP Server (14/11 – 18/11)

**Mục tiêu:** Agent thật (Claude Desktop / Cursor / Antigravity) gọi được 3 tool và nhận kết quả đúng.
**Đọc trước:** `docs/06`, ADR-005, README/Javadoc của Java MCP SDK **đúng phiên bản đã pin**.
**Khái niệm cần nắm:** MCP (client/server/tool/transport), JSON-RPC qua stdio, input schema (JSON Schema), vai trò của `description`.
**⚠️ Quy tắc sống còn:** không có byte nào ghi ra stdout ngoài SDK.

---
### P5-T1 🧰 (M) Hello-world MCP server
- Server stdio với 1 tool `echo` để xác nhận toolchain. Agent viết, **giải thích** vòng đời: khởi động → handshake → liệt kê tool → gọi tool.
- **AC:** Client thật liệt kê và gọi được `echo`. stdout sạch (không log lẫn vào).
- **Câu hỏi tại sao:** MCP giải quyết vấn đề gì so với việc dán code vào prompt? Khác function calling thuần ở đâu?

### P5-T2 🧠 (M) Ánh xạ kết quả → JSON
- Chuyển result record của `query` thành JSON theo đúng schema `docs/06` (Jackson). Tách lớp mapping khỏi handler.
- **AC:** Test snapshot đơn giản cho mỗi loại result: ok / ambiguous / not_found.

### P5-T3 🧠 (M) Tool `get_class_structure`
- Định nghĩa input schema, `description` (viết sao cho LLM biết khi nào dùng), handler gọi `StructureQuery`.
- **AC:** Gọi qua handler trên fixture ra JSON khớp kỳ vọng.

### P5-T4 🧠 (M) Tool `find_callers`
- Như trên; nhớ xử lý `ambiguous` (trả candidates), thêm `note` về giới hạn resolve.
- **AC:** 5 ca `find_callers` ở `docs/07`.

### P5-T5 🧠 (M) Tool `impact_analysis`
- Validate `maxDepth` (mặc định/trần), xử lý input rác (null, âm, quá lớn) trả lỗi có cấu trúc.
- **AC:** 2 ca `impact_analysis` ở `docs/07` + ca input sai.

### P5-T6 🧠 (M) `Main`: ghép toàn bộ
- Parse args (`--project-path` bắt buộc; thiếu → thông báo ra stderr + exit code ≠ 0) → index → log `ResolutionStats` ra **stderr** → khởi động MCP.
- **AC:** Chạy từ command line với fixture path không lỗi.

### P5-T7 🧠 (M) Smoke test cho handler
- Gọi trực tiếp 3 handler với graph fixture; xác nhận JSON hợp lệ & không ném exception với input xấu.

### P5-T8 🧰 (S) Đóng gói jar chạy được
- Maven plugin tạo fat/shaded jar (agent viết, giải thích). Lưu ý: shade plugin có thể cần xử lý `META-INF/services`.
- **AC:** `java -jar target/*.jar --project-path <fixture>` chạy và chờ stdin.

### P5-T9 🧠 (M) Thử với client thật
- Cấu hình client (Claude Desktop hoặc Cursor/Antigravity), hỏi agent câu thật: *"Ai gọi `PaymentService.pay`?"*, *"Sửa `Order` thì ảnh hưởng gì?"*.
- Chụp ảnh/quay màn hình vào `docs/media/`. Ghi lại cấu hình mẫu vào `docs/client-setup.md`.
- **AC:** Agent gọi đúng tool và diễn giải đúng kết quả. Nếu agent gọi sai tool → sửa `description`, đó là bài học cho phỏng vấn.

---
## Definition of Done Phase 5
- [ ] 3 tool chạy trên client thật · [ ] bằng chứng ảnh/video · [ ] smoke test xanh · [ ] stdout sạch · [ ] quiz Phase 5 đạt
> Nếu P5 bị trễ: giữ `get_class_structure` + `find_callers`, đẩy `impact_analysis` sang v1.0.1 (xem cut lines CP3).
