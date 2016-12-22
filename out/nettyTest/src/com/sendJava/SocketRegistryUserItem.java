package com.sendJava;

import io.netty.channel.Channel;

/**
 * Created by fadinglan on 2016/12/22.
 */
public class SocketRegistryUserItem extends SocketRegistryItem{

    public SocketRegistryUserItem(final String name, final Channel channel) {
        super(name, channel);
    }

}
