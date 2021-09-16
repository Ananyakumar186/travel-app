package com.example.travelapp

import org.json.JSONException
import org.json.JSONObject
import kotlin.math.round

class weatherData {
    private var mTemperature: String? = null
    var micon: String? = null
        private set
    var mcity: String? = null
        private set
    private var mWeatherType: String? = null
    private var mCondition = 0
    fun getmTemperature(): String {
        return "$mTemperature°C"
    }

    fun getmWeatherType(): String? {
        return mWeatherType
    }

    companion object {
        fun fromJson(jsonObject: JSONObject): weatherData? {
            return try {
                val weatherD = weatherData()
                weatherD.mcity = jsonObject.getString("name")
                weatherD.mCondition =
                    jsonObject.getJSONArray("weather").getJSONObject(0).getInt("id")
                weatherD.mWeatherType =
                    jsonObject.getJSONArray("weather").getJSONObject(0).getString("main")
                weatherD.micon = updateWeatherIcon(weatherD.mCondition)
                val tempResult = jsonObject.getJSONObject("main").getDouble("temp") - 273.15
                val roundedValue = round(tempResult).toInt()
                weatherD.mTemperature = roundedValue.toString()
                weatherD
            } catch (e: JSONException) {
                e.printStackTrace()
                null
            }
        }

        private fun updateWeatherIcon(condition: Int): String {
            if (condition in 0..300) {
                return "thunderstrom1"
            } else if (condition in 300..500) {
                return "lightrain"
            } else if (condition in 500..600) {
                return "shower"
            } else if (condition in 600..700) {
                return "snow2"
            } else if (condition in 701..771) {
                return "fog"
            } else if (condition in 772..800) {
                return "overcast"
            } else if (condition == 800) {
                return "sunny"
            } else if (condition in 801..804) {
                return "cloudy"
            } else if (condition in 900..902) {
                return "thunderstrom1"
            }
            if (condition == 903) {
                return "snow1"
            }
            if (condition == 904) {
                return "sunny"
            }
            return if (condition in 905..1000) {
                "thunderstrom2"
            } else "dunno"
        }
    }
}