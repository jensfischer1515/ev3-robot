package example.websocket.robot;

import org.glassfish.tyrus.core.coder.CoderAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.Decoder;

public class RobotMessageDecoder extends CoderAdapter implements Decoder.Text<RobotMessage> {

    @Override
    public boolean willDecode(String rawMessage) {
        return rawMessage.startsWith("42[\"ev3\",");
    }

    @Override
    public RobotMessage decode(String rawMessage) {
        JSONArray jsonArray = new JSONArray(rawMessage.substring(2));
        String event = jsonArray.optString(0);
        JSONObject payload = jsonArray.optJSONObject(1);
        return new RobotMessage(event, payload);
    }
}
