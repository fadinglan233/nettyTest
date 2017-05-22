package tcp.handler.impl;

import tcp.handler.MsgListener;
import tcp.message.MsgType;

/**
 * Created by fadinglan on 2017/5/10.
 */
public class ListenerFactory {

    public static MsgListener getListener(MsgType msgType) throws ClassNotFoundException, IllegalAccessException, InstantiationException {

        String name = msgType.toString().toLowerCase();
        return (MsgListener) Class.forName("tcp.handler.impl."
                + name.substring(0,1).toUpperCase() + name.substring(1)+ "Listener").newInstance();
    }
}
