package dev.khoa.codegraph.ingestion;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ProjectScannerTest {

    @Test
    void should_find_java_files_and_detect_source_root_when_standard_maven_project(@TempDir Path tempDir)
            throws IOException {
        // 1. Tạo thư mục src/main/java/com/example/ chứa file App.java
        Path srcDir = tempDir.resolve("src/main/java/com/example/");
        Files.createDirectories(srcDir);
        Path appFile = Files.createFile(srcDir.resolve("App.java"));

        // 2. Tạo các thư mục rác để kiểm tra bộ lọc
        Path targetDir = tempDir.resolve("target");
        Files.createDirectories(targetDir);
        Files.createFile(targetDir.resolve("Fake.java"));

        Path gitDir = tempDir.resolve(".git");
        Files.createDirectories(gitDir);
        Files.createFile(gitDir.resolve("dummy.txt"));

        Path buildDir = tempDir.resolve("build");
        Files.createDirectories(buildDir);
        Files.createFile(buildDir.resolve("Build.java"));

        // 3. Tiến hành quét thư mục gốc tempDir
        ProjectScanner projectScanner = new ProjectScanner();
        ScanResult result = projectScanner.scan(tempDir);

        // 4. Assert kết quả quét dùng AssertJ
        assertThat(result).isNotNull();

        // Kiểm tra javaFiles: Chỉ chứa đúng duy nhất file App.java
        assertThat(result.javaFiles()).containsExactly(appFile);

        // Kiểm tra sourceRoots: Trỏ đúng vào thư mục src/main/java
        Path expectedSourceRoot = tempDir.resolve("src/main/java");
        assertThat(result.sourceRoots()).containsExactly(expectedSourceRoot);
    }

    @Test
    void should_fallback_to_root_when_project_is_flat(@TempDir Path tempDir) throws IOException {
        // 1. Cấu hình dự án phẳng: Tạo file FlatApp.java ngay tại tempDir
        Path flatAppFile = Files.createFile(tempDir.resolve("FlatApp.java"));

        // 2. Tiến hành quét thư mục gốc tempDir
        ProjectScanner projectScanner = new ProjectScanner();
        ScanResult result = projectScanner.scan(tempDir);

        // 3. Assert: javaFiles chứa FlatApp.java và sourceRoots fallback về chính tempDir
        assertThat(result).isNotNull();
        assertThat(result.javaFiles()).containsExactly(flatAppFile);
        assertThat(result.sourceRoots()).containsExactly(tempDir);
    }

    @Test
    void should_throw_illegal_argument_exception_when_path_is_invalid(@TempDir Path tempDir) throws IOException {
        ProjectScanner projectScanner = new ProjectScanner();

        // Kịch bản 1: Quét một thư mục không tồn tại
        Path nonExistentPath = tempDir.resolve("does-not-exist");

        assertThatThrownBy(() -> projectScanner.scan(nonExistentPath))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Path is not a valid directory");

        // Kịch bản 2: Quét một file thông thường (không phải directory)
        Path regularFile = Files.createFile(tempDir.resolve("RegularFile.txt"));

        assertThatThrownBy(() -> projectScanner.scan(regularFile))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Path is not a valid directory");
    }
}
