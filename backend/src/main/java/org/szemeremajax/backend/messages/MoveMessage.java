package org.szemeremajax.backend.messages;

public class MoveMessage {
    private String uuid;
    private int from, to;

    public String getUuid() {
        return uuid;
    }

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }
}
