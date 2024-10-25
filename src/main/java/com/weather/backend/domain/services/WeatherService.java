package com.weather.backend.domain.services;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;


@Service
@RequiredArgsConstructor
public class WeatherService {
    private final String apiKey = "541723f70b80d1ebe81a23db011c3778";
    private final String baseUrl = "https://api.openweathermap.org/data/2.5/weather";

    private final RestTemplate restTemplate;


    @Cacheable(value = "weather", key="#city")

    public Map<String, Object> getWeatherByCity(String city) {
        try {
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                    .queryParam("q", city)
                    .queryParam("appid", apiKey)
                    .queryParam("units", "metric") // For temperature in Celsius
                    .toUriString();

// https://api.openweathermap.org/data/2.5/weather?q=amman&appid=541723f70b80d1ebe81a23db011c3778&units=metric

//            Map<String, Object> response = restTemplate.getForObject(url, Map.class);
//
//            response.get("weather");
            return restTemplate.getForObject(url, Map.class);
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.UNAUTHORIZED) {
                throw new RuntimeException("Invalid API key") ;
            } else if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("City not found") ;
            }
            throw new RuntimeException("Error fetching weather data: " + e.getMessage()) ;
        }
    }

}
