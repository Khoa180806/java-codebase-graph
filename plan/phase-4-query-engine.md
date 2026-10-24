# Phase 4 — Query Engine (10/11 – 13/11)

**Mục tiêu:** 3 truy vấn chạy đúng trên `CodeGraph`, **không biết gì về MCP**.
**Đọc trước:** `docs/03` (mục "Quy tắc ngữ nghĩa truy vấn"), `docs/06`, `docs/07` (bảng truy vấn).
**Khái niệm cần nắm:** BFS, reverse reachability, visited set, đồ thị có chu trình, độ phức tạp O(V+E).
**⏱ Checkpoint CP2 = 13/11.**

---
### P4-T1 🧠 (M) `MethodLookup` — xử lý tên mơ hồ
- Input: chuỗi (`ID đầy đủ` | `SimpleType#name` | `FQN#name`) → kết quả: `Found(method)` | `Ambiguous(candidates)` | `NotFound`.
- **AC:** Unit test: overload (`OrderService#create` → 2 candidates), trùng tên giữa class khác nhau, không tồn tại.
- **Câu hỏi tại sao:** Vì sao trả `Ambiguous` thay vì chọn đại một cái? Agent LLM xử lý kết quả này thế nào?

### P4-T2 🧠 (M) `StructureQuery`
- `getClassStructure(name)` theo spec `docs/06`: thông tin type, supertypes, subtypes trực tiếp, fields, methods. Tên type mơ hồ → ambiguous.
- **AC:** Test trên đồ thị dựng tay + fixture (`OrderService`, `PaymentService` → subtypes {Vnpay, Mock}).

### P4-T3 🧠 (L) `CallerQuery` với interface dispatch
- `callers(m)` = reverse CALLS của `m` ∪ reverse CALLS của mọi method mà `m` OVERRIDES (đi lên cả chuỗi, có `visited`). Gắn `via`.
- **AC:** Khớp 5 dòng `find_callers` trong bảng `docs/07`. Test hand-built: override 2 tầng; một caller vừa direct vừa dispatch (chọn và ghi quy tắc — ưu tiên `direct`).
- **Câu hỏi tại sao:** (1) Vì sao interface dispatch cần thiết cho tính đúng đắn? (2) Có nên đi *xuống* (callers của method cha được xem là callers của method con)? Ở đây ta chỉ đi **lên** — vì sao hướng đó đúng cho câu hỏi "ai gọi `Vnpay#pay`"?

### P4-T4 🧠 (L) `ImpactAnalyzer`
- BFS ngược từ type T theo 3 quan hệ (callers của method của T [kèm dispatch], `USES_TYPE`, subtypes). Theo dõi depth; `visited`; `maxDepth` (mặc định 3, trần 10); `truncated`; reasons; loại chính T; mỗi type xuất hiện ở depth nhỏ nhất.
- **AC:** Khớp 2 dòng `impact_analysis` trong `docs/07`.
- **Hint L1:** Hàng đợi chứa gì: type hay (type, depth)? Khi nào đánh dấu `visited` để depth đúng là nhỏ nhất?
- **Câu hỏi tại sao:** (1) Điều gì xảy ra với Ping↔Pong nếu thiếu `visited`? (2) BFS vs DFS — vì sao BFS cho depth nhỏ nhất? (3) Độ phức tạp?

### P4-T5 🧠 (M) Unit test bằng đồ thị dựng tay
- Bắt buộc: chu trình; `maxDepth` cắt đúng + `truncated=true`; đồ thị 1 node; type không có ai phụ thuộc; fan-in lớn (nhiều caller); thứ tự output ổn định.
- **AC:** Mỗi test có tên `should_..._when_...` và 1 câu comment "vì sao test này tồn tại".

### P4-T6 🧠 (S) Ghi chú phức tạp
- Trong learning log: phân tích O() của 3 query + bộ nhớ của `CodeGraph` cho repo 1000 class / 10 000 method.

### P4-T7 🧠 (S) Checkpoint CP2
- Đánh giá theo cut lines; ghi `STATUS.md`.

---
## Definition of Done Phase 4
- [ ] Tất cả dòng truy vấn trong `docs/07` đúng · [ ] test hand-built đủ danh sách · [ ] quiz Phase 4 đạt
