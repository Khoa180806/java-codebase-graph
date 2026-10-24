# ADR-005: MCP qua stdio, index một lần lúc khởi động
- **Trạng thái:** Accepted
- **Quyết định:** Transport stdio, project path qua CLI arg, index khi start, graph read-only trong suốt vòng đời server.
- **Lý do:** Cách đơn giản nhất để tích hợp Claude Desktop / Cursor / Antigravity; không cần quản lý port, auth.
- **Đánh đổi:** Sửa code xong phải restart server (re-index tăng dần → `future/v1.1`). **Không được ghi gì ra stdout ngoài SDK.**
