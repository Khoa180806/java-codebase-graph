# 01 — Requirements (MVP v1.0)

Ký hiệu: **[M]** Must · **[S]** Should (làm nếu còn thời gian) · **[X]** Out of scope v1.0 (xem `future/`)

## Ingestion
- [M] FR-01: Nhận đường dẫn thư mục project Java, duyệt đệ quy lấy các file `.java`.
- [M] FR-02: Bỏ qua thư mục `target/`, `build/`, `.git/`, `node_modules/`, `out/`.
- [M] FR-03: Xác định **source roots** (mặc định `src/main/java`; nếu không có thì dùng chính thư mục gốc) để cấu hình type solver.
- [X] Nhận file `.zip`.

## Parsing
- [M] FR-10: Với mỗi file, trích xuất class / interface / enum / record (kể cả nested ở mức cơ bản).
- [M] FR-11: Với mỗi type: tên đầy đủ (FQN), kind, modifiers, `extends`, `implements`, danh sách field (tên + kiểu), danh sách method/constructor (tên, tham số, kiểu trả về).
- [M] FR-12: File parse lỗi cú pháp → ghi log + đếm, **không dừng** cả quá trình.

## Graph
- [M] FR-20: Node: `TYPE`, `METHOD`. Edge: `EXTENDS`, `IMPLEMENTS`, `DECLARES`, `CALLS`, `USES_TYPE`, `OVERRIDES` (xem `03-domain-model.md`).
- [M] FR-21: Định danh method bằng **signature đầy đủ**, không chỉ bằng tên.
- [M] FR-22: Lưu cả **forward** và **reverse** adjacency để truy vấn 2 chiều hiệu quả.
- [M] FR-23: `ResolutionStats`: tổng số method call, số resolve được, số không resolve được (+ top lý do), số file lỗi.

## Query
- [M] FR-30: `getClassStructure(type)`.
- [M] FR-31: `findCallers(method)` có xử lý interface dispatch (callers của method mà nó override) và trường hợp tên mơ hồ.
- [M] FR-32: `impactAnalysis(type, maxDepth)` — reverse reachability, có `visited` set, chịu được chu trình, có giới hạn độ sâu.

## MCP
- [M] FR-40: MCP server transport **stdio**, nhận `--project-path <dir>` lúc khởi động và index một lần.
- [M] FR-41: Expose 3 tool: `get_class_structure`, `find_callers`, `impact_analysis`.
- [M] FR-42: Lỗi/không tìm thấy trả về kết quả có cấu trúc, thông báo rõ ràng (không stack trace thô).

## Export & Viewer (Phase 7 — [S], có điều kiện)
- [S] FR-50: CLI `--export-json <file>` xuất graph + `ResolutionStats` ra **file** (không ghi stdout).
- [S] FR-51: `viewer/` (React + TypeScript + Vite) mở file JSON local: tìm type/method, xem cấu trúc class, vẽ đồ thị caller/impact quanh một node, hiển thị stats.
- [X] Backend HTTP / chế độ live → `future/v1.7-live-viewer.md`.

## Non-functional
- [M] NFR-01: Java 21, Maven, JUnit 5 + AssertJ.
- [M] NFR-02: Log ra **stderr** (SLF4J + backend nhẹ). Không `System.out`.
- [S] NFR-03: Index repo ~500 file < 30 giây trên laptop thường (ghi số đo thực tế vào README).
- [M] NFR-04: Không dependency ngoài `docs/04`.

## Tiêu chí chấp nhận cấp dự án
Chạy trên fixture (`07-fixture-spec.md`): graph sinh ra khớp bảng expected edges. Chạy trên 1 repo Java thật cỡ nhỏ: không crash, có `ResolutionStats` hợp lý.
