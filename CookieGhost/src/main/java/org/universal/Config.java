package org.universal;

public class Config {
    public String name;
    public String executablePath;
    public String userDataDir;
    public String cookieDbPath;
    public int cdpPort;
    public String processName;

    public Config(String name, String exe, String profile, String db, int port, String process) {
        this.name = name;
        this.executablePath = exe;
        this.userDataDir = profile;
        this.cookieDbPath = db;
        this.cdpPort = port;
        this.processName = process;
    }
}

