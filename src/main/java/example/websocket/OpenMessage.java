package example.websocket;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@Builder
public class OpenMessage {
    private final String sid;
    private final List<Object> upgrades;
    private final Long pingInterval;
    private final Long pingTimeout;
}
