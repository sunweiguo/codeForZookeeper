import org.apache.zookeeper.AsyncCallback;

/**
 * @Author 【swg】.
 * @Date 2018/12/9 16:02
 * @DESC
 * @CONTACT 317758022@qq.com
 */
public class DeleteCallBack implements AsyncCallback.VoidCallback {
    @Override
    public void processResult(int i, String s, Object o) {
        System.out.println("创建节点："+s);
        System.out.println((String)o);
    }
}
