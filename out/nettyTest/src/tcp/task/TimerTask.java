package tcp.task;

import io.netty.channel.Channel;
import tcp.message.impl.SocketDefaultMsg;
import tcp.util.common;

import java.sql.Time;

/**
 * Created by fadinglan on 2017/5/16.
 */
public class TimerTask extends java.util.TimerTask {

    private Channel ctx;

    private String from;

    public TimerTask(Channel ctx, String from){
        this.ctx = ctx;
        this.from = from;
    }
    @Override
    public void run() {

        SocketDefaultMsg socketDefaultMsg = new SocketDefaultMsg();
        socketDefaultMsg.setFrom(from);
        socketDefaultMsg.setTo("server");
        socketDefaultMsg.setMsgType(2);
        socketDefaultMsg.setState(1);

        common.replyMsg(ctx,socketDefaultMsg.pinMsg());
    }
}
