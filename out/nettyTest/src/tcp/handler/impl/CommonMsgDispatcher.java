package tcp.handler.impl;

import tcp.handler.MsgDispatcher;
import tcp.handler.MsgListener;
import tcp.message.MsgType;
import tcp.message.SocketMsg;
import tcp.message.impl.MsgResponse;
import tcp.message.impl.SocketDefaultMsg;
import io.netty.channel.Channel;
import tcp.util.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Created by fadinglan on 2017/5/10.
 */
public enum  CommonMsgDispatcher implements MsgDispatcher {

    INSTANCE ;   //采用枚举实现单例模式

    private final Map<MsgType,Queue<MsgListener>> observers = new HashMap<>();


    @Override
    public void registerMsg(MsgType msgType, MsgListener listener) {

        Queue<MsgListener> listeners = observers.get(msgType);
        if (listeners == null){
            listeners = new ConcurrentLinkedDeque<>();
            observers.put(msgType, listeners);
        }
        listeners.offer(listener);

    }

    @Override
    public void removeEventListener(MsgListener listener, MsgType msgType) {
        observers.get(msgType).remove(listener);
    }

    @Override
    public void fireMsg(Channel ctx, SocketDefaultMsg msg, MsgType msgType) {

        if (msg == null){
            common.replyMsg(ctx,new SocketDefaultMsg().errorResponse());
        }

        Queue<MsgListener> listeners = observers.get(msgType);
        if (listeners != null){
            for (MsgListener listener : listeners){
                try {
                    listener.handlerMsg(ctx, msg);
                    removeEventListener(listener,msgType);

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }

    }
}
