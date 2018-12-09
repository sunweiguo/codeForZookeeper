import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @Author 【swg】.
 * @Date 2018/12/9 17:49
 * @DESC
 * @CONTACT 317758022@qq.com
 */
public class ZKGetChildrenList implements Watcher {

    private ZooKeeper zooKeeper;

    final static String serverPath = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    private static Stat stat = new Stat();

    private static CountDownLatch countDownLatch = new CountDownLatch(1);


    public ZKGetChildrenList(){}

    public ZKGetChildrenList(String connectionString, int sessionTimeout){
        try {
            zooKeeper = new ZooKeeper(connectionString,sessionTimeout,new ZKGetChildrenList());
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
        ZKGetChildrenList zkServer = new ZKGetChildrenList(serverPath,5*1000);

        String ctx = "{'callback':'childrenCallBack'}";
        zkServer.getZooKeeper().getChildren("/swg",true,new Children2Callback(),ctx);
        countDownLatch.await();
    }


    @Override
    public void process(WatchedEvent event) {
        try {
            if(event.getType() == Event.EventType.NodeDataChanged){

            }else if(event.getType() == Event.EventType.NodeCreated){

            }else if(event.getType() == Event.EventType.NodeChildrenChanged){
                ZKGetChildrenList zkServer = new ZKGetChildrenList(serverPath,5*1000);

                List<String> childList = zkServer.getZooKeeper().getChildren(event.getPath(),false);

                for(String str:childList){
                    System.out.println("更改后的值："+str);
                }

                countDownLatch.countDown();
            }else if(event.getType() == Event.EventType.NodeDeleted){

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
