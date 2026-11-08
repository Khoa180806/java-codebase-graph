package dev.khoa.codegraph.parser;

import java.util.List;

/**
 * DTO đại diện cho lớp/kiểu trong code.
 */
public record ParsedType(
        TypeKind kind, String name,
        String packageName, List<String> modifiers,
        List<String> extendsTypes, List<String> implementsTypes,
        List<String> imports,
        List<ParsedField> fields,
        List<ParsedMethod> methods, List<ParsedType> nestedTypes) {

    public ParsedType {
        packageName = packageName == null ? "" : packageName;
        modifiers = modifiers == null ? List.of() : List.copyOf(modifiers);
        extendsTypes = extendsTypes == null ? List.of() : List.copyOf(extendsTypes);
        implementsTypes = implementsTypes == null ? List.of() : List.copyOf(implementsTypes);
        imports = imports == null ? List.of() : List.copyOf(imports);
        fields = fields == null ? List.of() : List.copyOf(fields);
        methods = methods == null ? List.of() : List.copyOf(methods);
        nestedTypes = nestedTypes == null ? List.of() : List.copyOf(nestedTypes);
    }

    public String fullyQualifiedName() {
        return packageName.isEmpty() ? name : packageName + "." + name;
    }
}
