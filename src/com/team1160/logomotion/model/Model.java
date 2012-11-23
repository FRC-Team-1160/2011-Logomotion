/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.model;

/**
 * Keeps track of all the notifiers and holds the robot state
 * @author CJ
 */
public class Model {

    private static Model _INSTANCE = new Model();  //Makes all models share the same instance

    protected ModelNotifier[] notifiers; // List of all model notifiers
    protected RobotState currentRobotState; 

    private Model(){ //Private constructor needed for singleton 
        this.notifiers = new ModelNotifier[0];
        this.currentRobotState = new RobotState();
    }

    public static Model getInstance(){ // More singleton stuff
        if(_INSTANCE == null){
            _INSTANCE = new Model();
        }
        return _INSTANCE;
    }

    public void addModelNotifier(ModelNotifier toAdd){ // Adds a new notifier to the list
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

    public void updateModel(){  // Tells all the subsystems to update the model
        for(int i = 0; i < this.notifiers.length; ++i){  // Goes through all the notifers
            this.notifiers[i].updateModel(this);  // And tells them to update
        }
    }

    public RobotState getCurrentRobotState(){
        if(this.currentRobotState == null){
            throw new IllegalStateException("In Model:getCurrentRobotState, null RobotState.");
        }
        return this.currentRobotState;
    }
}
