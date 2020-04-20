package ru.geekbrains.home_level2_lesson2;

public class CalculateArrayThred extends Thread {
    private long timeRun;
    private float calcArr [];

    public CalculateArrayThred(long timeRun, float [] calcArr) {
        this.timeRun = timeRun;
        this.calcArr = calcArr;
    }

    public long getTime () {
        return timeRun;
    }

    @Override
    public void run() {
        long timeBegin = System.currentTimeMillis();
        for (int i =0; i < calcArr.length; i++)
         calcArr[i] = (float) (calcArr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        timeRun = System.currentTimeMillis()-timeBegin;
    }
}
