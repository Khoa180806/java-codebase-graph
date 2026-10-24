# 09 — Graph Export & Viewer (Phase 7, tuỳ chọn)

## 1. Export (backend)
`java -jar codegraph-mcp.jar --project-path <dir> --export-json out/graph.json`
- Ghi **ra file**, không ghi stdout. Xuất xong thì thoát (không khởi động MCP) trừ khi có thêm cờ khác — Khoa quyết định và ghi lại.
- `schemaVersion` bắt buộc; viewer từ chối file có version không hỗ trợ.

```json
{
  "schemaVersion": 1,
  "project": { "path": "…", "indexedAt": "2026-11-18T10:00:00Z" },
  "stats": { "files": 15, "parseErrors": 1, "calls": 13, "internal": 11, "external": 1, "unresolved": 1 },
  "types":   [ { "id": "fixture.shop.OrderService", "kind": "CLASS", "file": "…", "line": 8,
                 "fields": [ { "name": "paymentService", "type": "PaymentService" } ] } ],
  "methods": [ { "id": "fixture.shop.OrderService#cancel(long)", "owner": "fixture.shop.OrderService",
                 "returnType": "void", "line": 30 } ],
  "edges":   [ { "type": "CALLS", "from": "…#create(…)", "to": "…#log(java.lang.String)" } ]
}
```
Sắp xếp mảng ổn định (theo id) để file diff được và test so sánh được.

## 2. Viewer (frontend) — tối giản, 3 màn
| Màn | Nội dung |
|---|---|
| **Load & Stats** | Chọn/kéo-thả `graph.json`; hiển thị số type/method, tỷ lệ resolve, số file lỗi |
| **Explorer** | Ô tìm type/method (lọc theo chuỗi); chọn → panel chi tiết (giống `get_class_structure`: extends/implements/subtypes/fields/methods) |
| **Graph** | Chọn 1 node + chế độ **Callers** hoặc **Impact** + `depth` (1–5) → vẽ đồ thị lân cận. Màu theo loại cạnh; click node để chuyển tâm |

## 3. Quy tắc
- Viewer **tự tính** caller/impact từ `edges` ở client **hoặc** chỉ hiển thị — chọn 1 và ghi ADR nhỏ. Khuyến nghị: tính ở client bằng BFS (tái dùng đúng thuật toán đã học ở Phase 4, bằng TypeScript; có `visited`).
- Không vẽ toàn graph. Giới hạn số node vẽ (ví dụ 200) và hiển thị cảnh báo khi cắt.
- Không state manager nặng; không UI library nặng. Ưu tiên chạy được và đọc được.
- Xử lý lỗi: file sai định dạng / sai version → thông báo rõ.
- Test: ít nhất unit test cho hàm BFS ở client (Vitest) trên graph chu trình.
