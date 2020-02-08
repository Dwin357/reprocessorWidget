/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.dwin357.reconciler.file;

import io.github.dwin357.reconciler.output.OutputVector;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;

/**
 *
 * @author dwin
 */
public class OctaCatTest {
    
    @Rule
    public TemporaryFolder tgtDir = new TemporaryFolder();
    
    @Mock
    private OutputVector outputMock;

    private OctaCat classUnderTest;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        classUnderTest = new OctaCat(outputMock);
    }

    @Test
    public void test_getFileName() {
        // setup
        String tgtPath = tgtDir.getRoot().getPath();
        String expected = tgtPath + OctaCat.OUTPUT_FILE;
        
        // tested act
        String actual = classUnderTest.getFileName(tgtPath);
        
        // assertion
        assertEquals(expected, actual);
    }
    
    @Test
    public void test_writeUnprocessed_writeFile() {
        // setup
        String reportPath = "";
        String batchPath = getPathOfResource("/scenario_1/batch.dat");
        String tgtPath = tgtDir.getRoot().getPath();
        
        File tgtFile = new File(classUnderTest.getFileName(tgtPath));
        
        // sanity ck
        assertFalse(tgtFile.exists());

        // tested act
        classUnderTest.writeUnprocessed(reportPath, batchPath, tgtPath);
        
        // assertions
        assertTrue(tgtFile.exists());
    }
    
    @Test
    public void test_writeUnprocessed_outputsMsgWhenFileExists() throws IOException {
        // setup
        String reportPath = "";
        String batchPath = "";
        String tgtPath = tgtDir.getRoot().getPath();
        
        File tgtFile = new File(classUnderTest.getFileName(tgtPath));
        tgtFile.createNewFile();
        
        // sanity ck
        assertTrue(tgtFile.exists());

        // tested act
        classUnderTest.writeUnprocessed(reportPath, batchPath, tgtPath);
        
        // assertions
        verify(outputMock, atLeastOnce()).publish(any());
    }
    
    @Test
    public void test_scenario1_dat_with_one_error() throws IOException {
        // setup        
        //// args
        String reportPath = "";        
        String batchPath = getPathOfResource("/scenario_1/batch.dat");
        String tgtPath = tgtDir.getRoot().getPath();
        //// expected
        File expectedFile = new File(getPathOfResource("/scenario_1/expected.dat"));
        
        // tested act
        classUnderTest.writeUnprocessed(reportPath, batchPath, tgtPath);

        // measurements
        File actual = new File(classUnderTest.getFileName(tgtPath));
        
        // assertion
        //// new file created
        assertTrue("File was not created", actual.exists());
        //// new file has expected content
        assertTrue("Created file did not have expected content", FileUtils.contentEquals(actual, expectedFile));        
    }
    
    
    /////////   Utilities
    
    private String getPathOfResource(String resource) {
        return getClass().getResource(resource).getPath();
    }
    
    
}
