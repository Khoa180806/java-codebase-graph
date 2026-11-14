package dev.khoa.codegraph.parser;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.github.javaparser.ast.CompilationUnit;

public class AstExtractorTest {
    @Test
    void should_extract_class_with_extends_implements_fields_and_constructors() {
        String code = """
                package com.example;
                import java.io.Serializable;
                public class User extends BaseEntity implements Serializable {
                    private int id;
                    public User(int id) { this.id = id; }
                    public int getId() { return id; }
                }
                                """;
        JavaCodeParser parser = new JavaCodeParser();
        CompilationUnit cu = parser.parse(code).getResult().orElseThrow();
        AstExtractor astExtractor = new AstExtractor();
        var result = astExtractor.extract(cu);

        // Assert: kind == TypeKind.CLASS, name == "User", packageName == "com.example".
        assertThat(result.get(0).kind()).isEqualTo(TypeKind.CLASS);
        assertThat(result.get(0).name()).isEqualTo("User");
        assertThat(result.get(0).packageName()).isEqualTo("com.example");

        // Assert: extendsTypes chứa "BaseEntity", implementsTypes chứa "Serializable"
        assertThat(result.get(0).extendsTypes()).contains("BaseEntity");
        assertThat(result.get(0).implementsTypes()).contains("Serializable");

        // Assert: fields có 1 field "id".
        assertThat(result.get(0).fields().get(0).name()).isEqualTo("id");

        // Assert: methods có 1 constructor (isConstructor == true) và 1 method (getId).
        assertThat(result.get(0).methods().getLast().isConstructor()).isTrue();
        assertThat(result.get(0).methods().get(0).name()).isEqualTo("getId");

    }

    @Test
    void should_extract_interface_enum_and_record() {
        String tempInterface = """
                public interface Greeter {
                    String greet(String name);
                }
                """;
        String tempRecord = """
                public record Point(int x, int y) {
                }
                """;
        String tempEnum = """
                public enum Status {
                    PENDING,
                    ACTIVE,
                    COMPLETED,
                    FAILED
                }
                """;

        JavaCodeParser parser1 = new JavaCodeParser();
        CompilationUnit cu1 = parser1.parse(tempInterface).getResult().orElseThrow();
        AstExtractor astExtractor1 = new AstExtractor();
        var result1 = astExtractor1.extract(cu1);

        JavaCodeParser parser2 = new JavaCodeParser();
        CompilationUnit cu2 = parser2.parse(tempRecord).getResult().orElseThrow();
        AstExtractor astExtractor2 = new AstExtractor();
        var result2 = astExtractor2.extract(cu2);

        JavaCodeParser parser3 = new JavaCodeParser();
        CompilationUnit cu3 = parser3.parse(tempEnum).getResult().orElseThrow();
        AstExtractor astExtractor3 = new AstExtractor();
        var result3 = astExtractor3.extract(cu3);

        // Assert: Nhận diện đúng từng TypeKind tương ứng (INTERFACE, RECORD, ENUM).
        assertThat(result1.get(0).kind()).isEqualTo(TypeKind.INTERFACE);
        assertThat(result2.get(0).kind()).isEqualTo(TypeKind.RECORD);
        assertThat(result3.get(0).kind()).isEqualTo(TypeKind.ENUM);

        // Assert: Point trích xuất đúng 2 fields "x" và "y".
        assertThat(result2.get(0).fields().get(0).name()).isEqualTo("x");
        assertThat(result2.get(0).fields().get(1).name()).isEqualTo("y");
    }

    @Test
    void should_extract_overloaded_methods() {
        String code = """
                public class Calculator {
                    public int add(int a, int b){}
                    public double add(double a, double b){}
                    }
                """;
        JavaCodeParser parser = new JavaCodeParser();
        CompilationUnit cu = parser.parse(code).getResult().orElseThrow();
        AstExtractor astExtractor = new AstExtractor();
        var result = astExtractor.extract(cu);

        assertThat(result.get(0).methods()).extracting(ParsedMethod::name).containsOnly("add");
        assertThat(result.get(0).methods().get(0).parameterTypes())
                .isNotEqualTo(result.get(0).methods().get(1).parameterTypes());

    }
}
