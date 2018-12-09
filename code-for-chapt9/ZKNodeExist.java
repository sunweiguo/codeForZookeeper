import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Author 【swg】.
 * @Date 2018/12/9 18:29
 * @DESC
 * @CONTACT 317758022@qq.com
 */
public class ZKNodeExist implements Watcher{
    private ZooKeeper zooKeeper;

    final static String serverPath = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    private static CountDownLatch countDownLatch = new CountDownLatch(1);


    public ZKNodeExist(){}

    public ZKNodeExist(String connectionString,int sessionTimeout){
        try {
            zooKeeper = new ZooKeeper(connectionString,sessionTimeout,new ZKNodeExist());
        } catch (IOException e) {
            e.printStackTrace();
            if(zooKeeper != null){
                try {
                    zooKeeper.close();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException, KeeperException {
        ZKNodeExist zkServer = new ZKNodeExist(serverPath,5*1000);

        Stat stat = zkServer.getZooKeeper().exists("/swg",true);
        if(stat != null){
            System.out.println("version  ："+stat.getVersion());
        }else {
            System.out.println("该节点不存在");
        }
        countDownLatch.await();
    }


    @Override
    public void process(WatchedEvent event) {
        try {
            if(event.getType() == Watcher.Event.EventType.NodeDataChanged){
                System.out.println("节点改变");
                countDownLatch.countDown();
            }else if(event.getType() == Watcher.Event.EventType.NodeCreated){
                System.out.println("节点创建");
            }else if(event.getType() == Watcher.Event.EventType.NodeChildrenChanged){

            }else if(event.getType() == Watcher.Event.EventType.NodeDeleted){
                System.out.println("节点删除");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public ZooKeeper getZooKeeper() {
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper) {
        this.zooKeeper = zooKeeper;
    }
}
