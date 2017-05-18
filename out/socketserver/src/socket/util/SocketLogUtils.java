package socket.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import socket.SocketServer;
import socket.protocol.SocketMsg;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 调试信息辅助日志工具类
 * <p>
 * Created by ZFly on 2017/4/21.
 */
public final class SocketLogUtils {

    private SocketLogUtils() {
    }

    private static final Log logger = LogFactory.getLog(SocketLogUtils.class);

    public static void received(SocketServer context, String packet, SocketMsg msg) {
        final String receiveMsg = String.format(
                "Received[%s] message from [%s] to [%s]:\n%s\n",
                msg.getConnectType(),
                msg.getFrom(),
                msg.getTo(),
                packet);
        if (!context.getRouting().getDebugMap().contains(msg.getFrom())) {
            debugOutput(context, receiveMsg);
        }
    }

    public static void forwarded(SocketServer context, String packet, SocketMsg msg) {
        final String dispatchMsg = String.format(
                "Forwarded[%s] message from [%s] to [%s]:\n%s\n",
                msg.getConnectType(),
                msg.getFrom(),
                msg.getTo(),
                packet);
        if (!context.getRouting().getDebugMap().contains(msg.getTo())) {
            debugOutput(context, dispatchMsg);
        }
    }

    public static void exception(SocketServer context, String packet, SocketMsg msg) {
        final String exceptionMsg = String.format(
                "Exception[%s] on [%s]:\n%s\n",
                msg.getConnectType(),
                msg.getTo(),
                packet);
        if (!context.getRouting().getDebugMap().contains(msg.getTo())) {
            debugOutput(context, exceptionMsg);
        }
    }

    private static void debugOutput(SocketServer context, String msg) {
        final DateFormat format = new SimpleDateFormat("hh:mm:ss,SSS");
        final String date = format.format(new Date());
        logger.info(date);
        context.getRouting().getDebugMap().values().stream()
                .filter(item -> item.isFilter(msg))
                .forEach(item -> item.getTerm().write(String.format("[%s] %s - %s", item.getAddress(), date, msg)));
    }
}
