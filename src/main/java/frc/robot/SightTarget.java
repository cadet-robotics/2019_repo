package frc.robot;

import edu.wpi.first.networktables.NetworkTableEntry;

public class SightTarget {
    private double height;
    private double angle;
    private double dist;
    private double score;

    private static final double[] DEFAULTS = new double[] {0, 0, 0, 0};

    public SightTarget(double heightIn, double angleIn, double distIn, double scoreIn) {
        height = heightIn;
        angle = angleIn;
        dist = distIn;
        score = scoreIn;
    }

    public SightTarget(NetworkTableEntry entry) {
        double[] in = entry.getDoubleArray(DEFAULTS);
        height = in[0];
        angle = in[1];
        dist = in[2];
        score = in[3];
    }
}
