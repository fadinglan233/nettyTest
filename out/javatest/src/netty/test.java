package netty;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by fadinglan on 2017/5/9.
 */
public class test {

    public static void main(String[] args) throws IOException {


        int port = 1234;
        //打开ServerSocketChan 用于监听客户端连接
        ServerSocketChannel acceptorSvr = ServerSocketChannel.open();

        //绑定监听端口，设置连接为非阻塞模式
        acceptorSvr.socket().bind(new InetSocketAddress(InetAddress.getByName("IP"),port));
        acceptorSvr.configureBlocking(false);

        //创建Reactor线程，创建多路复用器并启动线程
        Selector selector = Selector.open();
//        new Thread(new ReactorTask().start);

        //将ServerSocketChannel注册到Reactor线程的多路复用器Selector上，监听ACCEPT事件
//        SelectionKey key = acceptorSvr.register(selector, SelectionKey.OP_ACCEPT, ioHandler);

        //多路复用器在线程run方法中无限循环体内轮询准备就绪的Key
        int num = selector.select();
        Set selectedKeys = selector.selectedKeys();
        Iterator it = selectedKeys.iterator();
        while (it.hasNext()){
            SelectionKey key1 = (SelectionKey) it.next();
        }

//        SocketChannel channel = s
    }
}
