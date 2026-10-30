package dev.khoa.codegraph.warmup;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

public class OptionalLookupTest {

    @Test
    void shouldFindMethodWhenMatchingPrefixExists() {
        List<String> methods = List.of("createName", "deleteName", "findName");

        Optional<String> opt = findMethodSignature(methods, "create");

        assertThat(opt).isPresent().hasValue("createName");
    }

    @Test
    void shouldReturnEmptyWhenNoMethodMatchesPrefix() {
        List<String> methods = List.of("createName", "deleteName", "findName");

        Optional<String> opt = findMethodSignature(methods, "get");

        assertThat(opt).isEmpty();
        assertThat(opt.orElse("fallback")).isEqualTo("fallback");
    }

    Optional<String> findMethodSignature(List<String> methods, String methodName) {
        for (String method : methods) {
            if (method.startsWith(methodName)) {
                return Optional.of(method);
            }
        }
        return Optional.empty();
    }
}
