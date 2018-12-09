import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

public class ZKConnectSessionWatcher implements Watcher {

    public static void main(String[] args) throws IOException {
        String serverPath = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";
        long sessionId = 999L;
        byte[] sessionPasswd = null;

        /**
         * 第一次连接
         */
        ZooKeeper zk = new ZooKeeper(serverPath,5*1000,new ZKConnectSessionWatcher());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //如果状态时已连接了，就获取sessionId
        if (zk.getState().equals(ZooKeeper.States.CONNECTED)){
            sessionId = zk.getSessionId();
            System.out.println(sessionId);

            String ssid = "0x" + Long.toHexString(sessionId);
            //对sessionId经过64位编码之后的值，也就是dump命令查出来的sessionId值
            System.out.println(ssid);

            sessionPasswd = zk.getSessionPasswd();
        }


        /**
         * 第2次连接，会话重连，那么用sessionID和password来进行来重新获取连接
         */
        System.out.println("会话重连...");


        ZooKeeper zkSession = new ZooKeeper(serverPath,5*1000,new ZKConnectSessionWatcher(),sessionId,sessionPasswd);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("重连之后的sessionId为："+zkSession.getSessionId());
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("接受到的watch通知："+watchedEvent);
    }
}
