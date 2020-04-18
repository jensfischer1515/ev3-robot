package example.websocket;

import example.websocket.handlers.ActionHandler;
import example.websocket.robot.RobotMessage;
import example.websocket.robot.RobotMessageHandler;
import lombok.Builder;
import lombok.Singular;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import java.nio.ByteBuffer;
import java.util.Map;

@Slf4j
@Builder
public class ApiBridgeEndpoint extends Endpoint {

    @Singular
    private final Map<String, ActionHandler> actionHandlers;

    @Override
    @SneakyThrows
    public void onOpen(Session session, EndpointConfig config) {
        LOGGER.info("Opening session with id {}", session.getId());
        session.setMaxIdleTimeout(-1);

        session.addMessageHandler(RobotMessage.class, new RobotMessageHandler(actionHandlers));
        //session.addMessageHandler(OpenMessage.class, new OpenMessageHandler());
        //session.addMessageHandler(String.class, new TimeMessageHandler());
        //session.addMessageHandler(String.class, message -> LOGGER.info("STRING: {}", message));
        session.addMessageHandler(ByteBuffer.class, message -> LOGGER.info("BINARY: {}", new String(message.array())));
        session.addMessageHandler(PongMessage.class, message -> LOGGER.warn("PONG: {}", new String(message.getApplicationData().array())));
    }

    @Override
    public void onClose(Session session, CloseReason closeReason) {
        LOGGER.warn("CLOSE: {}", closeReason);
        super.onClose(session, closeReason);
    }

    @Override
    public void onError(Session session, Throwable e) {
        LOGGER.error("ERROR", e);
        super.onError(session, e);
    }
}
