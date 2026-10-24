# Future — Các MVP sau v1.0

> **Quy tắc:** Chỉ bắt đầu một version trong thư mục này khi (1) `v1.0.0` đã được tag, (2) Dự án 2 (Spring Boot) đã có bản chạy được, (3) Khoa vẫn còn thời gian trước đợt nộp hồ sơ.
> Mỗi version là một MVP độc lập, có thể làm riêng, theo thứ tự gợi ý bên dưới.

| Version | File | Chủ đề | Giá trị phỏng vấn | Độ khó |
|---|---|---|---|---|
| v1.1 | `v1.1-incremental-reindex.md` | Re-index tăng dần + file watcher | Trung bình (I/O, concurrency nhẹ) | ★★ |
| v1.2 | `v1.2-more-mcp-tools.md` | Thêm tool & truy vấn | Trung bình | ★★ |
| v1.3 | `v1.3-resolution-upgrade.md` | Resolve tốt hơn (generics, lambda, JAR classpath) | **Cao** (độ sâu Java) | ★★★★ |
| v1.4 | `v1.4-metrics-and-cycles.md` | Chu trình phụ thuộc, coupling, dead code | **Cao** (thuật toán đồ thị) | ★★★ |
| v1.5 | `v1.5-persistence-and-export.md` | Lưu graph, xuất Mermaid/DOT | Thấp-TB | ★★ |
| v1.6 | `v1.6-spring-aware.md` | Hiểu Spring (DI, endpoint) | **Cao** (khớp JD Spring Boot) | ★★★ |
| v1.7 | `v1.7-live-viewer.md` | Viewer chế độ live qua HTTP, tương tác sâu | TB-Cao (fullstack) | ★★★ |
| v2.0 | `v2.0-http-transport.md` | MCP qua HTTP, multi-project | Trung bình | ★★★ |

**Thứ tự đề xuất theo lợi ích CV:** v1.6 → v1.4 → v1.3 → v1.2 → còn lại.
