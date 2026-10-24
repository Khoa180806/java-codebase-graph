# 07 — Fixture Project Spec & Expected Graph

Fixture là một mini-project Java **chỉ để test**, đặt tại:
`src/test/resources/fixtures/shop/src/main/java/fixture/shop/*.java`
(đường dẫn có `src/main/java` để test luôn việc phát hiện source root). **Khoa tự viết các file này** (task P2-T1) — đây cũng là bài luyện OOP.
Fixture KHÔNG nằm trong `src/test/java` nên Maven không compile nó (cho phép cố ý có file lỗi).

## Danh sách file (package `fixture.shop`)
| File | Loại | Nội dung |
|---|---|---|
| `BaseService` | abstract class | `protected void log(String msg)` |
| `PaymentService` | interface | `Receipt pay(Order order)` |
| `VnpayPaymentService` | class implements `PaymentService` | `pay(Order)` gọi `order.total()`, trả `new Receipt(...)` |
| `MockPaymentService` | class implements `PaymentService` | `pay(Order)` trả `new Receipt(...)`, **không** gọi method nào |
| `OrderRepository` | interface | `Order save(Order o)`, `Optional<Order> findById(long id)` |
| `InMemoryOrderRepository` | class implements `OrderRepository` | implement 2 method trên (thân đơn giản, không gọi method) |
| `Order` | class | constructor `Order(long)`, `double total()` |
| `OrderRequest` | record | `(long customerId, double amount)` |
| `Receipt` | record | `(long orderId, boolean ok)` |
| `OrderService` | class extends `BaseService` | field `PaymentService paymentService`, `OrderRepository repository`; xem bên dưới |
| `OrderController` | class | field `OrderService orderService`; `void handleCreate(OrderRequest req)` gọi `orderService.create(req)` |
| `PingService` | class | field `PongService pong`; `void ping(int n)` gọi `pong.pong(n)` |
| `PongService` | class | field `PingService ping`; `void pong(int n)` gọi `ping.ping(n - 1)` |
| `ExternalCalls` | class | `String describe(Order o)`: gọi `String.valueOf(o.total())` rồi `ThirdParty.unknown()` (class `ThirdParty` KHÔNG tồn tại) |
| `Broken` | (file lỗi cú pháp) | cố ý thiếu dấu `}` để test FR-12 |

`OrderService` chi tiết:
- `Order create(OrderRequest req)` → gọi `create(req, false)`
- `Order create(OrderRequest req, boolean express)` → gọi `log("...")`, `repository.save(...)`, `paymentService.pay(...)`
- `void cancel(long id)` → gọi `repository.findById(id)` và `log("...")`

Quy ước để số liệu khớp: chỉ có `MethodCallExpr` (không tính `new X()`), không chain thêm lời gọi nào khác ngoài mô tả trên.

## Expected — Thống kê
| Chỉ số | Giá trị |
|---|---|
| Số file `.java` | 15 (14 hợp lệ + `Broken`) |
| Parse error | 1 (`Broken`) |
| TypeNode | 14 |
| MethodNode (gồm constructor) | 17 |
| Method call tổng | 13 |
| – resolve → trong project (tạo CALLS) | 11 |
| – resolve → ngoài project (JDK) | 1 (`String.valueOf`) |
| – không resolve | 1 (`ThirdParty.unknown`) |

## Expected — Cạnh
**EXTENDS:** `OrderService → BaseService`
**IMPLEMENTS:** `VnpayPaymentService → PaymentService`, `MockPaymentService → PaymentService`, `InMemoryOrderRepository → OrderRepository`
**OVERRIDES:**
- `VnpayPaymentService#pay(fixture.shop.Order)` → `PaymentService#pay(fixture.shop.Order)`
- `MockPaymentService#pay(fixture.shop.Order)` → `PaymentService#pay(fixture.shop.Order)`
- `InMemoryOrderRepository#save(fixture.shop.Order)` → `OrderRepository#save(fixture.shop.Order)`
- `InMemoryOrderRepository#findById(long)` → `OrderRepository#findById(long)`

**CALLS (11):**
1. `OrderService#create(OrderRequest)` → `OrderService#create(OrderRequest,boolean)`  *(overload)*
2. `OrderService#create(OrderRequest,boolean)` → `BaseService#log(String)`  *(method kế thừa)*
3. `OrderService#create(OrderRequest,boolean)` → `OrderRepository#save(Order)`
4. `OrderService#create(OrderRequest,boolean)` → `PaymentService#pay(Order)`
5. `OrderService#cancel(long)` → `OrderRepository#findById(long)`
6. `OrderService#cancel(long)` → `BaseService#log(String)`
7. `OrderController#handleCreate(OrderRequest)` → `OrderService#create(OrderRequest)`
8. `PingService#ping(int)` → `PongService#pong(int)`
9. `PongService#pong(int)` → `PingService#ping(int)`  *(chu trình)*
10. `VnpayPaymentService#pay(Order)` → `Order#total()`
11. `ExternalCalls#describe(Order)` → `Order#total()`
(Tên rút gọn cho dễ đọc; trong test dùng ID đầy đủ có FQN.)

**USES_TYPE (17 cặp, erase generics → `Optional<Order>` chỉ tính `Optional` là ngoài project, KHÔNG tính `Order`):**
- `OrderService → PaymentService, OrderRepository, OrderRequest, Order`
- `PaymentService → Order, Receipt`
- `VnpayPaymentService → Order, Receipt`
- `MockPaymentService → Order, Receipt`
- `OrderRepository → Order`
- `InMemoryOrderRepository → Order`
- `OrderController → OrderService, OrderRequest`
- `PingService → PongService`
- `PongService → PingService`
- `ExternalCalls → Order`

## Expected — Truy vấn
| Truy vấn | Kết quả |
|---|---|
| `find_callers(PaymentService#pay(Order))` | `OrderService#create(OrderRequest,boolean)` — `direct` |
| `find_callers(VnpayPaymentService#pay(Order))` | `OrderService#create(OrderRequest,boolean)` — `interface-dispatch` |
| `find_callers(MockPaymentService#pay(Order))` | như trên |
| `find_callers(Order#total())` | `VnpayPaymentService#pay`, `ExternalCalls#describe` — đều `direct` |
| `find_callers("OrderService#create")` | `ambiguous`, 2 candidates (2 overload) |
| `find_callers("log")` | `ambiguous` hoặc xử lý theo spec; không được âm thầm chọn bừa |
| `impact_analysis(Order, depth 3)` | depth1 = {OrderService, PaymentService, VnpayPaymentService, MockPaymentService, OrderRepository, InMemoryOrderRepository, ExternalCalls}; depth2 = {OrderController}; depth3 = {} |
| `impact_analysis(PingService, depth 5)` | depth1 = {PongService}; kết thúc, không lặp vô hạn |
| `get_class_structure(OrderService)` | extends `BaseService`, 2 field, 3 method |
| `get_class_structure(PaymentService)` | subtypes = {Vnpay, Mock} |

> Nếu Khoa viết fixture khác một chút (thêm/bớt dòng) thì **cập nhật bảng này cùng lúc**; bảng là hợp đồng của test.
