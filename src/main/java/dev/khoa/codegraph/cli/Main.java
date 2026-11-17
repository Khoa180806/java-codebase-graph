package dev.khoa.codegraph.cli;

import java.nio.file.Path;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.khoa.codegraph.ingestion.ProjectScanner;
import dev.khoa.codegraph.ingestion.ScanResult;
import dev.khoa.codegraph.parser.AstExtractor;
import dev.khoa.codegraph.parser.JavaCodeParser;

/**
 * Entry point for Java Codebase Graph MCP Server.
 */
public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        try {
            Path projectPath = Path.of(".");
            for (int i = 0; i < args.length; i++) {
                if ("--project-path".equals(args[i]) && i + 1 < args.length) {
                    projectPath = Path.of(args[i + 1]);
                    break;
                }
            }

            long startTime = System.currentTimeMillis();
            log.info("Starting scan and parse for project: {}", projectPath.toAbsolutePath());

            ProjectScanner scanner = new ProjectScanner();
            ScanResult scanResult = scanner.scan(projectPath);
            List<Path> javaFiles = scanResult.javaFiles();

            JavaCodeParser parser = new JavaCodeParser();
            AstExtractor extractor = new AstExtractor();

            int totalTypes = 0;
            int totalMethods = 0;
            int failedFiles = 0;

            for (Path file : javaFiles) {
                try {
                    var parseResult = parser.parse(file);
                    if (parseResult.isSuccessful() && parseResult.getResult().isPresent()) {
                        var cu = parseResult.getResult().get();
                        var types = extractor.extract(cu);
                        totalTypes += types.size();
                        for (var type : types) {
                            totalMethods += type.methods().size();
                        }
                    } else {
                        failedFiles++;
                        log.warn("Failed to parse file: {} - problems: {}", file, parseResult.getProblems());
                    }
                } catch (Exception e) {
                    failedFiles++;
                    log.warn("Error reading file: {} - reason: {}", file, e.getMessage());
                }
            }

            long duration = System.currentTimeMillis() - startTime;
            log.info("=== SCAN & PARSE SUMMARY ===");
            log.info("Total Java files: {}", javaFiles.size());
            log.info("Total Types: {}", totalTypes);
            log.info("Total Methods & Constructors: {}", totalMethods);
            log.info("Failed files: {}", failedFiles);
            log.info("Elapsed time: {} ms", duration);
        } catch (Exception e) {
            log.error("Error scanning and parsing project", e);
        }

    }
}
