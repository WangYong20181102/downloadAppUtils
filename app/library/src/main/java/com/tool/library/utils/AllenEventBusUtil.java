package com.tool.library.utils;


import com.tool.library.v2.eventbus.CommonEvent;

import org.greenrobot.eventbus.EventBus;

public class AllenEventBusUtil {
    public static void sendEventBus(int eventType) {
        CommonEvent commonEvent = new CommonEvent();
        commonEvent.setSuccessful(true);
        commonEvent.setEventType(eventType);
        EventBus.getDefault().post(commonEvent);
    }
    public static void sendEventBusStick(int eventType) {
        CommonEvent commonEvent = new CommonEvent();
        commonEvent.setSuccessful(true);
        commonEvent.setEventType(eventType);
        EventBus.getDefault().postSticky(commonEvent);
    }
}
