package com.ssoft.ews4thai.data.model.warning

data class WarningStationMapResponse(
    val `data`: List<WarningStation>?,
    val warning_station: Int

)

data class WarningStationMapResponseAuto(
    val `data`: List<String>?,
    val warning_station: Int

)
