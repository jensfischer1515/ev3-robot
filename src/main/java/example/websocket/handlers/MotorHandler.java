package example.websocket.handlers;

import ev3dev.actuators.lego.motors.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

@Slf4j
public class MotorHandler implements ActionHandler {

    private final Port port;

    private final EV3LargeRegulatedMotor motor;

    public MotorHandler(Port port) {
        this.port = port;
        this.motor = new EV3LargeRegulatedMotor(port);
        this.motor.setAcceleration(6000);
        this.motor.setSpeed(200);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            LOGGER.warn("Emergency stop on port {}", port);
            motor.stop();
        }));
    }

    @Override
    @SneakyThrows
    public void handle(String subaction, Optional<JSONObject> data) {
        LOGGER.info("port: {}, subaction: {}, data: {}", port.getName(), subaction, data);

        Stream.of(motor.getClass().getMethods())
                .filter(method -> subaction.equals(method.getName()))
                .filter(method -> method.getParameterCount() == 0)
                .findFirst()
                .ifPresent(new Consumer<Method>() {
                    @Override
                    @SneakyThrows
                    public void accept(Method method) {
                        method.invoke(motor);
                    }
                });
    }
}
