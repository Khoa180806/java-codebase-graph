# 05 — Testing Strategy

## Nguyên tắc
Test là bằng chứng Khoa hiểu code. Mỗi thuật toán cốt lõi phải có test mà Khoa **giải thích được vì sao test đó tồn tại**.

## Ba tầng test
1. **Unit thuần (không parse file):** `CodeGraph`, `CallerQuery`, `ImpactAnalyzer` test trên đồ thị **dựng tay** vài node. Nhanh, tập trung vào thuật toán.
   - Bắt buộc có: chu trình (A→B→A), `maxDepth` cắt đúng, interface dispatch, tên method trùng giữa 2 class, method overload.
2. **Integration trên fixture:** parse `src/test/resources/fixtures/shop` → so graph với bảng expected trong `07-fixture-spec.md`.
3. **MCP smoke test:** gọi trực tiếp handler của 3 tool với graph fixture, kiểm tra JSON trả về. (Thử với client thật là việc thủ công ở Phase 5.)

## Quy ước
- Khẳng định bằng AssertJ (`assertThat(...).containsExactlyInAnyOrder(...)`).
- Không phụ thuộc thứ tự `HashSet` — nếu cần thứ tự, dùng cấu trúc có thứ tự và nói rõ trong test.
- Fixture là **dữ liệu test**, không nằm trong `src/test/java` (tránh bị compile cùng dự án).
  → Cần đảm bảo Maven không compile fixture (đặt trong `resources`, không phải `java`).
- Chạy `mvn test` trước mỗi commit lớn.

## Tiêu chí
Không đặt % coverage cứng. Đặt tiêu chí: mọi hàm trong `query` và `GraphBuilder` có ít nhất 1 test happy path + 1 test edge case.
