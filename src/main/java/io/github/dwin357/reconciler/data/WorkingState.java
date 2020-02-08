/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.github.dwin357.reconciler.data;

import io.github.dwin357.reconciler.output.OutputVector;
import java.util.Set;

/**
 *
 * @author dwin
 */
public class WorkingState {
    
    private Set<String> retries;
    private OutputVector vector;
    private boolean isFailed;
    
    ////  Constructors  ////
    
    public WorkingState(OutputVector logger) {
        super();
        this.vector = logger;
    }
    
    
    ////  Utilities  ////
    
    public void publish(String msg) {
        this.vector.publish(msg);
    }
    
    
    
    //// Getters + Setters  ////

    public Set<String> getRetries() {
        return retries;
    }

    public void setRetries(Set<String> retries) {
        this.retries = retries;
    }

    public boolean isIsFailed() {
        return isFailed;
    }

    public void setIsFailed(boolean isFailed) {
        this.isFailed = isFailed;
    }
    
    
}
