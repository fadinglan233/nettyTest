package socket.secure.delegate;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import socket.event.SocketEventsType;

import java.util.HashMap;
import java.util.Map;

/**
 * 安全组件
 * <p>
 * Created by ZFly on 2017/4/24.
 */
@Component
@Scope("prototype")
public class SocketSecureDelegatesGroup {

    private final Map<SocketSecureDelegateType, SocketSecureDelegate> group = new HashMap<>(SocketEventsType.values().length);

    /**
     * 安全代理
     *
     * @param delegate 代理
     * @param type     代理类型
     */
    public void addDelegate(SocketSecureDelegate delegate, SocketSecureDelegateType type) {
        group.put(type, delegate);
    }

    /**
     * 移除安全代理
     *
     * @param type 代理类型
     */
    public void removeDelegate(SocketSecureDelegateType type) {
        group.remove(type);
    }

    /**
     * 获取安全代理
     *
     * @param type 代理类型
     *
     * @return 代理
     */
    public SocketSecureDelegate getDelegate(SocketSecureDelegateType type) {
        return group.get(type);
    }
}
