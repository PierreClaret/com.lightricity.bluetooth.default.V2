package com.lightricity.station.bluetooth.decoder

import com.lightricity.station.bluetooth.FoundSensor

const val TEMPERATURE_MINIMUM = -40.00
const val TEMPERATURE_MAXIMUM = 85.00

const val HUMIDITY_MINIMUM = 0.0
const val HUMIDITY_MAXIMUM = 102.4

const val PRESSURE_MINIMUM = 50000.0
const val PRESSURE_MAXIMUM = 115534.0

const val TX_POWER_MINIMUM = -40.0
const val TX_POWER_MAXIMUM = 20.0


fun validateValues(tag: FoundSensor) : FoundSensor{
    tag.temperature?.let {
        if (it !in TEMPERATURE_MINIMUM..TEMPERATURE_MAXIMUM) tag.temperature = null
    }

    tag.humidity?.let {
        if (it !in HUMIDITY_MINIMUM..HUMIDITY_MAXIMUM) tag.humidity = null
    }

    tag.pressure?.let {
        if (it !in PRESSURE_MINIMUM..PRESSURE_MAXIMUM) tag.pressure = null
    }

    tag.light?.let {
        if (it !in PRESSURE_MINIMUM..PRESSURE_MAXIMUM) tag.light = null
    }

    tag.sound?.let {
        if (it !in PRESSURE_MINIMUM..PRESSURE_MAXIMUM) tag.sound = null
    }

    tag.accelX?.let {
        if (it !in PRESSURE_MINIMUM..PRESSURE_MAXIMUM) tag.accelX = null
    }

    tag.accelY?.let {
        if (it !in PRESSURE_MINIMUM..PRESSURE_MAXIMUM) tag.accelY = null
    }

    tag.accelZ?.let {
        if (it !in PRESSURE_MINIMUM..PRESSURE_MAXIMUM) tag.accelZ = null
    }


    tag.txPower?.let {
        if (it !in TX_POWER_MINIMUM..TX_POWER_MAXIMUM) tag.txPower = null
    }


    return tag
}