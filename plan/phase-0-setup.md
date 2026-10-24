# Phase 0 — Setup & Warm-up (24/10 – 25/10)

**Mục tiêu:** Có repo chạy được `mvn test` xanh, logging đúng (stderr), và Khoa "khởi động" các kỹ năng Java sẽ dùng.
**Đọc trước:** `docs/04-tech-and-conventions.md`, `docs/decisions/ADR-001`.

---
### P0-T1 🧰 (S) Khởi tạo repo & Maven skeleton
- Tạo Maven project Java 21 theo package ở `docs/04`, thêm Maven wrapper, `git init`, commit đầu.
- **AC:** `./mvnw -q compile` chạy được. `.gitignore` đã có. Có 1 commit `chore: init project`.
- **Agent phải giải thích:** `groupId/artifactId`, vì sao dùng wrapper, `maven.compiler.release`.

### P0-T2 🧰 (S) Thêm dependency & ghi bảng version
- Tra Maven Central bản mới nhất ổn định của: javaparser (core + symbol-solver), MCP Java SDK, Jackson, SLF4J + backend, JUnit 5, AssertJ. Pin cứng. Điền bảng trong `ADR-001`.
- **AC:** `./mvnw -q dependency:resolve` OK. Không dependency thừa.
- **Lưu ý:** kiểm tra README của MCP SDK để biết artifact chính xác; không đoán.

### P0-T3 🧰 (S) Logging ra stderr
- Cấu hình backend log ghi ra **stderr**. `Main` log một dòng "started" bằng SLF4J.
- **AC:** chạy `Main` và chuyển hướng `1>/dev/null` (Windows: `1>NUL`) vẫn thấy log; stdout trống.
- **Câu hỏi tại sao:** vì sao điều này sống còn với MCP stdio? Chuyện gì xảy ra nếu log lọt vào stdout?

### P0-T4 🧰 (S) [S] CI tối thiểu
- GitHub Actions: checkout → setup-java 21 → `./mvnw -B test`.
- **AC:** push lên GitHub, badge/ tick xanh.

### P0-T5 🧠 (M) Bài khởi động — tự viết, không AI
Trong `src/test/java/.../warmup/` (có thể xoá sau), viết **bằng tay** 5 bài nhỏ, mỗi bài có test AssertJ:
1. `record Point(int x,int y)` + dùng làm key của `HashMap`; giải thích vì sao `record` không cần tự viết `equals/hashCode`.
2. `Map<String, Set<String>>`: hàm `addEdge(from,to)` dùng `computeIfAbsent`.
3. BFS trên đồ thị `Map<String,Set<String>>` bằng `ArrayDeque` + `visited`; có test đồ thị chu trình.
4. `Optional`: hàm tìm theo tên trả `Optional`, không dùng `null`.
5. Stream: `groupingBy` + `counting` đếm số method theo class (dữ liệu giả).
- **AC:** 5 test xanh. Khoa giải thích được từng bài cho agent nghe.
- **Câu hỏi tại sao:** Vì sao `visited` đặt trước hay sau khi `add` vào queue? Khác biệt gì?

---
## Definition of Done Phase 0
- [ ] `mvn test` xanh · [ ] log ra stderr · [ ] ADR-001 điền bảng version · [ ] 5 bài warm-up tự viết xong · [ ] learning log hôm nay
