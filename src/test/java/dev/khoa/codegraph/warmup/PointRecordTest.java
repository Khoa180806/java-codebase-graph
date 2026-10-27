package dev.khoa.codegraph.warmup;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.assertj.core.api.Assertions.assertThat;

class PointRecordTest {

    // Khai báo record Point ở đây (hoặc bên trong test class)
    record Point(int x, int y) {
    }

    @Test
    void shouldFindValueInMapWhenUsingEqualPointKey() {
        // 1. Arrange: Khởi tạo Map<Point, String> và 2 instance Point có cùng x, y
        Map<Point, String> map = new HashMap<>();
        Point point1 = new Point(1, 2);
        Point point2 = new Point(1, 2);

        // 2. Act: Put điểm thứ nhất vào Map, sau đó Get ra bằng điểm thứ hai
        map.put(point1, "Value1");
        String result = map.get(point2);

        // 3. Assert: Dùng assertThat(...).isEqualTo(...) để kiểm tra kết quả get được
        assertThat(result).isEqualTo("Value1");
    }
}
