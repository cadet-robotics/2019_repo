package frc.robot;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Contains data from the vision systems
 * Javadoc comments lovingly provided by Alex Pickering 
 * 
 * @author Owen Avery
 */
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
    
    /**
     * Determines whether or not the read thing has timed out
     * 
     * @return Whether or not there's been a timeout
     */
    public boolean isTimeout() {
        editLock.readLock().lock();
        boolean v = (lastUpdate + timeout) < System.currentTimeMillis();
        editLock.readLock().unlock();
        return v;
    }
    
    /**
     * Sets vision target position for PID loops
     * 
     * @param p1x First point X
     * @param p1y First point Y
     * @param p2x Second point X
     * @param p2y Second point Y
     */
    public void setPoints(double p1x, double p1y, double p2x, double p2y) {
        editLock.writeLock().lock();
        lastUpdate = System.currentTimeMillis();
        int t1 = tieToFirst ? 0 : 2;
        int t2 = t1 + 1;
        tieToFirst ^= distSq(tieToFirst ? p1x : p2x, tieToFirst ? p1y : p2y, data[t1], data[t2]) > distSq(tieToFirst ? p2x : p1x, tieToFirst ? p2y : p1y, data[t1], data[t2]);
        data[0] = p1x;
        data[1] = p1y;
        data[2] = p2x;
        data[3] = p2y;
        double cx = (data[2] - data[0]) / 2;
        double cy = (data[3] - data[1]) / 2;
        double px = data[t1];
        double py = data[t2];
        r = Math.toDegrees(Math.atan2(py - cy, px - cx));
        xOff = (p2x - p1x - X_WIDTH) / 2;
        yOff = (p2y - p1y - Y_WIDTH) / 2;
        editLock.writeLock().unlock();
    }
    
    /**
     * Complements 'tieToFirst' variable
     */
    public void flip() {
        editLock.writeLock().lock();
        tieToFirst = !tieToFirst;
        editLock.writeLock().unlock();
    }
    
    /**
     * Gets the rotation offset
     * 
     * @return The rotation offset
     */
    public double getRotOffset() {
        editLock.readLock().lock();
        double v;
        if (isTimeout()) v = 0;
        else v = r;
        editLock.readLock().unlock();
        return v;
    }
    
    /**
     * Gets the X offset
     * 
     * @return The X offset
     */
    public double getXOffset() {
        editLock.readLock().lock();
        double v;
        if (isTimeout()) v = 0;
        else v = xOff;
        editLock.readLock().unlock();
        return v;
    }
    
    /**
     * Gets the Y offset
     * 
     * @return The Y offset
     */
    public double getYOffset() {
        editLock.readLock().lock();
        double v;
        if (isTimeout()) v = 0;
        else v = yOff;
        editLock.readLock().unlock();
        return v;
    }
    
    /**
     * Gets the length of the provided line squared
     * 
     * @param p1x The start point X
     * @param p1y The start point Y
     * @param p2x The end point X
     * @param p2y The end point Y
     * @return The length of the line squared
     */
    public static double distSq(double p1x, double p1y, double p2x, double p2y) {
        double x, y;
        x = p2x - p1x;
        y = p2y = p1y;
        return x * x + y * y;
    }
}