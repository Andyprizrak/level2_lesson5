package ru.geekbrains.home_level2_lesson2;

public class CalculateArrayThred extends Thread {
    private long timeRun;
    private float calcArr [];
    private int h;

    public CalculateArrayThred(long timeRun, float [] calcArr, int h) {
        this.timeRun = timeRun;
        this.calcArr = calcArr;
        this.h = h;
    }

    public long getTime () {
        return timeRun;
    }

    @Override
    public void run() {
        long timeBegin = System.currentTimeMillis();
        for (int i =0; i < calcArr.length; i++)
         calcArr[i] = (float) (calcArr[i] * Math.sin(0.2f + (i+h) / 5) * Math.cos(0.2f + (i+h) / 5) * Math.cos(0.4f + (i+h) / 2));
        timeRun = System.currentTimeMillis()-timeBegin;
    }
}
