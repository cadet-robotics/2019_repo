package frc.robot;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.ArrayList;

public class SightTargetHistory {
    public SightTarget[] targets = new SightTarget[20];
    public int[] timestamps = new int[20];
    public int len;

    public SightTargetHistory() {
    }

    public void addTarget(SightTarget targetIn) {
    }

    public double addScore(SightTarget targetIn) {
    }
}
