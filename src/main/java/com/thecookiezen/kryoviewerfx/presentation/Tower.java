package com.thecookiezen.kryoviewerfx.presentation;

import javax.annotation.PostConstruct;

public class Tower {

    @PostConstruct
    public void init() {
        System.out.println("Tower.init()");
    }

    public String readyToTakeoff() {
        System.out.println("Ready to take-off");
        return "ok from tower";
    }

}
