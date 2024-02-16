package com.wafflestudio.snugo.features.records

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.naver.maps.geometry.LatLng
import com.wafflestudio.snugo.features.onboarding.UserViewModel
import dagger.hilt.android.lifecycle.HiltViewModel

@Composable
fun RecordsScreen(modifier: Modifier = Modifier,
                  viewModel: UserViewModel,
                  navController: NavController,
                  ) {
    val recordState = remember{mutableStateOf(RecordState.BOX)}
    val boxIndex = remember{mutableStateOf(0)}
    val record1 = Record(
        "id",
        "nickname",
        listOf(Building(1), Building(2), Building(3)),
        listOf(Pair(20L, LatLng(37.465, 126.95)), Pair(40L, LatLng(36.466, 127.951))),
        System.currentTimeMillis(),
        6109789L
    )
    val recordList = listOf(record1)
    /*val recordList = viewModel.myRecords.collectAsState().value
    LaunchedEffect(Unit){
        viewModel.getRecord(SortMethod.BASIC)
    }*/
    if(recordState.value == RecordState.BOX){
        LazyColumn{
            itemsIndexed(recordList){
                    index, item ->
                RecordBox(record = item, navController = navController,
                    boxClicked = {
                        boxIndex.value = index
                        recordState.value = RecordState.MAP
                    })
            }
        }
    }
    else {
        Log.d("aaaa","changed")
        RecordMap(path = recordList[boxIndex.value].path.map{ it.second })
    }

}
