import org.apache.zookeeper.*;

import java.io.IOException;

/**
 * @Author 【swg】.
 * @Date 2018/12/9 15:43
 * @DESC 原生api操作zk节点
 * @CONTACT 317758022@qq.com
 */
public class ZKNodeOperator implements Watcher {

    final static String serverPath = "127.0.0.1:2181,127.0.0.1:2182,127.0.0.1:2183";

    private ZooKeeper zooKeeper = null;

    public ZooKeeper getZooKeeper(){
        return zooKeeper;
    }

    public void setZooKeeper(ZooKeeper zooKeeper){
        this.zooKeeper = zooKeeper;
    }

    public ZKNodeOperator(){}

    public ZKNodeOperator(String connectionString,int sessionTimeout){
        try {
            zooKeeper = new ZooKeeper(connectionString,sessionTimeout,new ZKNodeOperator());
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


    public static void main(String[] args) {
        ZKNodeOperator zkServer = new ZKNodeOperator(serverPath,5*1000);

        //1. 同步创建节点
        /**
         * zkServer.syncCreateZkNode(path,data,acls);
         * path：节点路径
         * data：节点数据
         * acls：节点权限，有Id ANYONE_ID_UNSAFE = new Id("world", "anyone");和Id AUTH_IDS = new Id("auth", "");
         */
        /*
        try {
            zkServer.getZooKeeper().create("/testNode1","123".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }*/


        //2. 异步创建节点
        /**
         * path：节点路径
         * data：节点数据
         * acls：权限
         * mode：持久类型还是其他类型
         * callback：异步的回调函数
         * ctx：回调内容
         */
        /*
        String ctx = "{'create':'success'}";
        zkServer.getZooKeeper().create("/testNode2","456".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE,CreateMode.PERSISTENT,new CreateCallBack(),ctx);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //3. 同步修改节点
        /*
        try {
            zkServer.getZooKeeper().setData("/testNode1","update123".getBytes(),0);//版本必须要对
            Thread.sleep(2000);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //4. 异步修改节点
        /*
        String ctx = "{'update':'success'}";
        zkServer.getZooKeeper().setData("/testNode1","update123456".getBytes(),1,new UpdateCallBack(),ctx);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        //5. 同步删除节点
        /*
        try {
            zkServer.getZooKeeper(). delete("/testNode1",2);
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }*/

        //6. 异步删除数据

        String ctx = "{'delete':'success'}";
        zkServer.getZooKeeper().delete("/testNode1",0,new DeleteCallBack(),ctx);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void process(WatchedEvent watchedEvent) {
        System.out.println("watch被触发..."+watchedEvent);
    }


}
