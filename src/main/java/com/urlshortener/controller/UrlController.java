package com.urlshortener.controller;

import com.urlshortener.entity.Url;
import com.urlshortener.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<Url> shortenUrl(@RequestParam String originalUrl) {
        Url url = urlService.createShortUrl(originalUrl);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> getOriginalUrl(@PathVariable String shortCode) {
        Optional<Url> url = urlService.getOriginalUrl(shortCode);

        if (url.isPresent()) {
            return ResponseEntity
                    .status(302)
                    .header("Location", url.get().getOriginalUrl())
                    .build();
        }

        return ResponseEntity.notFound().build();
    }
}