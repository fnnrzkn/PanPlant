package com.irfannurrizki.panplant.appui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.irfannurrizki.panplant.appdata.applocal.PlantEntity
import com.irfannurrizki.panplant.appnavigation.PlantScreen
import kotlinx.coroutines.launch

@Composable
fun PlantContentItem(
    listPlant: List<PlantEntity>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    onUpdateFavoritePlant: (id: Int, isFavorite: Boolean) -> Unit,
) {
    LazyColumn {
        items(listPlant, key = { it.name }) { plant ->
           PlantItem(plant,scaffoldState,navController, onUpdateFavoritePlant)
        }
    }
}

@Composable
fun PlantItem(
    plant: PlantEntity,
    scaffoldState: ScaffoldState,
    navController: NavController,
    onUpdateFavoritePlant: (id: Int, isFavorite: Boolean) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val (id, name,description, photoUrl,categories, isFavorite) = plant

    Card(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.small)
            .border(2.dp, Color.Magenta.copy(0.7f), MaterialTheme.shapes.small)
            .clickable { navController.navigate(PlantScreen.Detail.createRoute(id ?: 0)) }
            .background(Color.Cyan)
    ) {
        Column {
            Box {
                AsyncImage(
                    model = photoUrl,
                    contentDescription = name,
                    contentScale = ContentScale.FillWidth,
                    placeholder = painterResource(com.irfannurrizki.panplant.R.drawable.image_placeholder),
                    modifier = Modifier.fillMaxWidth()
                )
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(bottomEnd = 8.dp))
                        .background(Color.Green)
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = categories,
                            style = MaterialTheme.typography.body1,
                            color = Color.Black
                        )
                    }
                }
            }

            Column(modifier = Modifier.padding(18.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .background(Color.Cyan),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.h5,
                        color = Color.Blue
                    )

                    Icon(
                        imageVector = if (isFavorite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                        tint = if (isFavorite) Color.Red else MaterialTheme.colors.onSurface,
                        contentDescription = name,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(100))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) {
                                onUpdateFavoritePlant(id ?: 0, !isFavorite)
                                coroutineScope.launch {
                                    scaffoldState.snackbarHostState.showSnackbar(
                                        message = "$name ${if (isFavorite) "removed from" else "added as"} favorite ",
                                        actionLabel = "Got it",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            },
                    )
                }
            }
        }
    }
}