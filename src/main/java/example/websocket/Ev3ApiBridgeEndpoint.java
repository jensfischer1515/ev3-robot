package example.websocket;

import example.websocket.handlers.Ev3ActionHandler;
import lombok.Builder;
import lombok.Singular;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import java.nio.ByteBuffer;
import java.util.Map;

@Slf4j
@Builder
public class Ev3ApiBridgeEndpoint extends Endpoint {

    @Singular
    private final Map<String, Ev3ActionHandler> actionHandlers;

    @Override
    @SneakyThrows
    public void onOpen(Session session, EndpointConfig config) {
        LOGGER.info("Opening session with id {}", session.getId());
        session.setMaxIdleTimeout(-1);

        session.addMessageHandler(PongMessage.class, message -> LOGGER.warn("Pong: {}", new String(message.getApplicationData().array())));
        //session.addMessageHandler(Ev3Message.class, new Ev3MessageHandler(actionHandlers));
        session.addMessageHandler(String.class, message -> LOGGER.warn("STRING {}", message));
        session.getBasicRemote().sendPing(ByteBuffer.wrap("PING".getBytes()));
        session.getBasicRemote().sendPong(ByteBuffer.wrap("PONG".getBytes()));
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        LOGGER.info("Closing: {}", closeReason);
        super.onClose(session, closeReason);
    }

    @Override
    public void onError(Session session, Throwable thr) {
        LOGGER.error("Error", thr);
        super.onError(session, thr);
    }
}
