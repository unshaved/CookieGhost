# CookieGhost

**CookieGhost** is a cross-browser Chromium cookie extractor for educational and forensic use. It safely copies browser cookies from Chrome, Brave, Edge, Opera, and Opera GX, then relaunches the browser to avoid user disruption. All cookies are exported in Firefox-compatible `.txt` format.

> ⚠️ For ethical use only. Intended for cybersecurity research, red team exercises, and educational labs with explicit user consent.

---

##  Features

- Extracts cookies from multiple Chromium-based browsers
- Uses Chrome DevTools Protocol (CDP) to fetch cookies silently
- Uses a temporary SQLite copy to avoid file lock issues
- Exports in Firefox-compatible format for easy import
- Auto-detects installed browsers and profile directories
- Headless, sandboxed relaunch to retrieve data post-termination
- Built-in session cleanup and auto-restore logic
- Modular design for easy extension

---

##  Supported Browsers

- Google Chrome
- Brave Browser
- Microsoft Edge
- Opera
- Opera GX

---

##  Build Instructions

> Java 11 or newer is required.

1. Clone the project
2. Build the fat JAR:
   ```bash
   ./gradlew shadowJar
   ```
3. Run it:
   ```bash
   java -jar build/libs/CookieGhost-1.0.jar
   ```

---

##  How It Works

1. **Process Kill**  
   Target browser processes (like `chrome.exe`) are force-closed to avoid locked DB access.

2. **Cookie Backup**  
   The SQLite `Cookies` database is copied to a temporary file.

3. **CDP Launch**  
   The browser is restarted with `--remote-debugging-port`, in headless and sandbox-disabled mode.

4. **Cookie Fetching**  
   Via WebSocket and Chrome DevTools Protocol, cookies are dumped from memory.

5. **Firefox Export**  
   Output is formatted for compatibility with Firefox-style `.txt` importers.

6. **Cleanup**  
   Temp files are deleted and the original browser is relaunched (optional).

---

## ⚠️ Disclaimer

This software is intended for **ethical use only**.

Using CookieGhost on machines you do not own, or without user consent, may violate:
- Computer Fraud and Abuse Act (CFAA)
- GDPR, CCPA, and other privacy laws
- Terms of service of software platforms

The developers take **no responsibility** for unauthorized or malicious use.

---

## Donations

If you find this project useful, consider supporting it:

**Bitcoin (BTC):** `bc1pdf6xeaqm5t3k3pnth30cprtgs2x94dk9kvqludsaq6n9594akhzq4l3el8`

---

## Credits

Me, Myself and I.
