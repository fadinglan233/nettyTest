package socket.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import socket.SocketServer;
import socket.exception.SocketException;
import socket.protocol.SocketMsg;
import socket.routing.item.SocketRoutingItem;
import socket.schedule.SocketHandler;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * 控制器组
 * <p>
 * Created by ZFly on 2017/4/29.
 */
@Component(".socket.controllersGroup")
@Scope("prototype")
public class SocketControllersGroup implements SocketHandler {

    private static final Log logger = LogFactory.getLog(SocketControllersGroup.class);

    private Queue<SocketController> controllers = new PriorityQueue<>(Comparator.comparingInt(SocketController::priority));
    private SocketControllersGroup dependence = null;

    /**
     * 在dependence的基础上创建一个新的升级版控制器组
     * 新的控制器组继承所有dependence中已添加的控制器
     * 当升级版控制器中的控制器不响应请求时
     * 会继续在dependence中继续询问
     *
     * @param dependence 老控制器组
     *
     * @return 升级版控制器组
     */
    public static SocketControllersGroup depends(SocketControllersGroup dependence) {
        return dependence.upgrade();
    }


    /**
     * 从Spring已注册的Bean中添加控制器
     */
    public void addControllerFromSpringBeans(SocketServer context) {
        final ApplicationContext applicationContext = new ClassPathXmlApplicationContext(context.getConfig().getSpringPath());
        applicationContext.getBeansOfType(SocketController.class)
                .forEach((key, value) -> addController(value));
    }

    /**
     * 添加一个控制器
     *
     * @param controller 控制器
     *
     * @return 控制器组自身
     */
    public SocketControllersGroup addController(SocketController controller) {
        controllers.add(controller);
        logger.info("SocketServer mapped controller [" + controller.getClass().getName() + "]");
        return this;
    }

    /**
     * 创建一个新的升级版控制器组
     * 新的控制器组继承所有现控制器组中已添加的控制器
     * 当升级版控制器中的控制器不响应请求时
     * 会继续在现控制器中继续询问
     *
     * @return 升级版控制器组
     */
    public SocketControllersGroup upgrade() {
        final SocketControllersGroup upgradeGroup = new SocketControllersGroup();
        upgradeGroup.dependence = this;
        return upgradeGroup;
    }

    @Override
    public void handle(SocketRoutingItem item, SocketMsg request, List<SocketMsg> responses) throws SocketException {
        for (SocketController controller : controllers) {
            if (controller.isResponse(request)) {
                if (controller.work(item, request, responses))
                    break;
            }
        }
        if (dependence != null) dependence.handle(item, request, responses);
    }
}
