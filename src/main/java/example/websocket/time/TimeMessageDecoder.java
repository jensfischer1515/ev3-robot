package example.websocket.time;

import org.glassfish.tyrus.core.coder.CoderAdapter;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.websocket.Decoder;

public class TimeMessageDecoder extends CoderAdapter implements Decoder.Text<String> {

    @Override
    public boolean willDecode(String rawMessage) {
        return rawMessage.startsWith("42[\"time\",");
    }

    @Override
    public String decode(String rawMessage) {
        JSONArray jsonArray = new JSONArray(rawMessage.substring(2));
        JSONObject payload = jsonArray.optJSONObject(1);
        return payload.toString();
    }
}
