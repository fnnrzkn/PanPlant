package com.irfannurrizki.panplant.appui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.irfannurrizki.panplant.PlantMainScreen
import com.irfannurrizki.panplant.R
import com.irfannurrizki.panplant.ui.theme.PanPlantTheme

@Composable
fun PlantContentEmpty(){
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(text = stringResource(R.string.data_empty))
    }
}

