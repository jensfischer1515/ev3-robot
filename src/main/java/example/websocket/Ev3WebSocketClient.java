package example.websocket;

import example.websocket.handlers.MotorHandler;
import lejos.hardware.port.MotorPort;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.ClientEndpointConfig;
import javax.websocket.Decoder;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static javax.websocket.ContainerProvider.getWebSocketContainer;

@Slf4j
public class Ev3WebSocketClient {

    @SneakyThrows
    public static void main(String[] args) {
        URI uri = new URI("wss://ev3-api-bridge.herokuapp.com/socket.io/?EIO=3&transport=websocket");

        List<Class<? extends Decoder>> decoders = new ArrayList<>();
        decoders.add(Ev3MessageDecoder.class);

        ClientEndpointConfig endpointConfig = ClientEndpointConfig.Builder.create()
                .decoders(decoders)
                .build();

        Ev3ApiBridgeEndpoint endpoint = Ev3ApiBridgeEndpoint.builder()
                .actionHandler("motorA", new MotorHandler(MotorPort.A))
                .actionHandler("motorB", new MotorHandler(MotorPort.B))
                .actionHandler("motorC", new MotorHandler(MotorPort.C))
                .actionHandler("motorD", new MotorHandler(MotorPort.D))
                .build();

        WebSocketContainer container = getWebSocketContainer();
        container.setDefaultMaxSessionIdleTimeout(-1);
        container.connectToServer(endpoint, endpointConfig, uri);

        SECONDS.sleep(100);
        System.exit(0);
    }
}
