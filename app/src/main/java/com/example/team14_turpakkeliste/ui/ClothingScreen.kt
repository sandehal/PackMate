package com.example.team14_turpakkeliste

import android.content.Context
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.navigation.NavController
import com.example.team14_turpakkeliste.data.Clothing
import com.example.team14_turpakkeliste.data.MaxRequirementsClothes
import com.example.team14_turpakkeliste.data.MinRequirementsClothes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClothingScreen(clothing: List<Clothing>, navController: NavController){
    Spacer(modifier = Modifier.height(10.dp))
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 30.dp)){
        val liste = clothing
        val recommendedList = sortClothing(liste)
        items(recommendedList){
                recommendedList ->
            val title = "${recommendedList.material}${recommendedList.type}"
            val description = "Varme: ${recommendedList.warmth}\nVindtetthet: ${recommendedList.windproof} \nVanntetthet: ${recommendedList.waterproof}"
            val image= recommendedList.image
            ExpandableCard(title = title, description = description, img = image)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExpandableCard(
    title: String,
    titleFontSize: TextUnit = MaterialTheme.typography.titleMedium.fontSize,
    titleFontWeight: FontWeight = FontWeight.Bold,
    description: String,
    descriptionFontSize: TextUnit = MaterialTheme.typography.bodyMedium.fontSize,
    descriptionFontWeight: FontWeight = FontWeight.Normal,
    descriptionMaxLines: Int = 4,
    padding: Dp = 12.dp,
    img: String
) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),

        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier
                        .weight(6f),
                    text = title,
                    fontSize = titleFontSize,
                    fontWeight = titleFontWeight,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                IconButton(
                    modifier = Modifier
                        .weight(1f)
                        .rotate(rotationState),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }
            }
            if (expandedState) {
                val image = getImg(desc = img)
                Image(painter = image, contentDescription = "picture of clothing-piece")
                Text(
                    text = description,
                    fontSize = descriptionFontSize,
                    fontWeight = descriptionFontWeight,
                    maxLines = descriptionMaxLines,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
fun sortClothing(jsonClothesList: List<Clothing>): List<Clothing>{
    // Ta imot værdata og få som ouput outerReqMin, outerReqMax, innerReqMin og innerReqMax
    // Legg ved en boolean f.eks som sier om det er nedbør, kan være viktig for valg av klær, dersom man trenger varme, men ikke fra ytterlag.
    // Da verdsettes f.eks vannavstøtende kvaliteter, og et innerlag verdsetter høyere varme.

    // evt ha en minimumsvarme som må nås med alle lag sin varme kombinert
    val outerReqMin = MinRequirementsClothes(1,2,4)
    // disse to verdiene kan også gjelde for sko/fottøy
    val outerReqMax = MaxRequirementsClothes(2,3, 5)
    val innerReqMin = MinRequirementsClothes(2,1,3)
    val tempList: MutableList<Clothing> = mutableListOf()
    //val innerRequirement: MinRequirementsClothes = MinRequirementsClothes(3,3,3)
    for(clothing in jsonClothesList){
        val warmth: Int = clothing.warmth.toInt()
        if(warmth >= outerReqMin.warmth
            && warmth <= outerReqMax.warmth
            && clothing.type == "jacket"
            && clothing.layer == "outer"){
            tempList.add(clothing)
            continue
        }
        if(warmth >= outerReqMin.warmth
            && warmth <= outerReqMax.warmth
            && clothing.type == "pants"
            && clothing.layer == "outer"){
            tempList.add(clothing)
            continue
        }
        if(warmth >= outerReqMin.warmth
            && warmth <= outerReqMax.warmth
            && clothing.type == "jacket"
            && clothing.layer == "inner"){
            tempList.add(clothing)
            continue
        }
        if(warmth >= outerReqMin.warmth
            && warmth <= outerReqMax.warmth
            && clothing.type == "pants"
            && clothing.layer == "inner"
            && tempList.add(clothing))
            continue
    }
    return tempList
}
fun chooseReqs(temp: Int, wind: Int, water: Int){
    var warmth: Int
    var windproof: Int
    var waterproof: Int
    when(temp){
        in -40..-21 -> warmth = 5
        in -20..-11 -> warmth = 4
        in -10..-1 -> warmth = 3
        in 0..9 -> warmth = 2
        in 10..19 -> warmth = 1
        in 20..40 -> warmth = 0
    }
    when(wind){
        //finne ut verdi for hvordan vær tolkes
    }

}
@Composable
fun getImg(desc: String): Painter{
    val painter: Painter = when(desc){
        "SoftJO"->painterResource(id = R.drawable.softshelljakke)
        "SoftPO"->painterResource(id = R.drawable.softshellbukse)
        "DownJO"-> painterResource(id = R.drawable.dunjakke)
        "ShellJO"-> painterResource(id = R.drawable.skalljakke)
        //"ShellPO" -> painterResource(id = R.drawable.skallbukse)
        else -> {
            painterResource(id = R.drawable.tursekk_1)
        }
    }
    return painter
}