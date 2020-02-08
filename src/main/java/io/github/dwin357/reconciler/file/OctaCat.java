/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.dwin357.reconciler.file;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.dwin357.reconciler.output.OutputVector;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
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
            Set<String> reloads = parseFails(reportPath);
            generateFile(genFile, mixedEntries, reloads);
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
    
    ///////////////  Private  ///////////////////
    
    private Set<String> parseFails(String path) throws IOException {
        try {
            Set<String> excludes = new HashSet<>();

            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(Files.readAllBytes(Paths.get(path)));
            Iterator<JsonNode> errors = root.path("errors").elements();

            JsonNode er;
            while(errors.hasNext()) {
                er = errors.next();
                excludes.add(er.path("cor_id").asText());
            }
            
            return excludes;
            
        } catch (IOException ex) {
            throw new IOException(String.format("Failed loading file %s aborting", path), ex); 
        }
    }
    
    private Stream<String> loadFile(String path) throws IOException {
        try {
            return Files.lines(Paths.get(path));
        } catch (IOException ex) {            
            throw new IOException(String.format("Failed loading file %s aborting", path), ex); 
        }
    }
    
    private void generateFile(File genFile, Stream<String> mixedEntries, Set<String> reloads) throws IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();            
            try(PrintWriter pw = new PrintWriter(
                                    Files.newBufferedWriter(
                                            Paths.get(genFile.getPath())))) {
                mixedEntries
                        .filter(ln -> isErroredEntry(ln, reloads, mapper))
                        .forEach(pw::println);
            }
            
        } catch (IOException ex) {
            throw new IOException(String.format("Failed writing file %s aborting", genFile.getPath()), ex);            
        }
    }
    
    private boolean isErroredEntry(String line, Set<String> fails, ObjectMapper mapper) {
        try {
            
            
            JsonNode entry = mapper.readTree(line);
            String id = entry.path("load_data").path("crel_id").asText();

            System.out.println(String.format("id:%s is in?%b set:%s", 
                    id, fails.contains(id), fails.toString()));

  
            return fails.contains(id);
            
        } catch (IOException ex) {
            logger.publish("Error processing entry");
            logger.publish(line);
            return false;
        }
    }
}
