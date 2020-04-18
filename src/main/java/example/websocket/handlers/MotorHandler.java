package example.websocket.handlers;

import lejos.hardware.port.Port;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
public class MotorHandler implements ActionHandler {

    private final Port port;

    @Override
    public void handle(String subaction, Optional<JSONObject> data) {
        LOGGER.info("port: {}, subaction: {}, data: {}", port.getName(), subaction, data);
    }
}
