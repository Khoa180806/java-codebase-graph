# Phase 7 — Graph Viewer (TUỲ CHỌN, có cổng vào)

**Cổng vào (cả hai phải đúng):** (1) CP2 đạt đúng 13/11; (2) Phase 5 xong trước 17/11.
Không đạt → bỏ Phase 7, chuyển thành bản sau-Dự-án-2 (ADR-007, `docs/09`). **Tuyệt đối không lấn sang 22/11.**
**Timebox:** tối đa 2 ngày làm việc. Quá thì dừng, đóng gói phần có.
**Đọc trước:** `docs/09`, ADR-007, mục "FE cũ" trong `docs/legacy-audit.md`.
**Khái niệm cần nắm:** schema versioning, component/state trong React, BFS ở client, giới hạn render đồ thị.

---
### P7-T1 🧠 (S) Thiết kế JSON schema
- Chốt schema dựa trên `docs/09`, ghi lại điều chỉnh. Quyết định: có xuất `edges` thô hay xuất sẵn caller/impact?
- **AC:** Schema + 1 file ví dụ `viewer/sample/fixture-graph.json`.
- **Câu hỏi tại sao:** Vì sao cần `schemaVersion`? Điều gì xảy ra nếu bỏ nó rồi đổi format?

### P7-T2 🧠 (M) `GraphJsonExporter` + cờ CLI
- Xuất graph + stats ra file theo schema; sắp xếp ổn định; **không ghi stdout**.
- **AC:** Test trên fixture: số type=14, method=17, CALLS=11; chạy 2 lần ra file giống hệt nhau.

### P7-T3 🧰 (S) Scaffold viewer
- Agent tạo Vite + React + TypeScript trong `viewer/`, thêm thư viện vẽ đồ thị, giải thích cấu trúc thư mục & `tsconfig`. Chốt Cytoscape.js hay D3 bằng ADR nhỏ.
- **AC:** `npm run dev` hiển thị trang trống; `npm run build` OK.

### P7-T4 🧠 (M) Load file + Explorer + ClassDetail
- Đọc JSON (kiểm `schemaVersion`), định nghĩa kiểu TypeScript cho schema, tìm kiếm, panel chi tiết.
- **AC:** Mở `fixture-graph.json`: tìm `OrderService` thấy extends `BaseService`, 3 method, 2 field.
- **Câu hỏi tại sao:** Vì sao nên định nghĩa `type`/`interface` TS cho JSON thay vì dùng `any`?

### P7-T5 🧠 (L) Graph view: Callers / Impact
- BFS ở client (có `visited`, `depth`) → dựng phần tử đồ thị → vẽ. Click node đổi tâm. Giới hạn số node.
- **AC:** `Order` + Impact depth 3 ra đúng 3 mức như `docs/07`. Ping↔Pong không treo trình duyệt. Có Vitest cho hàm BFS.
- **Hint L1:** Tách hàm BFS thuần (không biết React/Cytoscape) để test được.

### P7-T6 🧠 (S) Stats panel + demo
- Hiển thị stats; chụp ảnh/GIF cho README; thêm 1 đoạn vào video demo.
- **AC:** README có ảnh viewer.

---
## Definition of Done Phase 7
- [ ] Cổng vào đã kiểm · [ ] export test xanh · [ ] viewer đọc fixture + 1 repo thật · [ ] BFS client có test · [ ] ảnh/GIF trong README
