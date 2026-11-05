package dev.khoa.codegraph.parser;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JavaCodeParserTest {

    @Test
    void should_Parse_Complete_When_Record_String_Is_Valid() {
        String javaCode = """
                record Point(int x) {}
                """;

        JavaCodeParser parser = new JavaCodeParser();
        var result = parser.parse(javaCode);

        assertThat(result.isSuccessful()).isTrue();
    }

    @Test
    void should_Parse_Incomplete_When_Record_String_Is_Not_Valid() {
        String javaCode = """
                public class Broken { int a = ; }
                """;
        JavaCodeParser parser = new JavaCodeParser();
        var result = parser.parse(javaCode);

        assertThat(result.isSuccessful()).isFalse();
        assertThat(result.getProblems().get(0).getMessage()).contains("error");
    }

    @Test
    void should_Parse_Complete_When_File_Is_Valid(@TempDir Path tempDir) throws IOException {
        Path file = tempDir.resolve("File.java");

        String javaCode = """
                public class File {
                    private int a;
                }
                """;

        Files.writeString(file, javaCode);

        JavaCodeParser parser = new JavaCodeParser();

        var result = parser.parse(file);

        assertThat(result.isSuccessful()).isTrue();
    }
}
