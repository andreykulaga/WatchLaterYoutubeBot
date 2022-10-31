package com.example.watchlateryoutubebot.controllers;

import com.example.watchlateryoutubebot.WatchLaterYoutubeBot;
import com.example.watchlateryoutubebot.models.TestEntity;
import com.example.watchlateryoutubebot.services.TestEntityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;

@RestController
@AllArgsConstructor
public class WebhookController {
    private final WatchLaterYoutubeBot watchLaterYoutubeBot;
    private final TestEntityService testEntityService;

    @PostMapping("/telegram/")
    public BotApiMethod<?> onUpdateReceived(@RequestBody Update update) {
        return watchLaterYoutubeBot.onWebhookUpdateReceived(update);
    }

    @GetMapping("/test_get")
    @ResponseBody
    public TestEntity onTestGet(@RequestParam Long id) {

        return testEntityService.getEntity(id);
    }

    @PostMapping("/test_put")
    public String onTestPut(@RequestParam String name,
                            @RequestParam Integer intValue) {
        testEntityService.putEntity(name, intValue);
        return "success";
    }
}