package socket.event;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import socket.exception.SocketException;
import socket.routing.item.SocketRoutingItem;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 监听事件组
 * <p>
 * Created by ZFly on 2017/4/25.
 */
@Component
@Scope("prototype")
public class SocketEventListenersGroup {

    private final Map<SocketEventsType, Set<SocketEventListener>> group = new HashMap<SocketEventsType, Set<SocketEventListener>>(SocketEventsType.values().length) {{
        for (SocketEventsType eventsType : SocketEventsType.values()) {
            put(eventsType, new HashSet<>(1));
        }
    }};

    /**
     * 添加事件监听者
     *
     * @param eventListener 监听者
     * @param eventsType    事件类型
     */
    public void addEventListener(SocketEventListener eventListener, SocketEventsType eventsType) {
        group.get(eventsType).add(eventListener);
    }

    /**
     * 移除事件监听者
     *
     * @param eventListener 监听者
     * @param eventsType    事件类型
     */
    public void removeEventListener(SocketEventListener eventListener, SocketEventsType eventsType) {
        group.get(eventsType).remove(eventListener);
    }

    /**
     * 发生某类型事件
     *
     * @param item       发送源客户端
     * @param info       附加消息
     * @param eventsType 事件类型
     *
     * @throws SocketException 异常信息
     */
    public void publishEvent(SocketRoutingItem item, Object info, SocketEventsType eventsType) throws SocketException {
        for (SocketEventListener eventListener : group.get(eventsType)) {
            eventListener.eventOccurred(item, info);
        }
    }

}
