package frc.robot;

import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Works with networktables to get vision data
 * Javadoc comments lovingly provided by Alex Pickering
 * 
 * @author Owen Avery
 */
public class UpdateLineManager {
    //public static final int LOG_STORE = 200;
    //public static final int LOG_TIME = 5;

    private NetworkTableInstance nt;
    private NetworkTableEntry dataTable;

    public SightData see;

   // private static final double LINE_ERROR_MAX = Double.MAX_VALUE;

    private double[] old = null;

    private int listener;
    
    /**
     * Updates the data for line tracking in the vision system
     * 
     * @param ntIn Networktables instance
     * @param seeIn Sightdata instance
     */
    public UpdateLineManager(NetworkTableInstance ntIn, SightData seeIn) {
        nt = ntIn;
        dataTable = ntIn.getTable("ShuffleBoard").getEntry("line");
        if (seeIn == null) see = new SightData();
        else see = seeIn;
        listener = dataTable.addListener((e) -> {
            double[] n;
            try {
                n = e.value.getDoubleArray();
            } catch (ClassCastException ex) {
                ex.printStackTrace();
                return;
            }
            if (n.length != 4) return;
            if (old != null) {
                System.out.println("Line Error: " + errorCalc(old, n));
            }
            old = n;
        }, EntryListenerFlags.kUpdate + EntryListenerFlags.kNew);
    }
    
    /**
     * Calculates the error of a pair of lines
     * 
     * @param l1 The first line
     * @param l2 The second line
     * @return The error between the two lines
     */
    public static double errorCalc(double[] l1, double[] l2) {
        double c1x = (l1[0] + l1[2]) / 2;
        double c1y = (l1[1] + l1[3]) / 2;
        double c2x = (l2[0] + l2[2]) / 2;
        double c2y = (l2[1] + l2[3]) / 2;
        double xd = c2x - c1x;
        double yd = c2y - c1y;
        double cerr = Math.sqrt(xd * xd / yd * yd);
        double serr = Math.atan2(l1[3] - l1[1], l1[2] - l1[0]) - Math.atan2(l2[3] - l2[1], l2[2] - l2[0]);
        return cerr + serr;
    }
}