package example.websocket.handlers;

import org.json.JSONObject;

import java.util.Optional;

public interface ActionHandler {
    void handle(String subaction, Optional<JSONObject> data);
}
