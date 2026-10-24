# ADR-001: Java 21 + Maven, pin phiên bản
- **Trạng thái:** Accepted
- **Quyết định:** Java 21 (LTS), Maven + wrapper. Mọi dependency pin version cố định, ghi bảng version vào cuối file này ở Phase 0.
- **Lý do:** Java 21 có record, switch expression, pattern matching; Maven phổ biến trong tuyển dụng Java và là nền cho Spring Boot ở Dự án 2.
- **Đánh đổi:** Gradle linh hoạt hơn nhưng thêm một thứ phải học.

## Bảng phiên bản (điền ở P0-T2)
| Dependency | Version | Ngày kiểm tra |
|---|---|---|
| javaparser-core / symbol-solver-core | _TBD_ | |
| mcp (Java SDK) | _TBD_ | |
| jackson | _TBD_ | |
| slf4j + backend | _TBD_ | |
| junit-jupiter | _TBD_ | |
| assertj-core | _TBD_ | |
