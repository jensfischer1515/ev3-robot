package example.websocket.open;

import lombok.extern.slf4j.Slf4j;

import javax.websocket.MessageHandler;

@Slf4j
public class OpenMessageHandler implements MessageHandler.Whole<OpenMessage> {
    @Override
    public void onMessage(OpenMessage message) {
        LOGGER.info("Open: {}", message);
    }
}
