/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.model.states;

/**
 *
 * @author CJ
 */
public class PnuematicsState {

    /**
     * Is the compressor on?
     */
    public boolean compressorOn;

    public String toString(){
        String output = "";
        output += "/-----\n";
        output += "Pneumatics State\n";
        output += "Compressor on?: " + this.compressorOn + '\n';
        return output;
    }
}
