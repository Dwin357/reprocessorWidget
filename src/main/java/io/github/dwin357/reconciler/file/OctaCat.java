/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.dwin357.reconciler.file;

import io.github.dwin357.reconciler.output.OutputVector;
import java.io.File;
import java.io.IOException;

/**
 *
 * @author dwin
 */
public class OctaCat {
    
    public static final String OUTPUT_FILE = "/filename.dat";
    private OutputVector logger;
    
    public OctaCat(OutputVector vector) {
        super();
        this.logger = vector;
    }
    
    public void writeUnprocessed(String reportPath, String batchPath, String tgtPath) {
        
        File genFile = new File(getFileName(tgtPath));
        try {
            genFile.createNewFile();            
        } catch (IOException ioe) {
            logger.publish(String.format(
                                    "Exception creating file:%s msg:%s", 
                                    genFile.getPath(),
                                    ioe.getMessage()));
        }

    }
    
    public String getFileName(String tgtPath) {
        return tgtPath + OUTPUT_FILE;
    }
}
