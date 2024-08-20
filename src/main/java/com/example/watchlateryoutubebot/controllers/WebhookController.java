package com.example.watchlateryoutubebot.controllers;

import com.example.watchlateryoutubebot.WatchLaterYoutubeBot;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@AllArgsConstructor
public class WebhookController {
    private final WatchLaterYoutubeBot watchLaterYoutubeBot;

    @PostMapping("/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return watchLaterYoutubeBot.onWebhookUpdateReceived(update);
    }

    // Handle GET requests to the /webhook endpoint
    @GetMapping("/")
    public String handleGetRequest() {
        // You can return a message or perform some logic here
        return "Webhook endpoint is active";
    }
}