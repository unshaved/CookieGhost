package org.universal;

import com.google.gson.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class Fetcher {
    public static List<Map<String, Object>> getCookies(int port) throws Exception {
        URL url = new URL("http://localhost:" + port + "/json");
        JsonArray tabs = JsonParser.parseReader(new InputStreamReader(url.openStream())).getAsJsonArray();
        String wsUrl = tabs.get(0).getAsJsonObject().get("webSocketDebuggerUrl").getAsString();

        WebSocketClient client = new WebSocketClient(new URI(wsUrl));
        client.connectBlocking();

        String request = new Gson().toJson(Map.of(
                "id", 1,
                "method", "Network.getAllCookies"
        ));

        client.send(request);
        String response = client.awaitResponse();

        JsonObject obj = JsonParser.parseString(response).getAsJsonObject();
        JsonArray cookies = obj.getAsJsonObject("result").getAsJsonArray("cookies");

        List<Map<String, Object>> result = new ArrayList<>();
        for (JsonElement e : cookies) {
            JsonObject c = e.getAsJsonObject();
            Map<String, Object> map = new Gson().fromJson(c, Map.class);
            result.add(map);
        }
        client.close();
        return result;
    }

    static class WebSocketClient extends org.java_websocket.client.WebSocketClient {
        private String response;

        public WebSocketClient(URI serverUri) {
            super(serverUri);
        }

        public String awaitResponse() throws InterruptedException {
            int tries = 0;
            while (response == null && tries++ < 50) Thread.sleep(100);
            return response;
        }

        @Override public void onOpen(org.java_websocket.handshake.ServerHandshake handshake) {}
        @Override public void onMessage(String message) { this.response = message; }
        @Override public void onClose(int code, String reason, boolean remote) {}
        @Override public void onError(Exception ex) { ex.printStackTrace(); }
    }
}
