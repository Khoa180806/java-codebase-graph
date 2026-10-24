# ADR-006: Không nhận file .zip
- **Trạng thái:** Accepted
- **Quyết định:** Chỉ nhận đường dẫn thư mục.
- **Lý do:** Giải nén input không tin cậy gây rủi ro zip-slip và cần xử lý thêm, không đóng góp vào mục tiêu học tập (AST, graph, MCP).
- **Đánh đổi:** Người dùng phải giải nén tay. Chấp nhận được.
