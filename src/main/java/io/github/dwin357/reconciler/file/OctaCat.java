/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.dwin357.reconciler.file;

import io.github.dwin357.reconciler.output.OutputVector;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author dwin
 */
public class OctaCat {
    
    public static final String OUTPUT_FILE = "/filename.dat";
    private final OutputVector logger;
    
    public OctaCat(OutputVector vector) {
        super();
        this.logger = vector;
    }
    
    public void writeUnprocessed(String reportPath, String batchPath, String tgtPath) {
        
        File genFile = new File(getFileName(tgtPath));

        if(genFile.exists()) {
            logger.publish(String.format("file already exists at location %s, aborting", genFile.getPath()));
            return;
        }

        try {
            Stream<String> mixedEntries = loadFile(batchPath);
            generateFile(genFile, mixedEntries);
        } catch (IOException ioe) {
            System.out.println("top line at explosion");
            logger.publish(String.format(
                                    "Exception creating file:%s msg:%s", 
                                    genFile.getPath(),
                                    ioe.getMessage())); 
        }
    }
    
    public String getFileName(String tgtPath) {
        return tgtPath + OUTPUT_FILE;
    }
    
    ///////////////  Private  ///////////////////
    
    private Stream<String> loadFile(String path) throws IOException {
        try {
            return Files.lines(Paths.get(path));
        } catch (IOException ex) {            
            System.out.println("load file at explosion");
            throw new IOException(String.format("Failed loading file %s aborting", path), ex); 
        }
    }
    
    private void generateFile(File genFile, Stream<String> mixedEntries) throws IOException {
        try {
            
            try(PrintWriter pw = new PrintWriter(
                                    Files.newBufferedWriter(
                                            Paths.get(genFile.getPath())))) {
                mixedEntries.forEach(pw::println);
            }
            
        } catch (IOException ex) {
            System.out.println("gen file at explosion");
            throw new IOException(String.format("Failed writing file %s aborting", genFile.getPath()), ex);            
        }
    }
}
