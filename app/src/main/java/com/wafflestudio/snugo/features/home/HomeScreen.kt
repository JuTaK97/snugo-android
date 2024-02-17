package com.wafflestudio.snugo.features.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.PathOverlay
import com.naver.maps.map.compose.PolygonOverlay
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import kotlinx.coroutines.launch

enum class HomePageMode {
    NORMAL,
    MOVING,
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun HomeScreen(
    modifier: Modifier,
    pageMode: HomePageMode,
    path: List<LatLng>,
    startMoving: () -> Unit,
) {
    val cameraPositionState = rememberCameraPositionState()
    val scope = rememberCoroutineScope()

    var department by remember { mutableStateOf(Department.entries[0]) }
    var pathCoords by remember {
        mutableStateOf(emptyList<LatLng>())
    }

    Column(
        modifier = modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        NaverMap(
            modifier = Modifier.weight(0.7f),
            cameraPositionState = cameraPositionState,
            onMapClick = { _, latLng ->
                pathCoords = (pathCoords + latLng)
            },
            locationSource = rememberFusedLocationSource(),
            properties = MapProperties(locationTrackingMode = LocationTrackingMode.Follow),
            uiSettings = MapUiSettings(isLocationButtonEnabled = true),
        ) {
            if (path.size >= 2) {
                PathOverlay(
                    coords = path,
                    width = 5.dp,
                    outlineWidth = 2.dp,
                    color = Color.Red,
                    outlineColor = Color.Green,
                )
            }

            if (pathCoords.size >= 2) {
                PathOverlay(
                    coords = pathCoords,
                    width = 2.dp,
                    color = Color.Red,
                )
            }

            PolygonOverlay(
                coords = polygonMap[department]!!,
                color =
                    department.color().copy(
                        alpha = 0.4f,
                    ),
            )
        }
        Row(modifier = Modifier.weight(0.3f)) {
            Text(
                text = "기록 시작",
                modifier =
                    Modifier
                        .padding(15.dp)
                        .clickable {
                            if (pageMode == HomePageMode.NORMAL) {
                                startMoving()
                            }
                        },
                style =
                    TextStyle(
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                    ),
            )
            DepartmentPicker(
                initialIndex = 0,
                onItemSelected = { index ->
                    scope.launch {
                        val latLngs = polygonMap[Department.entries[index]]!!
                        department = Department.entries[index]
                        cameraPositionState.animate(
                            CameraUpdate.fitBounds(
                                LatLngBounds.from(
                                    latLngs + latLngs.first(),
                                ),
                                40,
                            ),
                            animation = CameraAnimation.Fly,
                            durationMs = 1000,
                        )
                    }
                },
            )
        }
    }
}
