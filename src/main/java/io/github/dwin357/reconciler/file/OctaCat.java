/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.dwin357.reconciler.file;

import java.io.File;

/**
 *
 * @author dwin
 */
public class OctaCat {
    
    public static final String OUTPUT_FILE = "/filename.dat";
    
    public void writeUnprocessed(String reportPath, String batchPath, String tgtPath) {
        
//        File xx = new File(getFileName(tgtPath));
    }
    
    public String getFileName(String tgtPath) {
        return tgtPath + OUTPUT_FILE;
    }
}
