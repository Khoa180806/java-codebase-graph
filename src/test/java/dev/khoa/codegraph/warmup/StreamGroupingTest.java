package dev.khoa.codegraph.warmup;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

record MethodData(String className, String methodName) {
}

public class StreamGroupingTest {

    @Test
    void shouldCountMethodsPerClassWhenGroupingByClassName() {
        List<MethodData> methods = List.of(
                new MethodData("OrderService", "createOrder"),
                new MethodData("OrderService", "deleteOrder"),
                new MethodData("UserService", "getUserProfile"),
                new MethodData("PaymentService", "processPayment"),
                new MethodData("PaymentService", "refundPayment"),
                new MethodData("PaymentService", "getPaymentHistory"));

        Map<String, Long> methodCountByClass = methods.stream()
                .collect(Collectors.groupingBy(MethodData::className, Collectors.counting()));

        assertThat(methodCountByClass)
                .containsEntry("OrderService", 2L)
                .containsEntry("UserService", 1L)
                .containsEntry("PaymentService", 3L);
    }
}