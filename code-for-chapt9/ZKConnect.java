import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ZKConnect implements Watcher {

    public static void main(String[] args) throws IOException {
        String serverPath = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
        /**
         * serverPath:可以是一个ip，也可以是多个ip
         * sessionTimeout：超时时间，心跳收不到，就超时
         * watcher：通知事件，如果有对应的事件发生触发，则客户端会收到通知
         * canBeReadOnly：可读，当这个物理机节点断开以后，还是可以读到数据的，只是不能写
         * 此时数据被读取到的可能是旧数据，此处建议设置为false
         * sessionId：会话的ID
         * sessionPassword：会话密码 当会话消失以后，可以一句sessionId和sessionPasswd重新获取会话
         */
        ZooKeeper zk = new ZooKeeper(serverPath,5*1000,new ZKConnect());

        System.out.println("客户端开始连接zk...，连接状态为:"+zk.getState());

        /**
         * 休息一段时间，保证让节点状态看到
         */
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("连接状态为:"+zk.getState());

    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("接受到的watch通知："+watchedEvent);
    }
}
