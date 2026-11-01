package dev.khoa.codegraph.ingestion;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

/**
 * DTO chứa kết quả quét dự án gồm danh sách các file .java và source roots.
 */
public record ScanResult(List<Path> javaFiles, List<Path> sourceRoots) {
    public ScanResult {
        Objects.requireNonNull(javaFiles, "javaFiles must not be null");
        Objects.requireNonNull(sourceRoots, "sourceRoots must not be null");
        javaFiles = List.copyOf(javaFiles);
        sourceRoots = List.copyOf(sourceRoots);
    }
}
