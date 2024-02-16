package com.wafflestudio.snugo.features.records

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.wafflestudio.snugo.navigation.NavigationDestination
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.min

fun convertLongToTime(time: Long): String {
    val date = Date(time)
    val format = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
    return format.format(date)
}
fun durationToString(duration: Long): String{
    val second = (duration / 1000) % 60
    var minute = (duration / 1000) / 60
    val hour = minute / 60
    minute %= 60
    return if(hour == 0L) {

        if(minute == 0L){
            second.toString() + "초"
        }
        else minute.toString() + "분 " + second.toString() + "초"
    }
    else {
        hour.toString() + "시간 " +
                minute.toString() + "분 " +
                second.toString() + "초"
    }

}
@Composable
fun RecordBox (modifier: Modifier = Modifier,
               record: Record,
               navController: NavController,
               boxClicked : ()-> Unit){

    Box(modifier = Modifier.background(Color.Yellow).padding(12.dp)
        .fillMaxWidth().border(2.dp, Color.Magenta, RoundedCornerShape(10.dp))
        .clickable {
            boxClicked()
            //navController.navigate(NavigationDestination.RecordMap.route)
        }){
        Column(
            modifier = Modifier
        ){
            val startTime = convertLongToTime(record.startTime)
            val duration = durationToString(record.duration)
            /*Text(record.id)
            Text(record.nickname)*/
            Row{
                record.buildings.mapIndexed{ index, building ->
                    var addText = "동 -> "
                    if(index + 1 == record.buildings.size){
                        addText = "동"
                    }
                    Text(building.id.toString() + addText)
                }
            }
            Row{
                Text(startTime)
                Text(duration)
            }

            /*record.path.map{
                Row(){
                    Text(it.first.toString() + " : ")
                    Text(it.second.lat.toString() + ", "  + it.second.lng.toString())
                }
            }*/
        }
    }

}