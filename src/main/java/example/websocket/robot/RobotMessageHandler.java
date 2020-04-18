package example.websocket.robot;

import example.websocket.handlers.ActionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.MessageHandler;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class RobotMessageHandler implements MessageHandler.Whole<RobotMessage> {

    private final Map<String, ActionHandler> actionHandlers;

    @Override
    public void onMessage(RobotMessage message) {
        LOGGER.info("Received message: {}", message);

        message.getAction()
                .filter(action -> message.getSubaction().isPresent())
                .map(actionHandlers::get)
                .ifPresent(handler -> handler.handle(message.getSubaction().get(), message.getData()));
    }
}
