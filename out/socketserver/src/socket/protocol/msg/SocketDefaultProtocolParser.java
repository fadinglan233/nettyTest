package socket.protocol.msg;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import socket.exception.fatal.SocketProtocolBrokenException;
import socket.protocol.SocketMsg;
import socket.protocol.SocketProtocolParser;
import socket.util.SocketPriority;

/**
 * 默认协议解析器实现
 * 支持 JSON 格式，使用 FastJson 进行转换
 * 使用的实体类模板为 SocketDefaultMsg
 * 优先级为 SocketPriority.LOWEST
 * <p>
 * Created by ZFly on 2017/4/21.
 */
public class SocketDefaultProtocolParser implements SocketProtocolParser {


    @Override
    public int getPriority() {
        return SocketPriority.LOWEST;
    }

    @Override
    public boolean isResponse(String data) {
        return StringUtils.startsWith(data, "{") && StringUtils.endsWith(data, "}");
    }

    @Override
    public boolean isResponse(SocketMsg msg) {
        return StringUtils.equals(msg.getVersion(), "2.0");
    }

    @Override
    public SocketMsg parse(String data) throws SocketProtocolBrokenException {
        try {
            return JSON.parseObject(data, SocketDefaultMsg.class);
        } catch (Exception e) {
            throw new SocketProtocolBrokenException("Protocol broken [" + e.getMessage() + "]");
        }
    }

    @Override
    public String parse(SocketMsg msg) {
        return JSON.toJSONString(msg);
    }
}

