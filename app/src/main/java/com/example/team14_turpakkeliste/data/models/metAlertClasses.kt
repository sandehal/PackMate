package com.example.team14_turpakkeliste.data.models

data class MetAlertsMain(
    var title: String? = null,
    var description: String? = null,
    var link: String? = null,
    var author: String? = null,
    var category: String? = null,
    var guid: String? = null,
    var pubDate: String? = null
)

data class Alert(
    var language: String? = null,
    var event: String? = null,
    var severity: String? = null,
    var certainty: String? = null,
    var headline: String? = null,
    var description: String? = null,
    var instruction: String? = null,
    var eventAwarenessName: String? = null,
    var awareness_level: String? = null,
    var awarenessSeriousness: String? = null,
    var awareness_type: String? = null,
    var domain: String? = null,
    var areaPolygon: String? = null,
    var eventCode: String? = null
)