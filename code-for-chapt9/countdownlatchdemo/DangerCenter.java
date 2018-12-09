package countdownlatchdemo;

import java.util.concurrent.CountDownLatch;

/**
 * @Author 【swg】.
 * @Date 2018/12/9 16:54
 * @DESC
 * @CONTACT 317758022@qq.com
 */
public abstract class DangerCenter implements Runnable{

    private CountDownLatch countDownLatch;
    private String stationName;
    private boolean isOk;

    public DangerCenter(CountDownLatch countDownLatch,String stationName){
        this.countDownLatch = countDownLatch;
        this.stationName = stationName;
        this.isOk = false;
    }

    @Override
    public void run() {
        try {
            check();
            isOk = true;
        }catch (Exception e){
            isOk = false;
        }finally {
            if(countDownLatch != null){
                countDownLatch.countDown();
            }
        }
    }

    protected abstract void check();

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public boolean isOk() {
        return isOk;
    }

    public void setOk(boolean ok) {
        isOk = ok;
    }
}
