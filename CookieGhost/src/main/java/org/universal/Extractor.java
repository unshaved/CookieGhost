package org.universal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Extractor {
    public static void run(Config config) {
        Path backup = null;

        try {
            Utils.killProcess(config.processName);

            Path original = Paths.get(config.cookieDbPath);
            if (!Files.exists(original)) {
                System.out.println("[" + config.name + "] Cookies DB not found, skipping.");
                return;
            }

            backup = Files.createTempFile("cookie_copy_", ".db");
            Files.copy(original, backup, StandardCopyOption.REPLACE_EXISTING);
            backup.toFile().deleteOnExit();

            List<String> domains = getAllDomainsFromCopy(backup);
            Launcher.launch(config, domains);

            System.out.println("[" + config.name + "] Waiting for domains to load...");
            Thread.sleep(10000);

            List<Map<String, Object>> cookies = Fetcher.getCookies(config.cdpPort);
            Output.exportFirefoxImportFormat(cookies, config.name);

            Utils.killProcess(config.processName);
            Files.deleteIfExists(backup);
            System.out.println("[" + config.name + "] Dump complete.");

        } catch (Exception e) {
            System.err.println("[" + config.name + "] " + e.getMessage());
            try { if (backup != null) Files.deleteIfExists(backup); } catch (IOException ignored) {}
        }
    }

    private static List<String> getAllDomainsFromCopy(Path dbPath) {
        List<String> domains = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath.toString());
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT DISTINCT host_key FROM cookies")) {

            while (rs.next()) {
                String domain = rs.getString("host_key");
                if (domain.startsWith(".")) domain = "https://" + domain.substring(1);
                else if (!domain.startsWith("http")) domain = "https://" + domain;
                domains.add(domain);
            }
        } catch (SQLException e) {
            System.err.println("Failed to read domains: " + e.getMessage());
        }
        return domains;
    }
}
