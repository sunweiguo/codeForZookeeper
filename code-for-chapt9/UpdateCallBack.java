import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

/**
 * @Author 【swg】.
 * @Date 2018/12/9 16:02
 * @DESC
 * @CONTACT 317758022@qq.com
 */
public class UpdateCallBack implements AsyncCallback.StatCallback {
    @Override
    public void processResult(int i, String s, Object o, Stat stat) {
        System.out.println("更新节点："+s);
        System.out.println((String)o);
    }
}
