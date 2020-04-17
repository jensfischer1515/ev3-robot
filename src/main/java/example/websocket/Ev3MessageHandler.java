package example.websocket;

import example.websocket.handlers.Ev3ActionHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.MessageHandler;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
public class Ev3MessageHandler implements MessageHandler.Whole<Ev3Message> {

    private final Map<String, Ev3ActionHandler> actionHandlers;

    @Override
    public void onMessage(Ev3Message message) {
        LOGGER.info("Received message: {}", message);

        message.getAction()
                .filter(action -> message.getSubaction().isPresent())
                .map(actionHandlers::get)
                .ifPresent(handler -> handler.handle(message.getSubaction().get(), message.getData()));
    }
}
