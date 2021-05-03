package frc.robot;

public class SightTarget {
    private double hAngle;
    private double vAngle;
    private double dist;
    private double score;

    public static final int ARGCNT = 4;
    private static final double[] DEFAULTS = new double[ARGCNT];

    public SightTarget(double hAngleIn, double vAngleIn, double distIn, double scoreIn) {
        hAngle = hAngleIn;
        vAngle = vAngleIn;
        dist = distIn;
        score = scoreIn;
    }

    public SightTarget(double[] in, int index) {
        hAngle = in[index++];
        vAngle = in[index++];
        dist = in[index++];
        score = in[index];
    }

    public double getHAngle() {
        return hAngle;
    }

    public double getVAngle() {
        return vAngle;
    }

    public double getDist() {
        return dist;
    }

    public double getScore() {
        return score;
    }
}