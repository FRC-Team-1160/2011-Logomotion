/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.model;

/**
 *
 * @author CJ
 */
public class Model {

    private static Model _INSTANCE = new Model();

    protected ModelNotifier[] notifiers;
    protected RobotState currentRobotState;

    private Model(){
        this.notifiers = new ModelNotifier[0];
        this.currentRobotState = new RobotState();
    }

    public static Model getInstance(){
        if(_INSTANCE == null){
            _INSTANCE = new Model();
        }
        return _INSTANCE;
    }

    public void addModelNotifier(ModelNotifier toAdd){
        if(this.notifiers == null || toAdd == null){
            throw new IllegalStateException("In Model:addModelnotifier, null notifiers.");
        }
        ModelNotifier[] temp = new ModelNotifier[this.notifiers.length + 1];
        for(int i = 0; i < this.notifiers.length; ++i){
            temp[i] = this.notifiers[i];
        }
        temp[this.notifiers.length] = toAdd;
        this.notifiers = temp;
    }

    public void updateModel(){
        for(int i = 0; i < this.notifiers.length; ++i){
            this.notifiers[i].updateModel(this);
        }
    }

    public RobotState getCurrentRobotState(){
        if(this.currentRobotState == null){
            throw new IllegalStateException("In Model:getCurrentRobotState, null RobotState.");
        }
        return this.currentRobotState;
    }
}
