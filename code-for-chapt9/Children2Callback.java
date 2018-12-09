import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @Author 【swg】.
 * @Date 2018/12/9 18:22
 * @DESC
 * @CONTACT 317758022@qq.com
 */
public class Children2Callback implements AsyncCallback.Children2Callback {
    @Override
    public void processResult(int rc, String path, Object ctx, List<String> list, Stat stat) {
        for(String str:list){
            System.out.println(str);
        }

        System.out.println("ChildrenCallBack:"+path);
        System.out.println((String)ctx);
        System.out.println(stat.toString());
    }
}
