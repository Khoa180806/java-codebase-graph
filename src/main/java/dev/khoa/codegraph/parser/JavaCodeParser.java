package dev.khoa.codegraph.parser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ParseResult;
import com.github.javaparser.ParserConfiguration;
import com.github.javaparser.ast.CompilationUnit;

/**
 * Phân tích và chuyển đổi mã nguồn Java thành cấu trúc dạng cây (AST).
 */
public class JavaCodeParser {
    private final JavaParser javaParser;

    public JavaCodeParser() {
        this(new ParserConfiguration().setLanguageLevel(ParserConfiguration.LanguageLevel.JAVA_21));
    }

    public JavaCodeParser(ParserConfiguration config) {
        Objects.requireNonNull(config, "config must not be null");
        this.javaParser = new JavaParser(config);
    }

    public ParseResult<CompilationUnit> parse(String code) {
        Objects.requireNonNull(code, "code must not be null");
        return javaParser.parse(code);
    }

    public ParseResult<CompilationUnit> parse(Path path) throws IOException {
        Objects.requireNonNull(path, "path must not be null");
        return javaParser.parse(path);
    }
}
