package example.websocket;

import lombok.extern.slf4j.Slf4j;
import org.glassfish.tyrus.core.coder.CoderAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.Decoder;

@Slf4j
public class Ev3MessageDecoder extends CoderAdapter implements Decoder.Text<Ev3Message> {

    @Override
    public boolean willDecode(String rawMessage) {
        return rawMessage.startsWith("42[\"ev3\",");
    }

    @Override
    public Ev3Message decode(String rawMessage) {
        JSONArray jsonArray = new JSONArray(rawMessage.substring(2));
        String event = jsonArray.optString(0);
        JSONObject payload = jsonArray.optJSONObject(1);
        return new Ev3Message(event, payload);
    }
}
