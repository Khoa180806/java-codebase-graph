# ADR-007: Graph Viewer là trang tĩnh đọc file JSON (không backend HTTP)
- **Trạng thái:** Accepted (cho Phase 7 tuỳ chọn)
- **Bối cảnh:** Đồ án cũ có FE phục vụ người dùng. v1.0 là MCP server stdio, không có HTTP (ADR-005). Cần có UI mà không kéo thêm một lớp server vào 4 tuần.
- **Quyết định:** CLI xuất `graph.json`; `viewer/` (React + TS + Vite) mở file đó ở local. Chế độ live qua HTTP để sau (`future/v1.7`, `v2.0`).
- **Lý do:** Không thêm dependency server, không cần xử lý CORS/auth/concurrency, vẫn cho ra hình ảnh trực quan để demo. Tách rõ: backend phân tích ↔ định dạng dữ liệu ↔ UI, nên đổi viewer không đụng core.
- **Đánh đổi:** Không tương tác thời gian thực; mỗi lần đổi code phải export lại. Chấp nhận được cho v1.0.
- **Rủi ro:** Graph lớn vẽ chậm → viewer chỉ vẽ **lân cận một node** (depth giới hạn), không vẽ toàn bộ.
