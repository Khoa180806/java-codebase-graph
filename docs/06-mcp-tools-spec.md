# 06 — Đặc tả 3 MCP Tool

## Khởi động
```
java -jar codegraph-mcp.jar --project-path /path/to/java/project [--max-depth-default 3]
```
- Transport: **stdio**. Index 1 lần lúc khởi động. Log → **stderr**.
- Tên tool dùng `snake_case`. `description` của tool phải đủ rõ để LLM biết **khi nào nên gọi** (đây là phần ảnh hưởng chất lượng khi agent dùng tool).
- Kết quả trả về là 1 `TextContent` chứa **JSON** (pretty-print không cần thiết). Lỗi nghiệp vụ → JSON có `"status": "error"` và `isError=true` nếu SDK hỗ trợ.

> ⚠️ API cụ thể của Java MCP SDK (cách tạo server, đăng ký tool, kiểu `CallToolResult`) thay đổi theo phiên bản.
> Agent phải đọc README/Javadoc của đúng phiên bản đã pin, không viết theo trí nhớ.

---
## Tool 1 — `get_class_structure`
**Mục đích:** xem cấu trúc một class/interface: field, method, quan hệ kế thừa.
**Input**
```json
{ "className": "string (FQN hoặc simple name)" }
```
- Simple name trùng nhiều FQN → trả `status: "ambiguous"` + `candidates`.

**Output (found)**
```json
{
  "status": "ok",
  "type": { "id": "fixture.shop.OrderService", "kind": "CLASS", "file": "…/OrderService.java", "line": 8 },
  "extends": ["fixture.shop.BaseService"],
  "implements": [],
  "externalSupertypes": [],
  "subtypes": [],
  "fields": [ { "name": "paymentService", "type": "PaymentService" } ],
  "methods": [
    { "id": "fixture.shop.OrderService#create(fixture.shop.OrderRequest)", "returnType": "Order", "modifiers": ["public"] }
  ]
}
```
**Output (không thấy)** `{ "status": "not_found", "query": "...", "suggestions": ["..."] }` (suggestions: tên gần giống, tối đa 5 — [S]).

---
## Tool 2 — `find_callers`
**Mục đích:** ai gọi method này? (dùng trước khi sửa/đổi signature).
**Input**
```json
{ "method": "string — method ID đầy đủ HOẶC 'SimpleType#name' HOẶC 'FQN#name'" }
```
- Nếu khớp nhiều method (overload / nhiều class) → `status: "ambiguous"` + `candidates` (là các method ID đầy đủ) để agent gọi lại.

**Output**
```json
{
  "status": "ok",
  "target": "fixture.shop.PaymentService#pay(fixture.shop.Order)",
  "callers": [
    { "method": "fixture.shop.OrderService#create(fixture.shop.OrderRequest,boolean)",
      "via": "direct", "file": "…", "line": 21 }
  ],
  "count": 1
}
```
`via`: `"direct"` hoặc `"interface-dispatch"` (caller thực ra gọi method cha mà target override).
Ghi chú giới hạn trong output: `"note": "Chỉ gồm call resolve được trong project."` (trung thực với giới hạn).

---
## Tool 3 — `impact_analysis`
**Mục đích:** sửa class này thì còn class nào cần xem lại?
**Input**
```json
{ "className": "string", "maxDepth": "integer, optional, default 3, max 10" }
```
**Output**
```json
{
  "status": "ok",
  "target": "fixture.shop.Order",
  "maxDepth": 3,
  "truncated": false,
  "levels": [
    { "depth": 1, "items": [ { "type": "fixture.shop.OrderService", "reasons": ["USES_TYPE"] } ] },
    { "depth": 2, "items": [ { "type": "fixture.shop.OrderController", "reasons": ["USES_TYPE","CALLS"], "from": "fixture.shop.OrderService" } ] }
  ],
  "totalAffected": 8
}
```
- `truncated: true` nếu còn node ở độ sâu vượt `maxDepth`.
- Mỗi type chỉ xuất hiện **một lần** (ở depth nhỏ nhất).
- Sắp xếp item trong level theo tên để output ổn định.

---
## Thử với client thật (Phase 5)
Claude Desktop (`claude_desktop_config.json`) hoặc Cursor/Antigravity MCP config: khai báo command `java` với args `-jar … --project-path …`.
Kiểm tra: agent liệt kê được 3 tool, gọi được, kết quả khớp kỳ vọng. Chụp ảnh/quay màn hình làm bằng chứng cho README.
