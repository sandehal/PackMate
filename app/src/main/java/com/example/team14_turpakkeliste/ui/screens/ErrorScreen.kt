package com.example.team14_turpakkeliste.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.team14_turpakkeliste.R
import com.example.team14_turpakkeliste.ui.theme.WhiteYellow
import com.example.team14_turpakkeliste.ui.viewModel.TurViewModel

/**Funksjonan setter opp en errorScreen når det har dukket opp feil i appen
 * */
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
            text = "Det oppsto en feil!",
            fontSize = 30.sp
        )

        Spacer(modifier = Modifier.height(25.dp))
        Text( modifier = Modifier
            .fillMaxWidth()
            .background(WhiteYellow)
            .wrapContentWidth(Alignment.CenterHorizontally),
            text = "Feilmelding: ${viewModel.error!!}",
            fontSize = 18.sp
        )

        Spacer(modifier = Modifier.height(100.dp))
        Box(modifier = Modifier
            .fillMaxWidth().padding(0.dp),
            contentAlignment = Alignment.Center){
            Text( modifier = Modifier
                .fillMaxWidth()
                .background(WhiteYellow),
                text = "Der var du uheldig! Start applikasjonen på nytt!",
                fontSize = 20.sp

            )

        }

        Image(painter = painterResource(id = R.drawable.pack_buddy), contentDescription ="jumpscare")
    }
}