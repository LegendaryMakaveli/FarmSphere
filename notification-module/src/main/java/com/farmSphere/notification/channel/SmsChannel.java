package com.farmSphere.notification.channel;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;



@Slf4j
@Component
public class SmsChannel {
    public void send(String phoneNumber, String message) {
        // Twilio or Termii later
        log.info("📱 SMS SENT to {}: {}", phoneNumber, message);
    }
}
