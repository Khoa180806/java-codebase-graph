# Phase 2 — Fixture, Graph Model, Pass 1 (01/11 – 03/11)

**Mục tiêu:** Có `CodeGraph` bất biến với node TYPE/METHOD và các cạnh cấu trúc; có fixture + test integration.
**Đọc trước:** `docs/03`, `docs/07`, ADR-002/003. **⚠️ Làm P2-T2 TRƯỚC khi đọc `docs/03` sâu.**
**Khái niệm cần nắm:** adjacency list, forward/reverse index, immutability, `equals/hashCode`, erasure.

---
### P2-T1 🧠 (M) Viết fixture project
- Tự viết các file theo `docs/07-fixture-spec.md` vào `src/test/resources/fixtures/shop/src/main/java/fixture/shop/`. Gồm cả `Broken.java` (lỗi cú pháp cố ý).
- **AC:** Khớp bảng spec (số file, số method, số call). Chạy parse-only (P1-T6) trên fixture → 15 file, 1 lỗi.
- **Câu hỏi tại sao:** Vì sao fixture đặt trong `resources`, không phải `src/test/java`?

### P2-T2 🧠 (M) Tự thiết kế trước (design note)
- Viết `docs/my-design-note.md` (≤ 1 trang): em định danh node thế nào? Lưu cạnh thế nào? Cần những loại cạnh nào để trả lời 3 tool? Đã nghĩ tới overload / interface / chu trình chưa?
- **Sau đó** mới so với `docs/03-domain-model.md`; ghi lại điểm khác và vì sao.
- **AC:** Có note + phần "so sánh". Điền "Lý do bằng lời của Khoa" ở ADR-002 và ADR-003.

### P2-T3 🧠 (M) Model: `TypeNode`, `MethodNode`, `EdgeType`
- Theo `docs/03`. Bất biến. Collection trả ra immutable.
- **AC:** Test equals/hashCode theo `id`; không thể sửa danh sách bên trong từ bên ngoài.
- **Câu hỏi tại sao:** Nếu `equals` dùng mọi field thay vì `id` thì rủi ro gì khi node đi qua 2 pass?

### P2-T4 🧠 (L) `CodeGraph` + `GraphBuilder`
- `GraphBuilder`: `addType`, `addMethod`, `addEdge(type, from, to)`; `build()` trả `CodeGraph` bất biến. `CodeGraph`: `outgoing(edge, id)`, `incoming(edge, id)`, `findType`, `findMethod`, `allTypes`, `allMethods`.
- Duplicate: thêm node/cạnh trùng → idempotent hay lỗi? **Chọn và ghi lý do** (ADR nhỏ hoặc comment).
- **AC:** Unit test đồ thị dựng tay: forward và reverse luôn nhất quán; cạnh tới node không tồn tại bị từ chối hoặc ghi nhận theo quy tắc đã chọn.
- **Câu hỏi tại sao:** (1) Độ phức tạp `incoming` có/không có reverse index? (2) `build()` có nên copy dữ liệu? (3) `Set` loại nào để output ổn định?

### P2-T5 🧠 (M) Chuẩn hoá tên kiểu (một hàm duy nhất)
- Hàm tạo `methodId(ownerFqn, name, paramTypes)` và chuẩn hoá param type (erase generics, varargs → `[]`, FQN). Mọi chỗ tạo ID đều dùng hàm này.
- **AC:** Test bảng: `List<String>`→`java.util.List`, `int...`→`int[]`, `T`→ quy tắc đã chọn, `Map.Entry<K,V>`→`java.util.Map.Entry`.
- **Câu hỏi tại sao:** Nếu pass 1 và pass 2 tạo ID khác nhau cho cùng một method thì hậu quả là gì? (đây là bug phổ biến nhất của dự án)

### P2-T6 🧰 (M) `SolverFactory` (cấu hình type solver)
- Dựng `CombinedTypeSolver`: `ReflectionTypeSolver` (JDK) + một `JavaParserTypeSolver` cho mỗi source root; gắn vào `ParserConfiguration` (`JavaSymbolSolver`).
- Agent được viết nhưng **phải giải thích từng solver làm gì và thứ tự ưu tiên**; Khoa sau đó tự viết lại 1 test chứng minh hiểu.
- **AC:** `resolve()` một kiểu trong fixture (ví dụ `PaymentService` trong `OrderService`) ra đúng FQN.

### P2-T7 🧠 (L) Pass 1: `ParsedType` → graph
- Tạo TYPE/METHOD node; cạnh `DECLARES`; `EXTENDS`/`IMPLEMENTS` (resolve tên sang FQN qua solver; supertype ngoài project → `externalSupertypes`); `USES_TYPE` từ field/param/return (bỏ qua kiểu nguyên thuỷ và `java.*`).
- **AC (integration, fixture):** 14 TypeNode · 17 MethodNode · EXTENDS/IMPLEMENTS/DECLARES/USES_TYPE khớp `docs/07`. `Broken.java` được đếm lỗi, không làm sập.
- **Câu hỏi tại sao:** Vì sao pass 1 chưa đụng tới `CALLS`? (gợi ý: thứ tự tạo node)

---
## Definition of Done Phase 2
- [ ] Fixture khớp spec · [ ] design note + ADR điền · [ ] CodeGraph test xanh · [ ] integration pass 1 xanh · [ ] quiz Phase 2 đạt
