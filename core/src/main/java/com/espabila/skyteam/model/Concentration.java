package com.espabila.skyteam.model;

public class Concentration {
    private int tokenLimit = 3;
    private int tokenAmount = 0;

    public void generateToken(){
        if (tokenAmount <= tokenLimit){
            // code to take a dice


            tokenAmount++;
        }
        else {
            //return warning message "no more coffee space"
        }
    }
}
