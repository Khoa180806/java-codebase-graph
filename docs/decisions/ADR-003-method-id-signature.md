# ADR-003: Method ID = FQN#name(paramTypes) 🧠
- **Trạng thái:** Accepted
- **Quyết định:** Xem `docs/03-domain-model.md`. Param types dùng FQN, erase generics, không khoảng trắng.
- **Lý do:** Phân biệt overload và method trùng tên giữa các class.
- **Đánh đổi:** ID dài; phải chuẩn hoá kiểu (varargs `int...` → `int[]`, nested `Outer.Inner`) nhất quán giữa pass 1 (từ AST) và pass 2 (từ symbol solver). **Đây là nguồn bug chính** — cần 1 hàm chuẩn hoá duy nhất dùng cho cả hai pass.
- **Rủi ro cần test:** generic type param (`T`) → erase thành bound hoặc `java.lang.Object`. Chọn và ghi lại.
- **Lý do bằng lời của Khoa:** _(điền ở P2)_
