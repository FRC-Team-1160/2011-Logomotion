/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.model.states;

/**
 *
 * @author CJ
 */
public class PistonState {

    public boolean isExtended;

    public String toString(){
        String output = "";
        output += "Piston Extended State: " + isExtended + '\n';
        return output;
    }

}
