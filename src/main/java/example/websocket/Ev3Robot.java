package example.websocket;

import lombok.SneakyThrows;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.websocket.Session;
import java.io.InputStream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.logging.LogManager.getLogManager;

public class Ev3Robot {

    @SneakyThrows
    public static void main(String[] args) {
        try (InputStream inputStream = Ev3Robot.class.getResourceAsStream("/logging.properties")) {
            getLogManager().readConfiguration(inputStream);
            SLF4JBridgeHandler.removeHandlersForRootLogger();
            SLF4JBridgeHandler.install();
        }

        //Session session = new WebSocketClient("wss://ev3-api-bridge.herokuapp.com/socket.io/?EIO=3&transport=websocket").connect();
        //Session session = new WebSocketClient("ws://ev3-api-bridge.localtest.me:3000/socket.io/?EIO=3&transport=websocket").connect();
        Session session = new WebSocketClient("ws://192.168.2.39:3000/socket.io/?EIO=3&transport=websocket").connect();


        while (true) {
            SECONDS.sleep(10);
        }
        //System.exit(0);
    }
}
