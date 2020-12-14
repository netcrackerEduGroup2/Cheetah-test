package com.ncedu.cheetahtest.config.websocket;

import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;
import org.springframework.web.socket.handler.BinaryWebSocketHandler;

import java.net.http.WebSocket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class WebSocketHandler extends AbstractWebSocketHandler {
    List<WebSocketSession> sessions  = new CopyOnWriteArrayList<>();
    int messageCount = 0;

    @Override
    protected void handleBinaryMessage(WebSocketSession session, BinaryMessage message) throws Exception {
        System.out.println(message);
    }

}
