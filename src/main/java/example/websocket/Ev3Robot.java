package example.websocket;

import lombok.SneakyThrows;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.websocket.Session;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Ev3Robot {

    @SneakyThrows
    public static void main(String[] args) {
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        //Session session = new Ev3Client("wss://ev3-api-bridge.herokuapp.com/socket.io/?EIO=3&transport=websocket").connect();
        Session session = new Ev3Client("ws://ev3-api-bridge.localtest.me:3000/socket.io/?EIO=3&transport=websocket").connect();

        SECONDS.sleep(100);
        System.exit(0);
    }
}
