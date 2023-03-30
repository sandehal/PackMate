package com.example.team14_turpakkeliste.data

abstract class Equipment(){
    abstract fun show_info()
}
data class Clothing(val material: String, val type: String, val layer: String, val warmth: Int, val waterproof: Int, val windproof: Int, val image: String): Equipment(){
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

data class MinRequirementsClothes(val warmth: Int, val waterproof: Int, val windproof: Int)
data class MaxRequirementsClothes(val warmth: Int, val waterproof: Int, val windproof: Int)