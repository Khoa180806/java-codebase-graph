# ADR-004: Call không resolve được → bỏ qua, ghi log, đếm
- **Trạng thái:** Accepted
- **Quyết định:** Không ném exception ra ngoài `CallResolver`. Mỗi `MethodCallExpr` rơi vào 1 trong 3 nhóm: `INTERNAL` (tạo CALLS), `EXTERNAL` (resolve được nhưng ngoài project/JDK), `UNRESOLVED` (kèm lý do). Tổng hợp vào `ResolutionStats`.
- **Lý do:** Static analysis không bao giờ hoàn hảo; trung thực với giới hạn tốt hơn là hứa hẹn quá. Số liệu resolve-rate là điểm cộng khi phỏng vấn.
- **Đánh đổi:** `find_callers` có thể thiếu caller → output tool phải kèm ghi chú giới hạn.
- **Yêu cầu:** Có timeout/giới hạn cho resolve của từng call nếu thấy treo (ghi lại nếu gặp).
