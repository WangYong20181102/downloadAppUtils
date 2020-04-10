package com.tool.library.v2.eventbus;


public class BaseEvent {
    private int eventType;

    public int getEventType() {
        return eventType;
    }

    public BaseEvent setEventType(int eventType) {
        this.eventType = eventType;
        return this;
    }
}
