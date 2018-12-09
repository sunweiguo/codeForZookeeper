import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * @Author 【swg】.
 * @Date 2018/12/9 17:49
 * @DESC
 * @CONTACT 317758022@qq.com
 */
public class ZKGetNodeData implements Watcher {

    private ZooKeeper zooKeeper;

    final static String serverPath = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    private static Stat stat = new Stat();

    private static CountDownLatch countDownLatch = new CountDownLatch(1);


    public ZKGetNodeData(){}

    public ZKGetNodeData(String connectionString,int sessionTimeout){
        try {
            zooKeeper = new ZooKeeper(connectionString,sessionTimeout,new ZKGetNodeData());
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
        ZKGetNodeData zkServer = new ZKGetNodeData(serverPath,5*1000);

        byte[] resByte = zkServer.getZooKeeper().getData("/hello",true,stat);
        String result = new String(resByte);
        System.out.println("当前值:"+result);
        countDownLatch.await();
    }


    @Override
    public void process(WatchedEvent event) {
        try {
            if(event.getType() == Event.EventType.NodeDataChanged){
                ZKGetNodeData zkServer = new ZKGetNodeData(serverPath,5*1000);

                byte[] resByte = zkServer.getZooKeeper().getData("/hello",true,stat);
                String result = new String(resByte);

                System.out.println("更改后的值："+result);
                System.out.println("版本："+stat.getVersion());

                countDownLatch.countDown();
            }else if(event.getType() == Event.EventType.NodeCreated){

            }else if(event.getType() == Event.EventType.NodeChildrenChanged){

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
