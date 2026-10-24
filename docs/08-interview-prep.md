# 08 — Interview Prep (dùng để agent quiz Khoa)

Sau mỗi phase, agent chọn 3-5 câu và hỏi Khoa **trả lời bằng lời của mình** trước khi cho gợi ý.

## Phase 1 — Parsing
- AST là gì? Khác gì với chuỗi token và với bytecode?
- `CompilationUnit` / `ClassOrInterfaceDeclaration` / `MethodDeclaration` quan hệ với nhau thế nào?
- Vì sao một file `.java` có thể chứa nhiều type? Xử lý nested class thế nào?
- File lỗi cú pháp thì làm gì? Vì sao không để cả chương trình crash?

## Phase 2 — Graph model
- Vì sao dùng signature đầy đủ làm ID? Ví dụ sai khi chỉ dùng tên?
- Vì sao lưu cả forward và reverse adjacency? Độ phức tạp?
- `HashMap` vs `LinkedHashMap` vs `TreeMap` cho kết quả ổn định?
- Vì sao `equals/hashCode` quan trọng nếu node làm key? `record` giúp gì?
- Tại sao collection trả ra phải immutable?

## Phase 3 — Symbol resolution
- Parse và resolve khác nhau thế nào? Vì sao `foo.bar()` cần type solver?
- `ReflectionTypeSolver` vs `JavaParserTypeSolver` — mỗi cái giải quyết gì?
- Vì sao có `UnsolvedSymbolException`? Chiến lược xử lý và vì sao không nuốt im lặng?
- Overload resolution hoạt động thế nào ở mức khái niệm?
- Method kế thừa (`log` từ `BaseService`): resolver trả về declaring class nào?
- Hạn chế: generics, lambda, reflection — vì sao khó?

## Phase 4 — Queries
- BFS vs DFS cho impact analysis, chọn gì và vì sao?
- Vì sao cần `visited` set? Chuyện gì xảy ra với Ping↔Pong khi thiếu nó?
- Độ phức tạp thời gian/không gian của impact analysis?
- Interface dispatch: vì sao caller của `PaymentService#pay` có thể thực chất chạy `VnpayPaymentService#pay`?
- Vì sao cần `maxDepth`?

## Phase 5 — MCP
- MCP là gì, giải quyết vấn đề gì? Khác gì REST API và function calling thuần?
- Vì sao stdio transport không được `System.out.println`?
- Tool `description` ảnh hưởng thế nào tới hành vi LLM?
- Vì sao trả về `ambiguous + candidates` thay vì đoán?

## Phase 6 — Tổng kết
- Giải thích end-to-end từ `main()` đến JSON kết quả trong 2 phút.
- Hạn chế lớn nhất của dự án? Em sẽ cải thiện gì tiếp theo? (→ `future/`)
- So với language server / Sourcegraph, dự án này khác ở đâu? (Trả lời trung thực: học tập, phạm vi hẹp.)
- Số liệu: bao nhiêu file, bao lâu, resolve bao nhiêu %?
- Một bug khó nhất em gặp và cách debug?
