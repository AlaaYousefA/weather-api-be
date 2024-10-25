package com.weather.backend.application.controllers;

import com.weather.backend.domain.services.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class WeatherController {
    private final WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<Map<String, Object>> getWeatherByCity(@RequestParam String city) {
        return ResponseEntity.ok(weatherService.getWeatherByCity(city));
    }
}
