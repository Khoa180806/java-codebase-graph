package dev.khoa.codegraph.parser;

import java.util.List;

/**
 * DTO đại diện cho một thuộc tính/field trong code.
 */
public record ParsedField(String name, String type, List<String> modifiers) {
    public ParsedField {
        modifiers = modifiers == null ? List.of() : List.copyOf(modifiers);
    }
}
