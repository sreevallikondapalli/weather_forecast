package com.example.weatherforecast

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class WeatherForecastApplication

fun main(args: Array<String>) {
	runApplication<WeatherForecastApplication>(*args)
}
