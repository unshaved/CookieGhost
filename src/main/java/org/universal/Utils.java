package org.universal;

public class Utils {
    public static void killProcess(String processName) {
        try {
            Process kill = Runtime.getRuntime().exec("taskkill /F /IM " + processName);
            kill.waitFor();
            System.out.println("Killed process: " + processName);
        } catch (Exception e) {
            System.err.println("Could not kill process " + processName + ": " + e.getMessage());
        }
    }
}
