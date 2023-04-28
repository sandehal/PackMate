package com.example.team14_turpakkeliste.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.team14_turpakkeliste.R
import com.example.team14_turpakkeliste.TurViewModel
import com.example.team14_turpakkeliste.ui.theme.WhiteYellow


@Composable
fun ErrorScreen(viewModel: TurViewModel){

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(WhiteYellow)
            .wrapContentWidth(Alignment.CenterHorizontally)
            .padding(20.dp)
    ){
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(WhiteYellow)
                .wrapContentWidth(Alignment.CenterHorizontally),
            text = "Ingen forbindelse:",
            fontSize = 30.sp
        )
        Spacer(modifier = Modifier.height(25.dp))
        Text( modifier = Modifier
            .fillMaxWidth()
            .background(WhiteYellow)
            .wrapContentWidth(Alignment.CenterHorizontally),
            text = viewModel.error!!,
            fontSize = 15.sp
        )
        Spacer(modifier = Modifier.height(100.dp))
        Box(modifier = Modifier
            .fillMaxWidth().padding(0.dp),
            contentAlignment = Alignment.Center){
            Text( modifier = Modifier
                .fillMaxWidth()
                .background(WhiteYellow),
                text = "Kan ikke legge til nye pakklister uten internettforbindelse!\nDumrian...",
                fontSize = 20.sp

            )

        }

        Image(painter = painterResource(id = R.drawable.pack_buddy), contentDescription ="jumpscare")
    }
}