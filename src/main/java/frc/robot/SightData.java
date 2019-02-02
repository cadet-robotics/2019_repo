package frc.robot;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SightData {
    private double lastUpdate = Double.MIN_VALUE;
    private static final double timeout = 500;

    private double[] data = new double[4];
    private double r;
    private double xOff;
    private double yOff;

    private static final double X_WIDTH = 320;
    private static final double Y_WIDTH = 240;

    private boolean tieToFirst = true;

    private ReadWriteLock editLock = new ReentrantReadWriteLock();

    public boolean isTimeout() {
        editLock.readLock().lock();
        boolean v = (lastUpdate + timeout) < System.currentTimeMillis();
        editLock.readLock().unlock();
        return v;
    }

    public void setPoints(double p1x, double p1y, double p2x, double p2y) {
        editLock.writeLock().lock();
        tieToFirst ^= distSq(p1x, p1y, data[0], data[1]) < distSq(p2x, p2y, data[0], data[1]);
        data[0] = p1x;
        data[1] = p1y;
        data[2] = p2x;
        data[3] = p2y;
        int po = tieToFirst ? 0 : 2;
        double cx = (data[2] - data[0]) / 2;
        double cy = (data[3] - data[1]) / 2;
        double px = data[po];
        double py = data[po];
        r = Math.toDegrees(Math.atan2(py - cy, px - cx));
        xOff = (p2x - p1x - X_WIDTH) / 2;
        yOff = (p2y - p1y - Y_WIDTH) / 2;
        editLock.writeLock().unlock();
    }

    public void flip() {
        editLock.writeLock().lock();
        tieToFirst = !tieToFirst;
        editLock.writeLock().unlock();
    }

    public double getRotOffset() {
        editLock.readLock().lock();
        double v;
        if (isTimeout()) v = 0;
        else v = r;
        editLock.readLock().unlock();
        return v;
    }

    public double getXOffset() {
        editLock.readLock().lock();
        double v;
        if (isTimeout()) v = 0;
        else v = xOff;
        editLock.readLock().unlock();
        return v;
    }

    public double getYOffset() {
        editLock.readLock().lock();
        double v;
        if (isTimeout()) v = 0;
        else v = yOff;
        editLock.readLock().unlock();
        return v;
    }

    public static double distSq(double p1x, double p1y, double p2x, double p2y) {
        double x, y;
        x = p2x - p1x;
        y = p2y = p1y;
        return x * x + y * y;
    }
}