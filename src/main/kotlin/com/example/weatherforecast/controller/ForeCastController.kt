package com.example.weatherforecast.controller

import com.example.weatherforecast.models.DayResponse
import com.example.weatherforecast.models.ForeCastAPIResponse
import com.example.weatherforecast.models.WeatherAPIResponse
import com.example.weatherforecast.service.WeatherAPIService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import java.time.LocalDate


@RestController
class ForeCastController {

    @Autowired
    private var weatherAPIService: WeatherAPIService? = null

    fun KotlinWeatherController(externalApiService: WeatherAPIService?) {
        this.weatherAPIService = externalApiService
    }

    @GetMapping("/forecast")
    fun getDataFromExternalApi(): Mono<ForeCastAPIResponse?>? {
        return weatherAPIService?.getDataFromWeatherApi()?.map { response ->
            mapResponseToForeCast(response)
        }
    }

    private fun mapResponseToForeCast(response: WeatherAPIResponse?): ForeCastAPIResponse? {
        val apiProperties: Map<String, Any>? = response?.properties
        val periods = apiProperties?.get("periods") as? List<Map<String, Any>>?

        if (!periods.isNullOrEmpty()) {
            val curPeriod = periods[0]
            val temperature = curPeriod["temperature"] as? Int

            val celsius = temperature?.let { (it - 32) * 1.8 }

            val shortForecast = curPeriod["shortForecast"] as? String
            val day = LocalDate.now().dayOfWeek.name

            val dailyList = arrayListOf(DayResponse().apply {
                day_name = day
                temp_high_celsius = celsius
                forecast_blurp = shortForecast
            })

            return ForeCastAPIResponse().apply { daily = dailyList }
        } else {
            return null
        }
    }
}