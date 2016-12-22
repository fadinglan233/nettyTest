package com.sendJava;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by fadinglan on 2016/12/22.
 */
class SocketMemCache {

//    private static final ConcurrentHashMap<String, SocketRegistryItem> userTable = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, SocketRegistryItem> tmpTable = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<String, SocketRegistryItem> selectTable() {

        return tmpTable;
    }

    static void set(final String key, final SocketRegistryItem item) {
        if (item == null) {
            return;
        }
        selectTable().put(key, item);
    }

    static SocketRegistryItem get(final String key) {
        if (!selectTable().containsKey(key)) {
            return null;
        }
        return selectTable().get(key);
    }

    static void del(final String key) {
        selectTable().remove(key);
    }

    static boolean contains(final String key) {
        return selectTable().containsKey(key);
    }

    static Collection<SocketRegistryItem> values() {
        return selectTable().values();
    }
}