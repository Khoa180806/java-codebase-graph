package dev.khoa.codegraph.parser;

import java.util.List;

/**
 * DTO đại diện cho một phương thức/hàm trong code.
 */
public record ParsedMethod(String name, String returnType, List<String> parameterTypes, List<String> modifiers,
        boolean isConstructor, int beginLine, int endLine) {
    public ParsedMethod {
        parameterTypes = parameterTypes == null ? List.of() : List.copyOf(parameterTypes);
        modifiers = modifiers == null ? List.of() : List.copyOf(modifiers);
    }
}
