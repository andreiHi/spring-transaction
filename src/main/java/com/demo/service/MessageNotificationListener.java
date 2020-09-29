package com.demo.service;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

import static com.demo.controller.XaApiRestController.DESTINATION;

@Service
public class MessageNotificationListener {

    @JmsListener(destination = DESTINATION)
    public void onNewMessage(String id) {
        System.out.println("message Id: " + id);
    }
}
