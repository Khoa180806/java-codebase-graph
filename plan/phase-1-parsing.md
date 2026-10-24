# Phase 1 — Scanning & Parsing (26/10 – 31/10)

**Mục tiêu:** Từ một thư mục project → danh sách `ParsedType` (chưa có đồ thị).
**Đọc trước:** `docs/01` (FR-01..12), `docs/02`, `docs/05`. Tài liệu JavaParser chính thức (agent kiểm tra phiên bản đã pin).
**Khái niệm cần nắm:** AST, `CompilationUnit`, `ParseResult`, Visitor vs `findAll`, FQN.

---
### P1-T1 🧠 (M) Audit codebase đồ án cũ
- Tạo `docs/legacy-audit.md`: liệt kê từng class/package của đồ án cũ, cột **Giữ / Xoá / Viết lại**, và cột "Em giải thích được không (Y/N)".
- Mục đích: biết chính xác lỗ hổng. Không sửa code cũ ở bước này.
- Ghi riêng mục **FE cũ**: dùng framework gì, hiển thị gì, gọi backend ra sao, em hiểu được bao nhiêu %. Quyết định: tái dùng ý tưởng/màn hình cho Phase 7, hay viết lại.
- **AC:** Mọi thứ đánh "N" được đưa vào `future/IDEAS.md` hoặc thành task học thêm. Dự án mới chỉ lấy ý tưởng, **viết lại từ đầu**.

### P1-T2 🧠 (M) `ProjectScanner`
- Input: `Path root`. Output: danh sách file `.java` + danh sách source roots.
- Bỏ qua `target/ build/ .git/ out/ node_modules/`. Root không tồn tại / không phải thư mục → lỗi rõ ràng (exception có ý nghĩa hoặc result type).
- **AC:** Test dùng `@TempDir` (tạo thư mục giả): tìm đúng file, bỏ đúng thư mục, phát hiện `src/main/java`, fallback khi không có.
- **Hint L1:** `Files.walkFileTree` vs `Files.walk` — khác nhau gì, cái nào cắt nhánh thư mục sớm được?
- **Câu hỏi tại sao:** (1) Vì sao cần cắt nhánh `target/` sớm thay vì lọc sau? (2) `Files.walk` cần đóng stream — vì sao? (3) Symlink gây vấn đề gì?

### P1-T3 🧠 (S) Parse một file → `CompilationUnit`
- Hàm nhận `Path`/`String`, trả kết quả gồm CU hoặc lỗi. Dùng `ParseResult` đúng cách (đừng gọi `.get()` mù quáng).
- **AC:** Test với source hợp lệ và source lỗi cú pháp (chuỗi inline). File lỗi → trả lỗi, không throw.
- **Câu hỏi tại sao:** `StaticJavaParser` vs `JavaParser` instance + `ParserConfiguration`: khác gì? Language level cần đặt là gì để parse record?

### P1-T4 🧠 (S) DTO của parser
- Định nghĩa `ParsedType`, `ParsedMethod`, `ParsedField` (khuyến nghị `record`). Chỉ chứa **dữ liệu thuần**, chưa phụ thuộc JavaParser class.
- **AC:** Compile; có test construct + equals.
- **Câu hỏi tại sao:** Vì sao không để `ClassOrInterfaceDeclaration` rò rỉ ra ngoài tầng `parser`?

### P1-T5 🧠 (L) `AstExtractor`
- Từ `CompilationUnit` → `List<ParsedType>`: kind (class/interface/enum/record), tên, package, imports, `extends`/`implements` (tên **như viết trong code**), modifiers, fields, methods + constructors (tên, tham số, return type, vị trí dòng).
- Type lồng nhau: v1.0 hỗ trợ cơ bản (nếu trễ thì cắt — xem CP1).
- **Hint L1:** `cu.findAll(TypeDeclaration.class)` trả gì? Có bao gồm nested không? Kiểm chứng bằng thí nghiệm nhỏ.
- **Hint L2:** `TypeDeclaration`, `ClassOrInterfaceDeclaration`, `EnumDeclaration`, `RecordDeclaration`, `getFullyQualifiedName()`.
- **AC:** Test inline cho: class thường, interface, enum, record, class có extends+implements, overload, constructor.
- **Câu hỏi tại sao:** (1) AST khác text-search/regex ở điểm nào? (2) Method trong interface không có body — xử lý sao? (3) Vì sao tên `extends` ở bước này chưa phải FQN?

### P1-T6 🧠 (S) Wire-up CLI "parse-only"
- `Main` nhận `--project-path`, scan → parse → log tóm tắt ra **stderr**: số file, số type, số method, số file lỗi, thời gian.
- **AC:** chạy trên chính repo này và trên repo đồ án cũ không crash.

---
## Definition of Done Phase 1
- [ ] ≥ 8 test xanh · [ ] chạy parse-only trên repo thật · [ ] `legacy-audit.md` · [ ] learning log mỗi ngày · [ ] quiz Phase 1 (docs/08) đạt
