# ADR-001: Java 21 + Maven, pin phiên bản
- **Trạng thái:** Accepted
- **Quyết định:** Java 21 (LTS), Maven + wrapper. Mọi dependency pin version cố định, ghi bảng version vào cuối file này ở Phase 0.
- **Lý do:** Java 21 có record, switch expression, pattern matching; Maven phổ biến trong tuyển dụng Java và là nền cho Spring Boot ở Dự án 2.
- **Đánh đổi:** Gradle linh hoạt hơn nhưng thêm một thứ phải học.

## Bảng phiên bản (điền ở P0-T2)
| Dependency | Version | Ngày kiểm tra |
|---|---|---|
| javaparser-core / symbol-solver-core | 3.28.2 | 25/10/2026 |
| mcp (Java SDK) | 2.1.0 (mcp-bom) | 25/10/2026 |
| jackson | Đi kèm qua `mcp` (Jackson 3 / 3.1.4) | 25/10/2026 |
| slf4j + backend | 2.0.20 (slf4j-api + slf4j-simple) | 25/10/2026 |
| junit-jupiter | 5.14.4 | 25/10/2026 |
| assertj-core | 3.27.7 | 25/10/2026 |
