package com.example.dipexample;

import org.springframework.web.bind.annotation.*;

@RestController
public class CacheController {

    private final WishService wishService;

    public CacheController(WishService wishService) {
        this.wishService = wishService;
    }

    @PostMapping("/wish")
    void save(@RequestBody SaveRequest request) {
        wishService.saveWishCount(request.id(), request.value());
    }

    @GetMapping("/wish/{id}")
    Long getById(@PathVariable String id) {
        return wishService.getWishCount(id);
    }
}
