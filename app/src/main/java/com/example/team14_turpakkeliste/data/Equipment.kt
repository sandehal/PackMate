package com.example.team14_turpakkeliste.data

abstract class Equipment(){
    abstract fun show_info()
}
data class Clothing(val material: String, val type: String, val layer: String, val warmth: String, val waterproof: String, val windproof: String): Equipment(){
    override fun show_info() {
        TODO("Not yet implemented")
    }
}
data class SleepingSystem(val warmth: String, val type: String, val minTemp: Int): Equipment(){
    override fun show_info() {
        TODO("Not yet implemented")
    }
}

data class SunScreen(val factor: Int)