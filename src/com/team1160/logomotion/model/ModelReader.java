/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.team1160.logomotion.model;

/**
 *
 * @author CJ
 */
public class ModelReader {

    private static ModelReader _INSTANCE = new ModelReader();

    private ModelReader(){
    }

    public static ModelReader getInstance(){
        if(_INSTANCE == null){
            _INSTANCE = new ModelReader();
        }
        return _INSTANCE;
    }
}
