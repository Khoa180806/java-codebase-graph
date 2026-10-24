# Phase 3 — Symbol Resolution: CALLS, OVERRIDES, Stats (04/11 – 09/11)

**Mục tiêu:** Với mỗi method call, biết nó gọi method nào (trong project). Đây là **phần khó nhất** của dự án.
**Đọc trước:** `docs/03`, ADR-003, ADR-004, `docs/07` (bảng CALLS).
**Khái niệm cần nắm:** symbol resolution, overload resolution, declaring class vs static type, `UnsolvedSymbolException`.
**⏱ Checkpoint CP1 = 07/11:** CALLS đúng trên fixture. Chưa đạt → áp dụng cut line trong `00-roadmap.md`.

---
### P3-T1 🧠 (M) Spike: làm quen solver
- Viết 1 test thăm dò (có thể xoá sau): với từng `MethodCallExpr` trong `OrderService`, in `call.resolve()` → declaring type, signature. Thử các ca: gọi overload, gọi `log` (kế thừa), gọi qua interface.
- Ghi vào learning log: solver trả về gì cho `paymentService.pay(o)` — interface hay class cài đặt? Vì sao?
- **AC:** Khoa mô tả được bằng lời vì sao kết quả là vậy.

### P3-T2 🧠 (S) `ResolutionStats`
- Cấu trúc đếm: tổng call, `INTERNAL`, `EXTERNAL`, `UNRESOLVED` (kèm top lý do), file parse lỗi. Có hàm tính tỷ lệ %, và in tóm tắt.
- **AC:** Unit test cộng dồn + tỷ lệ; không chia cho 0 khi rỗng.

### P3-T3 🧠 (L) `CallResolver`
- Duyệt từng method body: `findAll(MethodCallExpr.class)` → resolve → quyết định outcome:
  - Đích thuộc project (có node trong graph theo method ID) → `INTERNAL`.
  - Resolve được nhưng ngoài project → `EXTERNAL`.
  - Exception do không resolve được → `UNRESOLVED` + lý do (bắt **đúng loại exception**, không `catch (Exception)` bừa).
- Method ID của đích tạo bằng **cùng hàm chuẩn hoá** ở P2-T5.
- **Hint L1:** Chú ý method có thể nằm trong constructor/initializer/lambda — v1.0 chỉ cần method + constructor body.
- **Hint L2:** `MethodCallExpr#resolve()` → `ResolvedMethodDeclaration`; `declaringType()`, `getSignature()`, `paramType`.
- **AC:** Trên fixture: 13 call = 11 INTERNAL + 1 EXTERNAL + 1 UNRESOLVED.
- **Câu hỏi tại sao:** (1) Vì sao không nuốt lỗi im lặng? (2) Hậu quả nếu ID đích khác ID node? (3) Lời gọi trong lambda thì sao — em chọn bỏ qua hay xử lý, vì sao?

### P3-T4 🧠 (M) Ghi cạnh `CALLS` vào graph
- Chạy pass 2: thêm CALLS (from = method chứa call, to = method đích). Cạnh trùng (gọi 2 lần cùng đích) → theo quy tắc idempotent đã chọn; **ghi line của call** nếu muốn (số lần gọi ≠ số cạnh — ghi quyết định).
- **AC:** 11 cạnh CALLS khớp danh sách ở `docs/07`, kể cả overload (#1) và inherited `log` (#2, #6).

### P3-T5 🧠 (M) `OverrideResolver` → cạnh `OVERRIDES`
- Với mỗi method của type, đi lên chuỗi EXTENDS/IMPLEMENTS (trong project), tìm method cùng tên + cùng param types ⇒ OVERRIDES.
- **AC:** 4 cạnh OVERRIDES khớp `docs/07`. Test thêm ca: override qua 2 tầng (A←B←C) bằng đồ thị dựng tay.
- **Câu hỏi tại sao:** Vì sao so khớp bằng **param types** chứ không chỉ tên? Covariant return / generics làm gì với quy tắc này (ghi lại hạn chế)?

### P3-T6 🧠 (M) Test tổng hợp trên fixture
- Test kiểm cả graph: khớp **toàn bộ** bảng expected (cạnh + thống kê). Dùng `containsExactlyInAnyOrder`.
- **AC:** `mvn test` xanh.

### P3-T7 🧠 (M) Thử trên repo Java thật
- Chọn 1 repo nhỏ-vừa (đồ án cũ của Khoa hoặc một dự án mã nguồn mở nhỏ). Chạy, thu: số file, thời gian, `ResolutionStats`, **top 5 lý do UNRESOLVED**.
- Ghi vào `docs/real-repo-notes.md`. **Đây là số liệu cho README/CV.**
- **AC:** Không crash; có số liệu; có nhận định "vì sao tỷ lệ chưa 100%" bằng lời của Khoa.

### P3-T8 🧠 (S) Checkpoint CP1
- Tự đánh giá theo cut lines. Ghi quyết định vào `progress/STATUS.md`.

---
## Definition of Done Phase 3
- [ ] Fixture: CALLS/OVERRIDES/Stats đúng · [ ] real-repo-notes · [ ] ADR-003/004 cập nhật nếu thực tế khác · [ ] quiz Phase 3 đạt

> Gợi ý tâm lý: tỷ lệ resolve 70-90% là **bình thường**. Quan trọng là trung thực và đo được.
