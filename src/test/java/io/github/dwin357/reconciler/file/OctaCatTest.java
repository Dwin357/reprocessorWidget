/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.dwin357.reconciler.file;

import io.github.dwin357.reconciler.output.OutputVector;
import java.io.File;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;

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
        String batchPath = "";
        String tgtPath = tgtDir.getRoot().getPath();
        
        File tgtFile = new File(classUnderTest.getFileName(tgtPath));
        
        // sanity ck
        assertFalse(tgtFile.exists());

        // tested act
        classUnderTest.writeUnprocessed(reportPath, batchPath, tgtPath);
        
        // assertions
        assertTrue(tgtFile.exists());
    }
    
}
