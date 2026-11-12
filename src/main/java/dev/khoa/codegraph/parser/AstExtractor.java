package dev.khoa.codegraph.parser;

import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.RecordDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.nodeTypes.NodeWithImplements;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import java.util.Objects;

/**
 * Trích xuất cây cú pháp trừu tượng (AST) từ CompilationUnit thành danh sách ParsedType DTO.
 */
public class AstExtractor {

    public List<ParsedType> extract(CompilationUnit cu) {
        Objects.requireNonNull(cu, "CompilationUnit must not be null");
        String packageName = cu.getPackageDeclaration()
                .map(pd -> pd.getNameAsString())
                .orElse("");

        List<String> imports = cu.getImports().stream()
                .map(id -> id.getNameAsString())
                .toList();

        List<ParsedType> result = new ArrayList<>();

        for (TypeDeclaration<?> typeDecl : cu.getTypes()) {
            String typeName = typeDecl.getNameAsString();

            List<String> typeModifiers = typeDecl.getModifiers().stream()
                    .map(m -> m.getKeyword().asString())
                    .toList();

            TypeKind kind = switch (typeDecl) {
                case ClassOrInterfaceDeclaration coid when coid.isInterface() -> TypeKind.INTERFACE;
                case RecordDeclaration rd -> TypeKind.RECORD;
                case EnumDeclaration ed -> TypeKind.ENUM;
                default -> TypeKind.CLASS;
            };

            List<String> extendsList = (typeDecl instanceof ClassOrInterfaceDeclaration coid)
                    ? coid.getExtendedTypes().stream().map(ClassOrInterfaceType::asString).toList()
                    : List.of();

            List<String> implementsList = (typeDecl instanceof NodeWithImplements<?> nwi)
                    ? nwi.getImplementedTypes().stream().map(ClassOrInterfaceType::asString).toList()
                    : List.of();

            List<ParsedField> fields = new ArrayList<>();
            if (typeDecl instanceof RecordDeclaration rd) {
                for (Parameter param : rd.getParameters()) {
                    fields.add(new ParsedField(
                            param.getNameAsString(),
                            param.getTypeAsString(),
                            List.of("private", "final")));
                }
            }

            for (FieldDeclaration fd : typeDecl.getFields()) {
                List<String> fieldModifiers = fd.getModifiers().stream()
                        .map(m -> m.getKeyword().asString())
                        .toList();
                for (VariableDeclarator vd : fd.getVariables()) {
                    fields.add(new ParsedField(
                            vd.getNameAsString(),
                            vd.getTypeAsString(),
                            fieldModifiers));
                }
            }

            List<ParsedMethod> methods = new ArrayList<>();
            for (MethodDeclaration md : typeDecl.getMethods()) {
                String name = md.getNameAsString();
                String returnType = md.getTypeAsString();
                List<String> paramTypes = md.getParameters().stream()
                        .map(p -> p.getTypeAsString()).toList();
                List<String> methodModifiers = md.getModifiers().stream()
                        .map(m -> m.getKeyword().asString()).toList();
                int beginLine = md.getRange().map(r -> r.begin.line).orElse(0);
                int endLine = md.getRange().map(r -> r.end.line).orElse(0);

                methods.add(new ParsedMethod(name, returnType, paramTypes, methodModifiers, false, beginLine, endLine));
            }

            for (ConstructorDeclaration cd : typeDecl.getConstructors()) {
                String name = cd.getNameAsString();
                String returnType = "";
                List<String> paramTypes = cd.getParameters().stream()
                        .map(p -> p.getTypeAsString()).toList();
                List<String> ctorModifiers = cd.getModifiers().stream()
                        .map(m -> m.getKeyword().asString()).toList();
                int beginLine = cd.getRange().map(r -> r.begin.line).orElse(0);
                int endLine = cd.getRange().map(r -> r.end.line).orElse(0);

                methods.add(new ParsedMethod(name, returnType, paramTypes, ctorModifiers, true, beginLine, endLine));
            }

            result.add(new ParsedType(kind, typeName, packageName, typeModifiers, extendsList, implementsList, imports,
                    fields,
                    methods, List.of()));
        }

        return result;
    }
}
