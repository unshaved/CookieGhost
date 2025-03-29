package org.universal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Launcher {
    public static void launch(Config config, List<String> domains) throws IOException {
        List<String> command = new ArrayList<>();
        command.add(config.executablePath);
        command.add("--remote-debugging-port=" + config.cdpPort);
        command.add("--user-data-dir=" + config.userDataDir);
        command.add("--profile-directory=Default");
        command.add("--no-first-run");
        command.add("--no-default-browser-check");
        command.add("--headless=new");
        command.add("--disable-gpu");
        command.add("--no-sandbox");
        command.add("--window-size=1920,1080");

        new ProcessBuilder(command).start();
        System.out.println("Launched " + config.name + " on port " + config.cdpPort);
    }
}
