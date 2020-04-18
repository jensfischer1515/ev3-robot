package example.websocket;

import org.glassfish.tyrus.core.coder.CoderAdapter;
import org.json.JSONObject;

import javax.websocket.Decoder;

// 0{"sid":"bo_MNU5PjAd6a6jbAAAN","upgrades":[],"pingInterval":25000,"pingTimeout":60000}
public class OpenMessageDecoder extends CoderAdapter implements Decoder.Text<OpenMessage> {

    @Override
    public boolean willDecode(String rawMessage) {
        return rawMessage.startsWith("0{");
    }

    @Override
    public OpenMessage decode(String rawMessage) {
        JSONObject jsonObject = new JSONObject(rawMessage.substring(1));
        return OpenMessage.builder()
                .sid(jsonObject.getString("sid"))
                .upgrades(jsonObject.getJSONArray("upgrades").toList())
                .pingInterval(jsonObject.getLong("pingInterval"))
                .pingTimeout(jsonObject.getLong("pingTimeout"))
                .build();
    }
}
