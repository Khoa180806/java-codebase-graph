package dev.khoa.codegraph.ingestion;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Quét thư mục dự án để tìm các file mã nguồn Java và xác định source roots,
 * đồng thời bỏ qua các thư mục build hoặc version control.
 */
public class ProjectScanner {
    private static final Set<String> IGNORED_DIRS = Set.of(".git", "target", "build", "out", "node_modules");

    public ScanResult scan(Path root) throws IOException {
        List<Path> javaFiles = new ArrayList<>();
        Objects.requireNonNull(root, "Path must not be null!");

        if (!Files.isDirectory(root)) {
            throw new IllegalArgumentException("Path is not a valid directory: " + root);
        }

        Files.walkFileTree(root, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                if (dir.getFileName() != null && IGNORED_DIRS.contains(dir.getFileName().toString())
                        && !dir.equals(root)) {
                    return FileVisitResult.SKIP_SUBTREE;
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                    throws IOException {

                if (file.toString().endsWith(".java") && Files.isRegularFile(file)) {
                    javaFiles.add(file);
                }
                return FileVisitResult.CONTINUE;
            }
        });

        Path mainSrc = root.resolve("src/main/java");
        List<Path> sourceRoots = null;

        if (mainSrc != null && Files.isDirectory(mainSrc)) {
            sourceRoots = List.of(mainSrc);
        } else {
            sourceRoots = List.of(root);
        }
        return new ScanResult(javaFiles, sourceRoots);
    }
}
