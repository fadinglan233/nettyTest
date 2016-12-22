package com.sendJava;

import com.protocol.Templates.SocketProtocol;
import io.netty.channel.Channel;

import java.util.List;

/**
 * Created by fadinglan on 2016/12/10.
 */
public interface SocketHandler {
    void invoke(Channel ctx, SocketProtocol request, List<SocketProtocol> responses);
}
