package frc.robot;

public class SightTarget {
    private double height;
    private double angle;
    private double dist;
    private double score;

    public static final int ARGCNT = 4;
    private static final double[] DEFAULTS = new double[ARGCNT];

    public SightTarget(double heightIn, double angleIn, double distIn, double scoreIn) {
        height = heightIn;
        angle = angleIn;
        dist = distIn;
        score = scoreIn;
    }

    public SightTarget(double[] in, int index) {
        height = in[index++];
        angle = in[index++];
        dist = in[index++];
        score = in[index];
    }
}