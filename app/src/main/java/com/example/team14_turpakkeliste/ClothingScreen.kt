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
import com.example.team14_turpakkeliste.data.showJsonAsList
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit

/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClothingScreen(context: Context){
    var expanded by remember {mutableStateOf(false)}
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
    modifier = Modifier.padding(horizontal = 30.dp)){
        val liste = showJsonAsList(context, "clothing.json")
        items(liste){
            liste ->
            var expandedInfo by remember {mutableStateOf(false)}
            ExposedDropdownMenuBox(
                    expanded = expandedInfo,
                    onExpandedChange = { expandedInfo = !expandedInfo},
                    modifier = Modifier
                        .padding(30.dp)
                        .fillMaxWidth(),
            ) {
            Text(
                text = "${liste.material}${liste.type}",
                modifier = Modifier.
                fillMaxWidth(),
                color = R.color.teal_200
            )
            ExposedDropdownMenu(
                expanded = expandedInfo,
                onDismissRequest = { expandedInfo = false }
            ){
                Card(modifier = Modifier
                    .fillMaxWidth()
                    .height(75.dp)) {
                    Text(text = "${liste.material} ${liste.type}",
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .weight(1F)
                            .padding(top = 20.dp))
                }
            }
        }
            Spacer(modifier = Modifier.height(10.dp))
        }
        item {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded}
            ) {
                Text(
                    text = "Vis værmelding",
                    modifier = Modifier.
                    fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ){
                    Card(modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)) {
                        Text(text = "I dag er det meldt kjøttboller og kos")
                    }
                }
            }
             }

    }
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClothingScreen(context: Context){
    var expanded by remember {mutableStateOf(false)}
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 30.dp)){
        val liste = showJsonAsList(context, "clothing.json")
        items(liste){
                liste ->
            ExpandableCard(title = liste.material, description = liste.layer)
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
    padding: Dp = 12.dp
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


