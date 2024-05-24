package com.tibame.group1.web.controller;

import jakarta.websocket.*;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
@ServerEndpoint("/websocket/bidproduct/{productId}")
public class BidProductWS {

    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

    @OnOpen
    public void onOpen(Session session, @PathParam("productId") Long productId) {
        sessions.add(session);
        // Add logic to retrieve and send initial product info, current price, and bids to the
        // client
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // Handle incoming WebSocket messages (e.g., bid requests)
        // Validate bid amount, update database, and send updated bids and current price to all
        // clients
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Handle errors
    }

    public static void broadcast(String message) {
        synchronized (sessions) {
            for (Session session : sessions) {
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    // Handle exception
                }
            }
        }
    }
}
