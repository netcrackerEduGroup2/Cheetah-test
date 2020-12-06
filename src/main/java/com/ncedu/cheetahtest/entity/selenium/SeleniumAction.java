package com.ncedu.cheetahtest.entity.selenium;

import lombok.Data;

@Data
public class SeleniumAction {
    private Integer actionId;
    private Integer compoundId;
    private String actionType;
    private String element;
    private String argument;

    public SeleniumAction(Integer compoundId, String actionType, String element, String argument) {
        this.compoundId = compoundId;
        this.actionType = actionType;
        this.element = element;
        this.argument = argument;
    }

    public SeleniumAction(String actionType, String element, String argument) {
        this.actionType = actionType;
        this.element = element;
        this.argument = argument;
    }
}
