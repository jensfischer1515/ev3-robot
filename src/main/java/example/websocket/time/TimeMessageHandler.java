package example.websocket.time;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.MessageHandler;

@Slf4j
public class TimeMessageHandler implements MessageHandler.Whole<String> {
    @Override
    public void onMessage(String message) {
        LOGGER.info("Time: {}", message);
    }
}
