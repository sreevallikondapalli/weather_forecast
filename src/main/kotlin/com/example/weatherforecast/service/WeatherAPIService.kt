package com.example.weatherforecast.service

import com.example.weatherforecast.models.WeatherAPIResponse
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono

@Service
class WeatherAPIService {

    private val webClient: WebClient = WebClient.builder()
            .baseUrl("https://api.weather.gov/gridpoints/MLB/33,70")
            .build()

    fun getDataFromWeatherApi(): Mono<WeatherAPIResponse> {
        return webClient
                .get()
                .uri("/forecast")
                .retrieve()
                .bodyToMono(WeatherAPIResponse::class.java)
    }
}