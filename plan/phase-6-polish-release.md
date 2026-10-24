# Phase 6 — Polish & Release (19/11 – 21/11)

**Mục tiêu:** Đóng gói để người khác (và nhà tuyển dụng) hiểu trong 2 phút. **Tag v1.0.0 vào 21/11.**
**Không thêm tính năng mới ở phase này.** Ý tưởng mới → `future/IDEAS.md`.

---
### P6-T1 🧠 (M) Đo số liệu thật
- Bảng: repo | số file | số type | số method | thời gian index | % resolve | top lý do unresolved. Ít nhất fixture + 1-2 repo thật.
- **AC:** Số liệu nằm trong README (mục "Results"). Không số bịa.

### P6-T2 🧠 (L) README.md tiếng Anh
Cấu trúc: What & Why · Demo (GIF/video) · Quick start (build + chạy + cấu hình client) · Tools (3 tool, ví dụ I/O) · How it works (sơ đồ Mermaid) · Design decisions (trích ADR) · **Limitations (trung thực)** · Results · Roadmap (`future/`).
- Khoa **tự viết**; agent chỉ review ngữ pháp/độ rõ.
- **AC:** Người lạ làm theo Quick start chạy được trong 5 phút (nhờ bạn bè thử nếu được).

### P6-T3 🧰 (S) Sơ đồ kiến trúc Mermaid
- Agent hỗ trợ vẽ dựa trên `docs/02`; Khoa kiểm tra đúng với code thật.

### P6-T4 🧠 (M) Video demo 2 phút
- Kịch bản: (0:00) vấn đề · (0:20) chạy server trên 1 repo · (0:40) hỏi agent "ai gọi X" · (1:10) hỏi impact · (1:35) giới hạn + số liệu · (1:50) kết.
- **AC:** Video ≤ 2:15, nói rõ ràng, link trong README.

### P6-T5 🧠 (M) Dọn dẹp
- Xoá code chết, `System.out` còn sót (`grep`), TODO không còn ý nghĩa, import thừa. `mvn verify` xanh. Javadoc 1-2 dòng cho class public.
- **AC:** Checklist ở `AGENTS.md` mục 7 không vi phạm.

### P6-T6 🧠 (M) Diễn tập phỏng vấn
- Ghi âm bản thân giải thích end-to-end trong 2 phút (không nhìn code). Agent hỏi ngẫu nhiên 10 câu trong `docs/08` (tập trung câu "Phase 6").
- **AC:** Trả lời được ≥ 8/10 không cần gợi ý. Câu nào hụt → ôn và ghi vào learning log.

### P6-T7 🧰 (S) Release
- Tag `v1.0.0`, GitHub Release đính kèm jar, ghi changelog ngắn.

### P6-T8 🧠 (S) Viết bullet CV
- 3 bullet có số liệu, ví dụ khung: *"Built a static-analysis tool in Java 21 using JavaParser + SymbolSolver, resolving X% of method calls on N-file projects; exposed call-graph queries to AI agents via an MCP server (3 tools)."* — điền số thật.

---
## Definition of Done v1.0
Xem danh sách ở `docs/00-overview.md` mục "Định nghĩa xong". Sau đó **dừng** và chuyển sang Dự án 2 vào 22/11.
