package countdownlatchdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author 【swg】.
 * @Date 2018/12/9 17:03
 * @DESC
 * @CONTACT 317758022@qq.com
 */
public class CheckStartUp {
    private static CountDownLatch countDownLatch;
    private static List<DangerCenter> dangerCenters;

    public CheckStartUp(){}

    public static boolean checkAllStations() throws InterruptedException {
        countDownLatch = new CountDownLatch(3);
        dangerCenters = new ArrayList<>();

        dangerCenters.add(new NanjingStation(countDownLatch));
        dangerCenters.add(new ShanghaiStation(countDownLatch));
        dangerCenters.add(new YanchengStation(countDownLatch));

        ExecutorService executorService = Executors.newFixedThreadPool(dangerCenters.size());

        for(DangerCenter center:dangerCenters){
            executorService.execute(center);
        }

        countDownLatch.await();

        executorService.shutdown();

        for (DangerCenter center:dangerCenters){
            if(!center.isOk()){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws InterruptedException {
        boolean result = CheckStartUp.checkAllStations();
        System.out.println("针对所有的站点的情况："+result);
    }
}
