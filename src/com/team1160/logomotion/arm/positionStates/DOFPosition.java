/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.arm.positionStates;

/**
 *
 * @author CJ
 */
public class DOFPosition {
    protected int value;

    private DOFPosition(int value){this.value = value;}

    public String toString(){
        switch(value){
            case 1: return "ScoreTopSide";
            case 2: return "ScoreMidSide";
            case 3: return "ScoreLowSide";
            case 4: return "Stow";
            case 5: return "Ground";
            case 6: return "User Move";
            case 7: return "Reload";
            case 8: return "ScoreTopMid";
            case 9: return "ScoreMidMid";
            case 10: return "ScoreLowMid";
            case 11: return "Reload Top";
        }
        throw new IllegalStateException("In DOFPosition:toString, fell through" +
                "on state values.");
    }

    public static DOFPosition kScoreTopSide = new DOFPosition(1);
    public static DOFPosition kScoreMidSide = new DOFPosition(2);
    public static DOFPosition kScoreLowSide = new DOFPosition(3);
    public static DOFPosition kStow = new DOFPosition(4);
    public static DOFPosition kGround = new DOFPosition(5);
    public static DOFPosition kUserMove = new DOFPosition(6);
    public static DOFPosition kReload = new DOFPosition(7);
    public static DOFPosition kScoreTopMid = new DOFPosition(8);
    public static DOFPosition kScoreMidMid = new DOFPosition(9);
    public static DOFPosition kScoreLowMid = new DOFPosition(10);
    public static DOFPosition kReloadTop = new DOFPosition(11);

    public boolean equals(Object two){
        return this.value == ((DOFPosition)two).value;
    }

}
