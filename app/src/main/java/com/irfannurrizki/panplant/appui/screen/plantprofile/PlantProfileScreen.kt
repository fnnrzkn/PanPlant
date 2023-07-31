package com.irfannurrizki.panplant.appui.screen.plantprofile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.irfannurrizki.panplant.R

@Composable
fun PlantProfileScreen () {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(R.drawable.irfannurrizki), contentDescription = null, modifier = Modifier
                .size(235.dp)
                .clip(
                    RectangleShape
                ))

            Spacer(modifier = Modifier.height(18.dp))

            Text(text = "Irfan Nur Rizki", style = MaterialTheme.typography.h3)
            Text(text = "nurrizkiirfan@gmail.com", color = Color.Magenta)
        }
    }
}