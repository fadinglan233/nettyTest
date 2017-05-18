package socket.protocol;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import socket.exception.fatal.SocketProtocolBrokenException;
import socket.exception.fatal.SocketProtocolUnsupportedException;
import socket.protocol.msg.SocketDefaultProtocolParser;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 协议簇，通过向协议簇注册解析器
 * 将不同协议的数据包统一转化到 SocketMsg
 * <p>
 * Created by ZFly on 2017/4/21.
 */
@Component
@Scope("prototype")
public class SocketProtocolFamily {

    /**
     * 解析器列表
     */
    private Queue<SocketProtocolParser> parsers = new PriorityQueue<SocketProtocolParser>(Comparator.comparingInt(SocketProtocolParser::getPriority)) {{
        add(new SocketDefaultProtocolParser());
    }};

    /**
     * 选择解析器从字符串数据中解析出消息对象
     *
     * @param data 字符串数据
     *
     * @return 消息对象
     *
     * @throws SocketProtocolBrokenException      消息格式错误
     * @throws SocketProtocolUnsupportedException 没有适合的解析器
     */
    public SocketMsg parse(String data) throws SocketProtocolBrokenException, SocketProtocolUnsupportedException {
        for (SocketProtocolParser parser : parsers) {
            if (parser.isResponse(data)) {
                return parser.parse(data);
            }
        }
        throw new SocketProtocolUnsupportedException("There was no protocol parser to convert string to message");
    }

    /**
     * 选择解析器将消息对象打包为字符串数据
     *
     * @param msg 消息对象
     *
     * @return 字符串数据
     */
    public String parse(SocketMsg msg) throws SocketProtocolUnsupportedException {
        for (SocketProtocolParser parser : parsers) {
            if (parser.isResponse(msg)) {
                return parser.parse(msg);
            }
        }
        throw new SocketProtocolUnsupportedException("There was no protocol parser to convert message to string");
    }

    /**
     * 注册解析器
     *
     * @param parser 要解析器对象
     */
    public void registerParser(SocketProtocolParser parser) {
        parsers.add(parser);
    }

    /**
     * 注销解析器
     *
     * @param parser 要解析器对象
     */
    public void unRegisterParser(SocketProtocolParser parser) {
        parsers.remove(parser);
    }

    /**
     * 清空所以解析器
     */
    public void clear() {
        parsers.clear();
    }

}
