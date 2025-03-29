package org.universal;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

public class Output {
    public static void exportFirefoxImportFormat(List<Map<String, Object>> cookies, String browserName) {
        String path = "cookies_" + browserName + ".txt";
        try (PrintWriter out = new PrintWriter(path)) {
            for (Map<String, Object> c : cookies) {
                String domain = (String) c.get("domain");
                String flag = domain.startsWith(".") ? "TRUE" : "FALSE";
                String cookiePath = c.get("path").toString();
                String secure = Boolean.parseBoolean(c.get("secure").toString()) ? "TRUE" : "FALSE";
                long expiry = ((Number) c.get("expires")) == null ? 9999999999L : ((Number) c.get("expires")).longValue();
                String name = c.get("name").toString();
                String value = c.get("value").toString();

                out.printf("%s\t%s\t%s\t%s\t%d\t%s\t%s\n",
                        domain, flag, cookiePath, secure, expiry, name, value);
            }
        } catch (Exception e) {
            System.err.println("Failed to write cookie file for " + browserName);
        }
    }
}
