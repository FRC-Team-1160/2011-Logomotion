/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.pnuematics;

import com.team1160.logomotion.model.states.PnuematicsState;
import com.team1160.logomotion.api.Constants;
import com.team1160.logomotion.model.Model;
import com.team1160.logomotion.model.ModelNotifier;
import edu.wpi.first.wpilibj.Compressor;

/**
 *
 * @author CJ
 */
public class Pnuematics implements ModelNotifier{

    protected Compressor compressor;

    public Pnuematics(){
        this.compressor = new Compressor(Constants.C_PNEU_SWITCH,
                Constants.C_PNEU_COMPRESS);
        this.compressor.start();
    }

    public void updateModel(Model model){
        updatePnuematics(model.getCurrentRobotState().getPnuematicsState());
    }

    protected void updatePnuematics(PnuematicsState pneumaticsState){
        pneumaticsState.compressorOn = this.compressor.enabled();
    }
}
