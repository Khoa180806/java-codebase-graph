package dev.khoa.codegraph.parser;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class ParserDtoTest {
    @Test
    void should_be_immutable_when_modifying_dto_lists() {
        List<String> modifiers = new ArrayList<>();
        ParsedField parsedField = new ParsedField("id", "int", modifiers);

        assertThatThrownBy(() -> parsedField.modifiers().add("static"))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    void should_verify_equals_and_hash_code_for_records() {
        ParsedMethod parsedMethod1 = new ParsedMethod("create", "int", List.of("User"), List.of("public"), false, 10,
                20);
        ParsedMethod parsedMethod2 = new ParsedMethod("create", "int", List.of("User"), List.of("public"), false, 10,
                20);

        assertThat(parsedMethod1).isEqualTo(parsedMethod2);
        assertThat(parsedMethod1.hashCode()).isEqualTo(parsedMethod2.hashCode());
    }

    @Test
    void should_return_correct_fully_qualified_name() {
        ParsedType parsedType1 = new ParsedType(TypeKind.CLASS, "MyClass", "dev.khoa", List.of("public"), null,
                null, null, null, null, null);
        ParsedType parsedType2 = new ParsedType(TypeKind.CLASS, "MyClass", "", List.of("public"), null, null, null,
                null, null, null);

        assertThat(parsedType1.fullyQualifiedName()).isEqualTo("dev.khoa.MyClass");
        assertThat(parsedType2.fullyQualifiedName()).isEqualTo("MyClass");
    }
}
