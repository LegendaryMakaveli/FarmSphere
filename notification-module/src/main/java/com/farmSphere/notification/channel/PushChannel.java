package com.farmSphere.notification.channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PushChannel {
    public void send(String userId, String title, String message) {
        // Firebase FCM later
        log.info("🔔 PUSH SENT to userId {}: {}", userId, message);
    }
}
