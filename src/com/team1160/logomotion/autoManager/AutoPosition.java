/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.autoManager;

/**
 *
 * @author CJ
 */
public class AutoPosition {

    protected int value;

    public static final int SIDE_MID = 1;
    public static final int SIDE_TOP = 2;
    public static final int MID_MID = 3;
    public static final int MID_TOP = 4;

    private AutoPosition(int value){
        this.value = value;
    }

    public String toString(){
        switch(value){
            case SIDE_MID: return "Side Middle.";
            case SIDE_TOP: return "Side Top.";
            case MID_MID: return "Middle Middle.";
            case MID_TOP: return "Middle Top.";
            default: throw new IllegalStateException("In AutoPosition:toString," +
                    "fell through on states.");
        }
    }

    public static AutoPosition kSideMiddle = new AutoPosition(SIDE_MID);
    public static AutoPosition kSideTop = new AutoPosition(SIDE_TOP);
    public static AutoPosition kMidMid = new AutoPosition(MID_MID);
    public static AutoPosition kMidTop = new AutoPosition(MID_TOP);

    public boolean equals(Object two){
        if(!(two instanceof AutoPosition)){
            return false;
        }
        return this.value == ((AutoPosition)two).value;
    }

}
