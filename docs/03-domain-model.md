# 03 — Domain Model (QUYẾT ĐỊNH THIẾT KẾ QUAN TRỌNG NHẤT)

> Khoa tự suy nghĩ phần này trước (Phase 2) rồi đối chiếu. Tài liệu này là phương án tham chiếu.

## Định danh (ID)
- **Type ID** = fully-qualified name. `com.app.order.OrderService`. Nested: `com.app.order.Outer.Inner`.
- **Method ID** = `<TypeFQN>#<name>(<paramType1>,<paramType2>)`
  - Tham số dùng **FQN đã erase generics**, phân cách bằng dấu phẩy không khoảng trắng.
  - Ví dụ: `com.app.OrderService#create(com.app.OrderRequest)`, `com.app.OrderService#create(com.app.OrderRequest,boolean)`, `com.app.Util#sum(int[])`, `com.app.Repo#save(java.lang.Object)`.
  - Constructor: tên là `<init>`. Ví dụ `com.app.Order#<init>(long)`.
- Vì sao không chỉ dùng tên? Overload và trùng tên giữa các class (`save`, `get`) sẽ làm kết quả sai. **Câu hỏi cho Khoa:** nếu key chỉ là `"create"`, sẽ sai ở đâu trong fixture?

## Node
```
TypeNode   { id, simpleName, kind(CLASS|INTERFACE|ENUM|RECORD|ANNOTATION), modifiers,
             filePath, line, externalSupertypes:List<String>, fields:List<FieldInfo> }
MethodNode { id, ownerTypeId, name, paramTypes:List<String>, returnType, modifiers,
             isConstructor, isAbstract, filePath, line }
FieldInfo  { name, type }          // field KHÔNG là node, chỉ là thuộc tính của TypeNode
```
Dùng Java `record` cho các lớp dữ liệu bất biến nếu hợp lý (Khoa tự cân nhắc).

## Edge
| Edge | Từ → Đến | Ý nghĩa | Ghi chú |
|---|---|---|---|
| `DECLARES` | Type → Method | type khai báo method | dùng để liệt kê method của class |
| `EXTENDS` | Type → Type | kế thừa | supertype ngoài project → `externalSupertypes`, không tạo edge |
| `IMPLEMENTS` | Type → Type | implement interface | tương tự |
| `CALLS` | Method → Method | method gọi method | chỉ khi resolve được đích **nằm trong project** |
| `USES_TYPE` | Type → Type | dùng làm kiểu của field / param / return | edge có thuộc tính `usage` = FIELD / PARAM / RETURN |
| `OVERRIDES` | Method → Method | method con override/implement method cha | tính ở pass 2, dựa trên EXTENDS/IMPLEMENTS + name + param types |

## Cấu trúc dữ liệu gợi ý
```
Map<String, TypeNode>   types
Map<String, MethodNode> methods
Map<EdgeType, Map<String, Set<String>>> outgoing     // from → tos
Map<EdgeType, Map<String, Set<String>>> incoming     // to → froms
```
**Câu hỏi cho Khoa:** tại sao nên có cả `incoming`? Chi phí nếu chỉ có `outgoing` mà cần "ai gọi method này"? (Gợi ý: độ phức tạp thời gian.)
Dùng `LinkedHashSet`/`TreeSet` hay `HashSet`? → ảnh hưởng thứ tự output và tính ổn định của test.

## Quy tắc ngữ nghĩa truy vấn

### getClassStructure(type)
Trả: thông tin type, supertypes (project + external), fields, methods (từ DECLARES), subtypes trực tiếp (reverse EXTENDS/IMPLEMENTS).

### findCallers(method)
`callers(m) = reverse(CALLS)[m]  ∪  ⋃ reverse(CALLS)[p]` với `p` ∈ mọi method mà `m` OVERRIDES (đi lên cả chuỗi).
Lý do (interface dispatch): code viết `paymentService.pay(o)` với `paymentService` kiểu interface → graph ghi cạnh tới `PaymentService#pay`, nhưng runtime có thể chạy `VnpayPaymentService#pay`. Mỗi caller gắn `via: "direct" | "interface-dispatch"`.

### impactAnalysis(type, maxDepth)
"Nếu sửa type T thì những type nào bị ảnh hưởng?" — BFS ngược từ T với các quan hệ:
1. Type nào **gọi** method của T (reverse CALLS vào các method T khai báo, kèm interface dispatch).
2. Type nào **USES_TYPE** T.
3. **Subtype** của T (reverse EXTENDS/IMPLEMENTS).
Kết quả nhóm theo depth, mỗi item có `reason` (loại edge dẫn tới) và `path` ngắn gọn.
- Bắt buộc có `visited` (đồ thị có chu trình: A gọi B, B gọi A → không có visited sẽ lặp vô hạn).
- `maxDepth` mặc định 3, tối đa 10.
- Loại chính T khỏi kết quả.
