package example.websocket;

import example.websocket.handlers.MotorHandler;
import example.websocket.open.OpenMessageDecoder;
import example.websocket.robot.RobotMessageDecoder;
import example.websocket.time.TimeMessageDecoder;
import lejos.hardware.port.MotorPort;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.ClientEndpointConfig.Configurator;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Collections.singletonList;
import static javax.websocket.ContainerProvider.getWebSocketContainer;

@Slf4j
@RequiredArgsConstructor
public class WebSocketClient {

    private final String endpointUri;

    @SneakyThrows
    public Session connect() {
        LOGGER.info("Connecting to {}", endpointUri);
        WebSocketContainer container = getWebSocketContainer();
        container.setDefaultMaxSessionIdleTimeout(-1);
        return container.connectToServer(endpoint(), endpointConfig(), URI.create(endpointUri));
    }

    private ApiBridgeEndpoint endpoint() {
        return ApiBridgeEndpoint.builder()
                .actionHandler("motorA", new MotorHandler(MotorPort.A))
                .actionHandler("motorB", new MotorHandler(MotorPort.B))
                .actionHandler("motorC", new MotorHandler(MotorPort.C))
                .actionHandler("motorD", new MotorHandler(MotorPort.D))
                .build();
    }

    private ClientEndpointConfig endpointConfig() {
        return ClientEndpointConfig.Builder.create()
                .configurator(new Configurator() {
                    @Override
                    public void beforeRequest(Map<String, List<String>> headers) {
                        headers.put("User-Agent", singletonList("ev3-robot"));
                    }
                })
                .decoders(Arrays.asList(
                        OpenMessageDecoder.class,
                        TimeMessageDecoder.class,
                        RobotMessageDecoder.class
                ))
                .build();
    }
}
