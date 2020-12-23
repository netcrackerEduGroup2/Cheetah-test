package com.ncedu.cheetahtest.config.websocket;

import lombok.Data;

import java.security.Principal;

@Data
public class StompPrincipal implements Principal {
    private final String name;

    @Override
    public String getName() {
        return name;
    }
}
