package example.websocket.robot;

import lombok.Data;
import org.json.JSONObject;

import java.util.Optional;

@Data
public class RobotMessage {
    private final String event;
    private final JSONObject payload;

    public Optional<String> getAction() {
        return Optional.ofNullable(payload).map(json -> json.optString("action"));
    }

    public Optional<String> getSubaction() {
        return Optional.ofNullable(payload).map(json -> json.optString("subaction"));
    }

    public Optional<JSONObject> getData() {
        return Optional.ofNullable(payload).map(json -> json.optJSONObject("data"));
    }
}
