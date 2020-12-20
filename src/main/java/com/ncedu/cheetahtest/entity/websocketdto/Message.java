package com.ncedu.cheetahtest.entity.websocketdto;

import lombok.Data;

@Data
public class Message {
    private String event;
    private Object data;
}
