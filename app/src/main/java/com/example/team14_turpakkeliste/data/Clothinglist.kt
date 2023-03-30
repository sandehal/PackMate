package com.example.team14_turpakkeliste.data

fun getClothes(): List<Clothing>{
    var clothingList: List<Clothing> = listOf(
    Clothing("Shell", "jacket","outer", 0, 5, 5, "ShellJO"),
    Clothing("Shell", "jacket", "outer", 0,5,5, "ShellPO" ),
    Clothing("Down", "jacket", "outer", 4,2,2, "DownJO"),
    Clothing("Softshell", "jacket", "outer", 1, 2,5,"SoftJO"),
    Clothing("Softshell", "pants", "outer", 1,2,5, "SoftPO"))
    return  clothingList
}