# ADR-002: Tự dựng graph thay vì JGraphT 🧠
- **Trạng thái:** Accepted
- **Quyết định:** Tự cài đặt `CodeGraph` bằng `Map`/`Set` (forward + reverse adjacency).
- **Lý do:** Mục tiêu là học và giải thích được mọi dòng. Đồ thị ở đây đơn giản (vài loại edge, BFS ngược). JGraphT che mất phần thú vị (cấu trúc dữ liệu, traversal).
- **Đánh đổi:** Mất thuật toán có sẵn (shortest path, SCC...). Nếu cần ở `future/v1.4`, cân nhắc lại hoặc tự cài Tarjan để học.
- **Lý do bằng lời của Khoa:** _(điền ở P2)_
