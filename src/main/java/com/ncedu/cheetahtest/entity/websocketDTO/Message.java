package com.ncedu.cheetahtest.entity.websocketDTO;

import lombok.Data;

@Data
public class Message {
    private String event;
    private Object data;
}
