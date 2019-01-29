package frc.robot.config;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.Filesystem;

/**
 * Contains the code that loads the config file
 * 
 * @author Alex Pickering
 */
public class ConfigLoader {
    boolean debug = true;
    
    public Map<String, Object> load(String section, ConfigParser cfgparser) throws IOException {
        HashMap<String, Object> retMap = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader(getConfigFile()));
        String type = "";
        
        String line = "";
        boolean inSection = false;
        
        while((line = br.readLine()) != null){
            if(debug) System.out.println(line);
            
            //Find segment
            if(line.equals(section)){
                inSection = true;
                type = br.readLine();
                continue;
            } else if(!inSection) {
                continue;
            }
            
            //End of segment
            if(line.equals("")){
                break;
            }
            
            //Parse and add to map
            String[] sa = line.split(" ");
            String val = "";
            
            //Value may contain spaces
            for(int i = 1; i < sa.length; i++){
                val += " " + sa[i];
            }
            
            retMap.put(sa[0], cfgparser.parse(val.substring(1), type));
        }
        
        br.close();
        
        return retMap;
    }
    
    /**
     * Gets the configuration file to read from
     * Basically chooses the first .cfg file in the deploy directory for ease of use with the file name
     * Returns null if no .cfg files are found
     * @return The File to read as the config file
     */
    File getConfigFile(){
        FilenameFilter fnf = new FilenameFilter() {
            @Override
            public boolean accept(File d, String n) {
                System.out.println(n);//debug
                return n.endsWith(".cfg");
            }
        };
        
        File[] dir = Filesystem.getDeployDirectory().listFiles(fnf);
        
        return (dir.length == 0) ? null : dir[0];
    }
}