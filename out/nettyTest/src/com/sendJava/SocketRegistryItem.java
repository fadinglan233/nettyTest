package com.sendJava;

import io.netty.channel.Channel;

/**
 * Created by fadinglan on 2016/12/22.
 */
public abstract class SocketRegistryItem {
    protected Long timeout = Long.MAX_VALUE;
    protected final Channel channel;
    protected final String name;

    protected SocketRegistryItem(final String name, final Channel channel) {
        this.name = name;
        this.channel = channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public String getName() {
        return name;
    }

    public void expire(final int seconds) {
        timeout = System.currentTimeMillis() + seconds * 1000;
    }

    public boolean isExpire() {
        return timeout < System.currentTimeMillis();
    }
}
