import org.apache.zookeeper.AsyncCallback;

/**
 * @Author 【swg】.
 * @Date 2018/12/9 16:02
 * @DESC
 * @CONTACT 317758022@qq.com
 */
public class CreateCallBack implements AsyncCallback.StringCallback {
    @Override
    public void processResult(int rc, String path, Object ctx, String name) {
        System.out.println("创建节点："+path);
        System.out.println((String)ctx);
    }
}
