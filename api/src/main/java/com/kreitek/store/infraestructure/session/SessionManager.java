package com.kreitek.store.infraestructure.session;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SessionManager {

    private Map<String, Session> sessions = new ConcurrentHashMap<>();

    public void addSession(String token, Session session) {
        sessions.put(token, session);
    }

    public void removeSession(String token) {
        sessions.remove(token);
    }

    public boolean isValid(String token) {
        return sessions.containsKey(token);
    }

    public boolean isActive(String token) {
        Session session = sessions.get(token);
        if (session == null) {
            return false;
        }
        return true;
    }

    public Map<String, Session> getSessions() {
        return sessions;
    }

}
