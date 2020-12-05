package com.ncedu.cheetahtest.entity.action;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SeleniumAction {
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
