

data class ForecastData(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)

data class Geometry(
    val coordinates: List<Number>, //må kanskje være number
    val type: String
)

data class Properties(
    val meta: Meta,
    val timeseries: List<TimeSeries>
)

data class Meta(
    val updated_at: String,
    val units: Units
)

data class Units(
    val air_pressure_at_sea_level: String,
    val air_temperature: String,
    val cloud_area_fraction: String,
    val precipitation_amount: String,
    val relative_humidity: String,
    val wind_from_direction: String,
    val wind_speed: String,
)

data class TimeSeries(
    val time: String,
    val data: Data

)

data class Data(
    val instant: Instant,
    val next_12_hours: Next_12_hours,
    val next_1_hours: Next_1_hours,
    val next_6_hours: Next_6_hours
)

data class Instant(
    val details: Details
)

data class Details(
    val air_pressure_at_sea_level: Number,
    val air_temperature:  Number,
    val cloud_area_fraction:  Number,
    val precipitation_amount:  Number,
    val relative_humidity:  Number,
    val wind_from_direction: Number,
    val wind_speed: Number
)

data class Next_12_hours(
    val summary: Summary,
    val details: Details?
)

data class Summary(
    val symbol_code: String
)

data class Next_1_hours(
    val summary: Summary,
    val details: Details?
)

data class Next_6_hours(
    val summary: Summary,
    val details: Details?
)
