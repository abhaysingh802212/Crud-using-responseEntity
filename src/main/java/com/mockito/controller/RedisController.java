package com.mockito.controller;

import com.mockito.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redis")
public class RedisController {

    private final RedisService redisService;

    @Autowired
    public RedisController(RedisService redisService) {
        this.redisService = redisService;
    }

    @PostMapping("/set")
    public ResponseEntity<String> set(@RequestParam String key, @RequestParam String value) {
        redisService.setValue(key, value);
        return ResponseEntity.ok("Value set in Redis");
    }

    @GetMapping("/get")
    public ResponseEntity<Object> get(@RequestParam String key) {
        Object value = redisService.getValue(key);
        if (value == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(value);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> delete(@RequestParam String key) {
        redisService.deleteKey(key);
        return ResponseEntity.ok("Key deleted from Redis");
    }

    @GetMapping("/exists")
    public ResponseEntity<String> exists(@RequestParam String key) {
        boolean exists = redisService.exists(key);
        return ResponseEntity.ok(exists ? "Key exists" : "Key does not exist");
    }
}

