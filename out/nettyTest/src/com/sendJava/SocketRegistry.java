package com.sendJava;

import io.netty.channel.Channel;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by fadinglan on 2016/12/22.
 */
public class SocketRegistry {
    private static final ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

    private SocketRegistry() {
    }

    public static void runExpire() {
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                for (SocketRegistryItem item : SocketMemCache.values()) {
                        if (item.isExpire()) {
                            SocketRegistry.unRegister(item.getName());
                        }
                    }
                }

        }, 60, 60, TimeUnit.SECONDS);
    }

    public static void register(final String name, final Channel channel) {

        if (SocketMemCache.contains("Tmp_" + channel.id())) {
            SocketMemCache.del("Tmp_" + channel.id());
        }

        final SocketRegistryItem item = new SocketRegistryUserItem(name, channel);
        item.expire(300);
        SocketMemCache.set(name, item);
    }

    public static void unRegister(final String name) {

        closeAndDelete(name);
    }

    public static boolean contains(final String name) {
        return SocketMemCache.contains(name);
    }


    public static SocketRegistryItem get(final String name) {
        return SocketMemCache.get(name);
    }

    public static Collection<SocketRegistryItem> values() {
        return SocketMemCache.values();
    }

    public static void feed(final String name) {

        SocketRegistryUserItem user = (SocketRegistryUserItem) SocketMemCache.get(name);
        if (user != null) {
            user.expire(300);
        }
    }

    private static void closeAndDelete(final String name) {
        if (!SocketMemCache.contains(name)) {
            return;
        }
        SocketRegistryItem item = SocketMemCache.get(name);
        if (item != null) {
            item.getChannel().close();
            SocketMemCache.del(name);
        }
    }

}
