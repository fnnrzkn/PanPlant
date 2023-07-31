package com.irfannurrizki.panplant.appui.screen.plantdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.irfannurrizki.panplant.PlantMainScreen
import com.irfannurrizki.panplant.PlantUIState
import com.irfannurrizki.panplant.appdata.applocal.PlantEntity
import com.irfannurrizki.panplant.appui.components.PlantContentError
import com.irfannurrizki.panplant.ui.theme.PanPlantTheme
import kotlinx.coroutines.launch

@Composable
fun PlantDetailScreen(plantId: Int, navController: NavController, scaffoldState: ScaffoldState) {
    val plantDetailViewModel = hiltViewModel<PlantDetailViewModel>()
    plantDetailViewModel.plant.collectAsState(PlantUIState.Loading).value.let { plantUIState ->
        when (plantUIState) {
            is PlantUIState.Loading -> plantDetailViewModel.getPlant(plantId)
            is PlantUIState.Error -> PlantContentError()
            is PlantUIState.Success -> {
                PlantDetailContent(
                    plantUIState.data,
                    navController,
                    scaffoldState,
                    plantDetailViewModel::updateFavoritePlant
                )
            }
        }
    }
}

@Composable
fun PlantDetailContent(
    plant: PlantEntity,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoritePlant: (id: Int, isFavorite: Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val (id, name,description,photoUrl,categories,isFavorite) = plant

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = name,
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(com.irfannurrizki.panplant.R.drawable.image_placeholder),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(320.dp)
                )

                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topEnd = 10.dp))
                        .background(Color.Green)
                        .align(Alignment.BottomStart),
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            text = categories,
                            style = MaterialTheme.typography.body2,
                            color = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                    .background(Color.Cyan),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.h6,
                        color= Color.Blue
                    )

                    Icon(
                        imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        tint = if (isFavorite) Color.Red else MaterialTheme.colors.onSurface,
                        contentDescription = name,
                        modifier = Modifier
                            .size(30.dp)
                            .clip(RoundedCornerShape(100))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onUpdateFavoritePlant(id ?: 0, !isFavorite)
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "$name ${if (isFavorite) "removed from" else "added as"} favorite ",
                                        actionLabel = "Dismiss",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = description,
                    style = MaterialTheme.typography.body2,
                    lineHeight = 24.sp,
                )
            }
        }

        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .size(40.dp)
                .testTag("back_button")
                .background(Color.Green)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = null,
            )
        }
    }
}
