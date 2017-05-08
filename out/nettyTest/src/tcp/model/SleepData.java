package tcp.model;


/**
 * Created by fadinglan on 2017/4/18.
 */

public class SleepData {
    private int sleepId;

    private String deviceId;
    private String startTime;
    private String endTime;
    private String heartRate;
    private String sleepMove;


    public int getSleepId() {
        return sleepId;
    }

    public void setSleepId(int sleepId) {
        this.sleepId = sleepId;
    }


    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getSleepMove() {
        return sleepMove;
    }

    public void setSleepMove(String sleepMove) {
        this.sleepMove = sleepMove;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
