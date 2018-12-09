package countdownlatchdemo;

import java.util.concurrent.CountDownLatch;

/**
 * @Author 【swg】.
 * @Date 2018/12/9 17:00
 * @DESC
 * @CONTACT 317758022@qq.com
 */
public class ShanghaiStation extends DangerCenter{

    public ShanghaiStation(CountDownLatch countDownLatch) {
        super(countDownLatch, "上海中心");
    }

    @Override
    protected void check() {
        System.out.println(this.getStationName()+"开始检查。。。");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.setOk(true);
        System.out.println(this.getStationName()+"检查结束。。。");
    }
}
