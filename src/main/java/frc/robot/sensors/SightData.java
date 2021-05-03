package frc.robot.sensors;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.SightTarget;
import frc.robot.UpdateLineManager;

/**
 * Contains data from the vision systems
 * <p>Javadoc comments lovingly provided by Alex Pickering 
 * 
 * @author Owen Avery
 */
public class SightData {
    private SightTarget[] targets = null;
    private int bestTarget;
    private NetworkTableInstance nt;
    private int listener;

    public SightData(NetworkTableInstance ntIn) {
        nt = ntIn;
        listener = UpdateLineManager.startListener(ntIn, this);
    }

    public void setData(NetworkTableEntry entry) {
        double[] data = entry.getDoubleArray((double[]) null);
        if ((data == null) || ((data.length % SightTarget.ARGCNT) != 0)) {
            targets = null;
            bestTarget = -1;
            return;
        }
        targets = new SightTarget[data.length / SightTarget.ARGCNT];
        int index = 0;
        for (int i = 0; i < targets.length; i++, index += SightTarget.ARGCNT) {
            targets[i] = new SightTarget(data, index);
        }
        if (targets.length == 0) {
            bestTarget = -1;
        } else {
            bestTarget = 0;
            double bestScore = targets[0].getScore();
            double t;
            for (int i = 0; i < targets.length; i++) {
                if ((t = targets[i].getScore()) > bestScore) {
                    bestTarget = i;
                    bestScore = t;
                }
            }
        }
    }

    public SightTarget getBest() {
        if (bestTarget == -1) return null;
        return targets[bestTarget];
    }

    public void clear() {
        targets = null;
        bestTarget = -1;
    }

    @Override
    protected void finalize() {
        nt.removeEntryListener(listener);
    }
}